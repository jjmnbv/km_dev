package com.kmzyc.framework.socket.exception;

public class SocketRemoteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SocketRemoteException() {
		super();
	}

	public SocketRemoteException(String message) {
		super(message);
	}

	public SocketRemoteException(Throwable cause) {
		super(cause);
	}

	public SocketRemoteException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
