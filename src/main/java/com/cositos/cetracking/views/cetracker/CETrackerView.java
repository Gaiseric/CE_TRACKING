package com.cositos.cetracking.views.cetracker;

import java.util.ArrayList;

import com.cositos.cetracking.datos.info.Packages;
import com.cositos.cetracking.datos.service.PackageService;
import com.cositos.cetracking.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CETracker")
@Route(value = "", layout = MainLayout.class)
public class CETrackerView extends VerticalLayout {
    private TextField name;
    Grid<Packages> PackagesGrid= new Grid<>(Packages.class);
    static ListBox<Object> Rutes = new ListBox<>();
    static Packages packages;
    TextField filterText= new TextField();
    PackageForm form;
    PackageService service;

    public CETrackerView(PackageService service) {
        this.service = service;
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
        add(
            hl,
            getToolbar(),
            getContent(),
            Rutes,
            Sendpack
        );

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

        form.addListener(PackageForm.SaveEvent.class, this::savePackage);
        form.addListener(PackageForm.DeleteEvent.class, this::deletePackage);
        form.addListener(PackageForm.CloseEvent.class, e-> closeEditor());
    }

    private void savePackage(PackageForm.SaveEvent event) {
        service.savePackage(event.getPackage());
        updateList();
        closeEditor();
    }

    private void deletePackage(PackageForm.DeleteEvent event) {
        service.deletePackage(event.getPackage());
        updateList();
        closeEditor();
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

    public static void configureRute(ArrayList<Object> posiblerutes, Packages pack) {
        packages= pack;
        Rutes.clear();
        Rutes.setItems(posiblerutes);
    }
    
    @SuppressWarnings("unchecked")
    public void sendpack() {
        ArrayList<Object> r= (ArrayList<Object>) Rutes.getValue();
        packages.sethexcode("Aqui va un codigo random");
        packages.setstatus("En envio pero esto aun no cambia");
        System.out.println(r.get(1));
        form.sendpackages(packages);
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
