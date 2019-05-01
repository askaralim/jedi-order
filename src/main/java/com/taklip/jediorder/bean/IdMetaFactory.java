package com.taklip.jediorder.bean;

public class IdMetaFactory {

	private static IdMeta minGranularity = new IdMeta((byte) 10, (byte) 10, (byte) 40, (byte) 1, (byte) 1, (byte) 1);

	public static IdMeta getIdMeta() {
		return minGranularity;
	}
}
