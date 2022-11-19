package com.cositos.cetracking.views.cetracker;

import java.util.ArrayList;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Packages;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class PackageForm extends FormLayout {
    private Packages packages;
    static ArrayList<String> DistributionsList = new ArrayList<>();

    TextField hexcode = new TextField("Hex Code");
    ComboBox<String> startingpoint = new ComboBox<>("Starting Point");
    ComboBox<String> deliverypoint = new ComboBox<>("Delivery Point");
    TextField status = new TextField("Status");
    Binder<Packages> binder = new BeanValidationBinder<>(Packages.class); 
    
    Button save= new Button("Save");
    Button delete= new Button("Delete");
    Button cancel= new Button("Cancel");
    
    public PackageForm() {

      addClassName("Package-form");
      binder.bindInstanceFields(this);
      startingpoint.setItems(DistributionsList);
      deliverypoint.setItems(DistributionsList);
      add(
          hexcode,
          startingpoint,
          deliverypoint,
          status,
          createButtonLayout()
      );
    }

    public void setPackage(Packages packages) {
        this.packages= packages;
        binder.readBean(packages);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event ->posiblerutes());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, packages)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }
    
    private void validateAndSave() {
      //binder.writeBean(packages);
      fireEvent(new SaveEvent(this, packages));
        
    }

    public void posiblerutes() {
      try {
        binder.writeBean(packages);
        ArrayList<Object> rutes= new ArrayList<>();
        String src= packages.getstartingpoint();
        String dtn= packages.getdeliverypoint();
        System.out.println("Incio: " + src + ", Fin: " + dtn);
        rutes= graphgenerator.getrutes(src, dtn);
        CETrackerView.configureRute(rutes, packages);
      } catch (ValidationException e){
        e.printStackTrace();
      }
      
    }

    public void sendpackages(Packages pack) {
      this.packages= pack;
      validateAndSave();
    }
    public static void setList(ArrayList<String> List) {
      DistributionsList= List;
  }

    // Events
    public static abstract class PackageFormEvent extends ComponentEvent<PackageForm> {
        private Packages packages;
    
        protected PackageFormEvent(PackageForm source, Packages packages) { 
          super(source, false);
          this.packages = packages;
        }
    
        public Packages getPackage() {
          return packages;
        }
    }

      public static class SaveEvent extends PackageFormEvent {
        SaveEvent(PackageForm source, Packages packages) {
            super(source, packages);
        }
      }

      public static class DeleteEvent extends PackageFormEvent {
        DeleteEvent(PackageForm source, Packages packages) {
          super(source, packages);
        }
    
      }

      public static class CloseEvent extends PackageFormEvent {
        CloseEvent(PackageForm source) {
          super(source, null);
        }
      }

      public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) { 
        return getEventBus().addListener(eventType, listener);
      }
}
