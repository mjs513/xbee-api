package com.rapplogic.xbee.connections.connectionprovider;

import com.rapplogic.xbee.connections.XBeeConnection;
import com.rapplogic.xbee.connections.XBeeConnectionException;

public interface IConnectionProvider {

	XBeeConnection open(String port, int baudRate) throws XBeeConnectionException;

}
