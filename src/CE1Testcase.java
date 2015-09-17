import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class CE1Testcase {
	private static final String TEXTBUDDY_FILENAME = "filename.b";
	private static BufferedReader brIn, brOut;
	private TextBuddy mTB;
	@Test
	public void test() {
		init();
		runCaseAdd();
		runCaseSort();
		runCaseSearch();
	}

	private void runCaseSearch() {
		mTB.processUsrCommand("clear", "");
		assertEquals(mTB.processUsrCommand("search", "a"), "filename.b is empty");
		mTB.processUsrCommand("add", " aa");
		assertEquals(mTB.processUsrCommand("search", "aa"), "1. aa");
		assertEquals(mTB.processUsrCommand("search", "a"), "Specified string not found.");
		mTB.processUsrCommand("add", " ab");
		mTB.processUsrCommand("add", " ac");
		assertEquals(mTB.processUsrCommand("search", "a"), "Specified string not found.");
		assertEquals(mTB.processUsrCommand("search", "ac"), "3. ac");
	}

	private void runCaseAdd() {
		mTB.processUsrCommand("clear", "");
		assertEquals(mTB.processUsrCommand("aad", ""), "Invalid command provided.");
		assertEquals(mTB.processUsrCommand("add"," hello world"), "added to filename.b: \"hello world\"");
		assertEquals(mTB.processUsrCommand("add"," bye world"), "added to filename.b: \"bye world\"");

		System.out.println("Case add complete without failure");
	}

	private void runCaseSort() {

		mTB.processUsrCommand("clear", "");
		assertEquals(mTB.processUsrCommand("sort", ""), "filename.b is empty");
		
		mTB.processUsrCommand("add"," aa");
		assertEquals(mTB.processUsrCommand("sort", ""), "1. aa\n");
		
		mTB.processUsrCommand("add"," cc");
		assertEquals(mTB.processUsrCommand("sort", ""), "1. aa\n2. cc\n");
		
		mTB.processUsrCommand("add"," bb");
		assertEquals(mTB.processUsrCommand("sort", ""), "1. aa\n2. bb\n3. cc\n");
	
		System.out.println("Case sort complete without failure");
	}
	
	private void init(){
			
			mTB = new TextBuddy(TEXTBUDDY_FILENAME);
			

	}
}
