package connection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONTokener;

public class infoRetriever {
	/**
	 * This method gets the city info in JSON format from the
	 * 
	 * @param cityName
	 * @return
	 * @throws BadCityNameException
	 * @throws ConnectionProblemException
	 */
	public static JSONArray getCityInfo(String cityName)
			throws BadCityNameException, ConnectionProblemException {
		//First we form the url form the input
		String queryUrlString = "http://api.goeuro.com/api/v2/position/suggest/en/"
				+ cityName;

		URL url = null;
		try {
			url = new URL(queryUrlString);
		} catch (MalformedURLException e) {
			throw new BadCityNameException(
					"The given city name seems to cause a malformed url exception");
		}
		//Now we try to make open a connection for the url 
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new ConnectionProblemException(
					"There seems to be a problem with the connection that causes an IOexception when trying url.openConnection()");
		}
		
		//Then we handle the return data from the http-request
		try {
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			return handleInputStream(in);
		} catch (IOException ioe) {
			throw new ConnectionProblemException(
					"There seems to be a problem with the connection that causes an IOexception when trying urlConnection.getInputStream()");
		} finally {
			urlConnection.disconnect();
		}

	}
	
	//Helper function to convert an inputstream to a JSONArray 
	private static JSONArray handleInputStream(InputStream in) {
		JSONTokener jTokener=new JSONTokener(in);
		JSONArray jArray=(JSONArray) jTokener.nextValue();
		return jArray;
	}
}
