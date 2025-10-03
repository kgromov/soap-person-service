package org.kgromov;

import org.kgromov.ws.server.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

public class Client {
    public static void main(String[] args) throws MalformedURLException, DatatypeConfigurationException {
        URL url = URI.create("http://localhost:8080/ws/PersonService?wsdl").toURL();

        var personService = new PersonService(url);
        PersonServicePortType personServicePort = personService.getPersonServicePort();
        var createPersonRequest = buildCreatePersonRequest();
        var createPersonResponse = personServicePort.createPerson(createPersonRequest);
        System.out.println("createPerson response: " + personToString(createPersonResponse.getPerson()));
        GetPersonRequest getPersonRequest = new GetPersonRequest();
        getPersonRequest.setId(createPersonResponse.getPerson().getId());
        GetPersonResponse getPersonResponse = personServicePort.getPerson(getPersonRequest);
        System.out.println("getPerson response: " + personToString(getPersonResponse.getPerson()));

        UpdatePersonRequest updatePersonRequest = new UpdatePersonRequest();
        updatePersonRequest.setPerson(getPersonResponse.getPerson());
        updatePersonRequest.getPerson().setFirstName("Ser John");
        UpdatePersonResponse updatePersonResponse = personServicePort.updatePerson(updatePersonRequest);
        System.out.println("updatePerson response: " + personToString(updatePersonResponse.getPerson()));

        DeletePersonRequest deletePersonRequest = new DeletePersonRequest();
        deletePersonRequest.setId(updatePersonResponse.getPerson().getId());
        personServicePort.deletePerson(deletePersonRequest);
        System.out.println("deletePerson response: " + personToString(personServicePort.getPerson(getPersonRequest).getPerson()));
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

    private static String personToString(Person person) {
        if (person == null) {
            return "null";
        }
        return "Person{" +
                "id=" + person.getId() +
                ", firstName='" + person.getFirstName() + '\'' +
                ", lastName='" + person.getLastName() + '\'' +
                ", gender='" + person.getGender() + '\'' +
                ", age=" + person.getAge() +
                ", birthDate=" + person.getBirthDate() +
                '}';
    }
}
