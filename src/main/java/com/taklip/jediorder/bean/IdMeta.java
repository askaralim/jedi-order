package com.taklip.jediorder.bean;

public class IdMeta {
	private byte machineBits;
	private byte sequenceBits;
	private byte timeBits;
	private byte versionBits;

	public IdMeta(byte versionBits, byte timeBits, byte machineBits, byte sequenceBits) {
		this.versionBits = versionBits;
		this.timeBits = timeBits;
		this.machineBits = machineBits;
		this.sequenceBits = sequenceBits;
	}

	public byte getVersionBits() {
		return versionBits;
	}

	public void setVersionBits(byte versionBits) {
		this.versionBits = versionBits;
	}

	public long getVersionBitsMask() {
		return -1L ^ -1L << versionBits;
	}

	public byte getTimeBits() {
		return timeBits;
	}

	public void setTimeBits(byte timeBits) {
		this.timeBits = timeBits;
	}

	public long getTimeBitsStartPos() {
		return versionBits;
	}

	public long getTimeBitsMask() {
		return -1L ^ -1L << timeBits;
	}

	public byte getMachineBits() {
		return machineBits;
	}

	public void setMachineBits(byte machineBits) {
		this.machineBits = machineBits;
	}

	public long getMachineBitsStartPos() {
		return versionBits + timeBits;
	}

	public long getMachineBitsMask() {
		return -1L ^ -1L << machineBits;
	}

	public byte getSequenceBits() {
		return sequenceBits;
	}

	public void setSequenceBits(byte sequenceBits) {
		this.sequenceBits = sequenceBits;
	}

	public long getSequenceBitsStartPos() {
		return versionBits + timeBits + machineBits;
	}

	public long getSequenceBitsMask() {
		return -1L ^ -1L << sequenceBits;
	}
}