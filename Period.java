import java.util.ArrayList;
 
public class Period {
	
	ArrayList<Student> students;
	Teacher teacher;
	String name;
	
	//I currently need this for creating a seeded schedule
	ArrayList<Teacher> possibleTeachers;
	
	//year-long, semester, trimester
	String time;
	
	//does the class take flex (Y or N)
	String flex;
	
	//changes during mutation
	int slot;
	
	//constructor
	//takes in name, time, flex (constant things) and slot
	public Period(String n, String t, String f, int s, ArrayList<Teacher> pt) {
		name = n;
		time = t;
		flex = f;
		slot = s;
		possibleTeachers = pt;
		students = new ArrayList<Student>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addStudent(Student s) {
		students.add(s);
	}
	
	public Student removeStudent(int i) {
		return students.remove(i);
	}
	
	public void setTeacher(Teacher t) {
		teacher = t;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public int numStudents() {
		return students.size();
	}
	
	public void setSlot(int s) {
		slot = s;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public String toString() {
		String toPrint = "\n\n\n";
		toPrint += name;
		
		toPrint += "\nStudents:";
		for (int i = 0; i < students.size(); i++) {
			toPrint += students.get(i).toString() + "\n";
		}
		
		toPrint += "\nTeacher:" + teacher.toString();
		
		return toPrint;
	}
}
