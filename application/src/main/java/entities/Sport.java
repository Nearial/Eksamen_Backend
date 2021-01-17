package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Nikolaj Larsen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Sport.getAll", query = "SELECT u FROM Sport u"),
    @NamedQuery(name = "Sport.findSport", query = "SELECT u FROM Sport u WHERE u.name = :name"),
    @NamedQuery(name = "Sport.deleteAllRows", query = "DELETE FROM Sport")})
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany (mappedBy = "type")
    private List<SportTeam> teams;
    
    private String name;
    private String description;

    public Sport(String name, String description) {
        this.name = name;
        this.description = description;
        this.teams = new ArrayList<>();
        
        teams.forEach((SportTeam sportTeam) -> {
            addTeam(sportTeam);
        });
    }
    
    public void addTeam(SportTeam sportTeam) {
        this.teams.add(sportTeam);
        if (sportTeam.getType() != this) {
            sportTeam.setType(this);
        }
    }
    
    public void removeTeam(SportTeam sportTeam) {
        if (teams.contains(sportTeam)) {
            teams.remove(sportTeam);
        }
    }
    
    public Sport() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SportTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<SportTeam> teams) {
        this.teams = teams;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
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
        final Sport other = (Sport) obj;
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
        return "Sport{" + "name=" + name + ", description=" + description + '}';
    }
    
}
