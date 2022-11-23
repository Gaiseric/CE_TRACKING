package com.cositos.cetracking.datos.service;
import com.cositos.cetracking.datos.info.Distributions;
import com.cositos.cetracking.datos.repository.DistributionsRepository;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * The DistributionService class is a service class that provides the business logic for the
 * Distribution entity.
 */
@Service
public class DistributionService {
    private final DistributionsRepository distributionsRepository;

    // A constructor.
    public DistributionService(DistributionsRepository distributionsrepository) {

        this.distributionsRepository = distributionsrepository;
    }

    /**
     * If the filterText is null or empty, return all distributions, otherwise return the filtered
     * distributions
     * 
     * @param filterText The text to search for.
     * @return A list of Distributions
     */
    public List<Distributions> findAllDistributions(String filterText) {
        if  (filterText == null || filterText.isEmpty()) {
            return distributionsRepository.findAll();
        } else {
            return distributionsRepository.search(filterText);
        }
    }

    /**
     * It counts the number of distributions in the database
     * 
     * @return The number of distributions in the database.
     */
    public long countDistribution() {
        return distributionsRepository.count();
    }

    /**
     * This function deletes a distribution from the database
     * 
     * @param distributions The Distributions object that you want to delete.
     */
    public void deleteDistribution(Distributions distributions) {
        distributionsRepository.delete(distributions);
    }

    /**
     * It saves the distribution object to the database
     * 
     * @param distributions This is the object that we want to save.
     */
    public void saveDistribution(Distributions distributions) {
        if (distributions == null) {
            System.out.println("Distribution is null");
            return;
        }
        
        distributionsRepository.save(distributions);
    }
}
