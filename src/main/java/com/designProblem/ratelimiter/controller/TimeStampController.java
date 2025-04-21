package com.designProblem.ratelimiter.controller;

import com.designProblem.ratelimiter.Service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TimeStampController {
    @Autowired
    RequestService service;
    @GetMapping("/getTimeStamp")
    public ResponseEntity<LocalDateTime> getTimeStamp(@RequestParam("id") String userId) throws Exception {
            try{
                LocalDateTime time =  service.getTimeStamp(userId);
                return ResponseEntity.ok(time);
            }catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
            }
    }
}
