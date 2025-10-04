package org.kgromov;

import org.junit.jupiter.api.Test;
import org.kgromov.ws.server.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest extends AbstractPersonServiceTest {

    @Test
    void testCreatePerson() throws Exception {
        var createPersonRequest = buildCreatePersonRequest();

        var createPersonResponse = personServicePort.createPerson(createPersonRequest);

        assertEquals(1, createPersonResponse.getPerson().getId());
        assertEquals(createPersonRequest.getPerson().getFirstName(), createPersonResponse.getPerson().getFirstName());
        assertEquals(createPersonRequest.getPerson().getLastName(), createPersonResponse.getPerson().getLastName());
        assertEquals(createPersonRequest.getPerson().getGender(), createPersonResponse.getPerson().getGender());
        assertEquals(createPersonRequest.getPerson().getAge(), createPersonResponse.getPerson().getAge());
    }

    @Test
    void testGetPerson() throws Exception {
        var createPersonRequest = buildCreatePersonRequest();
        var createPersonResponse = personServicePort.createPerson(createPersonRequest);
        var getPersonRequest = new GetPersonRequest();
        getPersonRequest.setId(createPersonResponse.getPerson().getId());

        var getPersonResponse = personServicePort.getPerson(getPersonRequest);

        assertEquals(createPersonResponse.getPerson().getId(), getPersonResponse.getPerson().getId());
        assertEquals("John", getPersonResponse.getPerson().getFirstName());
        assertEquals("Doe", getPersonResponse.getPerson().getLastName());
        assertEquals("Male", getPersonResponse.getPerson().getGender());
        assertEquals(30, getPersonResponse.getPerson().getAge());
    }

    @Test
    void testUpdatePerson() throws Exception {
        var updatePersonRequest = new UpdatePersonRequest();
        updatePersonRequest.setPerson(buildCreatePersonRequest().getPerson());
        updatePersonRequest.getPerson().setId(1);
        updatePersonRequest.getPerson().setFirstName("Ser John");

        var updatePersonResponse = personServicePort.updatePerson(updatePersonRequest);

        assertEquals(1, updatePersonResponse.getPerson().getId());
        assertEquals("Ser John", updatePersonResponse.getPerson().getFirstName());
        assertEquals("Doe", updatePersonResponse.getPerson().getLastName());
        assertEquals("Male", updatePersonResponse.getPerson().getGender());
        assertEquals(30, updatePersonResponse.getPerson().getAge());
    }

    @Test
    void testDeletePerson() {
        var deletePersonRequest = new DeletePersonRequest();
        deletePersonRequest.setId(1);

        var deletePersonResponse = personServicePort.deletePerson(deletePersonRequest);

        assertTrue(deletePersonResponse.isSuccess());
        var getPersonRequest = new GetPersonRequest();
        getPersonRequest.setId(1);
        GetPersonResponse getPersonResponse = personServicePort.getPerson(getPersonRequest);
        assertNull(getPersonResponse.getPerson());
    }

    private static CreatePersonRequest buildCreatePersonRequest() throws DatatypeConfigurationException {
        var createPersonRequest = new CreatePersonRequest();
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setGender("Male");
        person.setAge(30);
        person.setBirthDate(getBirthDate());
        createPersonRequest.setPerson(person);
        return createPersonRequest;
    }

    private static XMLGregorianCalendar getBirthDate() throws DatatypeConfigurationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse("1990-01-01", formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }
}
