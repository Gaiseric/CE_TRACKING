package com.cositos.cetracking.datos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cositos.cetracking.datos.info.Distributions;


public interface DistributionsRepository extends JpaRepository<Distributions, Integer>{
    
    @Query("select c from Distributions c " +
        "where lower(c.distribution) like lower(concat('%', :searchTerm, '%')) ")
    List<Distributions> search(@Param("searchTerm") String searchTerm);
}
