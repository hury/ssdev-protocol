package ctd.protocol.schema.support.meta;

import java.util.regex.Pattern;

import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.support.Meta;

public class PatternMeta extends Meta {
	private String regex;
	private Pattern pattern; 
	
	public PatternMeta(){
		super();
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		pattern = Pattern.compile(regex);
	}
	
	@Override
	public ValidateStatus validate(Object val){
		String s = (String)parseValue(val);
		if(!pattern.matcher(s).matches()){
			return ValidateStatus.buildStatus(ErrorType.DataValue, "regex[" + regex + "] not match", s);
		}
		return ValidateStatus.STATUS_OK;
		
	}
	
	
}
