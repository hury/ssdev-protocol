package ctd.protocol.schema.controller;

import org.apache.commons.lang.StringUtils;

import ctd.controller.exception.ControllerException;
import ctd.controller.support.AbstractController;
import ctd.protocol.schema.support.Meta;

public class MetaDictionaryController extends AbstractController<MetaDictionary> {
	private static MetaDictionaryController instance;
	
	public MetaDictionaryController(){
		instance = this;
		setLoader(new MetaDictionaryLocalLoader());
	}
	
	public static MetaDictionaryController instance(){
		if(instance == null){
			instance = new MetaDictionaryController();
		}
		return instance;
	}
	
	
	public Meta findMeta(String id) throws ControllerException{
		String dicId = StringUtils.substringBeforeLast(id, ".");
		String metaId = StringUtils.substringAfterLast(id, ".");
		MetaDictionary dic = get(dicId);
		
		return dic.findMeta(metaId);
	}
}
