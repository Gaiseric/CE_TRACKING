package com.cositos.cetracking;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.views.cetracker.DistributionForm;
import com.cositos.cetracking.views.cetracker.PackageForm;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "cetracking")
@PWA(name = "CETracking", shortName = "CETracking", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {
    static graphgenerator Generador= new graphgenerator();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        graphgenerator.Inicio();
        DistributionForm.setList(graphgenerator.getList());
        PackageForm.setList(graphgenerator.getList());
    }

   

}
