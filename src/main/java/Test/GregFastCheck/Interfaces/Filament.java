package Test.GregFastCheck.Interfaces;

public abstract class Filament {
	private String color;
	private String material;
	private int length;
	private int available_in_store;
	private int id = -1;
	
	public String getColor() {
		return color;
	}
	public String getMaterial() {
		return material;
	}
	public int getLength() {
		return length;
	}
	public int getAvailable_in_store() {
		return available_in_store;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Filament(String color, String material, int length, int number) {
		this.color = color;
		this.material = material;
		this.length = length;
		this.available_in_store = number;
	}
	
	public Filament(String color, String material, int length, int number, int id) {
		this.color = color;
		this.material = material;
		this.length = length;
		this.available_in_store = number;
		this.id = id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("color = ").append("\"").append(this.getColor()).append("\", ");
		sb.append("material = ").append("\"").append(this.getMaterial()).append("\", ");
		sb.append("length = ").append(this.getLength()).append(", ");
		sb.append("number = ").append(this.getAvailable_in_store());
		return sb.toString();
	}
	
	public String toString(String is_short) {
		if(!is_short.equals("short")) return this.toString();
			
		StringBuilder sb = new StringBuilder();
		sb.append("\"").append(this.getColor()).append("\", ");
		sb.append("\"").append(this.getMaterial()).append("\", ");
		sb.append(this.getLength()).append(", ");
		sb.append(this.getAvailable_in_store());
		return sb.toString();
	}
	
	public boolean equals(Filament f) {
		if (!this.color.equals( f.color )) return false;
		if (!this.material.equals( f.material )) return false;
		if (!(this.length == f.length)) return false; 
		
		return true;
	}
}
