package com.kiwi;

import com.kiwi.services.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.kiwi.*")
@EntityScan(basePackages="com.kiwi.model.*")
@EnableJpaRepositories(basePackages = {"com.kiwi.model.*"})
public class GrpcDemoApplication {

	public static void main(String[] args) throws InterruptedException, IOException {

		SpringApplication.run(GrpcDemoApplication.class, args);
		Server server = ServerBuilder.forPort(8081)
				.addService(new UserService())
				.build();
		server.start();

		server.awaitTermination();
	}
}
