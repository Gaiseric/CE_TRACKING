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

    public void setPackage(Distributions distributions) {
        this.distributions= distributions;
        binder.readBean(distributions);
    }

    public void changeBooleanToFalse(){
      this.edit=false;
    }

    public void changeBooleanToTrue(){
      this.edit=true;
    }

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
    public static abstract class DistributionsFormEvent extends ComponentEvent<DistributionForm> {
        private Distributions distributions;
    
        protected DistributionsFormEvent(DistributionForm source, Distributions distributions) { 
          super(source, false);
          this.distributions = distributions;
        }
    
        public Distributions getDistribution() {
          return distributions;
        }
    }

      public static class SaveEvent extends DistributionsFormEvent {
        SaveEvent(DistributionForm source, Distributions distributions) {
            super(source, distributions);
        }
      }

      public static class DeleteEvent extends DistributionsFormEvent {
        DeleteEvent(DistributionForm source, Distributions distributions) {
          super(source, distributions);
        }
    
      }

      public static class CloseEvent extends DistributionsFormEvent {
        CloseEvent(DistributionForm source) {
          super(source, null);
        }
      }

      public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) { 
        return getEventBus().addListener(eventType, listener);
      }
}
    

