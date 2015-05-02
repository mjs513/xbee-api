package com.rapplogic.xbee;

import java.io.IOException;

/**
 * Represents a protocol independent connection to a XBee radio (e.g. could be a serial connection, socket, xmpp etc.)
 * Important: The implementation must call this.notify() when new data is available; otherwise the XBee input stream thread
 * will go into a forever wait.
 * <p/>
 * You must implement read(int) and available() on the InputStream and
 * <p/>
 * write(int) and flush() on the OutputStream
 * <p/>
 * It's recommended to implement close
 *  
 * @author andrew
 *
 */
public interface XBeeConnection {
	public int getByte() throws IOException;
	public boolean hasData() throws IOException;
	public void close() throws IOException;
	public void writeIntArray(int[] packet) throws IOException;
	public boolean isConnected();
}
