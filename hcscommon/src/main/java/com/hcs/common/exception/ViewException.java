package com.hcs.common.exception;

public class ViewException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public enum MessageType {
	    NULL ("Null, view is not initialized");

	    private final String message;

	    MessageType(String message) {
	        this.message = message;
	    }
	    private String message() { return message; }
	}
	
    //Constructor that accepts a message
    public ViewException(MessageType messageType) {
       super(messageType.message());
    }
}
