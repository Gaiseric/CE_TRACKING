package com.cositos.cetracking.datos.info;

import javax.validation.constraints.NotEmpty;
import javax.persistence.Entity;

import com.cositos.cetracking.datos.service.AbstractEntity;

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

    public String gethexcode() {
        return this.hexcode;
    }

    public void sethexcode(String hexcode) {
		this.hexcode = hexcode;
	}

    public String getstatus() {
        return this.status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getstartingpoint() {
        return this.startingpoint;
    }

    public void setstartingpoint(String startingpoint) {
        this.startingpoint = startingpoint;
    }

    public String getdeliverypoint() {
        return this.deliverypoint;
    }

    public void setdeliverypoint(String deliverypoint) {
        this.deliverypoint = deliverypoint;
    }

    
}
