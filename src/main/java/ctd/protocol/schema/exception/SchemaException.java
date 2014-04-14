package ctd.protocol.schema.exception;

import ctd.util.exception.CodedBaseRuntimeException;
import ctd.protocol.schema.Node;

public class SchemaException extends CodedBaseRuntimeException {
	private static final long serialVersionUID = 481982034072157387L;
	private Node source;

	public SchemaException() {
		
	}

	public SchemaException(int code) {
		super(code);
	}

	public SchemaException(int code, String msg) {
		super(code, msg);
	}

	public SchemaException(int code, String msg, Throwable t) {
		super(code, msg, t);
	}

	public SchemaException(Throwable t) {
		super(t);
	}

	public SchemaException(String msg) {
		super(msg);
	}
	
	public SchemaException(String msg,Node source) {
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
