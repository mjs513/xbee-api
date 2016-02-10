package com.rapplogic.xbee;

import com.rapplogic.xbee.connections.XBeeConnection;
import com.rapplogic.xbee.connections.XBeeConnectionException;
import com.rapplogic.xbee.connections.connectionprovider.IConnectionProvider;

public class TestConnectionProvider implements IConnectionProvider {

	@Override
	public XBeeConnection open(String port, int baudRate) throws XBeeConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

}
