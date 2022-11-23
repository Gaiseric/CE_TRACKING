package com.cositos.cetracking.datos.info;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cositos.cetracking.datos.service.AbstractEntity;

/**
 * The Distributions class is a JPA entity that represents a distribution
 */
@Entity
public class Distributions extends AbstractEntity{

    @NotEmpty
    private String distribution = "";

    @NotEmpty
    private String connectedto = "";

    @NotNull
    private int cost = 0;


    /**
     * This function returns the distribution of the current object
     * 
     * @return The distribution of the data.
     */
    public String getdistribution() {
        return this.distribution;
    }

    /**
     * This function sets the distribution of the data
     * 
     * @param distribution The distribution of the data.
     */
    public void setdistribution(String distribution) {
		this.distribution = distribution;
	}

    /**
     * This function returns the value of the variable connectedto
     * 
     * @return The value of the connectedto variable.
     */
    public String getconnectedto() {
        return this.connectedto;
    }

    /**
     * This function sets the connectedto variable to the value of the connectedto parameter
     * 
     * @param connectedto The name of the device that the current device is connected to.
     */
    public void setconnectedto(String connectedto) {
        this.connectedto = connectedto;
    }

    /**
     * This function returns the cost of the item
     * 
     * @return The cost of the item.
     */
    public int getcost() {
        return this.cost;
    }

    /**
     * This function sets the cost of the item
     * 
     * @param cost The cost of the item.
     */
    public void setcost(int cost) {
        this.cost = cost;
    }
  
}
