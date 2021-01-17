package DTOs;

import entities.Sport;
import java.util.Objects;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportDTO {
    
    private String name;
    private String description;
    
    public SportDTO (Sport sport){
        this.name = sport.getName();
        this.description = sport.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
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
        final SportDTO other = (SportDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SportDTO{" + "name=" + name + ", description=" + description + '}';
    }
    
}
