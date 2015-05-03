package com.rapplogic.xbee.api;

import com.rapplogic.xbee.api.HardwareVersion.RadioType;

public class XBeeInformation {
	enum XBeeMode {
		COORDINATOR, ROUTER, END_DEVICE, UNKNOWN;
	}
	private RadioType hardwareVersion;
	private int firmwareVersion;
	private long panID;
	private int serialHigh;
	private int serialLow;
	private XBeeMode mode = XBeeMode.UNKNOWN; 
	private long lastReceivedMessage;

	public void setHardwareVersion(RadioType hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public void setFirmwareVersion(int[] fv) {
		this.firmwareVersion = fv[0] << 8 | fv[1];
		// First byte of the firmware version tells us which mode we're running in
		// in case we're using API mode (which we are).
		switch(fv[0])
		{
		case 0x21:
			mode = XBeeMode.COORDINATOR;
			break;
		case 0x23:
			mode = XBeeMode.ROUTER;
			break;
		case 0x29:
			mode = XBeeMode.END_DEVICE;
			break;
		default:
			mode = XBeeMode.UNKNOWN;
		}
	}

	public void setPanID(int[] id) {
		this.panID = (long)id[0] << 56 | (long)id[1] << 48 | (long)id[2] << 40 | (long)id[3] << 32 | 
				id[4] << 24 | id[5] << 16 | id[6] << 8 | id[7];
	}

	public void setSerialHigh(int[] sh) {
		this.serialHigh = sh[0] << 24 | sh[1] << 16 | sh[2] << 8 | sh[3]; 
	}

	public void setSerialLow(int[] sl) {
		this.serialLow = sl[0] << 24 | sl[1] << 16 | sl[2] << 8 | sl[3]; 
	}
	
	public XBeeMode getMode() {
		return mode;
	}
	
	public void touchLastReceivedTime() {
		lastReceivedMessage = System.currentTimeMillis();
	}
	
	public long getLastReceviedTime() {
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
