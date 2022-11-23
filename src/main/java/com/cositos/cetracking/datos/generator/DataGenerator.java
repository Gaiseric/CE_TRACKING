package com.cositos.cetracking.datos.generator;

import java.util.ArrayList;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Distributions;
import com.cositos.cetracking.datos.repository.DistributionsRepository;
import com.cositos.cetracking.views.cetracker.PackageForm;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

/**
 * It's a class that generates data and saves it to the database.
 */
@SpringComponent
public class DataGenerator {
    ArrayList<Distributions> list= new ArrayList<>();
    graphgenerator generato= new graphgenerator();

    /**
     * The function loadData() is a CommandLineRunner that loads the data from the graphgenerator class
     * and saves it to the database
     * 
     * @param distributionRepository is the repository that I use to save the data in the database.
     * @return A CommandLineRunner object.
     */
    @Bean
    public CommandLineRunner loadData(DistributionsRepository distributionRepository) {
        
        return args -> {
            graphgenerator.Inicio();
            
            list= generato.getDistributions();
            PackageForm.setList(generato.getList());
            PackageForm.setLinked(generato.getLinked());
            distributionRepository.saveAll(list);
        };
    }

}