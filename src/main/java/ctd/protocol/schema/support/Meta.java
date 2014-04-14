package ctd.protocol.schema.support;

import ctd.protocol.schema.constans.DataTypes;
import ctd.protocol.schema.exception.SchemaException;
import ctd.util.converter.ConversionUtils;

public class Meta extends AbstractNode {
	private String clz;
	
	public Meta(){
		setType("string");
	};
	
	
	@Override
	public void setType(String type) {
		if(!DataTypes.isSupportType(type)){
			throw new SchemaException("meta[" + id + "] type is not primitive type:" + type,this);
		}
		this.type = type;
		this.primitiveType = type;
	}
	
	@Override
	public Object parseValue(Object v){
		return DataTypes.toTypeValue(primitiveType, v);
	}
	
	@Override
	public String toDisplayValue(Object v){
		return ConversionUtils.convert(v, String.class);
	}

	
	public void setClass(String clz){
		this.setClz(clz);
	}


	public String getClz() {
		return clz;
	}


	public void setClz(String clz) {
		this.clz = clz;
	}
}
