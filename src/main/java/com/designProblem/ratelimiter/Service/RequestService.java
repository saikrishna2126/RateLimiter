package com.designProblem.ratelimiter.Service;

import com.designProblem.ratelimiter.utility.TockenBucketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestService {
    @Autowired
    TockenBucketManager bucketManager;
    public LocalDateTime getTimeStamp(String userId) throws Exception {
        if(bucketManager.allowRequest(userId)){
            return LocalDateTime.now();
        }else{
            throw new Exception();
        }
    }
}
