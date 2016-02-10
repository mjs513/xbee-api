package com.rapplogic.xbee;

import com.rapplogic.xbee.connections.XBeeConnection;
import com.rapplogic.xbee.connections.XBeeConnectionException;
import com.rapplogic.xbee.connections.connectionprovider.IConnectionProvider;
import com.rapplogic.xbee.mocks.Series2Mock;

public class TestConnectionProvider implements IConnectionProvider {

	@Override
	public XBeeConnection open(String port, int baudRate) throws XBeeConnectionException {
		return new Series2Mock();
	}

}
