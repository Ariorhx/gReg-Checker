package Test.GregFastCheck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Internet {
	
	private ArrayList<String> results = new ArrayList<>();
	
	public ArrayList<String> getResults() {
		return results;
	}

	Internet() throws IOException{
		Document doc = Jsoup.connect("https://greg-3d.ru/Expendables.Filament.php")
		            .userAgent("Chrome/86.0.4240.198 Safari/537.36")
		            .referrer("http://www.google.com")
		            .get();
		 set_data(doc);
	}
	
	Internet(String test) throws IOException{
		if(!test.equals("test")) {
			new Internet();
			return;
		}
		Document doc = Jsoup.parse(new File
				("gReg - производитель расходных материалов для 3D печати.html"),
				"UTF-8", "http://example.com/");
		 set_data(doc);
	}
	
	public void set_data(Document doc) {
		Elements filament_data = doc.select("div.card");
		
		for(int i=0; i<filament_data.size(); i++) {
			
			String length = filament_data.get(i).select("div.card-body > div > div:nth-child(2)").text();
			String number = filament_data.get(i).select("> div > div:nth-child(1)").text();
			String color = filament_data.get(i).select("p").text();
			String material = filament_data.get(i).select("div.card-body > div > div:nth-child(1)").text();
			
			if(		length.equals("") | number.equals("") |
					color.equals("")  | material.equals("") ) 
				continue;
			
			results.add(color);
			results.add(material);
			results.add(length);
			results.add(number);
			
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
