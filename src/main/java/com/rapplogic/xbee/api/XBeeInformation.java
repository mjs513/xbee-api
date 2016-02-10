package com.rapplogic.xbee.api;

import com.rapplogic.xbee.api.HardwareVersion.RadioType;

public class XBeeInformation {
	enum XBeeMode {
		COORDINATOR, ROUTER, END_DEVICE, UNKNOWN;
	}
	private final RadioType hardwareVersion;
	private final int firmwareVersion;
	private final long panID;
	private final int serialHigh;
	private final int serialLow;
	private final XBeeMode mode; 
	private long lastReceivedMessage;

	public XBeeInformation(RadioType hardwareVersion, int[] firmwareVersion, int[] panID, int[] serialHigh, int[] seriallow) {
		
		this.hardwareVersion = hardwareVersion;
		
		this.firmwareVersion = firmwareVersion[0] << 8 | firmwareVersion[1];
		
		this.panID = (long)panID[0] << 56 | (long)panID[1] << 48 | (long)panID[2] << 40 | (long)panID[3] << 32 | 
				panID[4] << 24 | panID[5] << 16 | panID[6] << 8 | panID[7];
		
		this.serialHigh = serialHigh[0] << 24 | serialHigh[1] << 16 | serialHigh[2] << 8 | serialHigh[3]; 
		
		this.serialLow = seriallow[0] << 24 | seriallow[1] << 16 | seriallow[2] << 8 | seriallow[3];
		
		this.mode = getModeFromFirmwareVersion(firmwareVersion);
	}

	private XBeeMode getModeFromFirmwareVersion(int[] firmwareVersion) {
		// First byte of the firmware version tells us which mode we're running in
		// in case we're using API mode (which we always should be).
		switch(firmwareVersion[0])	{
		case 0x21:
			return XBeeMode.COORDINATOR;
		case 0x23:
			return XBeeMode.ROUTER;
		case 0x29:
			return XBeeMode.END_DEVICE;
		default:
			return XBeeMode.UNKNOWN;
		}		
	}

	public XBeeMode getMode() {
		return mode;
	}

	public void touchLastReceivedTime() {
		lastReceivedMessage = System.currentTimeMillis();
	}

	public RadioType getHardwareVersion() {
		return hardwareVersion;
	}

	public int getFirmwareVersion() {
		return firmwareVersion;
	}

	public long getPanID() {
		return panID;
	}

	public int getSerialHigh() {
		return serialHigh;
	}

	public int getSerialLow() {
		return serialLow;
	}

	public long getLastReceivedMessage() {
		return lastReceivedMessage;
	}

	@Override
	public String toString() {
		return "XBeeInformation [hardwareVersion=" + hardwareVersion
				+ ", firmwareVersion=" + String.format("%02X", firmwareVersion) + ", panID=" + String.format("%08X", panID)
				+ ", serialHigh=" + String.format("%04X", serialHigh) + ", serialLow=" + String.format("%04X", serialLow)
				+ ", mode " + mode.toString() + "]";
	}

}
