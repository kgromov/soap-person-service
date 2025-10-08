package org.kgromov;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        // Create the service implementation
        PersonServiceImpl serviceImpl = new PersonServiceImpl();
        
        // Get the endpoint
        Endpoint endpoint = Endpoint.create(serviceImpl);
        
        // Add the interceptor to the handler chain
        List<Handler> handlerChain = endpoint.getBinding().getHandlerChain();
        handlerChain.add(new MessageLogInterceptor());
        endpoint.getBinding().setHandlerChain(handlerChain);
        
        // Publish the endpoint
        endpoint.publish("http://localhost:8080/ws/PersonService");
        
//        System.out.println("SOAP Web Service started at: http://localhost:8080/ws/PersonService");
//        System.out.println("WSDL available at: http://localhost:8080/ws/PersonService?wsdl");
    }
}
