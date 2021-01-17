package errorhandling.exceptions;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportNotFoundException extends Exception{
    
        public SportNotFoundException(String name) {
        super("Couldn't find sport with name: " + name);
    }
    
}
