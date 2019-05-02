package com.taklip.jediorder.bean;

public class IdMetaFactory {
	//|Version|Time|Machine Id|Sequence|
	//|*0*|*1-41*|*42-51*|*52-63*|
	private static IdMeta minGranularity = new IdMeta((byte) 1, (byte) 41, (byte) 10, (byte) 12);

	public static IdMeta getIdMeta() {
		return minGranularity;
	}
}