package edu.upenn.cis573.subsetsum;

public class SubsetSumRecoveryBlock extends SubsetSumSolver {

	/*
	 * Use a Recovery Block to implement this method.
	 * Use the SubsetSumSolverBF.solve and SubsetSumSolverDP.solve methods in your Recovery Block.
	 * Use the acceptance test you wrote in SubsetSumSolver
	 */	
	public boolean[] solve(int[] A, int target) throws ValidSolutionNotFoundException {

		//save state
		int[] copy = new int[A.length];
		System.arraycopy(A, 0, copy, 0, A.length);
		
		boolean[] result = SubsetSumImplementations.solveDP(A, target);
		if (accept(result, A, target)) {
			return result;
		} else {
			//restore state
			System.arraycopy(copy, 0, A, 0, copy.length);
			
			result = SubsetSumImplementations.solveBF(A, target);
			if (accept(result, A, target)) {
				return result;
			} else {
				//restore state
				System.arraycopy(copy, 0, A, 0, copy.length);
			}
		}
				
		throw new ValidSolutionNotFoundException();
	}
}
