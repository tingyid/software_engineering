package edu.upenn.cis573.hwk1;

import java.io.File;

import edu.upenn.cis573.hwk1.alphabet.Alphabet;
import edu.upenn.cis573.hwk1.alphabet.EnglishAlphabet;
import edu.upenn.cis573.hwk1.corpus.LocalDirectoryCorpusAccessor;
import edu.upenn.cis573.hwk1.encryption.SubstitutionEncrypter;
import edu.upenn.cis573.hwk1.output.StandardOutput;
import edu.upenn.cis573.hwk1.utils.CorpusDirectoryException;
import edu.upenn.cis573.hwk1.utils.CrossValidationRunner;

/**
 * Driver for the program
 * @author davehand
 */
public final class Main {
	
	private final static int EXP_NUM_ARGS = 1; //expected number of args
	
	/**
	 * Main function for running the program
	 * @param args - command line arguments
	 */
	public static void main(final String[] args) {
		try {
			Alphabet alphabet = new EnglishAlphabet();
			CrossValidationRunner crossValidationRunner = new CrossValidationRunner.Builder()
				.setAlphabet(alphabet)
				.setCorpusAccessor(new LocalDirectoryCorpusAccessor(getCorpusDirectory(args)))
				.setEncrypter(new SubstitutionEncrypter(alphabet))
				.setOutput(new StandardOutput())
				.build();
			crossValidationRunner.runCrossValidation();
		} catch (Exception e) {
			System.out.println("Exception thrown: " + e);
		}
	}
	
	/**
	 * Parse the command line arguments array for the
	 * corpus directory. Fail if more/less than one 
	 * runtime argument
	 * @param args - arguments to the program
	 * @return corpus directory string
	 */
	private static File getCorpusDirectory(final String[] args) {
		
		//check that expected number of arguments are present
		if (args.length != EXP_NUM_ARGS) {
			throw new RuntimeException("Wrong number of arguments");
		}
		
		//check that directory exists
		final File corpusDirectory = new File(args[0]);
		if (!corpusDirectory.exists()) {
			throw new CorpusDirectoryException("Failed to locate directory");
		}
		
		//check that file is a directory
		if (!corpusDirectory.isDirectory()) {
			throw new CorpusDirectoryException("File not a directory");
		}
		
		//check that can read the directory
		if (!corpusDirectory.canRead()) {
			throw new CorpusDirectoryException("Can't read directory");
		}

		//check that directory is not empty
		if (corpusDirectory.listFiles().length == 0) {
			throw new CorpusDirectoryException("Directory is empty");
		}
		
		return corpusDirectory;
	}
}
