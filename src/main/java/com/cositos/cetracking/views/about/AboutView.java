package com.cositos.cetracking.views.about;

import com.cositos.cetracking.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * It's a view that displays a title, a subtitle, a paragraph, and an image
 */
@Route(value = "about", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    // It's a view that displays a title, a subtitle, a paragraph, and an image
    public AboutView() {
        add(new H1("We are cositos"));
        add(new H2("Conformado por: Henry Castro Cosito, Jose Rivera Cosito, Erick Abarco Cosito y Christopher Rodriguez Cosito"));
        add(new H2("#TeamBarboLeiton"));
        add(new Paragraph("Agradecimientos especiales de nuestro querido barón Jason, y a nuestro querido señor Barboza 🥰😍🥳💗💖❣️💕💘"));
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
