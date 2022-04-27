package com.example.mspayment.client;

import com.example.mspayment.client.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "user-client" , url = "${client.user.url}" )
public interface UserClient {

    @GetMapping
    UserDto getUserAndBalanceByApiKey(@RequestParam String apiKey, @RequestParam BigDecimal amount);

    @GetMapping("/api-key")
    UserDto getUserByApiKey(@RequestParam String apiKey);

}
