/*
	Initializes all the courses from a csv
*/

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.io.PrintWriter;

public class CreateCourses {
	
	public static void main(String args[]) {
		
		//initialize the courses
		File file = new File(args[0]);	
		Scanner input = null;		
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
		
		//the list of courses
		ArrayList<Course> courses = new ArrayList<Course>();
		
		//get each course and add to the list
		while (input.hasNextLine()) {
			String line = input.nextLine();
		
			String name = line.split(",")[0];
			String time = line.split(",")[1];
			String flex = line.split(",")[2];
			
			Course newCourse = new Course(name, time, flex);
			courses.add(newCourse);
		}
		
		//System.out.println(courses);
		
		
		
		//give each course the correct list of students
		file = new File(args[1]);	
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
		
		//add each student
		while (input.hasNextLine()) {
		
			String line = input.nextLine();
			
			Student newStudent = new Student(line.split(",")[0]);
			String [] currentLine = line.split(",");
			
			//each class for current student
			for (int i = 1; i < currentLine.length; i++) {
				String name = currentLine[i];
				
				//add this student to the class for each course
				for (int c = 0; c < courses.size(); c++) {
					if (courses.get(c).getName().equals(name))
						courses.get(c).addStudent(newStudent);
				}
			}
		}
		
		
		//give each course the correct list of teachers
		file = new File(args[2]);	
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
		
		//add each teacher
		while (input.hasNextLine()) {
			
			String line = input.nextLine();
			
			Teacher newTeacher = new Teacher(line.split(",")[0]);
			String [] currentLine = line.split(",");
			
			//each class for current teacher
			for (int i = 1; i < currentLine.length; i++) {
				String courseName = currentLine[i];
				
				//add this teacher to the class for each course
				for (int c = 0; c < courses.size(); c++) {
					if (courses.get(c).getName().equals(courseName))
						courses.get(c).addTeacher(newTeacher);
				}
			}
		}
		
		
		//System.out.println(courses);
		
		//Schedule test = new Schedule(courses);
		//System.out.println(test);
		
		Schedule best = new Schedule(courses);
		int bestFitness = best.calculateFitness();
		
		System.out.println("gen: origin");
		System.out.println("Current Best Fitness: " + best.calculateFitness());
		
		String csv = "";
		
		//only change the fittest at the end of a generation
		Schedule tempBest = best;
		int tempBestFitness = bestFitness;
		
		/*
		for (int s = 0; s < 10000; s++) {
			
			Schedule newSchedule = new Schedule(courses);
			int fitness = newSchedule.calculateFitness();
			
			if (fitness < bestFitness) {
				best = newSchedule;
				bestFitness = fitness;
				System.out.println("Current Best Fitness: " + bestFitness);
			}
		}
		*/
		
		//go through each generation
		for (int g = 0; g < 1000; g++) {
			
			for (int s = 0; s < 1000; s++) {
			
				Schedule newSchedule = new Schedule(courses, best);
				int fitness = newSchedule.calculateFitness();
				
				if (fitness < bestFitness) {
					tempBest = newSchedule;
					tempBestFitness = fitness;
				}
			}
			
			//make a new seed if there is one worthy
			if (tempBestFitness < bestFitness) {
			
				System.out.println("gen: " + g);
				best = tempBest;
				bestFitness = tempBestFitness;
				System.out.println("Current Best Fitness: " + bestFitness);
			}
		}
		
		System.out.println(best);
		System.out.println(best.simplePrint());
		
		
		//create csv file for graphs
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
				  new FileOutputStream("test.csv"), "utf-8"));
			writer.write(csv);
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) { }
		}
		
		
	}
}
