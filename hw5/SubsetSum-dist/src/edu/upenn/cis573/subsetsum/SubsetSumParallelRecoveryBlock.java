package edu.upenn.cis573.subsetsum;

public class SubsetSumParallelRecoveryBlock extends SubsetSumSolver {

	/*
	 * Use a Parallel Recovery Block to implement this method.
	 * Use the SubsetSumSolverBF.solve and SubsetSumSolverDP.solve methods in your Parallel Recovery Block.
	 * Use the acceptance test you wrote in SubsetSumSolver
	 */	
	public boolean[] solve(int[] A, int target) throws ValidSolutionNotFoundException {

		BFThread bfThread = new BFThread(A, target);
		DPThread dpThread = new DPThread(A, target);
		bfThread.start();
		dpThread.start();
		
		//wait for both threads to finish
		try {
			bfThread.join();
			dpThread.join();
		} catch (InterruptedException ie) {
			throw new ValidSolutionNotFoundException();
		}
		
		if(accept(bfThread.result, A, target)) {
			return bfThread.result;
		} else if (accept(dpThread.result, A, target)) {
			return dpThread.result;
		}
		
		throw new ValidSolutionNotFoundException();
	}
	
	/**
	 * Private thread for executing the
	 * brute force subset sum solver
	 */
	private class BFThread extends Thread {
		
		int[] A;
		int target;
		boolean[] result;
		
		BFThread(int[] A, int target) {
			super();
			this.A = A;
			this.target = target;
			result = null;
		}
		
		public void run() {
			result = SubsetSumImplementations.solveBF(A, target);
		}
	}
	
	/**
	 * Private thread for executing the
	 * dynamic programming subset sum solver
	 */
	private class DPThread extends Thread {
		
		int[] A;
		int target;
		boolean[] result;
		
		DPThread(int[] A, int target) {
			super();
			this.A = A;
			this.target = target;
			result = null;
		}
		
		public void run() {
			result = SubsetSumImplementations.solveDP(A, target);
		}
	}
}
