package com.cositos.cetracking.views.cetracker;

import java.util.ArrayList;
import java.util.Hashtable;

import com.cositos.cetracking.ListaEnlazadas.Linked_List;
import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Packages;
import com.cositos.cetracking.datos.service.PackageService;
import com.cositos.cetracking.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * This class is the main view of the application, it contains the grid of packages, the form to add
 * and edit packages, the toolbar to filter packages and the button to send packages
 */
@PageTitle("CETracker")
@Route(value = "", layout = MainLayout.class)
public class CETrackerView extends VerticalLayout {
    Grid<Packages> PackagesGrid= new Grid<>(Packages.class);
    Linked_List Hexcodes= new Linked_List();
    private TextField name;
    private SendThread send;
    static ListBox<Object> Rutes = new ListBox<>();
    static Packages packages;
    int temp;
    int timeofwaiting;
    TextField filterText= new TextField();
    PackageForm form;
    PackageService service;

    // Creating a new view for the application.
    public CETrackerView(PackageService service, SendThread send) {
        this.service = service;
        this.send= send;
        add(new H1("We are cositos"));

        Button Enviarbtn = new Button("Send");
        Button Sendpack= new Button("Send Packages");

        name = new TextField("Insert your name");

        HorizontalLayout hl = new HorizontalLayout(name, Enviarbtn);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        Enviarbtn.addClickListener(click-> Actuar());
        Sendpack.addClickListener(click -> sendpack());
        

        addClassName("CETracker");
        setSizeFull();

        configureGrid();
        configureForm();
        Rutes.setWidthFull();
        Rutes.setHeightFull();
        Rutes.clear();
        Rutes.setVisible(false);
        add(
            hl,
            getToolbar(),
            getContent(),
            Rutes,
            Sendpack
        );
        temp=3600;
        updateing();
        updateList();
        closeEditor();
    }

    /**
     * It sets the package to null, sets the form to invisible, and removes the class name "editing"
     */
    private void closeEditor() {
        form.setPackage(null);
        form.setVisible(false);
        removeClassName("editing");

    }

    /**
     * The function updates the list of packages in the grid by calling the findAllPackages function in
     * the service class and passing it the value of the filterText field.
     */
    private void updateList() {
        PackagesGrid.setItems(service.findAllPackages(filterText.getValue()));
    }

    /**
     * "This function returns a HorizontalLayout object that contains a Grid and a Form object, and
     * sets the size of the Grid to be twice as large as the Form."
     * 
     * The first line of the function creates a HorizontalLayout object called "content" and adds the
     * Grid and Form objects to it.
     * 
     * The second line of the function sets the Grid to be twice as large as the Form.
     * 
     * The fourth line of the function adds a CSS class called "content" to the HorizontalLayout object.
     * 
     * 
     * The fifth line of the function sets the size of the HorizontalLayout object to be full.
     * 
     * The last line of the function returns the HorizontalLayout object.
     * 
     * @return A Component
     */
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(PackagesGrid, form);
        content.setFlexGrow(2, PackagesGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    /**
     * The configureForm() function creates a new PackageForm object, sets its width, and adds
     * listeners for the SaveEvent, ConfigureEvent, DeleteEvent, and CloseEvent.
     */
    private void configureForm() {
        form = new PackageForm();
        form.setWidth("25em");

        form.addListener(PackageForm.SaveEvent.class, this:: savePackage);
        form.addListener(PackageForm.ConfigureEvent.class, this::configureEvent);
        form.addListener(PackageForm.DeleteEvent.class, this::deletePackage);
        form.addListener(PackageForm.CloseEvent.class, e-> closeEditor());
    }

    /**
     * It saves the package.
     * 
     * @param event The event that was fired.
     */
    private void savePackage(PackageForm.SaveEvent event) {
        service.savesdsdPackage(event.getPackage());
        updateList();
    }

    /**
     * It deletes a package and the hexcodes
     * 
     * @param event The event that was fired.
     */
    private void deletePackage(PackageForm.DeleteEvent event) {
        Hexcodes.eliminateall(event.getPackage().gethexcode());
        service.deletePackage(event.getPackage());
        updateList();
        closeEditor();
    }

    /**
     * It takes a list of objects and a package, and then it calls another function
     * 
     * @param event PackageForm.ConfigureEvent
     */
    private void configureEvent(PackageForm.ConfigureEvent event) {
        packages= event.getPackage();
        ArrayList<Object> rutes= new ArrayList<>();
        String src= packages.getstartingpoint();
        String dtn= packages.getdeliverypoint();
        System.out.println("Incio: " + src + ", Fin: " + dtn);
        rutes= graphgenerator.getrutes(src, dtn);
        System.out.println(rutes);
        if(rutes.size()==0){
            Notification.show("No existe conexion entre los destinos selecionados");
        }else {
            configureRute(rutes, packages);
        }

    }

    /**
     * It creates a toolbar with a text field and a button.
     * 
     * @return A Component object.
     */
    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Codigo");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPackagebtn = new Button("Add Package");
        addPackagebtn.addClickListener(e -> addPackage());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPackagebtn);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /**
     * If the user clicks the add button, the grid will clear any selected items and then open the edit
     * form.
     */
    private void addPackage() {
        PackagesGrid.asSingleSelect().clear();
        editPackage(new Packages());
    }

