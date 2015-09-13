package edu.upenn.cis573.hwk1.decryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.upenn.cis573.hwk1.alphabet.Alphabet;
import edu.upenn.cis573.hwk1.model.Model;

/**
 * Decryption based on frequency models
 * @author davehand
 */
public class FrequencyDecrypter implements Decrypter {

	private final Map<Character,Character> cipherKey;
	private final File encryptedFile;
	private final Alphabet alphabet;
	
	public FrequencyDecrypter(Model model, File encryptedFile, Alphabet alphabet) throws Exception {
		this.encryptedFile = encryptedFile;
		this.alphabet = alphabet;
		cipherKey = generateCipherKey(model);
	}
	
	/**
	 * Generate the cipher key for decrypting the file
	 * @param corpusModel - frequency model from corpus
	 * @return the decrypted file
	 * @throws Exception
	 */
	private Map<Character,Character> generateCipherKey(Model corpusModel) throws Exception {
		//generate the frequency model for the decrypted file
		Model encryptedModel = new Model(encryptedFile, alphabet);
				
		//iterate over the model and decrypted model to pair the most frequent items with each other in a map
		Map<Character,Character> cipherKey = new HashMap<>();
		ArrayList<Character> corpusModelFrequencyList = new ArrayList<>(corpusModel.getModelFrequencyMap().keySet());
		ArrayList<Character> encryptedModelFrequencyList = new ArrayList<>(encryptedModel.getModelFrequencyMap().keySet());
		for (int i=0; i<encryptedModelFrequencyList.size(); i++){
			cipherKey.put(encryptedModelFrequencyList.get(i), corpusModelFrequencyList.get(i));
		}
		
		return cipherKey;
	}
	
	@Override
	public File decryptFile() throws Exception {
		
		File tempFile = File.createTempFile("decrypted", ".txt");
		tempFile.deleteOnExit();
		PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
		
		BufferedReader br = new BufferedReader(new FileReader(encryptedFile));
		String line;
		while ((line = br.readLine()) != null) {
			String decryptedLine = decryptText(line);
			writer.println(decryptedLine);
		}
		writer.close();
		br.close();
		
		return tempFile;
	}
	
	/**
	 * Helper method for decrypting a line of text
	 * @param text - text to be decrypted
	 * @return the decrypted text
	 */
	private String decryptText(String text) {
		
		char[] decryptedString = new char[text.length()];
		
		for (int i=0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (cipherKey.containsKey(c)) {
				decryptedString[i] = cipherKey.get(c);
			} else {
				decryptedString[i] = c;
			}
		}
		
		return new String(decryptedString);
	}
}
