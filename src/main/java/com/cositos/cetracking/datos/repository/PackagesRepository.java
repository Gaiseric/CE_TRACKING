package com.cositos.cetracking.datos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cositos.cetracking.datos.info.Packages;

public interface PackagesRepository extends JpaRepository<Packages, Integer> {
    
    @Query("select c from Packages c " +
        "where lower(c.hexcode) like lower(concat('%', :searchTerm, '%')) ")
    List<Packages> search(@Param("searchTerm") String searchTerm);
}
