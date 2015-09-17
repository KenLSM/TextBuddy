import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Ken Lee Shu Ming
 *
 */

public class TextBuddy {
	/**
	 * Const string declaration
	 */
	private static final String ERROR_FAIL_FORMAT = "Error: Wrong format of file. Ignoring all contents.";
	private static final String ERROR_UNABLE_TO_READLINE = "Error: Unable read line";
	private static final String ERROR_FAIL_READ_ALL_LINE = "Error: Failed to retrieve all lines of text.";
	private static final String SUCCESS_FORMAT = "Data format is correct.";
	private static final String ERROR_FILE_CREATION = "Error: Can't create file";

	private static final String ADD_TO_FILE = "added to %1$s: \"%2$s\"";
	private static final String WELCOME_MSG = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String FILE_INVALID_REPLY = "File not found. Provided file name is %1$s. \nWill proceed to create and use %1$s";
	private static final String DELETE_ALL = "all content deleted from %1$s";
	private static final String DElETE_AT = "deleted from %1$s: \"%2$s\"";

	private static PrintWriter pr;
	private static BufferedReader br;

	private static Vector<String> usrTexts;
	private static String usrFileName;
	private static Scanner scanner = new Scanner(System.in);
	private static final boolean DEBUG_MODE = false; // Future use

	public static void main(String[] args) {
		String fileName = args[0];
		TextBuddy tb = new TextBuddy(fileName);

		tb.run();
	}

	private static void init(String fileName) {
		usrTexts = new Vector<String>();
		usrFileName = fileName;
	}

	private void initDataSet(BufferedReader br) {
		int noOfLines = 0;
		try {
			noOfLines = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			showMsg(ERROR_FAIL_FORMAT);
			return;
		} catch (IOException e) {
			showMsg(ERROR_UNABLE_TO_READLINE);
		}

		for (int i = 0; i < noOfLines; i++) {
			try {
				usrTexts.add(br.readLine());
			} catch (IOException e) {
				showMsg(ERROR_FAIL_READ_ALL_LINE);
				e.printStackTrace(System.out);
			}
		}
		showMsg(SUCCESS_FORMAT);
	}

	public TextBuddy(String fileName) {
		init(fileName);

		try {
			br = new BufferedReader(new FileReader(fileName));
			initDataSet(br);

			pr = new PrintWriter(new FileWriter(fileName));
		} catch (FileNotFoundException e) {
			showMsg(String.format(FILE_INVALID_REPLY, fileName));

			try {
				pr = new PrintWriter(new FileWriter(fileName));
			} catch (IOException e2) {
				showMsg(ERROR_FILE_CREATION);
				e2.printStackTrace(System.out);
			}
		} catch (IOException e) {
			showMsg(ERROR_FILE_CREATION);
			e.printStackTrace(System.out);
		}

	}

	private void mainLogic() {
		while (true) {
			String feedback = "";
			showMsg("command: ", false);
			// read input
			feedback = processUsrCommand(getUsrCommand(), scanner.nextLine());
			showMsg(feedback);
		}
	}

	public static String processUsrCommand(String usrCommand, String data) {
		switch (usrCommand) {
		// process input
		case "add":
			return addToFile(data);

		case "display":
			return displayAll();

		case "delete":
			return removeAt();

		case "clear":
			return removeAll();

		case "exit":
			return exitSystem();
			
		case "sort":
			return sort();
		case "search":
			return search(data);
		default:
			return "Invalid command provided.";
		}
	}

	// User has to match complete string
	private static String search(String searchString) {
		searchString = searchString.trim();
		String msg = "";
		
		if(usrTexts.isEmpty()){
			return usrFileName + " is empty";
		}else{
			for(int i = 0; i < usrTexts.size(); i++){
				if(usrTexts.get(i).contains(searchString)){
					msg += (i + 1) + ". " + usrTexts.get(i) + "\n";
				}
			}
			if(msg.equals("")){
				return "Specified string not found.";
			}else{
				return msg;
			}
		}
		
	}

	private static String sort() {
		String msg = "";
		
		usrTexts = sortUsrText();
		
		msg = displayAll();
		return msg;
	}

	private static Vector<String> sortUsrText() {
		
		Vector<String>sortedVector = new Vector<String>();
		Object[] toSort = usrTexts.toArray();
		Arrays.sort(toSort);
		
		for(Object o : toSort){
			sortedVector.add((String)o);
		}
		return sortedVector;
	}

	private static void saveFile() {
		pr.println(usrTexts.size());
		for (int i = 0; i < usrTexts.size(); i++) {
			pr.println(usrTexts.get(i));
		}
		pr.close();
	}

	public static String addToFile(String input) {
		String addedText = addToVector(input);
		saveFile();
		return String.format(ADD_TO_FILE, usrFileName, addedText);
	}

	private static String addToVector(String input) {
		//String str = scanner.nextLine().trim();
		String str = input.trim();
		usrTexts.add(str);
		return str;
	}

	private static String removeAt() {
		int pos = scanner.nextInt();
		String removedText = usrTexts.remove(pos - 1);
		saveFile();
		return String.format(DElETE_AT, usrFileName, removedText);
	}

	private static String displayAll() {
		String msg = "";
		if (usrTexts.size() == 0) {
			msg += (usrFileName + " is empty");
		} else {
			for (int i = 0; i < usrTexts.size(); i++) {
				msg += ((i + 1) + ". " + usrTexts.get(i) + "\n");
			}
		}
		return msg;
	}

	private static String removeAll() {
		usrTexts.clear();
		saveFile();
		return String.format(DELETE_ALL, usrFileName);
	}

	private void run() {
		displayWelcomeMsg();
		mainLogic();
	}

	private static String exitSystem() {
		System.exit(0);
		// Display text if fail
		return "Failed to exit system";
	}

	private String getUsrCommand() {
		String str = scanner.next();
		return str;
	}

	private void displayWelcomeMsg() {
		showMsg(String.format(WELCOME_MSG, usrFileName));
	}

	private void showMsg(String text, boolean doNextLine) {
		if (doNextLine == false) {
			System.out.print(text);
		} else {
			showMsg(text);
		}
	}

	private void showMsg(String text) {
		System.out.println(text);
	}
}
