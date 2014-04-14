package ctd.protocol.schema;

import java.util.List;

import ctd.protocol.schema.order.Order;

public interface Container extends Element {

	Order getOrder();

	void setOrder(Order order);

	List<Element> getElements();

	void setElements(List<Element> elements);

	void addElement(Element el);

	Element getElementAt(int index);
	
	Element getElementById(String id);

	int getChildElementsCount();

}
