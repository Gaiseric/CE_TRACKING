package com.cositos.cetracking.views.cetracker;

import java.util.ArrayList;
import java.util.Hashtable;

import com.cositos.cetracking.Arduino.Arduino;
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

    private void closeEditor() {
        form.setPackage(null);
        form.setVisible(false);
        removeClassName("editing");

    }

    private void updateList() {
        PackagesGrid.setItems(service.findAllPackages(filterText.getValue()));
    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(PackagesGrid, form);
        content.setFlexGrow(2, PackagesGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }
    private void configureForm() {
        form = new PackageForm();
        form.setWidth("25em");

        form.addListener(PackageForm.SaveEvent.class, this:: savePackage);
        form.addListener(PackageForm.ConfigureEvent.class, this::configureEvent);
        form.addListener(PackageForm.DeleteEvent.class, this::deletePackage);
        form.addListener(PackageForm.CloseEvent.class, e-> closeEditor());
    }
    private void savePackage(PackageForm.SaveEvent event) {
        service.savesdsdPackage(event.getPackage());
        updateList();
    }
    private void deletePackage(PackageForm.DeleteEvent event) {
        service.deletePackage(event.getPackage());
        updateList();
        closeEditor();
    }
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
    private void addPackage() {
        PackagesGrid.asSingleSelect().clear();
        editPackage(new Packages());
    }
    private void configureGrid() {
        PackagesGrid.addClassName("datos-grid");
        PackagesGrid.setSizeFull();
        PackagesGrid.setColumns("hexcode", "startingpoint", "deliverypoint", "status");
        PackagesGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        PackagesGrid.asSingleSelect().addValueChangeListener(e -> editPackage(e.getValue()));
    }
    private  void configureRute(ArrayList<Object> posiblerutes, Packages pack) {
        packages= pack;
        Rutes.clear();
        Rutes.setItems(posiblerutes);
        Rutes.setVisible(true);
        closeEditor();
    }
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
    private void packagestate(Packages pack, PackageForm forms ,int timeofwating){
        int remaning= timeofwating;
        var ui= UI.getCurrent();
        send.SendAsync().addCallback(e -> {
            ui.access(() -> {
                if (remaning<=0){
                    pack.setstatus("Delivered");
                    forms.sendpackages(forms, pack);

                    new Arduino(pack.gethexcode());

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

    private void editPackage(Packages packages) {
        if (packages == null){
            closeEditor();
        } else {
            form.setPackage(packages);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    public void Actuar() {

        Notification.show("Hello, " + this.name.getValue());
        System.out.println("Hello, " + this.name.getValue());
    }
}
