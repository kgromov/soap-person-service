package org.kgromov;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.util.Set;

public class MessageLogInterceptor implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logMessage(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logMessage(context);
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // No operation needed
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    private void logMessage(SOAPMessageContext smc) {
        Boolean isRequest = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        String direction = isRequest ? "Outbound" : "Inbound";

        try {
            SOAPMessage message = smc.getMessage();
            System.out.println("=== " + direction + " SOAP Message ===");
            message.writeTo(System.out);
            System.out.println("\n");
        } catch (Exception e) {
            System.err.println("Error logging SOAP message: " + e.getMessage());
        }
    }
}