package com.example.musica2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Musica2Application {

    private static final Logger logger = LoggerFactory.getLogger(Musica2Application.class);

    public static void main(String[] args) {
        logger.info("Iniciando a aplicação Musica2Application...");
        SpringApplication.run(Musica2Application.class, args);
        logger.info("Aplicação Musica2Application iniciada com sucesso.");
    }
}