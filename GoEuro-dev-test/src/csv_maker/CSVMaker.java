package csv_maker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import connection.BadCityNameException;
import connection.ConnectionProblemException;
import connection.infoRetriever;

public class CSVMaker {
		
	private final JSONArray jSONArray;
	public CSVMaker(JSONArray jArray) {
		this.jSONArray = jArray;
	}

	//This method takes the jSONArray from this object and makes a csv file from it
	public void makeCSV(String fileName) throws IOException {
		File outputFile=new File(fileName+".csv");
		int i=2;
		while(outputFile.exists()){
			outputFile=new File(fileName+(i++)+".csv");
		}
		FileWriter writer=new FileWriter(outputFile);
		writer.write("");
		for (Object jObject : jSONArray) {
				JSONObject jSONObject = (JSONObject) jObject;
				System.out.println(jSONObject.toString());
				String csvLine=processJSONObject(jSONObject);
				writer.append(csvLine+"\n");
		}
		writer.close();
	}

	/**
	 * This method creates a csv-line string from a JSONObject.
	 * The JSONObject should contain the following fields: _id, name, type and a geo_position JSONObject containing the fields latitude and longitude.  
	 */
	private static String processJSONObject(JSONObject jSONObject) {
		String name=(String)jSONObject.get("name");
		System.out.println("name: "+name);
		
		int id=(int)jSONObject.get("_id");
		System.out.println("id: "+id);
		
		String type=(String)jSONObject.get("type");
		System.out.println("type: "+type);
		//we get the latitude by first getting the JSONOBject geo_position which contains the longitude and latitude
		double latitude=(double) ((JSONObject)jSONObject.get("geo_position")).get("latitude");
		System.out.println("latitude: "+latitude);
		//we get the longitude by first getting the JSONOBject geo_position which contains the longitude and latitude
		double longitude=(double) ((JSONObject)jSONObject.get("geo_position")).get("longitude");
		System.out.println("longitude: "+longitude);
		
		return id+", "+name+", "+type+", "+latitude+", "+longitude;
		

	}


}
