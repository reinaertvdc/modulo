package be.lambdaware.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
@ComponentScan({"be.lambdaware.controllers"})
public class ModuloBackendApplication {

    public static void main(String[] args) {
        // launch a new ModuloBackendApplication
        SpringApplication.run(ModuloBackendApplication.class, args);
    }
}
