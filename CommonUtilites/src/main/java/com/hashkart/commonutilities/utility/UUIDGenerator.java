package com.hashkart.commonutilities.utility;

import java.util.UUID;

public class UUIDGenerator {

	public static long generateUUID() {
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
}
