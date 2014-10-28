package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.tum.in.dss.project.Pause;
import de.tum.in.dss.project.PauseVotingResult;
import de.tum.in.dss.project.Speed;

public class FinalPauseChart {

	public void createChart(List<PauseVotingResult> pvr) {
		// create simple xy chart
		XYSeries yesSeries = new XYSeries("Yes votes");
		XYSeries noSeries = new XYSeries("No votes");
		XYSeries sleepingSeries = new XYSeries("Sleeping votes");
		
		int yes, no, sleeping;
		//add values to the chart
		for(int i=0; i<pvr.size(); i++) {
			long time = pvr.get(i).getTime();
			Date date = new Date(time);
			String voteTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
			
			yes = 0; no = 0; sleeping = 0;
			
			Map<Pause,Integer> map = pvr.get(i).getEntries(); 
			
			for(Map.Entry<Pause,Integer> entry : map.entrySet()) {
				//System.out.println(entry.getKey() + " : " +entry.getValue());
				if(entry.getKey().equals(Pause.YES)) yes = entry.getValue(); 
				if(entry.getKey().equals(Pause.NO)) no = entry.getValue();
				if(entry.getKey().equals(Pause.SLEEPING)) sleeping = entry.getValue(); 
			}
			
			yesSeries.add(time, yes);
			noSeries.add(time, no);
			sleepingSeries.add(time, sleeping);
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(yesSeries);
		dataset.addSeries(noSeries);
		dataset.addSeries(sleepingSeries);
		
		 // Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Pause Votings", // Title
				"time", // x-axis Label
				"speedvoting", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("pauseChart.jpg"), chart, 600, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
		
	}
}
