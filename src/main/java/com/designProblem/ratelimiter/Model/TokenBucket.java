package com.designProblem.ratelimiter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TokenBucket {
    private   int bucketCapacity;
    private  int  refillRate;
    private LocalDateTime lastRefillTime;
    private int currentCapacity;

    public TokenBucket(int bucketCapacity, int refillRate, LocalDateTime lastRefillTime, int currentCapacity){
        this.bucketCapacity = bucketCapacity;
        this.refillRate = refillRate;
        this.lastRefillTime = lastRefillTime;
        this.currentCapacity = currentCapacity;
    }
    public synchronized  boolean allowRequests(){
        LocalDateTime presentTime = LocalDateTime.now();
        Duration duration =  Duration.between(lastRefillTime, presentTime);
        long seconds = duration.getSeconds();
        long refilledCoins = refillRate * seconds;
        if(refilledCoins > 0) {
            lastRefillTime = presentTime;
            currentCapacity = Math.min(currentCapacity + (int) refilledCoins, bucketCapacity);
        }
        if(currentCapacity>0){
            currentCapacity--;
            return true;
        }else{
            return false;
        }

    }

}
