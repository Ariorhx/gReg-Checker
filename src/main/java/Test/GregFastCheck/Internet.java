package Test.GregFastCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Internet {
	
	private Document doc;
	private Elements elem;
	
	private ArrayList<String> colors = new ArrayList<>();
	private ArrayList<String> materials = new ArrayList<>();
	private ArrayList<Integer> lengths = new ArrayList<>();
	private ArrayList<Integer> numbers = new ArrayList<>();
	
	static Pattern p = Pattern.compile("\\d+");
	static Matcher m;
	
	public Elements getElem() {
		return elem;
	}
	public ArrayList<String> getColors() {
		return colors;
	}
	public ArrayList<String> getMaterials() {
		return materials;
	}
	public  ArrayList<Integer> getLengths() {
		return lengths;
	}
	public  ArrayList<Integer> getNumbers() {
		return numbers;
	}
	
	Internet() throws IOException{
		 doc = Jsoup.connect("https://greg-3d.ru/Expendables.Filament.php")
		            .userAgent("Chrome/86.0.4240.198 Safari/537.36")
		            .referrer("http://www.google.com")
		            .get();
		 
		 set_data();
	}
	
	public void set_data() {
		Elements filament_data = doc.select("div.card");
		int size = filament_data.size();
		
		for(int i=0; i<size; i++) {
			
			String length = filament_data.get(i).select("div.card-body > div > div:nth-child(2)").text();
			String number = filament_data.get(i).select("> div > div:nth-child(1)").text();
			String color = filament_data.get(i).select("p").text();
			String material = filament_data.get(i).select("div.card-body > div > div:nth-child(1)").text();
			
			if(		length.equals("") | number.equals("") |
					color.equals("")  | material.equals("") ) 
				continue;
			
			m = p.matcher(length);
			m.find();
			lengths.add( Integer.parseInt(m.group(0)) );
			
			m = p.matcher(number);
			m.find();
			numbers.add( Integer.parseInt(m.group(0)) );
			
			colors.add( color );
			materials.add( material );
		}
	}
	
//    public static void old() throws IOException{
//    	Document doc = Jsoup.connect("https://greg-3d.ru/Expendables.Filament.php")
//                .userAgent("Chrome/86.0.4240.198 Safari/537.36")
//                .referrer("http://www.google.com")
//                .get();
//    	Elements links = doc.select("div.card");
//    	
//    	for (int i=0; i< links.size(); i++) {
//          if(links.get(i).select("div.card-body > div > div:nth-child(2)").text().equals("")
//        		  | links.get(i).select("div.card-body > div > div:nth-child(1)").text().equals("")
//        		  | links.get(i).select("> div > div:nth-child(1)").text().equals("")
//        		  | links.get(i).select("p").text().equals(""))
//        	  continue;
////        	  & links.get(i).select("div.card-body > div > div:nth-child(1)").text().equals("PET-G"))
//        	  print_information(links.get(i));
//  	}
//    	
////    	for (Element element : links) {
////            if(element.select("div.card-body > div > div:nth-child(1)").text().equals("PET-G") 
////            		& !element.select("div.card-body > div > div:nth-child(2)").text().equals(""))
////            	print_information(element);
////    	}
//    	
//    }
//    
//   static void print_information(Element element) {
//	   
//	   System.out.print("length = "+element.select("div.card-body > div > div:nth-child(2)").text()+", ");
//	   System.out.print("number = "+element.select("> div > div:nth-child(1)").text()+", ");
//	   System.out.print("color = "+element.select("p").text()+", ");
//	   System.out.println("material = "+element.select("div.card-body > div > div:nth-child(1)").text());
//
////	   int length, number;
////	   String color, material;
////
////	   m = p.matcher(element.select("div.card-body > div > div:nth-child(2)").text());
////	   m.find();
////	   length = Integer.parseInt(m.group(0));
////	   
////	   m = p.matcher(element.select("> div > div:nth-child(1)").text());
////	   m.find();
////	   number = Integer.parseInt(m.group(0));
////	   
////	   color = element.select("p").text();
////	   material = element.select("div.card-body > div > div:nth-child(1)").text();
////	   
////	   System.out.println(new GregFilament(color, material, length, number).toString("short"));
//    }

	
}
