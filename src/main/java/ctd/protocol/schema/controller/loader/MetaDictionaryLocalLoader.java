package ctd.protocol.schema.controller.loader;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import ctd.controller.exception.ControllerException;
import ctd.controller.support.AbstractConfigurableLoader;
import ctd.protocol.schema.controller.MetaDictionary;
import ctd.protocol.schema.support.Meta;
import ctd.util.BeanUtils;
import ctd.util.converter.ConversionUtils;

public class MetaDictionaryLocalLoader extends AbstractConfigurableLoader<MetaDictionary> {

	@SuppressWarnings("unchecked")
	@Override
	public MetaDictionary createInstanceFormDoc(String id, Document doc,long lastModi) throws ControllerException {
		Element root = doc.getRootElement();
		if(root == null){
			throw new ControllerException(ControllerException.PARSE_ERROR,"root element missing.");
		}
		try{
			MetaDictionary dic = ConversionUtils.convert(root,MetaDictionary.class);
			List<Element> els = root.selectNodes("properies/p");
			for(Element el : els){
				dic.setProperty(el.attributeValue("name"), el.getText());
			}
			dic.setLastModify(lastModi);
			dic.setId(id);
			
			els = root.selectNodes("//element");
			
			for(Element el : els){
				String clz = el.attributeValue("class");
				Meta meta = null;
				if(StringUtils.isEmpty(clz)){
					meta = new Meta();
				}
				else{
					meta = (Meta)Class.forName(clz).newInstance();
				}
				List<org.dom4j.Attribute> attrs =  el.attributes();
				for(org.dom4j.Attribute attr : attrs){
					String nm = attr.getName();
					String val = attr.getText();
					BeanUtils.setProperty(meta, nm, val);
				}
				dic.addMeta(meta);
			}
			
			return dic;
		}
		catch(Exception e){
			throw new ControllerException(e,ControllerException.PARSE_ERROR,"metaDictionary[" + id + "] init unknow error:"+ e.getMessage());
		}
		
	}

	

}
