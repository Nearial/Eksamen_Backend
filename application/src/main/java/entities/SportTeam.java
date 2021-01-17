/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Nikolaj Larsen
 */
@Entity
public class SportTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Sport type;
    
    private int pricePerYear, minAge, maxAge;
    private String teamName;
    
    public SportTeam (Sport type ,String teamName, int minAge, int maxAge, int pricePerYear) {
        this.teamName = teamName;
        this.pricePerYear = pricePerYear;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.type = type;
    }
    
    public SportTeam () {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sport getType() {
        return type;
    }

    public void setType(Sport type) {
        if(this.type != null) {
            this.type.removeTeam(this);
        }
        this.type = type;
        if(!type.getTeams().contains(this)) {
            type.getTeams().add(this);
        }
    }

    public int getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(int pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + this.pricePerYear;
        hash = 37 * hash + this.minAge;
        hash = 37 * hash + this.maxAge;
        hash = 37 * hash + Objects.hashCode(this.teamName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SportTeam other = (SportTeam) obj;
        if (this.pricePerYear != other.pricePerYear) {
            return false;
        }
        if (this.minAge != other.minAge) {
            return false;
        }
        if (this.maxAge != other.maxAge) {
            return false;
        }
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SportTeam{" + "type=" + type + ", pricePerYear=" + pricePerYear + ", minAge=" + minAge + ", maxAge=" + maxAge + ", teamName=" + teamName + '}';
    }
    
}
