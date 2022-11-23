package com.cositos.cetracking.views;

import com.cositos.cetracking.views.cetracker.CETrackerView;
import com.cositos.cetracking.views.cetracker.GraphView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

/**
 * It creates a header and a drawer
 */
public class MainLayout extends AppLayout {
    // Creating the header and the drawer.
    public MainLayout () {
        createHeader();
        createDrawer();
    }

    /**
     * It creates a drawer with a logo and a toggle button.
     */
    private void createDrawer() {
        H1 logo= new H1("Cositos");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header= new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    /**
     * This function creates a header for the application
     */
    private void createHeader() {
        RouterLink packagesview = new RouterLink("Packages", CETrackerView.class);
        packagesview.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            packagesview,
            new RouterLink("Graph", GraphView.class)
        ));

    }

    
}