    /**
     * It adds a class name to the grid, sets the size to full, sets the columns, and sets the columns
     * to auto width
     */
    private void configureGrid() {
        PackagesGrid.addClassName("datos-grid");
        PackagesGrid.setSizeFull();
        PackagesGrid.setColumns("hexcode", "startingpoint", "deliverypoint", "status");
        PackagesGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        PackagesGrid.asSingleSelect().addValueChangeListener(e -> editPackage(e.getValue()));
    }

    /**
     * It takes an ArrayList of Objects and a Packages object, and then sets the items of a ComboBox to
     * the ArrayList, and sets the ComboBox to visible
     * 
     * @param posiblerutes ArrayList of objects that are the possible routes for the package.
     * @param pack is the package that is being edited
     */
    private  void configureRute(ArrayList<Object> posiblerutes, Packages pack) {
        packages= pack;
        Rutes.clear();
        Rutes.setItems(posiblerutes);
        Rutes.setVisible(true);
        closeEditor();
    }
    
    /**
     * It generates a random hexadecimal code, checks if it's unique, and if it is, it sends the
     * package
     */
    @SuppressWarnings("unchecked")
    private void sendpack() {
        ArrayList<Object> r= (ArrayList<Object>) Rutes.getValue();
        if (r!=null) {
            if (Hexcodes.size()==3600){
                Notification.show("Numero maximo de codigos generados, porfavor elimine paquetes que ya se entregaron");
                System.out.println("Numero maximo de codigos generados, porfavor elimine paquetes que ya se entregaron");
            }else {
                String hexcode;
                Hashtable<Integer, String> valor = new Hashtable<>();
                valor.put(0, "A");
                valor.put(1, "B");
                valor.put(2, "C");
                valor.put(3, "D");
                valor.put(4, "E");
                valor.put(5, "F");
                int num1, num2, num3, num4;
                num1= (int) (Math.random() * 6);
                num2= (int) (Math.random() * 6);
                num3= (int) (Math.random() * 10);
                num4= (int) (Math.random() * 10);
                hexcode= valor.get(num1)+valor.get(num2)+num3+num4;
                while(!Hexcodes.InsertLastUnique(hexcode)){
                    hexcode= valor.get(num1)+valor.get(num2)+num3+num4;
                    if(num4==9){
                        num4=0;
                        num3++;
                    } else {
                        num4++;
                    } if(num3>9){
                        num3=0;
                        num2++;
                    } if(num2>5){
                        num2=0;
                        num1++;
                    } if (num1>5){
                        num1=0;
                    }
                }
                packages.sethexcode(hexcode);
                timeofwaiting= (int) r.get(1);
                Rutes.clear();
                Rutes.setVisible(false);
                packages.setstatus("The package is on the way, time left: " + timeofwaiting);
                form.sendpackages(form, packages);
                packagestate(packages, form, timeofwaiting-1);
                
            }
        } else {
            Notification.show("Selecione una ruta primero para hacer el envio");
        }
    }

    /**
     * It's a recursive function that updates the status of a package every second until the time of
     * waiting is over
     * 
     * @param pack the package that is being sent
     * @param forms the form that the user is using to send the package
     * @param timeofwating the time it takes for the package to be delivered
     */
    private void packagestate(Packages pack, PackageForm forms ,int timeofwating){
        int remaning= timeofwating;
        var ui= UI.getCurrent();
        send.SendAsync().addCallback(e -> {
            ui.access(() -> {
                if (remaning<=0){
                    pack.setstatus("Delivered");
                    forms.sendpackages(forms, pack);
                    this.updateList();
                } else{
                    pack.setstatus("The package is on the way, time left: " + remaning);
                    forms.sendpackages(forms, pack);
                    this.updateList();
                    packagestate(pack, forms, (remaning-1));
                    
                }
            });
        }, err -> {
            ui.access(() -> Notification.show("Error"));
        });
    }

    /**
     * It updates the list, sends a request to the server, and then updates the list again
     */
    private void updateing(){
        var ui= UI.getCurrent();
        updateList();
        send.SendAsync().addCallback(e -> {
            ui.access(() -> {
                updateList();
                if(temp>=0) {
                    temp--;
                    updateing();
                }
                
            });
        }, err -> {
            ui.access(() -> Notification.show("Error"));
        });
    }

    /**
     * If the package is null, close the editor. Otherwise, set the package to the form and make the
     * form visible
     * 
     * @param packages The package that is being edited.
     */
    private void editPackage(Packages packages) {
        if (packages == null){
            closeEditor();
        } else {
            form.setPackage(packages);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    /**
     * The function Actuar() is called when the button is clicked. It shows a notification and prints a
     * message to the console
     */
    public void Actuar() {

        Notification.show("Hello, " + this.name.getValue());
        System.out.println("Hello, " + this.name.getValue());
    }
}
