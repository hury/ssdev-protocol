package ctd.protocol.schema.support;

import ctd.protocol.message.validate.ValidateStatus;

public class AnyEntry extends Entry {
	
	public AnyEntry(){
		super();
		this.addAttribute(new AnyAttribute());
	}
	
	@Override
	public ValidateStatus validate(Object val){
		return ValidateStatus.STATUS_OK;
	}
}
