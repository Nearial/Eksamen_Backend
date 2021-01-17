package rest;

import entities.Role;
import entities.Sport;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static rest.UserResourceTest.BASE_URI;
import static rest.UserResourceTest.startServer;
import utils.EMF_Creator;

/**
 *
 * @author Nikolaj Larsen
 */
public class SportResourceTest {
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Sport sport;
    private static List<Sport> sports;
    
    private static User user;
    private static User admin;
    private static Role role1;
    private static Role role2;
    private static List<Role> roles;
    private static List<Role> adminRole;
    private static String securityToken;
    private static List<User> users;
    
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    @BeforeAll
    public static void setupClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        sports = new ArrayList<>();
        roles = new ArrayList<>();
        adminRole = new ArrayList<>();
        users = new ArrayList<>();
        
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        role1 = new Role("User");
        role2 = new Role("Admin");  
        
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(role1);
            em.persist(role2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterAll
    public static void TearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        
        sport = new Sport("Serie Fiskeri", "Fiskeri Eksplicit til formål af at dræbe fisk");
        roles.add(role1);
        adminRole.add(role1);
        adminRole.add(role2);
        user = new User("userName", "Sven", "Bentsen", "password123", roles);
        admin = new User("admin", "Admin", "Jensen", "1234", adminRole);
        
        users.add(user);
        users.add(admin);
        sports.add(sport);
        
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
        sports.clear();
        roles.clear();
        adminRole.clear();
        users.clear();
        
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Sport.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    private static String login(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(String.format("{userName: \"%s\", password: \"%s\"}", username, password))
                .when().post("/auth/login")
                .then()
                .extract().path("token");
    }
    
    @Test
    public void getSports() {        
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/sport/getSports").then()
                .assertThat()
                .statusCode(200);
    }
}
