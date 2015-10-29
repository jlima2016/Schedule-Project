import java.util.ArrayList;

public class Schedule {
	
	//all courses
	ArrayList<Course> allCourses;
	
	//each of the seven periods has a list of courses
	ArrayList<ArrayList<Period>> periods;
	
	//constructor for random schedule
	public Schedule(ArrayList<Course> a) {
		periods = new ArrayList<ArrayList<Period>>(7);
		
		//initialize array of periods
		for (int i = 0; i < 7; i++) {
			periods.add(new ArrayList<Period>());
		}
		
		allCourses = a;
		initRandomSchedule();
	}
	
	//constructor for seeded schedule
	public Schedule(ArrayList<Course> a, Schedule s) {
		periods = new ArrayList<ArrayList<Period>>(7);
		
		//initialize array of periods
		for (int i = 0; i < 7; i++) {
			periods.add(new ArrayList<Period>());
		}
		
		allCourses = a;
		initSeededSchedule(s);
	}
	
	
	//creates initial random schedules
	//each course begins with 1 class per 12 students
	public void initRandomSchedule() {
		
		//get every period (class meeting)
		ArrayList<Period> allPeriods = new ArrayList<Period>();
		
		//add every period from each course to the full list of periods
		for (int c = 0; c < allCourses.size(); c++) {
		
			//add all periods of a single course to the full list of periods
			allPeriods.addAll(allCourses.get(c).initRandomPeriods());
		}
		
		
		//put every period in a random slot
		for (int p = 0; p < allPeriods.size(); p++) {
		
			int slot = (int)(Math.random()*7);
			periods.get(slot).add(allPeriods.get(p));
		}
	}
	
	//creates a schedule that is a child of the given schedule
	public void initSeededSchedule(Schedule seed) {
		
	}
	
	
	/*
		fitness function (gives a score to the schedule)
		
		Possible constraints:
		*Students' classes are all different periods
		Students' flexes do not overlap
		Teachers' classes are all different periods
		Teachers' flexes do not overlap
		Size of each class is as small as possible
		Class meets as little as possible
		Teachers teach the minimum amount of classes
		
		Optional:
		Classes are spread out for students and teachers
		happiness
		
	*/
	public int calculateFitness() {
		
		//fitness score
		//closer to 0 is better
		int score = 0;
		
		//constraint 1
		//students have classes on different periods
		for (ArrayList<Period> a : periods) {
			
			//the students in the current period
			ArrayList<Student> studentsInPeriod = new ArrayList<Student>();
			
			for (Period p : a) {
				
				ArrayList<Student> currentStudents = p.getStudents();
				
				for (Student s : currentStudents) {
					
					//student already has a class this period
					if (studentsInPeriod.contains(s)) {
						score += 5;
					}
					
					//student does not already have a class this period
					else {
						studentsInPeriod.add(s);
					}
				}
			}
		}
		
		return score;
	}
	
	
	public String toString() {
	
		String toPrint = "";
		
		for (int i = 0; i < 7; i++) {
			toPrint += "************PERIOD " + (i+1) + "************";
		
			for (Period p : periods.get(i)) {
				toPrint += p.toString() + "\n";
			}
			
			toPrint += "\n";
		}
		
		return toPrint;
	}
}
