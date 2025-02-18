package com.javainuse.websocket.config;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args)  throws SQLException {
		//Server server = Server.createTcpServer(args).start();
		SpringApplication.run(Application.class, args);
	}
}
