package com.rapplogic.xbee.connections;

import com.rapplogic.xbee.api.XBeeException;

public class XBeeConnectionException extends XBeeException {

	private static final long serialVersionUID = 8283447104282352813L;

	public XBeeConnectionException(String message, Exception e) {
		super(message, e);
	}

}
