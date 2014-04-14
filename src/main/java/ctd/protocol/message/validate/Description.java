package ctd.protocol.message.validate;

import ctd.protocol.message.Message;

public class Description {
	private final ErrorType errorType;
	private final String expected;
	private final String actual;
	private Message<?> source;
	private String position;
	
	public Description(ErrorType errorType,String expected,String actual,Message<?> source){
		this.errorType = errorType;
		this.expected = expected;
		this.actual = actual;
		this.source = source;
	}

	public ErrorType getErrorType() {
		return errorType;
	}


	public String getExpected() {
		return expected;
	}

	public String getActual() {
		return actual;
	}

	public Message<?> getSource() {
		return source;
	}

	public void setSource(Message<?> source) {
		this.source = source;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
