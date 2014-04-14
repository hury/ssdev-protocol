package ctd.protocol.schema;

import java.util.List;

import ctd.protocol.schema.support.Attribute;


public interface Element extends Node {
	public int getMinoccurs();
	void setMinoccurs(int minoccurs);
	int getMaxoccurs();
	void setMaxoccurs(int maxoccurs);
	boolean isRequire();
	void setRequire(boolean require);
	void addAttribute(Attribute attr);
	int getRepetition();
	void setRepetition(int repetition);
	List<Attribute> getAttributes();
	Attribute getAttribute(String id);
	int getPosition();
	void setPosition(int position);
	void setAttributes(List<Attribute> ls);
}
