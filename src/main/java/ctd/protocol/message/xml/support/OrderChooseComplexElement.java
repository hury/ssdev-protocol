package ctd.protocol.message.xml.support;


import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.message.xml.XMLMessage;
import ctd.protocol.schema.Element;


public class OrderChooseComplexElement extends ComplexElement {
	
	
	public OrderChooseComplexElement(Element el) {
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
		
		int count = 0;
		for(int i = 0; i < childMessages.size(); i ++){
			XMLMessage message = childMessages.get(i);
			status = message.validate();
			if(status.isNotOK()){
				return status;
			}
			if(message.getData() != null){
				count ++;
			}
		}
		
		if(count > 1){
			return ValidateStatus.buildStatus(ErrorType.OrderChoose, "element<=1", count,this);
		}

		return ValidateStatus.STATUS_OK;
	}

}
