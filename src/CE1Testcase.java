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
		assertEquals("filename.b is empty", mTB.processUsrCommand("search", "a"));
		
		mTB.processUsrCommand("add", " aa");
		assertEquals("1. aa\n", mTB.processUsrCommand("search", "aa"));
		assertEquals("1. aa\n", mTB.processUsrCommand("search", "a"));
		
		mTB.processUsrCommand("add", " ab");
		mTB.processUsrCommand("add", " ac");
		assertEquals("1. aa\n2. ab\n3. ac\n", mTB.processUsrCommand("search", "a"));
		assertEquals("3. ac\n", mTB.processUsrCommand("search", "ac"));
		assertEquals("3. ac\n", mTB.processUsrCommand("search", "c"));
		assertEquals("Specified string not found.", mTB.processUsrCommand("search", "d"));
	}

	private void runCaseAdd() {
		mTB.processUsrCommand("clear", "");
		assertEquals("Invalid command provided.", mTB.processUsrCommand("aad", ""));
		assertEquals("added to filename.b: \"hello world\"", mTB.processUsrCommand("add"," hello world"));
		assertEquals("added to filename.b: \"bye world\"", mTB.processUsrCommand("add"," bye world") );

		System.out.println("Case add complete without failure");
	}

	private void runCaseSort() {

		mTB.processUsrCommand("clear", "");
		assertEquals("filename.b is empty", mTB.processUsrCommand("sort", ""));
		
		mTB.processUsrCommand("add"," aa");
		assertEquals("1. aa\n", mTB.processUsrCommand("sort", ""));
		
		mTB.processUsrCommand("add"," cc");
		assertEquals("1. aa\n2. cc\n", mTB.processUsrCommand("sort", ""));
		
		mTB.processUsrCommand("add"," bb");
		assertEquals("1. aa\n2. bb\n3. cc\n", mTB.processUsrCommand("sort", ""));
	
		System.out.println("Case sort complete without failure");
	}
	
	private void init(){
			
			mTB = new TextBuddy(TEXTBUDDY_FILENAME);
			

	}
}
