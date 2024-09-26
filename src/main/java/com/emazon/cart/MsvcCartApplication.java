package com.emazon.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCartApplication.class, args);
	}

}
