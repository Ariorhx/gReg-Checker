package Test.GregFastCheck;

import java.io.IOException;
import java.util.ArrayList;

public class App {
    
	public static void main(String[] args) throws IOException {
		new App();
	}
	
	App() throws IOException{
//		Internet.old();
		Internet internet = new Internet();
		
		ArrayList<String> colors = internet.getColors(); 
		ArrayList<String> materials = internet.getMaterials();
		ArrayList<Integer> lengths = internet.getLengths(); 
		ArrayList<Integer> numbers = internet.getNumbers(); 

		for(int i = 0; i < colors.size(); i++)
			if(materials.get(i).equals("PET-G"))
				System.out.println(new GregFilament(
						colors.get(i), materials.get(i), lengths.get(i), numbers.get(i) )
						.toString("short") );
	}
}
