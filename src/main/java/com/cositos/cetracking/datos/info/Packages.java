package com.cositos.cetracking.datos.info;

import javax.validation.constraints.NotEmpty;
import javax.persistence.Entity;

import com.cositos.cetracking.datos.service.AbstractEntity;

/**
 * The class Packages is a JPA entity class that represents a package
 */
@Entity
public class Packages extends AbstractEntity{
    
    @NotEmpty
    private String hexcode = "";

    @NotEmpty
    private String status = "";

    @NotEmpty
    private String startingpoint = "";

    @NotEmpty
    private String deliverypoint = "";

    /**
     * This function returns the hexcode of the color
     * 
     * @return The hexcode of the color.
     */
    public String gethexcode() {
        return this.hexcode;
    }

    /**
     * This function sets the hexcode of the color
     * 
     * @param hexcode The hexcode of the color.
     */
    public void sethexcode(String hexcode) {
		this.hexcode = hexcode;
	}

    /**
     * This function returns the status of the current user
     * 
     * @return The status of the account.
     */
    public String getstatus() {
        return this.status;
    }

    /**
     * This function sets the status of the user
     * 
     * @param status The status of the order.
     */
    public void setstatus(String status) {
        this.status = status;
    }

    /**
     * This function returns the starting point of the route
     * 
     * @return The starting point of the trip.
     */
    public String getstartingpoint() {
        return this.startingpoint;
    }

    /**
     * This function sets the starting point of the trip
     * 
     * @param startingpoint The starting point of the route.
     */
    public void setstartingpoint(String startingpoint) {
        this.startingpoint = startingpoint;
    }

    /**
     * This function returns the delivery point of the package
     * 
     * @return The deliverypoint.
     */
    public String getdeliverypoint() {
        return this.deliverypoint;
    }

    /**
     * This function sets the delivery point of the package
     * 
     * @param deliverypoint The delivery point of the package.
     */
    public void setdeliverypoint(String deliverypoint) {
        this.deliverypoint = deliverypoint;
    }
  
}
