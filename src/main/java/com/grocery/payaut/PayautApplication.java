package com.grocery.payaut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PayautApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayautApplication.class, args);
	}

}
