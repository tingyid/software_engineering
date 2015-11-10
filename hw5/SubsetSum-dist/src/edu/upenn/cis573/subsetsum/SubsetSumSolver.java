package edu.upenn.cis573.subsetsum;

public abstract class SubsetSumSolver {
	
	/*
	 * This is the acceptance test.
	 * Note that if solution is null, we consider it "correct",
	 * since that means no solution could be found.
	 */
    protected boolean accept(boolean[] solution, int A[], int target) {
    	
    	//if null, we consider it to be correct
    	if (null == solution) {
    		return true;
    	}
    	
    	//calculate the total over "true" elements in array
    	//so can compare with target
    	int sum = 0;
    	for(int i=0; i < solution.length; i++) {
    		if (solution[i]) {
    			sum += A[i];
    		}
    	}
    	
    	if (sum == target) {
    		return true;
    	}
    	
    	return false;
    }

    /*
     * DO NOT IMPLEMENT THIS METHOD HERE!
     * You should implement this method in the different subclasses
     * using the different fault tolerance techniques in Steps 2-4.
     */
    public abstract boolean[] solve(int A[], int target) throws ValidSolutionNotFoundException ;
    

}
