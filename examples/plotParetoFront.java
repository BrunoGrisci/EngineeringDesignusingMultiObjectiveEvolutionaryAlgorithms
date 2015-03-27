import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;


public class plotParetoFront extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	public plotParetoFront(String chartTitle, String objective1, String objective2, float[][] data, String algorithm, String problem, int run) throws IOException {
		super(chartTitle);
		// TODO Auto-generated constructor stub

        final NumberAxis domainAxis = new NumberAxis(objective1);
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis(objective2);
        rangeAxis.setAutoRangeIncludesZero(false);
        final FastScatterPlot plot = new FastScatterPlot(data, domainAxis, rangeAxis);
        final JFreeChart chart = new JFreeChart(chartTitle, plot);
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        setContentPane(panel);
        
        String dirName = "Output/";
        File plotFile = new File(dirName + algorithm + "_" + problem + "_" + String.valueOf(run) + ".png");
        ChartUtilities.saveChartAsPNG(plotFile, chart, 500, 270);  
	}
	
}