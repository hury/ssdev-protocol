package ctd.protocol.message.xml.support;

import java.util.List;
import org.dom4j.Element;

import ctd.protocol.message.Message;
import ctd.protocol.message.exception.MessageException;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.message.xml.XMLMessageFactory;

public class AnyElement extends AbstractGroup<org.dom4j.Element> {
	

	public AnyElement(ctd.protocol.schema.Element el) {
		super(el);
	}
	
	@Override
	public org.dom4j.Element getCurrentData() {
		if(dataGroup.isEmpty()){
			return null;
		}
		return dataGroup.get(index);
	}
	
	private Message<Element> createMessageFromData(org.dom4j.Element data){
		XMLMessage msg = XMLMessageFactory.createMessage(el);
		msg.addData(data);
		msg.setParent(this);
		return msg;
	}

	@Override
	public Message<Element> child(int i) {
		org.dom4j.Element data = getCurrentData();
		if(data == null || i >= data.elements().size()){
			throw new MessageException("element[" + el.getId() + "] child[" + i + "] not found.");
		}
		return createMessageFromData((Element) data.elements().get(i));
	}

	@Override
	public Message<Element> child(String id) {
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			data = createXMLElement(el);
			addData(data);
		}
		org.dom4j.Element childEl = data.element(id);
		if(childEl == null){
			childEl = data.addElement(id);
		}
		return createMessageFromData(childEl);
	}

	@Override
	public Object getValue() {
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			return null;
		}
		return data.getText();
	}

	@Override
	public void setValue(Object v) {
		org.dom4j.Element data = getCurrentData();
		if(data != null){
			data.setText(el.toDisplayValue(v));
		}
	}

	@Override
	public void setAttribute(String id, Object v) {
		org.dom4j.Element data = getCurrentData();
		if(data != null){
			data.addAttribute(id, el.toDisplayValue(v));
		}
	}

	@Override
	public Object getAttribute(String id) {
		org.dom4j.Element data = getCurrentData();
		if(data == null){
			return null;
		}
		return data.attributeValue(id);
	}

	@Override
	public void addData(Element data) {
		dataGroup.add(data);
	}


	@Override
	public ctd.protocol.schema.Element getElement() {
		return el;
	}

	@Override
	public void validateAll(List<ValidateStatus> status) {
		
	}


	@Override
	public void appendTo(Element parentEl) {
		for(org.dom4j.Element dataEl : dataGroup){
			if(parentEl != null){
				if(dataEl.getParent() == parentEl){
					continue;
				}
				if(dataEl.getParent() != null){
					dataEl.detach();
				}
				parentEl.add(dataEl);
			}
		}
	}

}
