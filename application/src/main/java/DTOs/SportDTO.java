package DTOs;

import entities.Sport;

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
    
}
