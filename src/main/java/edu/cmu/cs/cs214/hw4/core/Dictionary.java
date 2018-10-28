package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a dictionary. Can be used to check validity of words played by the
 * user.
 *
 * @author akash
 *
 * @param <E>
 *                String.
 */
class Dictionary<E> {
	private static Set<String> words;

	Dictionary() {
		words = new HashSet<String>();
		addWords();
	}

	private void addWords() {
		File f = new File("src/main/resources/words.txt");
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				words.add(sc.next());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	boolean isAWord(String str) {
		if (str == null || str.equals("")) {
			return false;
		}

		if (words.contains(str.toLowerCase())) {
			return true;
		}

		return false;

	}

}
