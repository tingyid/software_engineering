package edu.upenn.cis573.subsetsum;

import java.util.Random;

public class SubsetSumRetryBlock extends SubsetSumSolver {

	/*
	 * Use a Retry Block to implement this method.
	 * Use the SubsetSumSolverDP.solve method in your Retry Block.
	 * Use the acceptance test in SubsetSumSolver
	 */	
	public boolean[] solve(int[] A, int target) throws ValidSolutionNotFoundException {

		//save state
		int[] copy = new int[A.length];
		System.arraycopy(A, 0, copy, 0, A.length);
		
		//try to solve original. if fails,
		//try two more times with shuffling
		for(int i=0; i < 3; i++) {
			if (i > 0) {
				shuffle(A);			
			}
			boolean[] result = SubsetSumImplementations.solveDP(A, target);
			if (accept(result, A, target)) {
				return result;
			} else {
				//restore state
				System.arraycopy(copy, 0, A, 0, copy.length);
			}
		}
		
		throw new ValidSolutionNotFoundException();
	}
	
	/*
	 * This is the method that you need to implement as part of your Retry Block.
	 * Durstenfeld shuffle - https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm
	 */
    protected void shuffle(int A[]) {
    	Random random = new Random();
    	for(int i=A.length-1; i > 0; i--) {
    		int index = random.nextInt(i+1);
    		//swap numbers around
    		int a = A[index];
    		A[index] = A[i];
    		A[i] = a;
    	}
    }
}
