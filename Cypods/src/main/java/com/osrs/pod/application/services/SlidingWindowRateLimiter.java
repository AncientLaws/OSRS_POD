package com.osrs.pod.application.services;

import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class SlidingWindowRateLimiter {

    private static final int MAX_CALLS = 25;
    private static final long WINDOW_MILLIS = 60 * 1000; // 60 seconds

    // Stores the timestamps (in milliseconds) of the last API calls
    private final Deque<Long> callTimestamps = new ConcurrentLinkedDeque<>();

    /**
     * Tries to register a new call.
     * @return true if the call is allowed, false if the rate limit is exceeded.
     */
    public synchronized boolean tryCall() {
        long now = System.currentTimeMillis();

        // Remove timestamps older than the sliding window
        while (!callTimestamps.isEmpty() && now - callTimestamps.peekFirst() > WINDOW_MILLIS) {
            callTimestamps.pollFirst();
        }

        // Allow call if less than MAX_CALLS within the window
        if (callTimestamps.size() < MAX_CALLS) {
            callTimestamps.addLast(now);
            return true;
        } else {
            return false;
        }
    }
}
