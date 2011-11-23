/**
 * 
 */
package org.snuderl.vaja3;


import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

/**
 * @author Blaž Šnuderl
 *
 */
public class XMLParser {
	public Vreme parse(String s){
		Vreme vreme = new Vreme();
		ContentHandler ch = newHandler(vreme);
		try {
			Xml.parse(s, ch);
			return vreme;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	 private ContentHandler newHandler(final Vreme vreme){
	        RootElement root = new RootElement("data");
	        Element request = root.getChild("request");
	        Element query = request.getChild("query");
	        query.setEndTextElementListener(new EndTextElementListener() {
				
				@Override
				public void end(String body) {
					vreme.query = body;
					
				}
			});
	        Element current = root.getChild("current_condition");
	        current.getChild("temp_C").setEndTextElementListener(new EndTextElementListener() {
				
				@Override
				public void end(String body) {
					vreme.temp = Integer.parseInt(body);
					
				}
			});
	        
	        
	        return root.getContentHandler();
	    }
}
