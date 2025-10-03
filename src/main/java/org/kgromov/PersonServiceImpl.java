package org.kgromov;

import jakarta.jws.WebService;
import org.kgromov.ws.server.*;

import java.util.HashMap;
import java.util.Map;

@WebService(
        serviceName = "PersonService",
        portName = "PersonServicePort",
        targetNamespace = "http://example.com/personservice",
//        wsdlLocation = "classpath:wsdl/PersonService.wsdl",
        endpointInterface = "org.kgromov.ws.server.PersonServicePortType"
)
public class PersonServiceImpl implements PersonServicePortType {

    private static final Map<Integer, Person> persons = new HashMap<>();
    private static int currentId = 1;

    @Override
    public GetPersonResponse getPerson(GetPersonRequest parameters) {
        Person person = persons.getOrDefault(parameters.getId(), null);
        GetPersonResponse personResponse = new GetPersonResponse();
        personResponse.setPerson(person);
        return personResponse;
    }

    @Override
    public CreatePersonResponse createPerson(CreatePersonRequest parameters) {
        Person person = parameters.getPerson();
        person.setId(currentId++);
        persons.put(person.getId(), person);
        CreatePersonResponse response = new CreatePersonResponse();
        response.setPerson(person);
        return response;
    }

    @Override
    public UpdatePersonResponse updatePerson(UpdatePersonRequest parameters) {
        Person person = parameters.getPerson();
        persons.put(person.getId(), person);
        UpdatePersonResponse response = new UpdatePersonResponse();
        response.setPerson(person);
        return response;
    }

    @Override
    public DeletePersonResponse deletePerson(DeletePersonRequest parameters) {
        persons.remove(parameters.getId());
        DeletePersonResponse response = new DeletePersonResponse();
        response.setSuccess(true);
        return response;
    }
}
