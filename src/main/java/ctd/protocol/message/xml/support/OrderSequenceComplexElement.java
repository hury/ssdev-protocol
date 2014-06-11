package ctd.protocol.message.xml.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ctd.protocol.message.validate.ErrorType;
import ctd.protocol.message.validate.ValidateStatus;
import ctd.protocol.schema.Element;
import ctd.protocol.schema.support.container.Segment;

public class OrderSequenceComplexElement extends ComplexElement {
	private ElementLink rootLink;
	
	public OrderSequenceComplexElement(Element el) {
		super(el);
		rootLink = new ElementLink(-1);
		Segment s = (Segment)el;
		int n = s.getChildElementsCount();
		setupLinks(rootLink,s,n);
	}
	
	private void setupLinks(ElementLink link,Segment s,int n){
		for(int i = link.position + 1;i < n; i ++){
			Element childEl = s.getElementAt(i);
			String id = childEl.getId();
			ElementLink next = link.addNextElement(id,i);
			setupLinks(next,s,n);
			
			if(childEl.isRequire()){
				break;
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addData(org.dom4j.Element data) {
		this.data = data;
		if(data != null){
			List<org.dom4j.Element> els = data.elements();
			Segment s = (Segment)el;
			for(org.dom4j.Element childEl : els){
				String id = childEl.getName();
				Element el = s.getElementById(id);
				if(el == null){
					continue;
				}
				childEl.detach();
				childMessages.get(el.getPosition()).addData(childEl);
			}		
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidateStatus validate() {
		ValidateStatus status = super.validate();
		if(status.isNotOK()){
			return status;
		}
		
		org.dom4j.Element data = getData();
		
		if(data == null && !el.isRequire()){
			return ValidateStatus.STATUS_OK;
		}
		
		List<org.dom4j.Element> els = data.elements();
		
		ElementLink lastLink = rootLink;
		String lastId = null;
		for(org.dom4j.Element el : els){
			String name = el.getName();
			
			if(name.equals(lastId)){
				continue;
			}
			else{
				if(!lastLink.hasNextElement(name)){
					return ValidateStatus.buildStatus(ErrorType.ElementPosition,"","element[" + name + "] position invaild.",this);
				}
				lastLink = lastLink.getNextElement(name);
				lastId = name;
			}
			
			status = this.child(name).validate();
			if(status.isNotOK()){
				return status;
			}
			
		}
		
		return ValidateStatus.STATUS_OK;
	}

}

class ElementLink{
	final int position;
	final Map<String,ElementLink> nextElements = new HashMap<String,ElementLink>();
	
	public ElementLink(int position){
		this.position = position;
	}
	
	public ElementLink addNextElement(String id,int position){
		ElementLink link = new ElementLink(position);
		nextElements.put(id,link);
		return link;
	}
	
	public ElementLink getNextElement(String id){
		return nextElements.get(id);
	}
	
	public boolean hasNextElement(String id){
		return nextElements.containsKey(id);
	}
}