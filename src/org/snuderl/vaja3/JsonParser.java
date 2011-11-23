/**
 * 
 */
package org.snuderl.vaja3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Blaž Šnuderl
 *
 */
public class JsonParser {
	
	public Vreme parse(String s){
		try {
			JSONObject object = new JSONObject(s);
			JSONObject data = (JSONObject) object.get("data");
			JSONArray current = data.getJSONArray("current_condition");
			Vreme v = new Vreme();
			JSONObject temp = current.getJSONObject(0);
			v.temp = temp.getInt("temp_C");
			JSONObject request = data.getJSONArray("request").getJSONObject(0);
			v.query = request.getString("query");
			return v;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
