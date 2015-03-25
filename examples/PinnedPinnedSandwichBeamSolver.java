import java.util.List;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

//import jmetal.qualityIndicator.Hypervolume;




import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.RefineryUtilities;
import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.indicator.Hypervolume;

public class PinnedPinnedSandwichBeamSolver {
	
	final static String PROBLEM = "PinnedPinnedSandwichBeam";
	final static Class<PinnedPinnedSandwichBeam> PROBLEM_CLASS = PinnedPinnedSandwichBeam.class; 
	final static String ALGORITHM = "NSGAII";
	final static int MAX_EVALUATIONS = 10000;
	final static int NUMBER_RUNS = 30;
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
		
		Analyzer analyzer = new Analyzer()
		.withProblemClass(PROBLEM_CLASS)
		.includeAllMetrics()
		.showStatisticalSignificance();
		
		Executor executor = new Executor()
		.withProblemClass(PROBLEM_CLASS)
		.withMaxEvaluations(MAX_EVALUATIONS);
		
		List<NondominatedPopulation> results = new ArrayList<NondominatedPopulation>();
		results = executor.withAlgorithm(ALGORITHM).runSeeds(NUMBER_RUNS);
		
		analyzer.addAll(ALGORITHM, results);
		//analyzer.printAnalysis();
		analyzer.showIndividualValues().printAnalysis();
		
		for (NondominatedPopulation result : results) {
			int COUNT = result.size();
			float [][] data = new float[2][COUNT];
			int i = 0;		
			System.out.format("Objective1  Objective2%n");
			for (Solution solution : result) {
				System.out.format("%.4f      %.4f%n",
						solution.getObjective(0),
						solution.getObjective(1));
				data[0][i] = (float) solution.getObjective(0);
				data[1][i] = (float) solution.getObjective(1);
				i++;
			}
			
			plotParetoFront plotChart = new plotParetoFront("Pareto solutions", data);
			plotChart.pack();
			RefineryUtilities.centerFrameOnScreen(plotChart);
			plotChart.setVisible(true);
		}
		
	}

}

