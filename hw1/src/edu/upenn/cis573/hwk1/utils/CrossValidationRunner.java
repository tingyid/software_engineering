package edu.upenn.cis573.hwk1.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import edu.upenn.cis573.hwk1.model.Model;
import edu.upenn.cis573.hwk1.output.Output;
import edu.upenn.cis573.hwk1.alphabet.Alphabet;
import edu.upenn.cis573.hwk1.corpus.CorpusAccessor;
import edu.upenn.cis573.hwk1.decryption.Decrypter;
import edu.upenn.cis573.hwk1.decryption.FrequencyDecrypter;
import edu.upenn.cis573.hwk1.encryption.Encrypter;

/**
 * Class for running cross validation over a
 * corpus of documents
 * @author davehand
 */
public final class CrossValidationRunner {
	
	private final Alphabet alphabet;
	private final CorpusAccessor corpusAccessor;
	private final Encrypter encrypter;
	private final Output output;
	
	private CrossValidationRunner(Builder builder) {
		this.alphabet = builder.alphabet;
		this.corpusAccessor = builder.corpusAccessor;
		this.encrypter = builder.encrypter;
		this.output = builder.output;
	}
	
	/**
	 * Runs cross validation over the corpus
	 * @throws Exception
	 */
	public void runCrossValidation() throws Exception {
		
		int correct = 0, incorrect = 0;
		
		//iterate over the files in the list
		for (File file : corpusAccessor.getFiles()) {
			//remove T from corpus, use other docs to build model
			Model model = new Model(corpusAccessor, file, alphabet);
			
			//encrypt T
			File encryptedFile = encrypter.encryptFile(file);
			
			//apply decrypt model to encrypted file
			Decrypter decrypter = new FrequencyDecrypter(model, encryptedFile, alphabet);
			File decryptedFile = decrypter.decryptFile();
			
			//compare original to model applied file
			DecryptionResult decryptionResult = compareFiles(file, decryptedFile);
			correct += decryptionResult.getCorrect();
			incorrect += decryptionResult.getIncorrect();
			
			output.displayFileResults(file, decryptionResult);
			
			encryptedFile.delete();
			decryptedFile.delete();
		}
		
		//overall metric is number of letters decrypted correctly across all files in the corpus
		output.displayAggregateResults(correct, incorrect);
	}
	
	/**
	 * Check the correctness of the decrypted file
	 * @param originalFile
	 * @param decryptedFile
	 * @return DecryptionResult with number of correct/incorrect
	 * @throws Exception
	 */
	private DecryptionResult compareFiles(File originalFile, File decryptedFile) throws Exception {
		int correct = 0, incorrect = 0;
		
		BufferedReader brOriginal = new BufferedReader(new FileReader(originalFile));
		BufferedReader brDecrypted = new BufferedReader(new FileReader(originalFile));
		String lineOriginal, lineDecrypted;
		while ((lineOriginal = brOriginal.readLine()) != null
				&& (lineDecrypted = brDecrypted.readLine()) != null) {
			
			for (int i=0; i < lineOriginal.length(); i++) {
				char cOriginal = Character.toLowerCase(lineOriginal.charAt(i));
				if (alphabet.getAlphabet().contains(cOriginal)) { //only care about alphabet
					char cDecrypted = lineDecrypted.charAt(i);
					if (cOriginal == cDecrypted) {
						correct++;
					} else {
						incorrect++;
					}
				}
			}
		}
		brOriginal.close();
		brDecrypted.close();
		
		return new DecryptionResult(correct, incorrect);
	}
	
	/**
	 * Static class for cleanly building validators
	 */
	public static class Builder {
		private Alphabet alphabet;
		private CorpusAccessor corpusAccessor;
		private Encrypter encrypter;
		private Output output;
		
		public Builder() { }
		
		public Builder setAlphabet(Alphabet alphabet) {
			this.alphabet = alphabet;
			return this;
		}
		
		public Builder setCorpusAccessor(CorpusAccessor corpusAccessor) {
			this.corpusAccessor = corpusAccessor;
			return this;
		}
		
		public Builder setEncrypter(Encrypter encrypter) {
			this.encrypter = encrypter;
			return this;
		}
		
		public Builder setOutput(Output output) {
			this.output = output;
			return this;
		}
		
		public CrossValidationRunner build() {
			return new CrossValidationRunner(this);
		}
	}
}
