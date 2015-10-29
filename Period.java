import java.util.ArrayList;

public class Period {
	
	ArrayList<Student> students;
	Teacher teacher;
	String name;
	
	public Period(ArrayList<Student> s, Teacher t, String n) {
		students = s;
		teacher = t;
		name = n;
	}
	
	public Period(String n) {
		name = n;
		students = new ArrayList<Student>();
	}
	
	public void addStudent(Student s) {
		students.add(s);
	}
	
	public void setTeacher(Teacher t) {
		teacher = t;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
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
