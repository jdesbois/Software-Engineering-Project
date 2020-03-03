

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

	// Stores model
	private static Model model;
	// Stores view
	private static View view;
	// Initialises Scanner to listen for input
	private static Scanner userInput = new Scanner(System.in);
	
	/**
	 * Constructor
	 */
	public Controller(Model m, View v) {
		
		// Initialises view and model
		model = m;
		view = v;
	}
	
	/**
	 * Prompts user to select what level of access they want
	 * @return int 
	 */
	public int selectUser() {
		 
		// Displays user selection screen
		view.selectUser();
		// Stores user input
		int selected = userInput.nextInt();
				
		// If user does not select a valid input
		// ask for a valid input and store input
		while(selected < 0 || selected > 4) {
			
			// Warns user of invalid input
			view.incorrectInput();
			
			// Stores new input
			selected = userInput.nextInt();
		}
				
		return selected;
	}
	
	/**
	 * Shows course director the available options and responds to input
	 * Will display courses that the director can assign requirements to
	 */
	public boolean courseDirectorOptions() {
		
		// Displays course director options
		view.courseDirectorOptions();
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Will keep asking for input until valid input received
		while(selected < 0 || selected > 2) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays course director options
			view.courseDirectorOptions();
			
			// Stores user input
			selected = userInput.nextInt();
		}
		
		// If user has selected to return to user selection return false
		if(selected == 0) {
			
			return true;
			// If user selects to add new course
		} else if (selected == 1) {
			
			unapprovedCourses();
			return false;
			// Else let user assign requirements
		} else {
			
			createCourse();
			return false;
		}
	}
	
	/**
	 * Shows administrator the available options and responds to input
	 * Will show staff members 
	 * Will show teaching requirements which can be selected to be filled
	 * Will allow creation of new staff 
	 * Will allow training to be assigned
	 */
	public boolean adminOptions() {
		
		// Shows administrator options
		view.adminOptions();
		
		// Stores user selection
		int selected = userInput.nextInt();
		
		while(selected < 0 || selected > 4) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays choices
			view.adminOptions();
			
			selected = userInput.nextInt();
		}
		
		// If 0 selected return to user selection
		if(selected == 0) {
			
			return true;
			// If 1 selected display staff
		} else if(selected == 1) {
			
			view.displayStaff(model.returnStaffList()); // Add if statement to display empty array message
			// If 2 selected enter course assignment menu
		} else if(selected == 2) {
			
			fillRequirements(model.findUnapprovedCourses());
			// If 3 selected enter add staff menu
		} else if(selected == 3){
			
			createNewStaffMember();
			// If 4 selected show untrained staff
		} else {
			
			train();
		}
		
		return false;
	}
	
	/**
	 * Shows PTT Director the available options and responds to input
	 * Will allow to the director to approve teaching requests
	 */
	public boolean pttDirectorOptions() {
		
		// Displays ptt director options
		view.pttDirectorOptions();
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until user input is valid
		while(selected < 0 || selected > 2) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays course director options
			view.pttDirectorOptions();
			// Stores user input
			selected = userInput.nextInt();
		}
		
		// If user choses to exit, return false
		if(selected == 0 ) {
			
			return true;
		} else if(selected ==  1) {
			
			// Displays requirement approval menu
			approveCourse();
		} else {
			
			// If user selected to view approved courses show approved courses
			unapproveCourse();			
		}
		
		return false;
	}
	
	/**
	 * Shows courses without requirements and allows user to add requirements
	 */
	private static void unapprovedCourses() {
		
		// Stores courses without requirements
		ArrayList<Course> courses = model.findUnapprovedCourses();
		// Displays courses
		view.displayCourses(courses);
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until valid input is selected
		while(selected < 0 || selected > courses.size()) {
					
			// Displays wrong input message
			view.incorrectInput();
			// Displays courses
			view.displayCourses(courses);
			// Stores user input
			selected = userInput.nextInt();
		}
		
		// If user chooses to exit, return false
		if(selected == 0 ) {
			
			return;
		}
		
		// Allows user to assign course requirements
		assignRequirements(courses.get(selected -1));
	}
	/**
	 * Allows course director to assign requirements to a given course
	 */
	private static void assignRequirements(Course course) {
		
		// Displays assign requirements message
		view.askRequirement();
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// If user chooses to exit, return false
		if(selected == 0) {
			
			return;
		}
		
		// Assigns requirement
		model.assignCourseRequirements(course, selected);
	}
	
	/**
	 * Allows Course Director to create new courses
	 */
	private static void createCourse() {
		
		// Asks user to input a course name
		view.createCourse();
		// Stores user input
		String name = userInput.next();
		
		// Ask user to input course requirements
		view.askRequirement();
		// Stores user input
		int requirement = userInput.nextInt();
		
		model.createCourse(name, requirement);
	}
	
	/**
	 * Allows administrator to create new staff members
	 */
	private static void createNewStaffMember() {
		
		view.createStaff();
		
		// Stores staff member name
		String name = userInput.next();
		
		// Calls model to create staff member
		model.createStaff(name);
	}
	
	/**
	 * Allows admin to view course requirements and add or remove staff
	 * @param ArrayList<Course>
	 */
	private static void fillRequirements(ArrayList<Course> requirements) {
		
		// Displays list of courses
		view.displayCourses(requirements);
		
		// Stores user selection
		int selected = userInput.nextInt();
		
		// Loops until valid input is selected
		while(selected < 0 || selected > requirements.size()) {
					
			// Displays wrong input message
			view.incorrectInput();
			// Displays courses
			view.displayCourses(requirements);
			// Stores user input
			selected = userInput.nextInt();
		}
				
		// If user wants to return to admin options, return
		if(selected == 0) {
			
			return;
		}
		
		// Stores course selection
		Course course = requirements.get(selected - 1);
		
		courseOptions(course);
	}
	
	/**
	 * Allows admin to assign staff to training
	 */
	private static void train() {
		
		// Stores untrained staff
		ArrayList<Staff> untrainedStaff = model.UntrainedStaff();
		
		// Displays untrained staff
		view.displayStaff(untrainedStaff);
		// Asks user to select a staff member to train
		view.trainStaffOptions(); // only display if staff members != 0
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loop until user selects valid input
		while(selected < 0 || selected > untrainedStaff.size()) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays untrained staff
			view.displayStaff(untrainedStaff);
			// Asks user to select a staff member to train
			view.trainStaffOptions();
			// Stores user input
			selected = userInput.nextInt();			
		}
		
		// If user selects to exit return false
		if(selected == 0) {
			
			return;
		}
		
		model.train(untrainedStaff.get(selected - 1));
	}
	
	/**
	 * Displays the course requirements and add/remove staff options
	 * @param Course
	 */
	private static void courseOptions(Course course) {
		
		// Displays course options
		view.displayCourse(course);
		// display course options
		view.displayAdminCourseOptions();
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loop until user selects valid input
		while(selected < 0 || selected > 2) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays courses
			view.displayCourse(course);
			// Stores user input
			selected = userInput.nextInt();			
		}
		
		switch (selected) {
		
		case 0:
			return;
		case 1:
			assignStaff(course);
		case 2:
			removeStaff(course);
		}
	}
	
	/**
	 * Allows administrator to assign staff to a course
	 */
	private static void assignStaff(Course course) {
		
		// Stores available staff
		ArrayList<Staff> availableStaff = model.AvailableStaff();
		
		// Shows available staff to assign
		view.displayStaff(availableStaff);
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until valid input is selected
		while(selected < 0 || selected > availableStaff.size()) {
				
			// Displays wrong input message
			view.incorrectInput();
			// Displays courses
			view.displayStaff(availableStaff);
			// Stores user input
			selected = userInput.nextInt();
		}
		
		// If user selects to exit return false
		if(selected == 0) {
			
			return;
		}
		
		// Adds selected staff member to course
		model.addStaffToCourse(course, availableStaff.get(selected - 1));
	}

	
	/**
	 * Allows administrator to remove staff member from course
	 */
	private static void removeStaff(Course course) {
		
		// Stores staff associated with this course
		ArrayList<Staff> staff = model.UnavailableStaff();
		
		// Displays staff
		view.displayStaff(staff);
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until user enters valid input
		while(selected < 0 || selected > staff.size()) {
			
			// Displays wrong input message
			view.incorrectInput();
			// Displays courses
			view.displayStaff(staff);
			// Stores user input
			selected = userInput.nextInt();
		}
		
		// If user selects to exit return false
		if(selected == 0) {
			
			return;
		}
		
		// Remove selected staff from selected course
		model.removeStaffFromCourse(course, staff.get(selected - 1));
	}
	
	/**
	 * Allows the PTT Director to approve courses
	 */
	private static void approveCourse() {
		
		// Gets approved courses
		ArrayList<Course> unapprovedCourses = model.findUnapprovedCourses();
		
		// Shows approved courses and asks for user input
		view.displayCourses(unapprovedCourses);
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until user enters valid input
		while(selected < 0 || selected > 1) {
					
			// Displays wrong input message
			view.incorrectInput();
			// Asks user to approve a course requirement
			view.displayCourses(unapprovedCourses);						
			// Stores user input
			selected = userInput.nextInt();
		}
				
		// If user selects to exit return false
		if(selected == 0) {
					
			return;
		}
			
		// Requirement approved
		model.giveRequestApproval(unapprovedCourses.get(selected - 1), true);
	}
	
	/**
	 * Allows PTT Director to view approved courses and unapprove if needed
	 */
	private static void unapproveCourse() {
		
		// Gets approved courses
		ArrayList<Course> approvedCourses = model.findApprovedCourses();
		
		// Shows approved courses and asks for user input
		view.displayCourses(approvedCourses);
		
		// Stores user input
		int selected = userInput.nextInt();
		
		// Loops until user enters valid input
		while(selected < 0 || selected > 1) {
					
			// Displays wrong input message
			view.incorrectInput();
			// Asks user to approve a course requirement
			view.displayCourses(approvedCourses);						
			// Stores user input
			selected = userInput.nextInt();
		}
				
		// If user selects to exit return false
		if(selected == 0) {
					
			return;
		}
				
		// Unapproves Course
		model.giveRequestApproval(approvedCourses.get(selected - 1), false);
	}
	
	/**
	 * Closes scanner and writed to file
	 */
	public void exit() {
		
		// Writes state to file
		//model.writeToFile();
	}
}