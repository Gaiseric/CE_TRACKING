package com.cositos.cetracking.views.cetracker;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;

import java.util.ArrayList;

import com.cositos.cetracking.Application;
import com.cositos.cetracking.datos.info.Distributions;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class DistributionForm extends FormLayout {
    private Distributions distributions;
    static ArrayList<String> DistributionsList= new ArrayList<>();
    static ComboBox<String> distribution = new ComboBox<>("Distributions");
    static ComboBox<String> connectedto = new ComboBox<>("Connectedto");
    TextField cost = new TextField("Cost");
    Binder<Distributions> binder = new BeanValidationBinder<>(Distributions.class); 
    
    
    Button save= new Button("Save");
    Button delete= new Button("Delete");
    Button cancel= new Button("Cancel");


    public DistributionForm() {
        addClassName("Package-form");
      binder.bindInstanceFields(this);
      distribution.setItems(DistributionsList);
      connectedto.setItems(DistributionsList);
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

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, distributions)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }
    
    private void validateAndSave() {
        try{
            binder.writeBean(distributions);
            fireEvent(new SaveEvent(this, distributions));
        } catch (ValidationException e) {
            Notification.show("Please fill all spaces");
        }
    }

    public static void setList(ArrayList<String> List) {
        DistributionsList= List;
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
    

