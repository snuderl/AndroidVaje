package org.snuderl.vaja3;



import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.snuderl.vaja3.RestClient.RequestMethod;
import org.snuderl.vaja3.RestClient.Response;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AndroidVaja3Activity extends Activity {

    /** Called when the activity is first created. */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tw1 = (TextView)findViewById(R.id.queryText);
				String query = (tw1).getText().toString();

				Download download = new Download();
				download.execute(new String[]{query, "JSON"});
				
			}
		});
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tw1 = (TextView)findViewById(R.id.queryText);
				String query = tw1.getText().toString();
				Download download = new Download();
				download.execute(new String[]{query, "JSON"});
				
				
				
				
			}
		});
    }
    
    public void SetTempValue(Vreme vreme){
    	StringBuilder rezultat = new StringBuilder();
		rezultat.append("Iskalni niz: ");
		rezultat.append(vreme.query+".\n");
		rezultat.append("Trenutna temperatura je: " + vreme.temp + "°C");
		

		TextView tw2 = (TextView)findViewById(R.id.textView1);
		tw2.setText(rezultat);
    }
    
    

private class Download extends AsyncTask<String, Void, Vreme>{
	String url ="http://free.worldweatheronline.com/feed/weather.ashx";
	String apiKey="6421eb0ff1084035112211";
	
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Vreme doInBackground(String... params) {
		String query = params[0];
		RestClient.Response method = RestClient.Response.valueOf(params[1]);
		Vreme vreme=null;
		String response = request(query, method);
		if(method==Response.JSON){
		JsonParser parser = new JsonParser();
		vreme = parser.parse(response);
		}
		else if(method==Response.XML){
			XMLParser parser = new XMLParser();
			vreme = parser.parse(response);
		}
		
		
		return vreme;
	}
	
	private String request(String query, RestClient.Response format){
    	RestClient client = new RestClient(url);
    	client.AddParam("q", query);
    	client.AddParam("format", format.toString());
    	client.AddParam("num_of_days", "1");
    	client.AddParam("key", apiKey);
    	
    	try {
    	    client.Execute(RestClient.RequestMethod.GET);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	 
    	String response = client.getResponse();
    	return response;
    }
	
    protected void onPostExecute(Vreme vreme) {
    	if(vreme!=null){
		SetTempValue(vreme);
		}
    }
	
}
}

