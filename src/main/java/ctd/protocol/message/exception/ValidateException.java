package ctd.protocol.message.exception;

import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Node;

public class ValidateException extends MessageException {
	private static final long serialVersionUID = 3992774641062326508L;
	private ValidateStatus status;

	public ValidateException(ValidateStatus status) {
		this.setStatus(status);
	}

	public ValidateException(int code) {
		super(code);
		
	}

	public ValidateException(int code, String msg) {
		super(code, msg);
	}

	public ValidateException(int code, String msg, Throwable t) {
		super(code, msg, t);
	}

	public ValidateException(String msg, Throwable t) {
		super(msg, t);
	}

	public ValidateException(Throwable t) {
		super(t);
	}

	public ValidateException(String msg) {
		super(msg);
	}

	public ValidateException(String msg, Node source) {
		super(msg, source);
	}

	public ValidateStatus getStatus() {
		return status;
	}

	public void setStatus(ValidateStatus status) {
		this.status = status;
	}

}
