package com.cositos.cetracking.views.cetracker;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CETracker")
@Route(value = "")
public class CETrackerView extends VerticalLayout {
    private TextField name;

    public CETrackerView() {

        add(new H1("Somos cositos"));

        Button Enviarbtn = new Button("Enviar");

        name = new TextField("Insert your name");

        HorizontalLayout hl = new HorizontalLayout(name, Enviarbtn);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        Enviarbtn.addClickListener(click-> Actuar());
        add(hl);

        //setSpacing(false);
        //Image img = new Image("images/empty-plant.png", "placeholder plant");
        //img.setWidth("200px");
        //add(img);

        //add(new H2("This place intentionally left empty"));
        //add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        //setSizeFull();
        //setJustifyContentMode(JustifyContentMode.CENTER);
        //setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //getStyle().set("text-align", "center");
    }

    public void Actuar() {
        Notification.show("Hello, " + this.name.getValue());
        System.out.println("Hello, " + this.name.getValue());
    }
}
