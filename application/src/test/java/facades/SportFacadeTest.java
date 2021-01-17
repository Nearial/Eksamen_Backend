package facades;

import DTOs.SportDTO;
import entities.Sport;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.SportCreationException;
import errorhandling.exceptions.SportNotFoundException;
import errorhandling.exceptions.UserCreationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportFacadeTest {
    
    private static EntityManagerFactory emf;
    private static SportFacade facade;
    private static Sport sport;
    private static SportDTO sportDTO;
    
    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SportFacade.getSportFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        
        sport = new Sport ("Sportsbold", "Bold brugt i sammenhæng af sport");
        
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
            
            sportDTO = new SportDTO(sport);
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        sport = null;
        sportDTO = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testCreateSport_Success() throws DatabaseException, SportCreationException {
        //Arrange
        sport.setName("Luftbold");
        SportDTO expected = new SportDTO(sport);
        
        //Act
        SportDTO actual = facade.createSport(sport.getName(), sport.getDescription());
        
        //Assert
        assertEquals(expected, actual);      
    }
    
    @Test
    public void testCreateSport_Failed_Sport_Already_In_Use () {
        assertThrows(SportCreationException.class, () -> {
            facade.createSport(sport.getName(), sport.getDescription());
        });
    }
    
    @Test
    public void testGetUserByUserName_Success() throws SportNotFoundException {
        // Arrange
        Sport expected = sport;

        // Act
        Sport actual = facade.getSportByName(sport.getName());

        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetSports() {
        //Arrange
        List<SportDTO> expected = new ArrayList<>();
        expected.add(sportDTO);
        
        //Act
        List<SportDTO> actual = facade.getAllSports();
        
        //Assert
        assertTrue(actual.containsAll(expected));
    }
    
}
