package ctd.protocol.message.xml;

import ctd.protocol.message.Message;

public interface XMLMessage extends Message<org.dom4j.Element> {
	void appendTo(org.dom4j.Element parentEl);
	XMLMessage getParent();
	void setParent(XMLMessage parent);
}
