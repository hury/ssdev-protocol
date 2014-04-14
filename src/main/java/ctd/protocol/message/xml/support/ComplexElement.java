package ctd.protocol.message.xml.support;

import java.util.ArrayList;
import java.util.List;

import ctd.protocol.message.Message;
import ctd.protocol.message.exception.MessageException;
import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.validate.Validator;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.order.Order;
import ctd.protocol.schema.support.container.Segment;

public class ComplexElement extends AbstractXMLMessage {
	protected final List<XMLMessage> childMessages;
	protected org.dom4j.Element data;
	
	public ComplexElement(Element el) {
		super(el);
		childMessages = new ArrayList<XMLMessage>();
		Segment s = (Segment)el;
		List<Element> ls = s.getElements();
		
		for(Element e : ls){
			XMLMessage xmlMessage = XMLMessageFactory.createMessage(e);
			xmlMessage.setParent(this);
			childMessages.add(xmlMessage);
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addData(org.dom4j.Element data) {
		this.data = data;
		if(data != null){
			List<org.dom4j.Element> els = data.elements();
			Segment s = (Segment)el;
			for(org.dom4j.Element childEl : els){
				String id = childEl.getName();
				Element el = s.getElementById(id);
				if(el == null){
					continue;
				}
				childMessages.get(el.getPosition()).addData(childEl);
			}		
		}
	}
	
	@Override
	public void appendTo(org.dom4j.Element parentEl) {
		if(data == null){
			
			if(el.isRequire() || el.getDefaultValue() !=null || isRoot()){
				data = createXMLElement(el);
				addData(data);
			}
			else{
				return;
			}
		}
		
		for(XMLMessage message: childMessages){
			message.appendTo(data);
		}
		
		if(parentEl != null){
			if(data.getParent() == parentEl){
				return;
			}
			if(data.getParent() != null){
				data.detach();
			}
			parentEl.add(data);
		}
		
	}
	
	@Override
	protected  org.dom4j.Element getCurrentData(){
		return data;
	}
	
	@Override
	public org.dom4j.Element getData(){
		if(this.getParent() == null){
			appendTo(null);
		}
		return data;
	}

	@Override
	public Message<org.dom4j.Element> child(int i) {
		if(i < 0 || i >= childMessages.size()){
			throw new MessageException("element[" + el.getId() + "] child[" + i + "] not defined.");
		}
		getData();
		return childMessages.get(i);
	}

	@Override
	public Message<org.dom4j.Element> child(String id) {
		Segment s = (Segment)el;
		Element child = s.getElementById(id);
		if(child == null){
			throw new MessageException("element[" + el.getId() + "] child[" + id + "] not defined.");
		}
		getData();
		return childMessages.get(child.getPosition());
	}

	@Override
	public Object getValue() {
		if(data == null){
			
		}
		return null;
	}

	@Override
	public void setValue(Object v) {
		
	}

	@Override
	public void clear() {
		for(XMLMessage message: childMessages){
			message.clear();
		}
		data = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidateStatus validate() {
		org.dom4j.Element data = getData();
		
		ValidateStatus status = super.validate();
		if(!status.isOK()){
			return status;
		}
		
		if(data == null && !el.isRequire()){
			return ValidateStatus.STATUS_OK;
		}
		
		int count = 0;
		for(int i = 0; i < childMessages.size(); i ++){
			XMLMessage message = childMessages.get(i);
			status = message.validate();
			if(!status.isOK()){
				return status;
			}
			if(message.getData() != null){
				count ++;
			}
		}
		
		Segment s = (Segment)el;
		Order order = s.getOrder();
		if(order == Order.CHOOSE && count > 1){
			return ValidateStatus.buildStatus(ErrorType.OrderChoose, "element<=1", count,this);
		}
		
		if(order == Order.SEQUENCE){
			List<org.dom4j.Element> els = data.elements();
			Element last = null;
			for(org.dom4j.Element xmlEl : els){
				String id = xmlEl.getName();
				Element el = s.getElementById(id);
				if(el == null){
					//define elements is over
					if(last != null && last.getPosition() == s.getChildElementsCount() - 1){
						break;
					}
					else{
						Element expectedElement = null;
						if(last == null){
							expectedElement = s.getElementAt(0);
						}
						else{
							expectedElement = s.getElementAt(last.getPosition() + 1);
						}
						return ValidateStatus.buildStatus(ErrorType.OrderSequence, "expected element[" + expectedElement.getId() +"]", id,this);
					}
				}
				else{
					int expectedPosition = 0;
					if(last != null){
						if(last.getId().equals(id)){
							continue;
						}
						expectedPosition = last.getPosition() + 1;
					}
					if(last.isRequire()){
						if(expectedPosition != el.getPosition()){
							return ValidateStatus.buildStatus(ErrorType.OrderChoose, "element[" + el.getId() + "] position=" + el.getPosition(),expectedPosition,this);
						}
					}
					last = el;	
				}
				
			}//for
		}//if(order == Order.SEQUENCE){
		
		Validator validator = s.getValidator();
		if(validator != null){
			status = validator.validate(this);
			if(!status.isOK()){
				return status;
			}
		}
		return ValidateStatus.STATUS_OK;
	}

}
