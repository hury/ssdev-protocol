package ctd.protocol.message.xml.support;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Element;


public class SingleElement extends AbstractXMLMessage {
	
	protected org.dom4j.Element data;

	public SingleElement(Element el) {
		super(el);
	}
	
	@Override
	public void addData(org.dom4j.Element data) {
		this.data = data;
	}
	
	@Override
	public void appendTo(org.dom4j.Element parentEl){
		if(data == null){
			if(el.isRequire() || el.getDefaultValue() !=null || isRoot()){
				data = createXMLElement(el);
			}
			else{
				return;
			}
		}
		
		if(data != null && parentEl != null){
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
	public org.dom4j.Element getCurrentData(){
		return data;
	}


	@Override
	public Element getElement() {
		return el;
	}

	@Override
	public void clear() {
		data = null;
	}

	@Override
	public ValidateStatus validate() {
		ValidateStatus status =  super.validate();
		
		if(status.isNotOK()){
			return status;
		}
		
		if(this.getData() == null && !el.isRequire()){
			return ValidateStatus.STATUS_OK;
		}
		
		status = el.validate(getValue());
		status.setSource(this);
		return status;
	}
}
