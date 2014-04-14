package ctd.protocol.schema.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ctd.protocol.schema.Element;
import ctd.protocol.schema.exception.SchemaException;
import ctd.protocol.schema.utils.CompatibleUtils;


public class AbstractElement extends AbstractNode implements Element{
	
	protected int position;
	protected int minoccurs = 0 ;
	protected int maxoccurs = 1;
	protected Map<String, Attribute> attrs;

	@Override
	public void setAttributes(List<Attribute> ls){
		if(ls == null || ls.isEmpty()){
			return;
		}
		
		if(attrs == null){
			attrs = new HashMap<String,Attribute>();
		}
		for(Attribute attr : ls){
			attrs.put(attr.getId(), attr);
		}
	}
	
	@Override
	public List<Attribute> getAttributes(){
		List<Attribute> ls = new ArrayList<Attribute>();
		if(attrs != null){
			for(Attribute attr : attrs.values()){
				if(attr instanceof AnyAttribute){
					continue;
				}
				ls.add(attr);
			}
		}
		return ls;
	}
	
	@Override
	public void addAttribute(Attribute attr){
		if(attrs == null){
			attrs = new LinkedHashMap<String,Attribute>();
		}
		String id = attr.getId();
		//for override
		if(attrs.containsKey(id)){
			CompatibleUtils.compatibleCheck(attrs.get(id), attr);
		}
		attrs.put(attr.getId(), attr);
	}
	
	
	@Override
	public Attribute getAttribute(String id){
		if(attrs == null){
			return null;
		}
		if(attrs.containsKey(id)){
			return attrs.get(id);
		}
		else{
			if(attrs.containsKey(AnyAttribute.AnyAttributeId)){
				return attrs.get(AnyAttribute.AnyAttributeId);
			}
			return null;
		}
	}
	
	@Override
	public int getMinoccurs() {
		if(minoccurs == 0 && !allowBlank){
			return 1;
		}
		return minoccurs;
	}
	
	@Override
	public void setMinoccurs(int minoccurs) {
		if(minoccurs < 0){
			minoccurs = 0;
		}
		if(minoccurs > maxoccurs){
			throw new SchemaException("element[" + id + "] minoccurs set value[ " + minoccurs + " ] invalid.",this);
		}
		this.minoccurs = minoccurs;
	}
	
	@Override
	public int getMaxoccurs() {
		return maxoccurs;
	}
	
	@Override
	public void setMaxoccurs(int maxoccurs) {
		if(minoccurs < 0){
			minoccurs = Integer.MAX_VALUE;
		}
		if(minoccurs > maxoccurs){
			throw new SchemaException("element[" + id + "] maxoccurs set value[" + maxoccurs + "] invalid.",this);
		}
		this.maxoccurs = maxoccurs;
	}
	
	@Override
	public boolean isRequire() {
		return getMinoccurs() > 0;
	}
	
	@Override
	public void setRequire(boolean require) {
		if(require){
			if(minoccurs == 0){
				setMinoccurs(1);
			}
		}
		else{
			setMinoccurs(0);
		}
	}
	
	@Override
	public int getRepetition() {
		return getMaxoccurs();
	}

	@Override
	public void setRepetition(int repetition) {
		setMinoccurs(repetition);
		setMaxoccurs(repetition);
	}

	@Override
	public int getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(int position) {
		this.position = position;
	}
	
}
