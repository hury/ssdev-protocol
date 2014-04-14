package ctd.protocol.schema.support.meta;

import java.util.regex.Pattern;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.support.Meta;

public class ChinaIDNumberMeta extends Meta {
	private static final Pattern pattern = Pattern.compile("[0-9]{17}");
	private static final char[] validateCodes = { '1', '0', 'x', '9', '8', '7', '6', '5', '4','3', '2' };
	private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,9, 10, 5, 8, 4, 2};

	public ChinaIDNumberMeta() {
		super();
		length = 18;
	}
	
	@Override
	public ValidateStatus validate(Object val){
		String idNumber = (String) parseValue(val);
		int len = idNumber.length();
		if(!(len == 15 || len == 18)){
			return ValidateStatus.buildStatus(ErrorType.DataLength, "len=15 or 18", len);
		}
		String ai = "";
		
		if(len == 18){
			ai = idNumber.substring(0, 17);
		}
		else{
			ai = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
		}
		
		if(!pattern.matcher(ai).matches()){
			return ValidateStatus.buildStatus(ErrorType.DataValue, "[0-17] must be number", idNumber);
		}
		String birth = ai.substring(6,14);
		try{
			LocalDate birthDay = DateTimeFormat.forPattern("yyyyMMdd").parseLocalDate(birth);
			LocalDate now = new LocalDate();
			int age = Years.yearsBetween(birthDay, now).getYears();
			if(age < 0 || age > 120){
				return ValidateStatus.buildStatus(ErrorType.DataValue, "","BirthdayOverflow[" + birth + "]");
			}
		}
		catch(RuntimeException e){
			return ValidateStatus.buildStatus(ErrorType.DataValue, "", "BirthdayDateInvaid[" + birth + "]");
		}
		
		 int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(String.valueOf(ai.charAt(i))) * wi[i];
        }
        int mod = sum % 11;
        char c = validateCodes[mod];
        if(idNumber.charAt(17) != c){
        	return ValidateStatus.buildStatus(ErrorType.DataValue, "char[17]=" + c, idNumber.charAt(17));
        }
		return ValidateStatus.STATUS_OK;
	}
	
}
