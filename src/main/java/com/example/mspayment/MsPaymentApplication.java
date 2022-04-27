package com.example.mspayment;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RequiredArgsConstructor
@EnableFeignClients
public class MsPaymentApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsPaymentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



    }
}
