////////////////////////
package main_pod;

public class Run {

	public static void main(String args[]) {

		// GET Get1 = new
		// GET("https://chroniclingamerica.loc.gov/search/pages/results/?format=json");
		// GET GET1 = new
		// GET("https://services6.arcgis.com/bKYAIlQgwHslVRaK/arcgis/rest/services/DailyTestPerformance_ViewLayer/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json","ReportDate");
		// GET Get2 = new GET("https://icanhazdadjoke.com/","joke");
		System.out.println("Ran");
		// GET Get3 = new
		// GET("https://chroniclingamerica.loc.gov/search/pages/results/?format=json",
		// new int [] {0,1,2,3});
		// GET rs = new
		// GET("https://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=21787");
		// GET rs2 = new
		// GET("https://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=21787","item");
		GET rs3 = new GET("http://192.168.1.157:3000/root");
	}
}