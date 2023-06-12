import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SkipList_testcase {

	// Create an instance of the SkipList class to test its functions
	SkipList obj = new SkipList();

	// Define some test data to use in the test cases
	KVPair<String, Rectangle> test1 = new KVPair<String, Rectangle>("X", new Rectangle(20, 12, 3, 3));
	KVPair<String, Rectangle> test2 = new KVPair<String, Rectangle>("Y", new Rectangle(80, 19, 33, 9));
	KVPair<String, Rectangle> test3 = new KVPair<String, Rectangle>("X", new Rectangle(120, 2, 9, 10));
	KVPair<String, Rectangle> test4 = new KVPair<String, Rectangle>("Z", new Rectangle(3, 189, 73, 303));

	// Test case for the insert function
	@Test
	public void insert_testcase() {
		// Insert some test data into the SkipList
		obj.insert(test1);
		// Verify that the size of the SkipList has increased to 1
		assertEquals(1, obj.size());

		obj.insert(test2);
		// Verify that the size of the SkipList has increased to 2
		assertEquals(2, obj.size());
	}

	// Test case for the search function
	@Test
	public void search_testcase() {
		// Insert some test data into the SkipList
		obj.insert(test1);
		obj.insert(test3);
		// Search the SkipList for all elements with key "X"
		ArrayList<KVPair<String, Rectangle>> result = obj.search("X");

		// Verify that the search returned the expected results
		assertEquals("X", result.get(0).getKey());
		assertEquals("X", result.get(1).getKey());
	}

	// Test case for the remove function
	@Test
	public void remove_testcase() {
		// Insert some test data into the SkipList
		obj.insert(test1);
		// Remove an element with key "X" from the SkipList
		KVPair<String, Rectangle> result = obj.remove("X");

		// Verify that the removed element matches the expected result
		assertEquals("X", result.getKey());
		assertEquals(new Rectangle(20, 12, 3, 3), result.getValue());
	}

	// Test case for the removeByValue function
	@Test
	public void removebyvalue_testcase() {
		// Insert some test data into the SkipList
		obj.insert(test4);
		// Remove an element with value Rectangle(3, 189, 73, 303) from the SkipList
		KVPair<String, Rectangle> result = obj.removeByValue(new Rectangle(3, 189, 73, 303));

		// Verify that the removed element matches the expected result
		assertEquals(new Rectangle(3, 189, 73, 303), result.getValue());
	}
}