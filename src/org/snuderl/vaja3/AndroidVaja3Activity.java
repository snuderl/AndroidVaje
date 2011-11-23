package org.snuderl.vaja3;



import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AndroidVaja3Activity extends Activity {

    /** Called when the activity is first created. */
	String url ="http://free.worldweatheronline.com/feed/weather.ashx";
	String apiKey="6421eb0ff1084035112211";
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
				String response = request(query, RestClient.Response.XML);
				tw1.setText(response);
				
			}
		});
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tw1 = (TextView)findViewById(R.id.queryText);
				String query = (tw1).getText().toString();
				String response = request(query, RestClient.Response.JSON);
				JsonParser parser = new JsonParser();
				Vreme vreme = parser.parse(response);
				
				
				if(vreme!=null){
					StringBuilder rezultat = new StringBuilder();
					rezultat.append("Iskalni niz: ");
					rezultat.append(vreme.query+".\n");
					rezultat.append("Trenutna temperatura je: " + vreme.temp + "°C");
					

					TextView tw2 = (TextView)findViewById(R.id.textView1);
					tw2.setText(rezultat);
				}
				
			}
		});
    }
    
    
    
    String request(String query, RestClient.Response format){
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
}