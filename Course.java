import java.util.ArrayList;

public class Course {

	//maximum initial class size
	private final int CLASS_SIZE = 12;
	
	//constant
	ArrayList<Student> possibleStudents;
	ArrayList<Teacher> possibleTeachers;
	String name;
	
	//year-long, semester, trimester
	String time;
	
	//does the class take flex (Y or N)
	String flex;
	
	
	//constuctor
	public Course(String n, String t, String f) {
		name = n;
		time = t;
		flex = f;
		possibleStudents = new ArrayList<Student>();
		possibleTeachers = new ArrayList<Teacher>();
	}
	
	public void addStudent(Student s) {
		possibleStudents.add(s);
	}
	
	public void addTeacher(Teacher t) {
		possibleTeachers.add(t);
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumStudents() {
		return possibleStudents.size();
	}
	
	
	//create random periods for the course
	public ArrayList<Period> initRandomPeriods() {
		
		ArrayList<Period> periods = new ArrayList<Period>();
		
		//number of classes so that no class can have more that CLASS_SIZE students
		int numClasses = (int)(possibleStudents.size()/CLASS_SIZE)+1;
		
		//each period has a default of CLASS_SIZE students
		for (int p = 0; p < numClasses; p++) {
			periods.add(new Period(name));
			
			//put a random teacher in each period
			periods.get(p).setTeacher(possibleTeachers.get((int)(Math.random()*possibleTeachers.size())));
		}
		
		for (int s = 0; s < possibleStudents.size(); s++) {
			//the best line of code ever written
			//make all of the class sizes equal to start with
			int currentClass = s % numClasses;
			periods.get(currentClass).addStudent(possibleStudents.get(s));
		}
		
		return periods;
	}
	
	//create periods based on a given list of periods
	//public ArrayList<Period> initSeededPeriods(ArrayList<Period> p) {
	
	//}
	
	
	public String toString() {
	
		String toPrint = "\n\n\n\n";
		toPrint += name;
		
		toPrint += "\n\nStudents:";
		for (int i = 0; i < possibleStudents.size(); i++) {
			toPrint += possibleStudents.get(i).toString() + "\n";
		}
		
		toPrint += "\n\nTeachers:";
		for (int i = 0; i < possibleTeachers.size(); i++) {
			toPrint += possibleTeachers.get(i).toString() + "\n";
		}
		
		return toPrint;
	}
}
