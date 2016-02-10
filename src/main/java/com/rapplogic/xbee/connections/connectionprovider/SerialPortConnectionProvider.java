package com.rapplogic.xbee.connections.connectionprovider;

import java.io.IOException;
import java.util.TooManyListenersException;

import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.connections.SerialPortConnection;
import com.rapplogic.xbee.connections.XBeeConnection;
import com.rapplogic.xbee.connections.XBeeConnectionException;

import jssc.SerialPortException;

public class SerialPortConnectionProvider implements IConnectionProvider {

	@Override
	public XBeeConnection open(String port, int baudRate) throws XBeeConnectionException {
		SerialPortConnection serial = new SerialPortConnection(); 
		try {
			serial.openSerialPort(port, baudRate);
		} catch (TooManyListenersException | IOException | XBeeException | SerialPortException e) {
			throw new XBeeConnectionException("Failed to open connection", e);
		}
		return serial;
	}

}
