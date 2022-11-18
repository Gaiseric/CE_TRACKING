package com.cositos.cetracking.datos.generator;

import java.util.ArrayList;

import com.cositos.cetracking.datos.graph.graphgenerator;
import com.cositos.cetracking.datos.info.Distributions;
import com.cositos.cetracking.datos.repository.DistributionsRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {
    ArrayList<Distributions> contacts= new ArrayList<>();
    graphgenerator generato= new graphgenerator();
    @Bean
    public CommandLineRunner loadData(DistributionsRepository contactRepository) {
        
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (contactRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            generato.Inicio();
            
            contacts= generato.getDistributions();
            
            logger.info("Generated demo data");
            contactRepository.saveAll(contacts);
        };
    }

}