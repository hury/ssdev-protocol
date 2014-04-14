package ctd.protocol.schema.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ctd.protocol.schema.Container;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.order.Order;
import ctd.protocol.schema.utils.CompatibleUtils;

public class AbstractContainer extends AbstractElement implements Container {
	protected List<Element> elements = new ArrayList<Element>();;
	protected HashMap<String,Element> elementsMappings = new HashMap<String,Element>();;
	protected Order order = Order.SEQUENCE;
	
	@Override
	public Order getOrder() {
		return order;
	}
	
	@Override
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void setOrder(String order) {
		this.order = Order.valueOf(order.toUpperCase());
	}

	@Override
	public List<Element> getElements() {
		return elements;
	}
	
	@Override
	public void setElements(List<Element> ls) {
		int i = 0;
		for(Element el : ls){
			el.setPosition(i);
			elements.add(el);
			elementsMappings.put(el.getId(), el);
			i ++;
		}
	}
	
	@Override
	public void addElement(Element el){
		
		//for override
		String id = el.getId();
		if(elementsMappings.containsKey(id)){
			Element find = elementsMappings.get(id);
			CompatibleUtils.compatibleCheck(find, el);
			
			int position = find.getPosition();
			el.setPosition(position);
			elements.set(position, el);
			elementsMappings.put(id, el);
		}
		else{
			el.setPosition(elements.size());
			elements.add(el);
			elementsMappings.put(id, el);
		}
	}
	
	@Override
	public Element getElementAt(int index){
		if(elements.size() <= index || index < 0){
			return null;
		}
		return elements.get(index);
	}
	
	@Override
	public Element getElementById(String id) {
		return elementsMappings.get(id);
	};
	
	@Override
	public int getChildElementsCount(){
		return elements.size();
	}
	
	@Override
	public int getLength() {
		int len = 0;
		for(Element el : elements){
			len+=el.getLength();
		}
		return len;
	}
	
	@Override
	public void setLength(int length) {}

}
