package edu.upenn.cis573.subsetsum;

public abstract class SubsetSumSolver {
	
	/*
	 * This is the acceptance test.
	 * Note that if solution is null, we consider it "correct",
	 * since that means no solution could be found.
	 */
    protected boolean accept(boolean[] solution, int A[], int target) {
    	
    	// IMPLEMENT THIS METHOD HERE!
    	
    	return false;
    }

    /*
     * DO NOT IMPLEMENT THIS METHOD HERE!
     * You should implement this method in the different subclasses
     * using the different fault tolerance techniques in Steps 2-4.
     */
    public abstract boolean[] solve(int A[], int target) throws ValidSolutionNotFoundException ;
    

}
