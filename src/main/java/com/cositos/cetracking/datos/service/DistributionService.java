package com.cositos.cetracking.datos.service;
import com.cositos.cetracking.datos.info.Distributions;
import com.cositos.cetracking.datos.repository.DistributionsRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DistributionService {
    private final DistributionsRepository distributionsRepository;

    public DistributionService(DistributionsRepository distributionsrepository) {

        this.distributionsRepository = distributionsrepository;
    }

    public List<Distributions> findAllPackages(String filterText) {
        if  (filterText == null || filterText.isEmpty()) {
            return distributionsRepository.findAll();
        } else {
            return distributionsRepository.search(filterText);
        }
    }

    public long countPackage() {
        return distributionsRepository.count();
    }

    public void deletePackage(Distributions distributions) {
        distributionsRepository.delete(distributions);
    }

    public void savePackage(Distributions distributions) {
        if (distributions == null) {
            System.out.println("Package is null");
            return;
        }
        
        distributionsRepository.save(distributions);
    }
}
