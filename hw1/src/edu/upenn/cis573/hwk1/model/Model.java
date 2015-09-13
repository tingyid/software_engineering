package edu.upenn.cis573.hwk1.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.upenn.cis573.hwk1.alphabet.Alphabet;
import edu.upenn.cis573.hwk1.corpus.CorpusAccessor;

/**
 * Class for generating frequency models
 * @author davehand
 */
public class Model {

	private final Map<Character,Integer> charFrequency;
	private final Alphabet alphabet;
	
	public Model(File encryptedFile, Alphabet alphabet) throws Exception {
		this.alphabet = alphabet;
		charFrequency = buildModelFromFile(encryptedFile);
	}
	
	public Model(CorpusAccessor corpusAccessor, File excludedFile, Alphabet alphabet) throws Exception {
		this.alphabet = alphabet;
		charFrequency = buildModelFromCorpus(corpusAccessor, excludedFile);
	}
	
	/**
	 * Build the frequency model for a single file
	 * @param encryptedFile - file to build model for
	 * @return frequency map
	 * @throws Exception
	 */
	private Map<Character,Integer> buildModelFromFile(File encryptedFile) throws Exception {
		
		Map<Character,Integer> charFrequency = buildMapFromAlphabet();
		
		processFileFrequency(encryptedFile, charFrequency);
				
		return sortHashMap(charFrequency);
	}
	
	/**
	 * Build the frequency model for a corpus, excluding one file
	 * @param corpusAccessor - accessor for corpus
	 * @param excludedFile - file to not include in model
	 * @return frequency map
	 * @throws Exception
	 */
	private Map<Character,Integer> buildModelFromCorpus(CorpusAccessor corpusAccessor, File excludedFile) throws Exception {
		
		Map<Character,Integer> charFrequency = buildMapFromAlphabet();
		
		//iterate over the files in the list
		for (File file : corpusAccessor.getFiles()) {			
			//don't count the excluded file
			if (!file.equals(excludedFile)) {
				processFileFrequency(file, charFrequency);
			}
		}
				
		return sortHashMap(charFrequency);
	}
	
	/**
	 * Create the starting frequency map from the alphabet
	 * @return starting map
	 */
	private Map<Character,Integer> buildMapFromAlphabet() {
				
		Map<Character,Integer> charFrequency = new HashMap<>();
		
		for (Character c : alphabet.getAlphabet()) {
			charFrequency.put(c, 0);
		}
		
		return charFrequency;
	}
	
	/**
	 * Increment counts for each character in the file in our map
	 * @param file - file to read
	 * @param charFrequency - frequency map
	 * @throws Exception
	 */
	private void processFileFrequency(File file, Map<Character,Integer> charFrequency) throws Exception {
		//count the characters in the file
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			for (int i=0; i < line.length(); i++) {
				Character c = Character.toLowerCase(line.charAt(i));
				if (charFrequency.containsKey(c)) {
					charFrequency.put(c, charFrequency.get(c) + 1);
				}
			}
		}
		br.close();
	}
	
	/**
	 * Sort the frequency map by occurrences
	 * @param charFrequency - map to be sorted
	 * @return sorted map
	 */
	private Map<Character,Integer> sortHashMap(Map<Character,Integer> charFrequency) {
		List<Map.Entry<Character,Integer>> frequencyMapList = new LinkedList<>(charFrequency.entrySet());
		Collections.sort(frequencyMapList, new FrequencyModelComparator());
		
		Map<Character,Integer> sortedFrequencyMap = new LinkedHashMap<>();
		
		for (Map.Entry<Character,Integer> entry : frequencyMapList) {
			sortedFrequencyMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedFrequencyMap;
	}
	
	/**
	 * Accessor for the frequency map
	 * @return frequency map
	 */
	public Map<Character,Integer> getModelFrequencyMap() {
		return charFrequency;
	}
}
