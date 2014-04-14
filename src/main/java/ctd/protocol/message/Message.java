package ctd.protocol.message;


import java.util.List;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Element;

public interface Message<T>{
	void parseString(String msg);
	Message<T> child(int i);
	Message<T> child(String id);
	Object getValue();
	void setValue(Object v);
	void setAttribute(String id, Object v);
	Object getAttribute(String id);
	void addData(T data);
	T getData();
	Element getElement();
	void nextGroup();
	void toGroup(int index);
	int getGroupIndex();
	int getGroupCount();
	
	int getPosition();
	void setPosition(int position);
	boolean isRoot();
	
	ValidateStatus validate();
	void validateAll(List<ValidateStatus> status);
	void clear();
}
