package edu.upenn.cis573.hwk1.decryption;

import java.io.File;

/**
 * Expected behavior for all decrypters
 * @author davehand
 */
public interface Decrypter {

	public File decryptFile() throws Exception;
}