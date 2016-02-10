package com.rapplogic.xbee;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeConfiguration;
import com.rapplogic.xbee.api.XBeeException;

public class SimpleSeries2Test {

	//TODO: Add series2 tests to get easy example of how it could be done and how to abstract the HW.
	
	@Test
	public void createXBee() {
		XBee xbee = getDefaultXBee();
		assertThat(xbee).isNotNull();
	}
	
	@Test(expectedExceptions=XBeeException.class)
	public void createXBeeAndOpenConnectionWithInvalidConnectionProvider() throws XBeeException {
		XBee xbee = getDefaultXBee();
		xbee.open("foobar", 12345);
	}
	
	private XBee getDefaultXBee() {
		return new XBee(new XBeeConfiguration().withMaxQueueSize(100).withStartupChecks(true).withConnectionProvider(new TestConnectionProvider()));
	}
}
