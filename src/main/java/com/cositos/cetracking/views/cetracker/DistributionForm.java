package com.cositos.cetracking.views.cetracker;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Distributions;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

/**
 * It's a form that allows the user to create a connection between two distribution centers
 */
public class DistributionForm extends FormLayout {
    private Distributions distributions;
    private boolean edit= false;
    static TextField distribution = new TextField("Distributions");
    static TextField connectedto = new TextField("Connected To");
    IntegerField cost = new IntegerField("Cost");
    Binder<Distributions> binder = new BeanValidationBinder<>(Distributions.class); 
    
    
    Button save= new Button("Save");
    Button delete= new Button("Delete");
    Button cancel= new Button("Cancel");


    // Creating the form that will be used to create a connection between two distribution centers.
    public DistributionForm() {
        addClassName("Package-form");
      binder.bindInstanceFields(this);
      add(
        distribution,
        connectedto,
        cost,
        createButtonLayout()
      );
    }

    /**
     * This function is called when the user clicks on a row in the table, and it sets the form fields
     * to the values of the selected row.
     * 
     * @param distributions This is the object that is being edited.
     */
    public void setPackage(Distributions distributions) {
        this.distributions= distributions;
        binder.readBean(distributions);
    }

    /**
     * This function changes the boolean value of the variable edit to false
     */
    public void changeBooleanToFalse(){
      this.edit=false;
    }

    /**
     * This function changes the boolean variable edit to true
     */
    public void changeBooleanToTrue(){
      this.edit=true;
    }

    /**
     * It creates a button layout with three buttons: save, delete, and cancel.
     * </code>
     * 
     * @return A HorizontalLayout with the buttons save, delete and cancel.
     */
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> eliminate());
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    } 
    
    /**
     * It validates the form and saves the data
     */
    private void validateAndSave() {
      try{
        binder.writeBean(distributions);
        String dis= distributions.getdistribution(); 
        String con= distributions.getconnectedto();
        if(dis!=con){
          if(graphgenerator.contains(dis, con)){
            if(this.edit){
              graphgenerator.Modify(dis, con, distributions.getcost());
              PackageForm.insert(dis);
              PackageForm.insert(con);
              fireEvent(new SaveEvent(this, distributions));
            }else{
              fireEvent(new CloseEvent(this));
              Notification.show("Already exist a connection between " + dis + " and  " + con);
            }
          } else{
            graphgenerator.Modify(dis, con, distributions.getcost());
            PackageForm.insert(dis);
            PackageForm.insert(con);
            fireEvent(new SaveEvent(this, distributions));
          }
        }else{
          Notification.show("There cannot be a connection between the same distribution center");
        }
      } catch (ValidationException e) {
        Notification.show("Please fill all spaces");
      }
    }

    /**
     * This function is used to eliminate the edge between two nodes
     */
    private void eliminate() {
      if(this.edit){
        String dis= distributions.getdistribution(); 
        String con= distributions.getconnectedto();
        graphgenerator.EliminateEdge(dis, con);
        PackageForm.eliminateformlist(dis);
        PackageForm.eliminateformlist(con);
        fireEvent(new DeleteEvent(this, distributions));
      }
        
    }
    // Events
    /**
     * The DistributionsFormEvent class is an abstract class that extends the ComponentEvent class. It
     * has a Distributions object as a field
     */
    public static abstract class DistributionsFormEvent extends ComponentEvent<DistributionForm> {
        private Distributions distributions;
    
        // Calling the constructor of the superclass.
        protected DistributionsFormEvent(DistributionForm source, Distributions distributions) { 
          super(source, false);
          this.distributions = distributions;
        }
    
        /**
         * This function returns the distribution of the data
         * 
         * @return The distributions object is being returned.
         */
        public Distributions getDistribution() {
          return distributions;
        }
    }

      /**
       * The SaveEvent class is a subclass of the DistributionsFormEvent class.
       */
      public static class SaveEvent extends DistributionsFormEvent {
        // Calling the constructor of the superclass.
        SaveEvent(DistributionForm source, Distributions distributions) {
            super(source, distributions);
        }
      }

      /**
       * The DeleteEvent class is a subclass of the DistributionsFormEvent class.
       */
      public static class DeleteEvent extends DistributionsFormEvent {
        // Calling the constructor of the superclass.
        DeleteEvent(DistributionForm source, Distributions distributions) {
          super(source, distributions);
        }
    
      }

      /**
       * The CloseEvent class is a subclass of the DistributionsFormEvent class
       */
      public static class CloseEvent extends DistributionsFormEvent {
        // A constructor that calls the constructor of the superclass.
        CloseEvent(DistributionForm source) {
          super(source, null);
        }
      }

      /**
       * > Adds a listener for a specific event type
       * 
       * @param eventType The type of event to listen for.
       * @param listener The listener to be added.
       * @return A Registration object.
       */
      public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) { 
        return getEventBus().addListener(eventType, listener);
      }
}
    

