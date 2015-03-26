import java.util.List;
import java.awt.GraphicsEnvironment;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.jfree.ui.RefineryUtilities;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class PinnedPinnedSandwichBeamSolver {
	
	final static String PROBLEM = "PinnedPinnedSandwichBeam";
	final static Class<PinnedPinnedSandwichBeam> PROBLEM_CLASS = PinnedPinnedSandwichBeam.class; 
	final static String ALGORITHM = "NSGAII";
	final static int MAX_EVALUATIONS = 10000;
	final static int NUMBER_RUNS = 3;
	final static String OBJECTIVE1 = "Fundamental Frequency";
	final static String OBJECTIVE2 = "Total cost";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*NondominatedPopulation result = new Executor()
		.withProblem(PROBLEM)
		.withAlgorithm(ALGORITHM)
		.withMaxEvaluations(MAX_EVALUATIONS)	
		.distributeOnAllCores()
		.run();

		//display the results
		int COUNT = result.size();
		float [][] data = new float[2][COUNT];
		double [][] ddata = new double[2][COUNT];
		int i = 0;		
		System.out.format("Objective1  Objective2%n");
		for (Solution solution : result) {
			System.out.format("%.4f      %.4f%n",
					solution.getObjective(0),
					solution.getObjective(1));
			data[0][i] = (float) solution.getObjective(0);
			data[1][i] = (float) solution.getObjective(1);
			ddata[0][i] = (double) solution.getObjective(0);
			ddata[1][i] = (double) solution.getObjective(1);
			i++;
		}
		
		plotParetoFront plotChart = new plotParetoFront("Pareto solutions", data);
		plotChart.pack();
		RefineryUtilities.centerFrameOnScreen(plotChart);
		plotChart.setVisible(true);
		
		Hypervolume hv = new Hypervolume(new PinnedPinnedSandwichBeam(), analyzer.getReferenceSet());
		//double hvi = hv.evaluate(result);	
		//System.out.println(hvi);*/
		
		String dirName = "Output/";
		new File(dirName).mkdirs();
		
		Analyzer analyzer = new Analyzer()
		.withProblemClass(PROBLEM_CLASS)
		.includeAllMetrics()
		.showStatisticalSignificance();
		
		Executor executor = new Executor()
		.withProblemClass(PROBLEM_CLASS)
		.withMaxEvaluations(MAX_EVALUATIONS);
		
		List<NondominatedPopulation> results = new ArrayList<NondominatedPopulation>();
		results = executor.withAlgorithm(ALGORITHM).runSeeds(NUMBER_RUNS);
		
		File analyzerFile = new File(dirName + "Analyzer_" + "ALGORITHM" + ".txt");
		
		analyzer.addAll(ALGORITHM, results);
		//analyzer.printAnalysis();
		analyzer.showIndividualValues().printAnalysis();
		analyzer.showIndividualValues().saveAnalysis(analyzerFile);
		
		PrintWriter writer = new PrintWriter(dirName + ALGORITHM + "_" + PROBLEM + ".txt", "UTF-8");
		writer.println("Bruno Iochins Grisci");
		writer.println("Variable values for " + String.valueOf(NUMBER_RUNS) + " runs of " + ALGORITHM + " for " + PROBLEM);
		
		int r = 0;
		for (NondominatedPopulation result : results) {
			int COUNT = result.size();
			float [][] data = new float[2][COUNT];
			
			writer.println(" ");
			writer.println("RUN #" + String.valueOf(r));
			
			int i = 0;		
			//System.out.format("Objective1  Objective2%n");
			for (Solution solution : result) {
				/*System.out.format("%.4f      %.4f%n",
						solution.getObjective(0),
						solution.getObjective(1));*/
				
				writer.println("	---");
				writer.println("	Solution #" + String.valueOf(i));
				writer.println("	Objective 1 (fundamental frequency): " + String.valueOf(solution.getObjective(0)));
				writer.println("	Objective 2 (total cost): " + String.valueOf(solution.getObjective(1)));
				
				String variables = "	Variables: ";
				int numberOfVariables = solution.getNumberOfVariables();
				for (int v = 0; v < numberOfVariables; v++){
					variables = variables + solution.getVariable(v).toString() + " | ";
				}
				writer.println("	Variables labels: L | b | d1 | d2 | d3");
				writer.println(variables);
				
				data[0][i] = (float) solution.getObjective(0);
				data[1][i] = (float) solution.getObjective(1);
				i++;
			}
			
			plotParetoFront plotChart = new plotParetoFront("Pareto solutions for run #" + String.valueOf(r), "Objective 1", "Objective 2", data,
															ALGORITHM, PROBLEM, r);
			plotChart.pack();
			RefineryUtilities.centerFrameOnScreen(plotChart);
			plotChart.setVisible(true);
			plotChart.setEnabled(true);
			
			r++;
		}
		
		writer.close();
	}

}