package ctd.protocol.schema.support;

import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.constans.DataTypes;
import ctd.protocol.schema.exception.SchemaException;
import ctd.util.BeanUtils;

public class Entry extends AbstractElement {
	private Meta meta;
	
	public Entry(){
		this("string");
	};
	
	public Entry(String type){
		if(!DataTypes.isSupportType(type)){
			throw new SchemaException("entry[" + id + "] type is not primitive type:" + type,this);
		}
		this.type = this.primitiveType = type;
	}
	
	public Entry(Meta meta){
		this.primitiveType = meta.getType();
		this.meta = meta;
		BeanUtils.copy(meta, this);
	}
	
	@Override
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
		if(!allowBlank){
			this.minoccurs = 1;
		}
	}
	
	@Override
	public Object parseValue(Object v){
		return meta == null ? super.parseValue(v) : meta.parseValue(v);
	}
	
	@Override
	public String toDisplayValue(Object v){
		return meta == null ? super.toDisplayValue(v) : meta.toDisplayValue(v);
	}
	
	@Override
	public ValidateStatus validate(Object val){
		if(meta != null){
			ValidateStatus status = meta.validate(val);
			if(status.isNotOK()){
				return status;
			}
		}
		return super.validate(val);
	}

}
