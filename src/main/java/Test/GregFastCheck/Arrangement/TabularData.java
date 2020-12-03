package Test.GregFastCheck.Arrangement;

import Test.GregFastCheck.Interfaces.Filament;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TabularData {
	private StringProperty color;
	private StringProperty material;
	private IntegerProperty length;
	private StringProperty availiable_in_store;
	
	private Filament filament;
	public Filament getFilament() {
		return filament;
	}
	
	public StringProperty getColor() {
		return color;
	}
	public StringProperty getMaterial() {
		return material;
	}
	public IntegerProperty getLength() {
		return length;
	}
	public StringProperty getAvailiable_in_store() {
		return availiable_in_store;
	}
	
	public TabularData(Filament f, String s){
		material = new SimpleStringProperty(f.getMaterial());
		color = new SimpleStringProperty(f.getColor());
		length = new SimpleIntegerProperty(f.getLength());
		availiable_in_store = new SimpleStringProperty(f.getAvailable_in_store()+s);
		filament = f;
	}
}
