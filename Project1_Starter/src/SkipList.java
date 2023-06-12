import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K> Key
 * @param <V> Value
 */
public class SkipList<K extends Comparable<? super K>, V>

		implements Iterable<KVPair<K, V>> {
	private SkipNode head; // First element of the top level
	private int size; // number of entries in the Skip List

	/**
	 * Initializes the fields head, size and level
	 */
	public SkipList() {
		head = new SkipNode(null, 0);
		size = 0;
	}

	/**
	 * Returns a random level number which is used as the depth of the SkipNode
	 * 
	 * @return a random level number
	 */
	int randomLevel() {
		int lev;
		Random value = new Random();
		for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
			// Do nothing
		}
		return lev; // returns a random level
	}

	/**
	 * Searches for the KVPair using the key which is a Comparable object.
	 * 
	 * @param key key to be searched for
	 */

	/**
	 * 
	 * Searches for KVPair entries in the SkipList that have the specified key and
	 * returns an ArrayList of all the KVPair entries found.
	 * 
	 * @param key the key to search for
	 * 
	 * @return an ArrayList containing all the KVPair entries found with the
	 *         specified key
	 */
	public ArrayList<KVPair<K, V>> search(K key) {
		SkipNode x = head; // Dummy header node
		ArrayList<KVPair<K, V>> result = new ArrayList<KVPair<K, V>>();

		// Traverse the SkipList from the top level to the bottom level
		for (int i = head.level; i >= 0; i--) {
			// Go forward until either the end of the level is reached or a node with a
			// greater key value is found
			while ((x.forward[i] != null) && (x.forward[i].pair.getKey().compareTo(key)) < 0) {
				x = x.forward[i];
			}
		}

		// Traverse the bottom level of the SkipList and add all KVPair entries with the
		// specified key to the result ArrayList
		x = x.forward[0];
		while (x != null && x.pair.getKey().compareTo(key) == 0) {
			result.add(x.element());
			x = x.forward[0];
		}

		return result;
	}

	/**
	 * @return the size of the SkipList
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts the KVPair in the SkipList at its appropriate spot as designated by
	 * its lexicoragraphical order.
	 * 
	 * @param it the KVPair to be inserted
	 */
	@SuppressWarnings("unchecked")
	public void insert(KVPair<K, V> it) {

		int newLevel = randomLevel(); // New node's level
		if (newLevel > head.level) { // If new node is deeper
			adjustHead(newLevel); // adjust the header
		}

		// Track end of level
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, newLevel + 1);
		SkipNode x = head; // Start at header node
		for (int i = newLevel; i >= 0; i--) { // Find insert position
			while ((x.forward[i] != null) && (x.forward[i].element().getKey().compareTo(it.getKey()) < 0))
				x = x.forward[i];
			update[i] = x; // Track end at level i
		}

		x = new SkipNode(it, newLevel);
		for (int i = 0; i <= newLevel; i++) { // Splice into list
			x.forward[i] = update[i].forward[i]; // Who x points to
			update[i].forward[i] = x; // Who points to x
		}
		size++; // Increment dictionary size

	}

	// Increases the number of levels in head so that no element has more indices
	// than the head.
	// @param newLevel the number of levels to be added to head
	/**
	 * Adjusts the head node of the SkipList to have the specified new level.
	 * 
	 * @param newLevel the new level of the head node
	 */
	@SuppressWarnings("unchecked")
	private void adjustHead(int newLevel) {
		// Create a temporary node to hold the current head node
		SkipNode temp = head;

		// Create a new head node with the specified new level
		head = new SkipNode(null, newLevel);

		// If the new level is 0, set it to 1 instead
		if (head.level == 0) {
			head.level = 1;
		}

		// Copy the forward pointers of the temporary node to the new head node for each
		// level up to the minimum of the new level and the number of levels in the
		// temporary node
		for (int i = 0; i < Math.min(head.level, temp.forward.length); i++) {
			head.forward[i] = temp.forward[i];
		}

		// Set the level of the new head node to the new level
		head.level = newLevel;
	}

	/**
	 * Removes the KVPair that is passed in as a parameter and returns true if the
	 * pair was valid and false if not.
	 * 
	 * @param pair the KVPair to be removed
	 * @return returns the removed pair if the pair was valid and null if not
	 *         Removes the KVPair entry with the specified key from the SkipList, if
	 *         it exists, and returns the removed KVPair entry.
	 * 
	 * @param key the key of the KVPair entry to remove
	 * 
	 * @return the removed KVPair entry, or null if the specified key does not exist
	 *         in the SkipList
	 */
	@SuppressWarnings("unchecked")
	public KVPair<K, V> remove(K key) {
		// Search for the key to remove
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, head.level + 1);
		boolean removed = false;
		SkipNode current = head;
		SkipNode x = head;

		// Traverse the SkipList starting from the head node at the highest level
		for (int i = head.level; i >= 0; i--) {
			// Move down the levels until the target key is found or a larger key is
			// encountered
			while (current.forward[i] != null && current.forward[i].element().getKey().compareTo(key) < 0) {
				current = current.forward[i];
			}
			update[i] = current;
		}

		current = current.forward[0];
		if (current != null && current.element().getKey().compareTo(key) == 0) {
			// If the KVPair entry with the specified key is found, remove it from the
			// SkipList
			for (int i = current.level; i >= 0; i--) {
				update[i].forward[i] = current.forward[i];
			}
			size--;
			return current.element();
		} else {
			// If the KVPair entry with the specified key is not found, return null
			return null;
		}
	}

	/**
	 * Removes a KVPair with the specified value.
	 * 
	 * @param val the value of the KVPair to be removed
	 * @return returns true if the removal was successful Removes the KVPair entry
	 *         with the specified value from the SkipList, if it exists, and returns
	 *         the removed KVPair entry.
	 * 
	 * @param val the value of the KVPair entry to remove
	 * 
	 * @return the removed KVPair entry, or null if a KVPair entry with the
	 *         specified value does not exist in the SkipList
	 */
	public KVPair<K, V> removeByValue(V val) {
		// Traverse the bottom level of the SkipList and find the first KVPair entry
		// with the specified value
		SkipNode cur = head.forward[0];
		KVPair<K, V> removedPair = null;
		while (cur != null && cur.element().getValue().equals(val) == false) {
			cur = cur.forward[0];
		}

		// If a KVPair entry with the specified value is found, remove it from the
		// SkipList using the remove() method
		if (cur != null && cur.element().getValue().equals(val) == true) {
			removedPair = cur.element();
			remove(cur.element().getKey());
		}

		return removedPair;
	}

	/**
	 * Prints out the SkipList in a human readable format to the console. Prints a
	 * dump of the SkipList, including the level and value of each node, as well as
	 * the size of the SkipList.
	 */
	public void dump() {
		SkipNode current = head;
		System.out.println("SkipList dump:");

		// If the SkipList is empty, print a message indicating that the size is 0
		if (current.forward[0] == null) {
			System.out.println("Node has depth 1, Value (null)");
			System.out.println("SkipList size is: 0");
		} else {
			// Print the level and value of the head node
			System.out.println("Node has depth " + current.level + ", Value (null) ");

			// Traverse the bottom level of the SkipList and print the level and value of
			// each node
			current = current.forward[0];
			while (current != null) {
				System.out.println("Node has depth " + current.level + ", Value (" + current.pair.getKey() + ", "
						+ current.pair.getValue() + ")");
				current = current.forward[0];
			}

			// Print the size of the SkipList
			System.out.println("SkipList size is: " + size);
		}
	}

	// This class implements a SkipNode for the SkipList data structure.
	private class SkipNode {

		// the KVPair to hold
		private KVPair<K, V> pair;
		// what is this
		private SkipNode[] forward;
		// the number of levels
		private int level;

		/**
		 * Initializes the fields with the required KVPair and the number of levels from
		 * the random level method in the SkipList.
		 * 
		 * @param tempPair the KVPair to be inserted
		 * @param level    the number of levels that the SkipNode should have
		 */
		@SuppressWarnings("unchecked")
		public SkipNode(KVPair<K, V> tempPair, int level) {
			pair = tempPair;
			forward = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, level + 1);
			this.level = level;
		}

		/**
		 * Returns the KVPair stored in the SkipList.
		 * 
		 * @return the KVPair
		 */
		public KVPair<K, V> element() {
			return pair;
		}

	}

	/**
	 * Private inner class that implements the Iterator interface and allows for
	 * iterating over the elements in the SkipList.
	 */
	private class SkipListIterator implements Iterator<KVPair<K, V>> {
		private SkipNode current;

		/*
		 * Constructs a new SkipListIterator object with the current node set to the
		 * head node.
		 */

		public SkipListIterator() {
			current = head;
		}

		/**
		 * Returns true if there is another element in the SkipList, and false
		 * otherwise.
		 * 
		 * @return true if there is another element in the SkipList, and false otherwise
		 */
		@Override
		public boolean hasNext() {
			if (current.forward[0] != null) {
				return true;
			}
			return false;
		}

		/*
		 * Returns the next element in the SkipList and advances the current node.
		 * 
		 * @return the next element in the SkipList
		 */
		@Override
		public KVPair<K, V> next() {
			KVPair<K, V> x = current.forward[0].element();
			current = current.forward[0];
			return x;
		}
	}

	/*
	 * Returns a new SkipListIterator object that can be used to iterate over the
	 * elements in the SkipList.
	 * 
	 * @return a new SkipListIterator object
	 */
	@Override
	public Iterator<KVPair<K, V>> iterator() {
		return new SkipListIterator();
	}
}