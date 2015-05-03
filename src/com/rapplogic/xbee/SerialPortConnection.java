/**
 * Copyright (c) 2008 Andrew Rapp. All rights reserved.
 *  
 * This file is part of XBee-API.
 *  
 * XBee-API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * XBee-API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with XBee-API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rapplogic.xbee;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TooManyListenersException;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.XBeeException;

/** 
 * This class encapsulates a RXTX serial port, providing access to input/output streams,
 * and notifying the subclass of new data events via the handleSerialData method.
 * 
 * @author andrew
 * 
 */
public class SerialPortConnection implements XBeeConnection, SerialPortEventListener {

	private final static Logger log = Logger.getLogger(SerialPortConnection.class);

	private static final int DEFAULT_TIMEOUT = 5000;

	private SerialPort serialPort;
	
	private int timeout;

	public SerialPortConnection() {

	}

	public void openSerialPort(String port, int baudRate) throws TooManyListenersException, IOException, XBeeException, SerialPortException {
		this.openSerialPort(port, "XBee", DEFAULT_TIMEOUT, baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE);
	}

	public void openSerialPort(String port, String appName, int timeout, int baudRate) throws TooManyListenersException, IOException, XBeeException, SerialPortException {
		this.openSerialPort(port, appName, timeout, baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE);
	}

	public void openSerialPort(String port, String appName, int timeout, int baudRate, int dataBits, int stopBits, int parity, int flowControl) throws TooManyListenersException, IOException, XBeeException, SerialPortException {
		// Apparently you can't query for a specific port, but instead must iterate
		Set<String> serialPorts = new HashSet<String>(Arrays.asList(SerialPortList.getPortNames()));

		if (!serialPorts.contains(port)) {
			throw new XBeeException("Could not find port: " + port);
		}

		this.timeout = timeout;

		serialPort = new SerialPort(port);
		serialPort.openPort();

		serialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

		serialPort.addEventListener(this);
	}

	/**
	 * Shuts down RXTX
	 */
	@Override
	public void close() throws IOException {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			throw new IOException(e);
		}
	}

	public void serialEvent(SerialPortEvent event) {
		try {
			if (serialPort.getInputBufferBytesCount() > 0) {
				try {
					log.debug("serialEvent: " + serialPort.getInputBufferBytesCount() + " bytes available");

					synchronized (this) {
						this.notify();
					}
				} catch (Exception e) {
					log.error("Error in handleSerialData method", e);
				}
			} else {
				log.warn("We were notified of new data but available() is returning 0");
			}
		} catch (SerialPortException ex) {
			// it's best not to throw the exception because the RXTX thread may not be prepared to handle
			log.error("RXTX error in serialEvent method", ex);
		}
	}

	@Override
	public int getByte() throws IOException {
		try {
			return serialPort.readBytes(1, timeout)[0] & 0xFF;
		} catch (SerialPortException e) {
			throw new IOException(e);
		} catch (SerialPortTimeoutException e) {
			throw new IOException(e);
		}
	}

	@Override
	public boolean hasData() throws IOException {
		try {
			return serialPort.getInputBufferBytesCount() > 0;
		} catch (SerialPortException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void writeIntArray(int[] packet) throws IOException {
		try {
			serialPort.writeIntArray(packet);
		} catch (SerialPortException e) {
			throw new IOException(e);
		}
	}

	@Override
	public boolean isConnected() {
		return serialPort != null && serialPort.isOpened();
	}
}