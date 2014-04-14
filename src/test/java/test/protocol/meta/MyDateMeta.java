package test.protocol.meta;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;

import ctd.protocol.schema.support.Meta;
import ctd.util.converter.ConversionUtils;

public class MyDateMeta extends Meta {
	private String format="yyyy-MM-dd'T'HH:mm:ss";

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	@Override
	public String toDisplayValue(Object v){
		Date dt = ConversionUtils.convert(v, Date.class);
		return DateTimeFormat.forPattern(format).print(dt.getTime());
	}
}
