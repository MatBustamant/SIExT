/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.grupocapa8.siext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Matias
 */
public class SIExT {

    public static final URI BASE_URI = URI.create("http://localhost:8080/api");

    public static void main(String[] args) {
        try {
            System.out.println("\"Hello World\" Jersey Example App");

            // Se configura Jackson para serializar a JSON correctamente incluso las fechas
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            
            // Se configura con la clase o el paquete donde están los recursos y con el objectMapper de arriba.
            final ResourceConfig resourceConfig = new ResourceConfig()
                    .packages("com.grupocapa8.siext.controller")
                    .register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));

            // Con esto se configura el arranque del servidor Grizzly
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            
            // Con esto, si la JVM se detiene, nos aseguramos de que el servidor Grizzly cierre todas las conexiones de forma correcta.
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            
            // Con esto arrancamos el servidor Grizzly
            server.start();

            System.out.println(String.format("Application started.\nTry out %s\nStop the application using CTRL+C",
                    BASE_URI));
            
            // Para que no termine la ejecución cuando estas líneas acaban
            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            System.getLogger(SIExT.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}
