import java.util.ArrayList;

public class Schedule {
	
	//the chance that any period will be switched to another slot
	double periodMoveChance = 0.2;
	
	//the chance that any student will be switched to another class
	double studentMoveChance = 0.2;
	
	//the chance that the teacher for this class will be changed
	double teacherMoveChance = 0.3;
	
	
	//all courses
	ArrayList<Course> allCourses;
	
	//each of the seven periods has a list of courses
	ArrayList<ArrayList<Period>> periods;
	ArrayList<Period> allPeriods;
	
	//constructor for random schedule
	public Schedule(ArrayList<Course> a) {
		
		periods = new ArrayList<ArrayList<Period>>(7);
		allPeriods = new ArrayList<Period>();
		
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
		allPeriods = new ArrayList<Period>();
		
		for (int i = 0; i < 7; i++) {
			periods.add(new ArrayList<Period>());
		}
		
		allPeriods.addAll(s.allPeriods);
		allCourses = a;
		initSeededSchedule();
	}
	
	
	//creates initial random schedules
	//each course begins with 1 class per 12 students
	public void initRandomSchedule() {
		
		//add every period from each course to the full list of periods
		for (int c = 0; c < allCourses.size(); c++) {
		
			//add all periods of a single course to the full list of periods
			allPeriods.addAll(allCourses.get(c).initRandomPeriods());
		}
		
		
		//put every period in its slot
		for (int p = 0; p < allPeriods.size(); p++) {
			periods.get(allPeriods.get(p).getSlot()).add(allPeriods.get(p));
		}
	}
	
	//creates a schedule that is a child of the given schedule
	public void initSeededSchedule() {

		ArrayList<Period> newAllPeriods = new ArrayList<Period>();

		ArrayList<Period> currentCourse = new ArrayList<Period>();
		String currentName = "";
		
		
		//add all the new periods
		for (int p = 0; p < allPeriods.size(); p++) {
			
			//add this period to the list of periods for this course
			if (allPeriods.get(p).getName().equals(currentName)) {
				currentCourse.add(allPeriods.get(p));
			}
			
			//start a new list of periods for this course
			else {
				
				//calls method to make new periods for this course
				newAllPeriods.addAll(initSeededPeriods(currentCourse));
				currentCourse.clear();
				
				//start a new list of periods for this new course
				currentName = allPeriods.get(p).getName();
				currentCourse.add(allPeriods.get(p));
			}
			
		}
		
		//add the final course
		newAllPeriods.addAll(initSeededPeriods(currentCourse));
		currentCourse.clear();
		
		//move the periods around in the schedule
		for (int np = 0; np < newAllPeriods.size(); np++) {
		
			//change the slot of this period
			if (Math.random() < periodMoveChance) {
				int slot = (int)(Math.random()*7);
				newAllPeriods.get(np).setSlot(slot);
			}
			
			//put this period in its slot
			newAllPeriods.get(np).setSlot(newAllPeriods.get(np).getSlot());
		}
		
		//put every period in its slot
		for (int xp = 0; xp < newAllPeriods.size(); xp++) {
			periods.get(newAllPeriods.get(xp).getSlot()).add(newAllPeriods.get(xp));
		}
				
		allPeriods.clear();
		allPeriods.addAll(newAllPeriods);
	}
	
	
	/*
		fitness function (gives a score to the schedule)
		
		Possible constraints:
		*Students' classes are all different periods
		Students' flexes do not overlap
		*Teachers' classes are all different periods
		Teachers' flexes do not overlap
		Size of each class is as small as possible
		Class meets as little as possible
		Teachers teach the minimum amount of classes
		
		Optional:
		Classes are spread out for students and teachers
		happiness
		
	*/
	public int calculateFitness() {
		
		//individual # of constraints broken
		int studentConflicts = 0;
		int teacherConflicts = 0;
		
		//constraint 1 and 2
		//students have classes on different periods
		for (ArrayList<Period> a : periods) {
			
			//the students in the current period
			ArrayList<Student> studentsInPeriod = new ArrayList<Student>();
			ArrayList<Teacher> teachersInPeriod = new ArrayList<Teacher>();
			
			for (Period p : a) {
				
				ArrayList<Student> currentStudents = p.getStudents();
				Teacher currentTeacher = p.getTeacher();
				
				//check if each student already has a class this period
				for (Student s : currentStudents) {
					
					//student already has a class this period
					if (studentsInPeriod.contains(s)) {
						studentConflicts++;
					}
					
					//student does not already have a class this period
					else {
						studentsInPeriod.add(s);
					}
				}
				
				//check if the teacher already has a class this period
				if (teachersInPeriod.contains(currentTeacher)) {
					teacherConflicts++;
				}
				
				else {
					teachersInPeriod.add(currentTeacher);
				}
			}
		}
		
		return (studentConflicts*5) + (teacherConflicts*13);
	}
	
	
	
	
	//create periods based on a given list of periods
	public ArrayList<Period> initSeededPeriods(ArrayList<Period> per) {
		
		//move students to other periods of this class
		for (int p = 0; p < per.size(); p++) {
		
			for (int s = 0; s < per.get(p).numStudents(); s++) {
			
				//move this student to a different period
				if (Math.random() < studentMoveChance) {
					int randPeriod = (int)(Math.random()*per.size());
					
					per.get(randPeriod).addStudent(per.get(p).removeStudent(s));
				}
				
				//change the teacher in this period
				if (Math.random() < studentMoveChance) {
					per.get(p).setTeacher(per.get(p).possibleTeachers.get((int)(Math.random()*per.get(p).possibleTeachers.size())));
				}
			}
		}
		
		return per;
	}
	
	
	public ArrayList<Period> getAllPeriods() {
		return allPeriods;
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
	
	
	
	
	public String simplePrint() {
	
		String toPrint = "";
		int totalPeriods = 0;
		
		for (int i = 0; i < 7; i++) {
			toPrint += "Period " + (i+1) + ": ";
			toPrint += periods.get(i).size() + "\n";
			totalPeriods += periods.get(i).size();
		}
		
		toPrint += "\nTotal Periods: " + totalPeriods + "\n";
		return toPrint;
	}
	
	public String printAllPeriods() {
		
		String toPrint = "";
		
		for (int i = 0; i < allPeriods.size(); i++) {
			toPrint += allPeriods.get(i).toString() + "\n";
		}
		
		return toPrint;
	}
}
