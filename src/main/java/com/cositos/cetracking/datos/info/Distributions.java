package com.cositos.cetracking.datos.info;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cositos.cetracking.datos.service.AbstractEntity;

@Entity
public class Distributions extends AbstractEntity{

    @NotEmpty
    private String distribution = "";

    @NotEmpty
    private String connectedto = "";

    @NotNull
    private int cost = 0;


    public String getdistribution() {
        return this.distribution;
    }

    public void setdistribution(String distribution) {
		this.distribution = distribution;
	}

    public String getconnectedto() {
        return this.connectedto;
    }

    public void setconnectedto(String connectedto) {
        this.connectedto = connectedto;
    }

    public int getcost() {
        return this.cost;
    }

    public void setcost(int cost) {
        this.cost = cost;
    }

    
}
