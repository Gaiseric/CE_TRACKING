package com.cositos.cetracking.datos.generator;

import java.util.ArrayList;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Distributions;
import com.cositos.cetracking.datos.repository.DistributionsRepository;
import com.cositos.cetracking.views.cetracker.PackageForm;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {
    ArrayList<Distributions> list= new ArrayList<>();
    graphgenerator generato= new graphgenerator();
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