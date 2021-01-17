package DTOs;

import entities.Sport;
import entities.SportTeam;
import java.util.Objects;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportTeamDTO {

    private String teamName;
    private int pricePerYear, minAge, maxAge;
    private Sport type;
    
    public SportTeamDTO(SportTeam sportTeam) {
        this.teamName = sportTeam.getTeamName();
        this.pricePerYear = sportTeam.getPricePerYear();
        this.minAge = sportTeam.getMinAge();
        this.maxAge = sportTeam.getMaxAge();
        this.type = sportTeam.getType();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPricePerYear() {
        return pricePerYear;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Sport getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.teamName);
        hash = 89 * hash + this.pricePerYear;
        hash = 89 * hash + this.minAge;
        hash = 89 * hash + this.maxAge;
        hash = 89 * hash + Objects.hashCode(this.type);
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
        final SportTeamDTO other = (SportTeamDTO) obj;
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
        return "SportTeamDTO{" + "teamName=" + teamName + ", pricePerYear=" + pricePerYear + ", minAge=" + minAge + ", maxAge=" + maxAge + ", type=" + type + '}';
    }
    
    
    
}
