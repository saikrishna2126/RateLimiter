package com.designProblem.ratelimiter.utility;

import com.designProblem.ratelimiter.Model.TokenBucket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class TockenBucketManager {

    private final Map<String, TokenBucket> tockenBucketMap = new ConcurrentHashMap<>();
    private final  int bucketCapacity = 5;
    private final  int refillRate = 1;  // per Second

    public synchronized boolean allowRequest(String userId){
        tockenBucketMap.computeIfAbsent(userId,id->new TokenBucket(bucketCapacity,refillRate, LocalDateTime.now(),bucketCapacity));
        return tockenBucketMap.get(userId).allowRequests();
    }

}
