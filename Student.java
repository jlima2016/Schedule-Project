public class Student {
	
	//constant
	String name;
	
	//constructor
	public Student(String n) {
		name = n;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Student s) {
		return name.equals(s.name);
	}
}
