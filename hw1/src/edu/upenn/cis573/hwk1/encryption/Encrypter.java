package edu.upenn.cis573.hwk1.encryption;

import java.io.File;

/**
 * Expected behavior for all encrypters
 * @author davehand
 */
public interface Encrypter {

	/**
	 * Create an encrypted version of the file
	 * @param fileToEncrypt the file to encrypt
	 * @return the encrypted File reference
	 * @throws Exception any exceptions with the file
	 */
	public File encryptFile(File fileToEncrypt) throws Exception;
}