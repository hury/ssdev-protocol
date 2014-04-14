package ctd.protocol.message.xml.support;

import ctd.protocol.schema.Element;

import ctd.protocol.message.exception.MessageException;
import ctd.protocol.message.validate.ValidateStatus;


public class SingleElementGroup extends AbstractGroup<org.dom4j.Element> {

	public SingleElementGroup(Element el) {
		super(el);
	}
	
	@Override
	public void addData(org.dom4j.Element data) {
		index = dataGroup.size();
		dataGroup.add(data);
	}
	
	@Override
	public void appendTo(org.dom4j.Element parentEl){
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

	@Override
	public void parseString(String msg) {
		throw new MessageException("parseSrting for group message is unsupported");
	}

	protected org.dom4j.Element getCurrentData() {
		if(dataGroup.isEmpty()){
			return null;
		}
		return dataGroup.get(index);
	}

	@Override
	public ValidateStatus validate() {
		
		ValidateStatus status = super.validate();
		if(!status.isOK()){
			return status;
		}
		int i = 0;
		for(org.dom4j.Element data : dataGroup){
			status = el.validate(data.getText());
			if(!status.isOK()){
				status.setSource(this);
				status.setPosition("[" + i + "]");
				return status;
			}
			i ++;
		}
		
		return ValidateStatus.STATUS_OK;
	}
}
