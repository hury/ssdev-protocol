package ctd.protocol.message.xml.support;

import ctd.protocol.message.Message;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessageFactory;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.support.container.Segment;


public class ComplexElementGroup extends AbstractGroup<ComplexElement> {
	
	public ComplexElementGroup(Element el) {
		super(el);
	}

	@Override
	public void appendTo(org.dom4j.Element parentEl) {
		for(ComplexElement cm : dataGroup){
			cm.appendTo(parentEl);
		}
	}

	@Override
	public void addData(org.dom4j.Element data) {
		index = dataGroup.size();
		ComplexElement cm = (ComplexElement) XMLMessageFactory.createMessage(el);
		cm.setPosition(dataGroup.size());
		cm.setParent(this.getParent());
		cm.addData(data);
		dataGroup.add(cm);
	}

	@Override
	public Message<org.dom4j.Element> child(int i) {
		if(dataGroup.isEmpty()){
			addData(createXMLElement(el));
		}
		return dataGroup.get(index).child(i);
	}

	@Override
	public Message<org.dom4j.Element> child(String id) {
		if(dataGroup.isEmpty()){
			addData(createXMLElement(el));
		}
		
		Segment s = (Segment)el;
		Element child = s.getElementById(id);
		return dataGroup.get(index).child(child.getPosition());
	}
	
	@Override
	public org.dom4j.Element getCurrentData() {
		if(dataGroup.isEmpty()){
			return null;
		}
		return dataGroup.get(index).getData();
	}
	
	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setValue(Object v) {
		
	}

	@Override
	public ValidateStatus validate() {
		ValidateStatus status = super.validate();
		if(!status.isOK()){
			return status;
		}
		
		for(ComplexElement ce : dataGroup){
			status = ce.validate();
			if(!status.isOK()){
				return status;
			}
		}
		
		return ValidateStatus.STATUS_OK;
	}	

}
