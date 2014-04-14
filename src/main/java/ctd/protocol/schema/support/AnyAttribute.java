package ctd.protocol.schema.support;

import ctd.protocol.message.validate.ValidateStatus;

public class AnyAttribute extends Attribute {
	public static final String AnyAttributeId = "$AnyAttributeId";
	
	public AnyAttribute(){
		super();
		this.id = AnyAttributeId;
	}
	
	@Override
	public ValidateStatus validate(Object val){
		return ValidateStatus.STATUS_OK;
	}

}
