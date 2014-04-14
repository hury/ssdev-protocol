package ctd.protocol.schema;

import ctd.protocol.message.validate.ValidateStatus;


public interface Node {

	String getId();

	void setId(String id);

	String getName();

	void setName(String name);

	String getType();

	void setType(String type);

	void setLength(int length);

	int getLength();

	String getTable();

	void setTable(String table);

	boolean isFixedLength();

	void setFixedLength(boolean isFixedLength);

	Object parseValue(Object v);
	
	String toDisplayValue(Object v);

	Object getDefaultValue();

	void setDefaultValue(Object defaultValue);

	boolean isFixedValue();

	void setFixedValue(boolean isFiexedValue);

	ValidateStatus validate(Object val);

	Object getMaxValue();

	void setMaxValue(Object maxValue);

	boolean isAllowBlank();

	void setAllowBlank(boolean allowBlank);
}
