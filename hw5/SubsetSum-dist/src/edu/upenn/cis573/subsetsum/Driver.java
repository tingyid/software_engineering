package edu.upenn.cis573.subsetsum;

public class Driver  {

	private static final int NUM_TRIALS = 10000;



	/*
	 * This evaluates the SubsetSumSolver by giving it random inputs and
	 * seeing how many times it finds a correct solution.
	 */
	protected static void runTest(String type, SubsetSumSolver solver) {

		// Create an array of ints
		int[] values = new int[20];

		// for keeping track of the number of correct solutions
		int success = 0;

		// for keeping track of the total execution time so far
		long totalTimeNS = 0;

		for (int i = 0; i < NUM_TRIALS; i++) {

			// Populate the array with random values from 1 to 100, inclusive
			for (int j = 0; j < values.length; j++) 
				values[j] = (int)(Math.random() * 100) + 1;

			// Generate a random target value from 1 to 200, inclusive
			int target = (int)(Math.random() * 200) + 1;

			// Try to find a solution
			long start = System.nanoTime();
			try {
				solver.solve(values, target);
				// if there wasn't an exception, we know it passed the acceptance test
				success++;
			}
			catch (ValidSolutionNotFoundException e) {
				// if there was an exception, we know that it didn't pass the test

			}
			long end = System.nanoTime();

			totalTimeNS += (end - start);
		}

		long timeMS = totalTimeNS / 1000000;

		System.out.println(type + ": " + success + " correct out of " + NUM_TRIALS + " in " + timeMS + "ms");
	}



	public static void main(String[] args) {

		// Baseline: DP SOLUTION
		runTest("Dynamic Programming", new SubsetSumSolver(){ 
			public boolean[] solve(int A[], int target) throws ValidSolutionNotFoundException {
				boolean[] result = SubsetSumImplementations.solveDP(A, target);
				if (accept(result, A, target)) return result;
				else throw new ValidSolutionNotFoundException();
			}
		});

		// Baseline: BF SOLUTION
		runTest("Brute Force", new SubsetSumSolver(){ 
			public boolean[] solve(int A[], int target) throws ValidSolutionNotFoundException {
				boolean[] result = SubsetSumImplementations.solveBF(A, target);
				if (accept(result, A, target)) return result;
				else throw new ValidSolutionNotFoundException();
			}
		});

		// Step 2. RETRY BLOCK
		runTest("Retry Block", new SubsetSumRetryBlock());

		// Step 3. RECOVERY BLOCK
		runTest("Recovery Block", new SubsetSumRecoveryBlock());

		// Step 4. PARALLEL RECOVERY BLOCK
		runTest("Parallel Recovery Block", new SubsetSumParallelRecoveryBlock());

	}




}
