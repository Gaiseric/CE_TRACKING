package com.cositos.cetracking.views.cetracker;

import java.util.ArrayList;

import com.cositos.cetracking.ListaEnlazadas.Linked_List;
import com.cositos.cetracking.datos.info.Packages;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
/**
 * It's a form that allows the user to create a package, and it has a list of possible starting points
 * and delivery points.
 */
public class PackageForm extends FormLayout {
    private Packages packages;
    static ArrayList<String> DistributionsList = new ArrayList<>();
    static Linked_List Linked= new Linked_List();

    static ComboBox<String> startingpoint = new ComboBox<>("Starting Point");
    static ComboBox<String> deliverypoint = new ComboBox<>("Delivery Point");
    Binder<Packages> binder = new BeanValidationBinder<>(Packages.class); 
    
    Button save= new Button("Save");
    Button delete= new Button("Delete");
    Button cancel= new Button("Cancel");
    
    // It's a constructor that initializes the form.
    public PackageForm() {
      addClassName("Package-form");
      binder.bindInstanceFields(this);
      startingpoint.setItems(DistributionsList);
      deliverypoint.setItems(DistributionsList);
      add(
          startingpoint,
          deliverypoint,
          createButtonLayout()
      );
    }

    /**
     * This function is called when the user clicks on a row in the grid. It takes the selected row and
     * sets the form fields to the values of the selected row.
     * 
     * @param packages The object that is being edited.
     */
    public void setPackage(Packages packages) {
        this.packages= packages;
        binder.readBean(packages);
    }

    /**
     * It creates a button layout with three buttons: save, delete, and cancel
     * 
     * @return A HorizontalLayout with the buttons save, delete and cancel.
     */
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

    /**
     * It takes the data from the form and sends it to the server
     */
    public void posiblerutes() {
      try {
        binder.writeBean(packages);
        fireEvent(new ConfigureEvent(this, packages));
      } catch (ValidationException e){
        Notification.show("Please fill all spaces");
      }
      
    }

    /**
     * It fires an event to the event bus, which is then handled by the event handler
     * 
     * @param sou The source of the event.
     * @param pack is the package object that is being sent to the server
     */
    public void sendpackages(PackageForm sou ,Packages pack) {
      fireEvent(new SaveEvent(sou, pack));
    }

    /**
     * It takes an ArrayList of Strings and sets the DistributionsList variable to that ArrayList
     * 
     * @param List The list of distributions to be displayed in the dropdown menu.
     */
    public static void setList(ArrayList<String> List) {
      DistributionsList= List;
    }

    /**
     * This function takes a linked list as a parameter and sets the Linked_List object Linked to the
     * linked list that was passed in
     * 
     * @param linked The linked list that is being passed in.
     */
    public static void setLinked(Linked_List linked){
      linked.displayList();
      Linked= linked;
    }

    /**
     * It removes the data from the LinkedList and then updates the ComboBoxes
     * 
     * @param data the data to be eliminated
     */
    public static void eliminateformlist(String data){
      if(Linked.eliminate(data)){
        DistributionsList.remove(data);
        startingpoint.clear();
        deliverypoint.clear();
        startingpoint.setItems(DistributionsList);
        deliverypoint.setItems(DistributionsList);
      }
    }

    /**
     * If the data is unique, add it to the linked list and the observable list.
     * 
     * @param data The data to be inserted
     */
    public static void insert(String data){
      if(Linked.InsertLastUnique(data)){
        DistributionsList.add(data);
        startingpoint.clear();
        deliverypoint.clear();
        startingpoint.setItems(DistributionsList);
        deliverypoint.setItems(DistributionsList);
      }
    }

    // Events
    /**
     * The PackageForm class is a UI component that allows the user to edit a Package. It has a Save,
     * Configure, Delete, and Close button. When the user clicks one of these buttons, the PackageForm
     * fires an event
     */
    public static abstract class PackageFormEvent extends ComponentEvent<PackageForm> {
        private Packages packages;

    
        // It's a constructor that initializes the event.
        protected PackageFormEvent(PackageForm source, Packages packages) { 
          super(source, false);
          this.packages = packages;
        }
    
        /**
         * This function returns the package of the class
         * 
         * @return The packages object.
         */
        public Packages getPackage() {
          return packages;
        }
    }

      /**
       * The SaveEvent class extends the PackageFormEvent class and is used to save a package
       */
      public static class SaveEvent extends PackageFormEvent {
        // It's a constructor that initializes the event.
        SaveEvent(PackageForm source, Packages packages) {
            super(source, packages);
        }
      }

      /**
       * > The ConfigureEvent class is a subclass of the PackageFormEvent class
       */
      public static class ConfigureEvent extends PackageFormEvent {
        // It's a constructor that initializes the event.
        ConfigureEvent(PackageForm source, Packages packages) {
          super(source, packages);
        }
      }

      /**
       * The DeleteEvent class is a subclass of the PackageFormEvent class.
       */
      public static class DeleteEvent extends PackageFormEvent {
        // It's a constructor that initializes the event.
        DeleteEvent(PackageForm source, Packages packages) {
          super(source, packages);
        }
    
      }

      /**
       * The CloseEvent class is a subclass of PackageFormEvent
       */
      public static class CloseEvent extends PackageFormEvent {
        // It's a constructor that initializes the event.
        CloseEvent(PackageForm source) {
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
