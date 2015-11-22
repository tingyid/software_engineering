package edu.upenn.cis573.plagiarism;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * This class implements a simple plagiarism detection algorithm.
 * 
 * Your goal is to improve its execution time by identifying three major inefficiencies
 * (each of which will result in at least a 10% reduction in execution time compared to the
 * original), as well as at least seven minor inefficiencies.
 */


public class PlagiarismDetector {
	
	private String dirName; // the name of the directory containing the corpus
	
	public PlagiarismDetector(String name) {
		dirName = name;
	}
	
	/*
	 * This method reads the given file and then converts it into a List of Strings.
	 * It does not include punctuation and converts all words in the file to uppercase.
	 */
	private List<String> readFile(String filename) {

		if (filename == null) return null;
		
		List<String> words = new ArrayList<String>();
		
		try {
			Scanner in = new Scanner(new File(filename));
			while (in.hasNext()) {
				words.add(in.next().replaceAll("[^a-zA-Z]", "").toUpperCase()); //MINOR: could do toUpperCase first and then not match on a-z
			}
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return words;
	}
	
	/*
	 * This method reads a file and converts it into a Set of distinct phrases,
	 * each of size "window". The Strings in each phrase are whitespace-separated.
	 */
	private Set<String> createPhrases(String filename, int window) {
		if (filename == null || window < 1) return null;
		
		StringBuilder filePath = new StringBuilder();
		filePath.append(dirName);
		filePath.append("/");
		filePath.append(filename);
		
		List<String> words = readFile(filePath.toString());
		
		Set<String> phrases = new HashSet<String>();
		
		for (int i = 0; i < words.size() - window + 1; i++) {
			StringBuilder phrase = new StringBuilder();
			for (int j = 0; j < window; j++) {
				phrase.append(words.get(i+j));
				phrase.append(" ");
			}

			phrases.add(phrase.toString());
		}
		
		return phrases;
	}

	/*
	 * Returns a Map (sorted by the value of the Integer, in non-descending order) indicating
	 * the number of matches of phrases of size windowSize or greater between each document in the corpus
	 * 
	 * Note that you may NOT remove this method or change its signature or specification!
	 */
	public Map<String, Integer> detectPlagiarism(int windowSize, int threshold) {
		File dirFile = new File(dirName);
		String[] files = dirFile.list();
		
		Map<String, Integer> numberOfMatches = new HashMap<String, Integer>();
		
		//used to store phrases for a particular file so don't need to recompute
		Map<String, Set<String>> filePhrases = new HashMap<String, Set<String>>();
		
		for (int i = 0; i < files.length-1; i++) {
			String file1 = files[i];

			for (int j = i+1; j < files.length; j++) { 
				String file2 = files[j];

				Set<String> file1Phrases; 
				Set<String> file2Phrases; 
				
				if (filePhrases.containsKey(file1)) {
					file1Phrases = filePhrases.get(file1);
				} else {
					file1Phrases = createPhrases(file1, windowSize);
					filePhrases.put(file1, file1Phrases);
				}
				
				if (file1Phrases == null) {
					return null;
				}
				
				if (filePhrases.containsKey(file2)) {
					file2Phrases = filePhrases.get(file2);
				} else {
					file2Phrases = createPhrases(file2, windowSize);
					filePhrases.put(file2, file2Phrases);
				}
				
				if (file2Phrases == null)
					return null;
				
				Set<String> matches = findMatches(file1Phrases, file2Phrases);
								
				if (matches.size() > threshold) {
					StringBuilder key = new StringBuilder();
					key.append(file1);
					key.append("-");
					key.append(file2);
					numberOfMatches.put(key.toString(),matches.size());
				}
			}
		}		
		
		return sortResults(numberOfMatches);
	}
	
	/*
	 * Returns a Set of Strings that occur in both of the Set parameters.
	 * However, the comparison is case-insensitive.
	 */
	private Set<String> findMatches(Set<String> myPhrases, Set<String> yourPhrases) {
	
		Set<String> matches = new HashSet<String>();
		
		for (String mine : myPhrases) {
			if (yourPhrases.contains(mine)) {
				matches.add(mine);
			}
		}
		
		return matches;
	}
	
	/*
	 * Returns a LinkedHashMap in which the elements of the Map parameter
	 * are sorted according to the value of the Integer, in non-ascending order.
	 */
	public LinkedHashMap<String, Integer> sortResults(Map<String, Integer> possibleMatches) {
		
		// Because this approach modifies the Map as a side effect of printing 
		// the results, it is necessary to make a copy of the original Map
		Map<String, Integer> copy = new HashMap<String, Integer>();

		for (String key : possibleMatches.keySet()) {
			copy.put(key, possibleMatches.get(key));
		}	
		
		LinkedHashMap<String, Integer> list = new LinkedHashMap<String, Integer>();

		for (int i = 0; i < copy.size(); i++) {
			int maxValue = 0;
			String maxKey = null;
			for (String key : copy.keySet()) {
				if (copy.get(key) > maxValue) {
					maxValue = copy.get(key);
					maxKey = key;
				}
			}
			
			list.put(maxKey, maxValue);
			copy.put(maxKey, -1);
		}
		
		return list;
	}

}
