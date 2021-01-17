package DTOs;

import entities.Sport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportDTOTest {
    
    private Sport sport;
    private SportDTO dto;
    
    @BeforeAll
    public static void setupClass() {
    }
    
    @BeforeEach
    public void setUp() {
        sport = new Sport("Gulvbold", "Her sparkes til en bold på et gulv.");
        dto = new SportDTO(sport);
    }
    
    @AfterEach
    public void tearDown() {
        sport = null;
        dto = null;
    }
    
    @Test
    public void testGetSportName() {
        String expected = "Gulvbold";

        String actual = dto.getName();

        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetSportDescription() {
        String expected = "Her sparkes til en bold på et gulv.";

        String actual = dto.getDescription();

        assertEquals(expected, actual);
    }
}
