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
/**
 * It's a view that shows a grid of Distributions, and when you click on one of them, it shows a form
 * to edit it
 */
@Route(value = "graph", layout = MainLayout.class)
@PageTitle("Graph")
public class GraphView extends VerticalLayout{
    Grid<Distributions> DistributionsGrid= new Grid<>(Distributions.class);
    TextField filterText= new TextField();
    DistributionForm form;
    DistributionService service;
    
    // It's a view that shows a grid of Distributions, and when you click on one of them, it shows a
    // form
    //  * to edit it
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

    /**
     * It sets the package to null, sets the form to invisible, and removes the class name "editing"
     */
    private void closeEditor() {
        form.setPackage(null);
        form.setVisible(false);
        removeClassName("editing");

    }

    /**
     * The function updateList() is a private function that updates the list of items in the
     * DistributionsGrid.
     */
    private void updateList() {
        DistributionsGrid.setItems(service.findAllDistributions(filterText.getValue()));
    }

    /**
     * "This function returns a HorizontalLayout object that contains a DistributionsGrid object and a
     * form object. The DistributionsGrid object is given a flexGrow of 2 and the form object is given
     * a flexGrow of 1. The content object is given a size of full and a class name of content."
     * 
     * The above function is called in the following function:
     * 
     * // Java
     * private Component getAppLayout() {
     *         return new HorizontalLayout(new DrawerMenu(), getContent());
     *     }
     * 
     * @return A HorizontalLayout object.
     */
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(DistributionsGrid, form);
        content.setFlexGrow(2, DistributionsGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    /**
     * The configureForm() function creates a new DistributionForm object, sets its width, and adds
     * listeners for the SaveEvent, DeleteEvent, and CloseEvent.
     */
    private void configureForm() {
        form = new DistributionForm();
        form.setWidth("25em");

        form.addListener(DistributionForm.SaveEvent.class, this::saveDistribution);
        form.addListener(DistributionForm.DeleteEvent.class, this::deleteDistribution);
        form.addListener(DistributionForm.CloseEvent.class, e-> closeEditor());
    }

    /**
     * The function saves the distribution and updates the list
     * 
     * @param event The event that was fired.
     */
    private void saveDistribution(DistributionForm.SaveEvent event) {
        service.saveDistribution(event.getDistribution());
        updateList();
        closeEditor();
    }

    /**
     * The function deletes a distribution from the database and updates the list of distributions
     * 
     * @param event The event that was fired.
     */
    private void deleteDistribution(DistributionForm.DeleteEvent event) {
        service.deleteDistribution(event.getDistribution());
        updateList();
        closeEditor();
    }

    /**
     * It creates a toolbar with a text field and a button.
     * 
     * @return A Component
     */
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

    /**
     * If the user clicks the add button, the grid will clear any selected items and create a new
     * distribution.
     */
    private void addDistribution() {
        DistributionsGrid.asSingleSelect().clear();
        createDistribution(new Distributions());
    }

    /**
     * It adds a class name to the grid, sets the size to full, sets the columns, and sets the columns
     * to auto width
     */
    private void configureGrid() {
        DistributionsGrid.addClassName("datos-grid");
        DistributionsGrid.setSizeFull();
        DistributionsGrid.setColumns("distribution", "connectedto", "cost");
        DistributionsGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        DistributionsGrid.asSingleSelect().addValueChangeListener(e -> editDistribution(e.getValue()));
    }
    
    /**
     * If the distributions object is null, close the editor. Otherwise, set the package to the
     * distributions object and make the form visible
     * 
     * @param distributions This is the object that is being edited.
     */
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

    /**
     * If the distribution is null, close the editor. Otherwise, set the package to the distribution
     * and make the form visible.
     * 
     * @param distributions the object that is being edited
     */
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

