package ctd.protocol.schema.support.meta;


public class EMailMeta extends PatternMeta {

	public EMailMeta() {
		setRegex("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");
	}
	
}
