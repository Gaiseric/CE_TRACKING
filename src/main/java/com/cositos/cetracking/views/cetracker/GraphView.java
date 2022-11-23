package com.cositos.cetracking.views.cetracker;

import com.cositos.cetracking.datos.service.DistributionService;
import com.cositos.cetracking.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.cositos.cetracking.datos.info.Distributions;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
@Route(value = "graph", layout = MainLayout.class)
@PageTitle("Graph")
public class GraphView extends VerticalLayout{
    Grid<Distributions> DistributionsGrid= new Grid<>(Distributions.class);
    TextField filterText= new TextField();
    DistributionForm form;
    DistributionService service;
    
    public GraphView(DistributionService service) {
        this.service = service;
        add(new H1("We are cositos"));

        addClassName("CETracker");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
            getToolbar(),
            getContent()
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
        DistributionsGrid.setItems(service.findAllDistributions(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(DistributionsGrid, form);
        content.setFlexGrow(2, DistributionsGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new DistributionForm();
        form.setWidth("25em");

        form.addListener(DistributionForm.SaveEvent.class, this::saveDistribution);
        form.addListener(DistributionForm.DeleteEvent.class, this::deleteDistribution);
        form.addListener(DistributionForm.CloseEvent.class, e-> closeEditor());
    }

    private void saveDistribution(DistributionForm.SaveEvent event) {
        service.saveDistribution(event.getDistribution());
        updateList();
        closeEditor();
    }

    private void deleteDistribution(DistributionForm.DeleteEvent event) {
        service.deleteDistribution(event.getDistribution());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Codigo");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPackagebtn = new Button("Add Distribution");
        addPackagebtn.addClickListener(e -> addDistribution());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPackagebtn);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addDistribution() {
        DistributionsGrid.asSingleSelect().clear();
        createDistribution(new Distributions());
    }

    private void configureGrid() {
        DistributionsGrid.addClassName("datos-grid");
        DistributionsGrid.setSizeFull();
        DistributionsGrid.setColumns("distribution", "connectedto", "cost");
        DistributionsGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        DistributionsGrid.asSingleSelect().addValueChangeListener(e -> editDistribution(e.getValue()));
    }
    
    private void createDistribution(Distributions distributions){
        form.changeBooleanToFalse();
        if (distributions == null){
            closeEditor();
        } else {
            form.setPackage(distributions);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void editDistribution(Distributions distributions) {
        form.changeBooleanToTrue();
        if (distributions == null){
            closeEditor();
        } else {
            form.setPackage(distributions);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}

