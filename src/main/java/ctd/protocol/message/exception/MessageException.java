package ctd.protocol.message.exception;

import ctd.util.exception.CodedBaseRuntimeException;
import ctd.protocol.schema.Node;

public class MessageException extends CodedBaseRuntimeException {
	private static final long serialVersionUID = 481982034072157387L;
	private Node source;

	public MessageException() {
		
	}

	public MessageException(int code) {
		super(code);
	}

	public MessageException(int code, String msg) {
		super(code, msg);
	}

	public MessageException(int code, String msg, Throwable t) {
		super(code, msg, t);
	}
	
	public MessageException(String msg, Throwable t) {
		super(msg, t);
	}

	public MessageException(Throwable t) {
		super(t);
	}

	public MessageException(String msg) {
		super(msg);
	}
	
	public MessageException(String msg,Node source) {
		super(msg);
		this.source = source;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

}
