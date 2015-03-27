import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

public class PinnedPinnedSandwichBeam extends AbstractProblem {
	
	public PinnedPinnedSandwichBeam() {
		super(5,2);
	}
	
	//Indexes for acessing the decision variables
	private final int index_L = 0;
	private final int index_b = 1;
	private final int index_d1 = 2;
	private final int index_d2 = 3;
	private final int index_d3 = 4;
	
	//Constraints values
	private boolean g3;
	private boolean g6;
	private boolean g7;
	
	//Problem constants
	private final double p1 = 100.0;
	private final double p2 = 2770.0;
	private final double p3 = 7780.0;
	private final double E1 = 1.60E+09;
	private final double E2 = 7.00E+10;
	private final double E3 = 2.00E+11;
	private final double c1 = 500;
	private final double c2 = 1500;
	private final double c3 = 800;
	
	@Override
	public void evaluate(Solution solution) {
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f2 = 0.0; 
		
		//Objective function 1: fundamental frquency
		f1 = (Math.PI/(2.0 * Math.pow(x[index_L], 2))) * Math.pow(EI(x)/mu(x), 0.5);
		//Objective function 2: total cost
		f2 = 2.0*x[index_b]*x[index_L]*(c1*x[index_d1] + c2*(x[index_d2] - x[index_d1]) + c3*(x[index_d3] - x[index_d2]));
		
		//Constraints
		g3 = (2000.0 <= mu(x)*x[index_L]) && (mu(x)*x[index_L] <= 2800.0);
		g6 = (0.01 <= x[index_d2] - x[index_d1]) && (x[index_d2] - x[index_d1] <= 0.58);
		g7 = (0.01 <= x[index_d3] - x[index_d2]) && (x[index_d3] - x[index_d2] <= 0.57);
		
		if (g3 && g6 && g7) {
			solution.setObjective(0, f1);
			solution.setObjective(1, f2);
		}
		else {
			 //If it is an infeasible, penalise it.
			 solution.setObjective(0, Double.POSITIVE_INFINITY);
			 solution.setObjective(1, Double.POSITIVE_INFINITY);		
		}
	}
	
	public double EI(double[] x) {
		double c = (2.0 * x[index_b])/3.0;
		double d13 = Math.pow(x[index_d1], 3);
		double d23 = Math.pow(x[index_d2], 3);
		double d33 = Math.pow(x[index_d3], 3);
		return c*(E1*d13 + E2*(d23 - d13) + E3*(d33 - d23));
	}
	
	public double mu(double[] x) {
		double c = 2.0 * x[index_b];
		return c*(p1*x[index_d1] + p2*(x[index_d2] - x[index_d1]) + p3*(x[index_d3] - x[index_d2]));
	}
	
	@Override
	public Solution newSolution() {
		//Decision variables
		Solution solution = new Solution(numberOfVariables, numberOfObjectives);
		solution.setVariable(index_L, new RealVariable(3.0, 6.0)); // L
		solution.setVariable(index_b, new RealVariable(0.3, 0.55)); // b
		solution.setVariable(index_d1, new RealVariable(0.01, 0.58)); // d1
		solution.setVariable(index_d2, new RealVariable(0.02, 1.16)); //d2
		solution.setVariable(index_d3, new RealVariable(0.3, 0.6)); //d3
		return solution;
	}

}

