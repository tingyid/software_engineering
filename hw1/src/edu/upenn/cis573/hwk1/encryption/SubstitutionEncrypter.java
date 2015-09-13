package edu.upenn.cis573.hwk1.encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.upenn.cis573.hwk1.alphabet.Alphabet;

/**
 * Class for encrypting files via Substitution
 * Cipher. Cipher randomly generated on object creation
 * @author davehand
 */
public class SubstitutionEncrypter implements Encrypter {
	
	private final Alphabet alphabet;
	private final Map<Character,Character> cipher;
	
	public SubstitutionEncrypter(Alphabet alphabet) {
		this.alphabet = alphabet;
		this.cipher = generateCipher();
	}
	
	/**
	 * Generate the cipher from the alphabet by shuffling the alphabet
	 * array and mapping elements at the same index
	 * @return Map of plaintext characters to cipher text
	 */
	private Map<Character,Character> generateCipher() {
		
		Map<Character,Character> cipher = new HashMap<>();
		
		ArrayList<Character> originalAlphabet = new ArrayList<>(alphabet.getAlphabet());
		ArrayList<Character> shuffledAlphabet = new ArrayList<>(alphabet.getAlphabet());
		Collections.shuffle(shuffledAlphabet);
		
		for (int i=0; i < originalAlphabet.size(); i++) {
			cipher.put(originalAlphabet.get(i), shuffledAlphabet.get(i));
			cipher.put(Character.toUpperCase(originalAlphabet.get(i)), shuffledAlphabet.get(i));
		}
		
		return cipher;
	}
	
	@Override
	public File encryptFile(File fileToEncrypt) throws Exception {
		
		File tempFile = File.createTempFile("encrypted", ".txt");
		tempFile.deleteOnExit();
		PrintWriter writer = new PrintWriter(tempFile, "UTF-8");
		
		BufferedReader br = new BufferedReader(new FileReader(fileToEncrypt));
		String line;
		while ((line = br.readLine()) != null) {
			String encryptedLine = encryptText(line);
			writer.println(encryptedLine);
		}
		writer.close();
		br.close();
		
		return tempFile;
	}
	
	/**
	 * Function for encrypting a String of text with our cipher
	 * @param text - the text to be encrypted
	 * @return the encrypted String
	 */
	private String encryptText(String text) {
		
		char[] encryptedString = new char[text.length()];
		
		for (int i=0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (cipher.containsKey(c)) {
				encryptedString[i] = cipher.get(c);
			} else {
				encryptedString[i] = c;
			}
		}
		
		return new String(encryptedString);
	}				
}
