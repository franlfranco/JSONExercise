package jsonexercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONExercise {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.print("Enter student name: ");
		String name = input.nextLine();

		// create a new JSON Object
		JSONObject root = new JSONObject();

		// put the name name-value pair
		root.put("name", name);

		JSONArray courses = new JSONArray();

		while (true) {
			// get the course name
			System.out.print("Enter course name: ");
			String course = input.nextLine();

			// check to see if the use hit enter
			if (course.length() == 0) {
				break;
			}

			// get the grade
			System.out.print("Enter grade: ");
			int grade = input.nextInt();

			if (input.hasNextLine()) {
				input.nextLine();
			}

			// create a JSON object and array and store a class object in it
			JSONObject courseObject = new JSONObject();
			courseObject.put("grade", grade);
			courseObject.put("name", course);

			// add the course to the array
			courses.add(courseObject);
		}

		// add the array to the root object
		root.put("courses", courses);

		System.out.println(root.toJSONString());

		// now we'll create the file and write the JSON structure to it
		File file = new File("StudentGrades.txt");

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.print(root.toJSONString());
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}

		System.out.println("File created succesfully\n\n Hit return to display");
		input.nextLine();

		try {
			input = new Scanner(file);
			StringBuilder jsonIn = new StringBuilder();
			while (input.hasNextLine()) {
				jsonIn.append(input.nextLine());
			}
			System.out.println(jsonIn.toString());
			
			JSONParser parser = new JSONParser();

			JSONObject objRoot = (JSONObject) parser.parse(jsonIn.toString());

			System.out.printf("Student name is %s\n", objRoot.get("name").toString());

			JSONArray coursesIn = (JSONArray) objRoot.get("courses");

			for (int i = 0; i < coursesIn.size(); i++) {
				JSONObject courseIn = (JSONObject) coursesIn.get(i);
				long gradeIn = (long) courseIn.get("grade");
				String nameIn = (String) courseIn.get("name");

				System.out.printf("Course %s: grade %d\n", nameIn, gradeIn);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
}
