package org.kgromov;

import jakarta.xml.ws.Endpoint;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.kgromov.ws.server.PersonService;
import org.kgromov.ws.server.PersonServicePortType;

abstract class AbstractPersonServiceTest {

    protected static PersonServicePortType personServicePort;
    private static Endpoint endpoint;

    @BeforeAll
    static void beforeAll() {
        var personService = new PersonService();
        personServicePort = personService.getPersonServicePort();

        endpoint = Endpoint.publish("http://localhost:8080/ws/PersonService", new PersonServiceImpl());
    }

    @AfterAll
    static void afterAll() {
        endpoint.stop();
    }
}
