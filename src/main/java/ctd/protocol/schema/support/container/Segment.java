package ctd.protocol.schema.support.container;

import java.util.HashMap;
import java.util.Map;

import ctd.controller.Configurable;
import ctd.controller.exception.ControllerException;
import ctd.protocol.message.validate.Validator;
import ctd.protocol.schema.exception.SchemaException;
import ctd.protocol.schema.support.AbstractContainer;
import ctd.util.converter.ConversionUtils;

public class Segment extends AbstractContainer implements Configurable{
	private static final long serialVersionUID = -3621208890004374161L;
	protected Long lastModi;
	protected Map<String,Object> properties;
	private Validator validator;
	
	
	@Override
	public void setProperty(String nm,Object v){
		if(properties == null){
			properties = new HashMap<String,Object>();
		}
		properties.put(nm, v);
	}
	
	@Override
	public Object getProperty(String nm){
		Object v = properties.get(nm);
		return v;
	}
	
	@Override
	public <T> T getProperty(String nm,Class<T> targetType){
		return ConversionUtils.convert(getProperty(nm), targetType);
	}
	

	@Override
	public Map<String,Object> getProperties(){
		if(properties == null || properties.size() == 0){
			return null;
		}
		return properties;
	}

	@Override
	public Long getlastModify() {
		return lastModi;
	}

	@Override
	public void setLastModify(Long lastModi) {
		this.lastModi = lastModi;
	}
	
	public boolean isInited(){
		return true;
	}
	
	public void init() throws ControllerException{}

	public Validator getValidator() {
		return validator;
	}

	public void setValidatorClass(String validatorClass) {
		try {
			this.validator = (Validator) Class.forName(validatorClass).newInstance();
		} 
		catch (Exception e) {
			throw new SchemaException("validatorClass[" + validatorClass +"] is invalid");
		} 
	};
	
}
