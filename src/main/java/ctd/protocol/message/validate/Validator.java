package ctd.protocol.message.validate;

import java.util.List;

import ctd.protocol.message.Message;
import ctd.protocol.message.exception.ValidateException;

public interface Validator {
	ValidateStatus validate(Message<?> message) throws ValidateException;
	List<ValidateStatus> validateAll(Message<?> message,List<ValidateStatus> status);
}
