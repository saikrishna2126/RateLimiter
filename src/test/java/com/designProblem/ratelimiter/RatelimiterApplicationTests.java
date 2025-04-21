package com.designProblem.ratelimiter;

import com.designProblem.ratelimiter.utility.TockenBucketManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class RatelimiterApplicationTests {

	@Autowired
	private TockenBucketManager tokenBucketManager;

	@Test
	void simulateRequestsForUser() throws InterruptedException {
		String userId = "testUser";

		// Simulate 12 rapid requests
		for (int i = 1; i <= 12; i++) {
			boolean allowed = tokenBucketManager.allowRequest(userId);
			System.out.println("Request " + i + " for " + userId + ": " + (allowed ? "Allowed" : "Throttled"));
			Thread.sleep(900); // Optional delay to mimic real requests
		}

		// Wait for refill
		System.out.println("Waiting for token refill...");
		Thread.sleep(60000); // 1 minute

		for (int i = 1; i <= 3; i++) {
			boolean allowed = tokenBucketManager.allowRequest(userId);
			System.out.println("Post-refill Request " + i + ": " + (allowed ? "Allowed" : "Throttled"));
		}
	}

	@Test
	void testConcurrentRequestsFromMultipleUsers() throws InterruptedException {
		int userCount = 5;
		int threadCountPerUser = 5;
		ExecutorService executor = Executors.newFixedThreadPool(userCount * threadCountPerUser);
		CountDownLatch latch = new CountDownLatch(userCount * threadCountPerUser);

		for (int userIndex = 1; userIndex <= userCount; userIndex++) {
			String userId = "user_" + userIndex;

			for (int t = 0; t < threadCountPerUser; t++) {
				executor.execute(() -> {
					boolean allowed = tokenBucketManager.allowRequest(userId);
					System.out.println(Thread.currentThread().getName() + " - " + userId + " Request: " + (allowed ? "Allowed" : "Throttled"));
					latch.countDown();
				});
			}
		}

		latch.await(); // Wait for all threads to complete
		executor.shutdown();
	}



}
