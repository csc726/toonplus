package com.toonplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan
@SpringBootApplication
public class ToonPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToonPlusApplication.class, args);
	}

}
