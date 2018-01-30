package gmit;

public class User {
	private String name;
	private String address;
	private String PPS;
	private String age;
	private String weight;
	private String height;
	
	public User(String name, String address, String pPS, String age, String weight, String height) {
		super();
		this.name = name;
		this.address = address;
		PPS = pPS;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}
	
	public User() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPPS() {
		return PPS;
	}
	public void setPPS(String pPS) {
		PPS = pPS;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "User Registered with following details: "
				+ "\nName: " + name + "\nAddress: " + address + "\nPPS: " + PPS + "\nAge: " + age + "\nWeight: " + weight
				+ "\nHeight: " + height +"\n";
	}
	
}
