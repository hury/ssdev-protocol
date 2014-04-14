package ctd.protocol.message.validate;

import org.apache.commons.lang3.StringUtils;

import ctd.protocol.message.Message;
import ctd.util.converter.ConversionUtils;

public class ValidateStatus {
	public static ValidateStatus STATUS_OK = new ValidateStatus(Level.OK);
	public static enum Level {OK,WARN,ERROR}

	private final Level level;
	private final Description message;
	
	public ValidateStatus(Level level){
		this(level,null);
	}
	
	public ValidateStatus(Level level,Description message){
		this.level = level;
		this.message = message;
	}
	
	public boolean isOK(){
		return level == Level.OK;
	}
	
	public boolean isNotOK(){
		return level != Level.OK;
	}
	
	public Level getLevel() {
		return level;
	}

	public Description getMessage() {
		return message;
	}
	
	public void setSource(Message<?> source){
		if(message != null){
			message.setSource(source);
		}
	}
	
	public void setPosition(String position){
		if(message != null){
			message.setPosition(position);
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("[").append("level=").append(level);
		
		if(message != null){
			sb.append(",errorType=").append(message.getErrorType());
			sb.append(",expected=").append(message.getExpected());
			sb.append(",actual=").append(message.getActual());
			
			Message<?> source = message.getSource();
			if(source != null){
				sb.append(",elementId=").append(source.getElement().getId());
				if(!StringUtils.isEmpty(message.getPosition())){
					sb.append(message.getPosition());
				}
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	
	public static ValidateStatus buildStatus(ErrorType errType,String expected,Object actual){
		String actualStr = ConversionUtils.convert(actual, String.class);
		return buildStatus(Level.ERROR,errType,expected,actualStr,null);
	}
	public static ValidateStatus buildStatus(ErrorType errType,String expected,Object actual,Message<?> source){
		String actualStr = ConversionUtils.convert(actual, String.class);
		return buildStatus(Level.ERROR,errType,expected,actualStr,source);
	}
	
	public static ValidateStatus buildStatus(ErrorType errType){
		return buildStatus(Level.ERROR,errType,null,null,null);
	}
	
	public static ValidateStatus buildStatus(Level level,ErrorType errType){
		return buildStatus(level,errType,null,null,null);
	}
	
	public static ValidateStatus buildStatus(Level level,ErrorType errType,String elementId){
		return buildStatus(level,errType,elementId,null,null);
	}
	
	public static ValidateStatus buildStatus(Level level,ErrorType errType,String expected,String actual,Message<?> source){
		ValidateStatus status = new ValidateStatus(level,new Description(errType,expected,actual,source));
		return status;
	}

	
}
