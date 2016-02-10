package com.rapplogic.xbee.mocks;

import java.io.IOException;

import com.rapplogic.xbee.connections.XBeeConnection;

public class Series2Mock implements XBeeConnection {

	@Override
	public int getByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasData() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeIntArray(int[] packet) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getLastDataReceiveTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastDataSendTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
