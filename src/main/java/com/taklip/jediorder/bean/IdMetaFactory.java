package com.taklip.jediorder.bean;

public class IdMetaFactory {
	private static IdMeta minGranularity = new IdMeta((byte) 1, (byte) 40, (byte) 10, (byte) 12);

	public static IdMeta getIdMeta() {
		return minGranularity;
	}
}