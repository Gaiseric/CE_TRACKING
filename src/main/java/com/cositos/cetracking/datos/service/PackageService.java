package com.cositos.cetracking.datos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cositos.cetracking.datos.info.Packages;
import com.cositos.cetracking.datos.repository.PackagesRepository;

/**
 * The above class is a service class that is used to perform CRUD operations on the database.
 */
@Service
public class PackageService {
    
    private final PackagesRepository packagesRepository;

    // A constructor.
    public PackageService(PackagesRepository packagesrepository) {

        this.packagesRepository = packagesrepository;
    }

    /**
     * If the filterText is null or empty, return all packages, otherwise return the packages that
     * match the filterText
     * 
     * @param filterText The text to search for.
     * @return A list of packages.
     */
    public List<Packages> findAllPackages(String filterText) {
        if  (filterText == null || filterText.isEmpty()) {
            return packagesRepository.findAll();
        } else {
            return packagesRepository.search(filterText);
        }
    }

    /**
     * It counts the number of packages in the database
     * 
     * @return The number of packages in the database.
     */
    public long countPackage() {
        return packagesRepository.count();
    }

    /**
     * This function deletes a package from the database
     * 
     * @param packages The package object that you want to delete.
     */
    public void deletePackage(Packages packages) {
        packagesRepository.delete(packages);
    }

    /**
     * It saves a package to the database
     * 
     * @param packages The package object that is being saved.
     */
    public void savesdsdPackage(Packages packages) {
        if (packages == null) {
            System.out.println("Package is null");
            return;
        }
        
        packagesRepository.save(packages);
    }
    
}
