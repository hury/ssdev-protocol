package ctd.protocol.message.xml.support;

import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.schema.Element;


public class OrderAllComplexElement extends ComplexElement {
	
	
	public OrderAllComplexElement(Element el) {
		super(el);

	}
	
	@Override
	public ValidateStatus validate() {
		ValidateStatus status = super.validate();
		if(status.isNotOK()){
			return status;
		}
		
		org.dom4j.Element data = getData();
		
		if(data == null && !el.isRequire()){
			return ValidateStatus.STATUS_OK;
		}
		
		for(int i = 0; i < childMessages.size(); i ++){
			XMLMessage message = childMessages.get(i);
			status = message.validate();
			if(status.isNotOK()){
				return status;
			}
		}
		return ValidateStatus.STATUS_OK;
	}

}
