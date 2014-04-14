package ctd.protocol.message.xml.support;

import java.util.ArrayList;
import java.util.List;

import ctd.protocol.message.exception.MessageException;
import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Element;

public abstract class AbstractGroup<E> extends AbstractXMLMessage {
	
	protected final List<E> dataGroup = new ArrayList<E>();
	protected int index;
	
	public AbstractGroup(Element el) {
		super(el);
	}
	
	@Override
	public void nextGroup() {
		if(index < dataGroup.size() - 1){
			index ++;
		}
		else{
			addData(createXMLElement(el));
		}
	}
	
	@Override
	public int getGroupIndex(){
		return index;
	}
	
	@Override
	public void toGroup(int index) {
		if(index >= 0 && index < dataGroup.size()){
			this.index = index;
		}
		if(index == dataGroup.size()){
			addData(createXMLElement(el));
		}
	}
	
	@Override
	public int getGroupCount(){
		return dataGroup.size();
	}
	
	@Override
	public void parseString(String msg) {
		throw new MessageException("parseSrting for group message is unsupported");
	}
	
	@Override
	public void clear() {
		dataGroup.clear();
	}
	
	@Override
	public ValidateStatus validate() {
		
		int n = dataGroup.size();
		
		int minoccurs = el.getMinoccurs();
		if(n < minoccurs){
			return ValidateStatus.buildStatus(ErrorType.ElementMinoccurs, ">=" + minoccurs, n,this);
		}
		int maxoccurs = el.getMaxoccurs();
		if(n > maxoccurs){
			return ValidateStatus.buildStatus(ErrorType.ElementMaxoccurs,"<=" + maxoccurs,n,this);
		}
		
		return ValidateStatus.STATUS_OK;
	}
	
}
