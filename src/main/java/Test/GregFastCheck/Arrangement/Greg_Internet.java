package Test.GregFastCheck.Arrangement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Test.GregFastCheck.Interfaces.IInternet;

public class Greg_Internet implements IInternet{
	
	Document doc;

	public Greg_Internet(){
		try {
			doc = Jsoup.connect("https://greg-3d.ru/Expendables.Filament.php")
			            .userAgent("Chrome/86.0.4240.198 Safari/537.36")
			            .referrer("http://www.google.com")
			            .get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Greg_Internet(String test){
		if(!test.equals("test")) {
			return;
		}
		try {
			doc = Jsoup.parse(new File
					("gReg - производитель расходных материалов для 3D печати.html"),
					"UTF-8", "http://example.com/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> get_data() {
		ArrayList<String> results = new ArrayList<>();
		
		Elements filament_data = doc.select("div.card");
		
		for(int i=0; i<filament_data.size(); i++) {
			
			String length = filament_data.get(i).select("div.card-body > div > div:nth-child(2)").text();
			String available_in_store = filament_data.get(i).select("> div > div:nth-child(1)").text();
			String color = filament_data.get(i).select("p").text();
			String material = filament_data.get(i).select("div.card-body > div > div:nth-child(1)").text();
			
			if(		length.equals("") | available_in_store.equals("") |
					color.equals("")  | material.equals("") ) 
				continue;
			
			results.add(color);
			results.add(material);
			results.add(length);
			results.add(available_in_store);
		}
		return results;
	}
}
