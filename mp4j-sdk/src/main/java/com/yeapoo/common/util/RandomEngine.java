package com.yeapoo.common.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class RandomEngine {
    private static Random random = new Random();
    private static char[] alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * Select by probability
     * 
     * @param keyProbabilityMap
     *            key is the unique identifier，value is the probability of the
     *            identifier, a number without %.
     * @return the key that has been selected. If no key selected, null is
     *         returned.
     */
    public static String select(Map<String, Integer> keyProbabilityMap) {
        if (keyProbabilityMap == null || keyProbabilityMap.size() == 0) {
            return null;
        }

        Integer sum = 0;
        for (Integer value : keyProbabilityMap.values()) {
            sum += value;
        }
        // Start from 1
        Integer rand = random.nextInt(sum) + 1;

        for (Entry<String, Integer> entry : keyProbabilityMap.entrySet()) {
            rand -= entry.getValue();
            // Got it
            if (rand <= 0) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static int select(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        // get the range, casting to long to avoid overflow problems
        long range = (long) end - (long) start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        return (int) (fraction + start);
    }

    public static final String random(int length) {
        if (length < 1) {
            return null;
        }

        char[] buffer = new char[length];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = alphabet[random.nextInt(61)];
        }
        return new String(buffer);
    }
}