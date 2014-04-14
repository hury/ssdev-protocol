package ctd.protocol.schema.controller;

import java.util.HashMap;
import java.util.Map;

import ctd.controller.support.AbstractConfigurable;
import ctd.protocol.schema.support.Meta;

public class MetaDictionary extends AbstractConfigurable{
	private static final long serialVersionUID = 6362623894759230338L;
	private final Map<String,Meta> metas = new HashMap<String,Meta>();
	
	public Meta findMeta(String id){
		return metas.get(id);
	}
	
	public void addMeta(Meta meta){
		metas.put(meta.getId(), meta);
	}
}
