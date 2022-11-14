package com.cositos.cetracking.datos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cositos.cetracking.datos.info.Packages;
import com.cositos.cetracking.datos.repository.PackagesRepository;

@Service
public class CrmService {
    
    private final PackagesRepository packagesRepository;

    public CrmService(PackagesRepository packagesrepository) {

        this.packagesRepository = packagesrepository;
    }

    public List<Packages> findAllPackages(String filterText) {
        if  (filterText == null || filterText.isEmpty()) {
            return packagesRepository.findAll();
        } else {
            return packagesRepository.search(filterText);
        }
    }

    public long countPackage() {
        return packagesRepository.count();
    }

    public void deletePackage(Packages packages) {
        packagesRepository.delete(packages);
    }

    public void savePackage(Packages packages) {
        if (packages == null) {
            System.out.println("Package is null");
            return;
        }
        
        packagesRepository.save(packages);
    }
    
}
