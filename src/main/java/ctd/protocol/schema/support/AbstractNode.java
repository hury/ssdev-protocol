package ctd.protocol.schema.support;

import org.apache.commons.lang3.StringUtils;

import ctd.controller.exception.ControllerException;
import ctd.dictionary.DictionaryController;
import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Node;
import ctd.protocol.schema.constans.DataTypes;
import ctd.util.StringValueParser;
import ctd.util.converter.ConversionUtils;

public class AbstractNode implements Node{
	protected String id;
	protected String name;
	protected String type;
	protected int length;
	protected boolean isFixedLength;
	protected boolean isFixedValue;
	protected String primitiveType;
	protected String table;
	protected Object defaultValue;
	protected Object maxValue;
	protected Object minValue;
	protected boolean allowBlank = true;

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public void setLength(int length) {
		this.length = length;
	}
	
	@Override
	public boolean isFixedLength() {
		return isFixedLength;
	}
	
	@Override
	public void setFixedLength(boolean isFixedLength) {
		this.isFixedLength = isFixedLength;
	}
	
	@Override
	public boolean isAllowBlank() {
		return allowBlank;
	}
	
	@Override
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	@Override
	public String getTable() {
		return table;
	}
	
	@Override
	public void setTable(String table) {
		this.table = table;
	}
	
	@Override
	public Object getDefaultValue() {
		if(defaultValue instanceof String){
			defaultValue = StringValueParser.parse((String)defaultValue, DataTypes.getTypeClass(primitiveType));
		}
		return defaultValue;
	}
	
	@Override
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public boolean isFixedValue() {
		return isFixedValue;
	}
	
	@Override
	public void setFixedValue(boolean isFixedValue) {
		this.isFixedValue = isFixedValue;
	}
	
	@Override
	public Object getMaxValue() {
		return maxValue;
	}
	
	@Override
	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

	public Object getMinValue() {
		return minValue;
	}

	public void setMinValue(Object minValue) {
		this.minValue = minValue;
	}

	@Override
	public Object parseValue(Object v){
		return DataTypes.toTypeValue(primitiveType, v);
	}
	
	@Override
	public String toDisplayValue(Object v){
		return ConversionUtils.convert(v, String.class);
	}
	
	@Override
	public ValidateStatus validate(Object val){

		if(DataTypes.isNumberType(primitiveType)){
			if(val == null && !allowBlank){
				return ValidateStatus.buildStatus(ErrorType.NullValue);
			}
			double n = ConversionUtils.convert(val, Number.class).doubleValue();
			if(this.minValue != null){
				double min = ConversionUtils.convert(this.minValue, Number.class).doubleValue();
				
				if(n < min){
					return ValidateStatus.buildStatus(ErrorType.MinValue,">=" + min,n);
				}
			}
			if(this.maxValue != null){
				double max = ConversionUtils.convert(this.maxValue, Number.class).doubleValue();
				if(n > max){
					return ValidateStatus.buildStatus(ErrorType.MaxValue,"<=" + max,n);
				}
			}
		}
		
		if(primitiveType.equals(DataTypes.STRING)){
			String s = (String) parseValue(val);
			if(StringUtils.isEmpty(s)){
				if(!allowBlank){
					return ValidateStatus.buildStatus(ErrorType.NullValue);
				}
			}
			else{
				int len = s.length();
				if(isFixedLength){
					if(len != length){
						return ValidateStatus.buildStatus(ErrorType.DataLength,"=" + length,len);
					}
				}
				else{
					if(len > length && length > 0){
						return ValidateStatus.buildStatus(ErrorType.DataLength,"<=" + length,len);
					}
				}
				if(table != null){
					try {
						if(!DictionaryController.instance().get(table).keyExist(s)){
							return ValidateStatus.buildStatus(ErrorType.DicItemNotFound,null,s);
						}
					} 
					catch (ControllerException e) {
						e.printStackTrace();
						return ValidateStatus.buildStatus(ErrorType.DicNotFound,"table=" + table,null);
					}
				}
			}
		}
		
		Object v = getDefaultValue();
		if(isFixedValue && !val.equals(v)){
			return ValidateStatus.buildStatus(ErrorType.DataValue,"=" + v,val);
		}
		return ValidateStatus.STATUS_OK;
	}

	public void setPrimitiveType(String primitiveType) {
		this.primitiveType = primitiveType;
	}



}
