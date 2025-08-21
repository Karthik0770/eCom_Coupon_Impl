package com.monk.backend.controller;

import com.monk.backend.dto.Health.HealthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

    @GetMapping("getStatus")
    public ResponseEntity<HealthResponseDto> getStatus(){
        return new ResponseEntity<>(
                new HealthResponseDto(HttpStatus.OK,"Service is up and running!"),
                HttpStatus.OK);
    }
}
