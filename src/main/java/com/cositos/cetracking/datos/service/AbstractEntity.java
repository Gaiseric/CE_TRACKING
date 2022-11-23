package com.cositos.cetracking.datos.service;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * This class is a base class for all entities in the system. It provides a unique identifier for each
 * entity
 */
@MappedSuperclass
public abstract class AbstractEntity {
    
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    /**
     * This function returns the id of the object
     * 
     * @return The id of the object.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * This function sets the id of the object to the id passed in as a parameter
     * 
     * @param id The id of the user.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * If the id is not null, return the hashcode of the id, otherwise return the hashcode of the
     * superclass
     * 
     * @return The hash code of the object.
     */
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    /**
     * If the object is not an instance of the class, return false. If the id is not null, return the
     * result of the id.equals(other.id) function. Otherwise, return the result of the
     * super.equals(other) function
     * 
     * @param obj The object to compare to.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;

        if(id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }

}
