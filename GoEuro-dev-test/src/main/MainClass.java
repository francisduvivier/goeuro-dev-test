package main;

import java.io.IOException;

import org.json.JSONArray;

import connection.BadCityNameException;
import connection.ConnectionProblemException;
import connection.infoRetriever;
import csv_maker.CSVMaker;

public class MainClass {
	public static void main(String[] args) throws IOException{
		String cityName=args[0];
		//Check that the input is not empty
		if(cityName==null||cityName.equals("")){
			System.out.println("No city name given, please give a city name.");
			return;
		}
		try {
			//We get the city info with the online api and retrieve it in a JSONArray.
			JSONArray jArray=infoRetriever.getCityInfo(cityName);
			//We make a csv from the jArray
			CSVMaker cm=new CSVMaker(jArray);
			try{
			cm.makeCSV(cityName);
			}catch(IOException ioe){
				System.out.println("There was a problem writing the csv file");
			}
		} catch (BadCityNameException e) {
			System.out.println("It seems you have given an invalid city name");
		} catch (ConnectionProblemException e) {
			System.out.println("There is a problem trying to receive info for this city name, try another name or check your internet connection");
		}
	}
}
