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

import de.tum.in.dss.project.Speed;
import de.tum.in.dss.project.SpeedVotingResult;

public class FinalSpeedChart {

	public void createChart(List<SpeedVotingResult> svr) {
		// create simple xy chart
		XYSeries series = new XYSeries("Speed Voting");
		
		int slower, ok, faster;
		double resultSpeed;
		//add values to the chart
		for(int i=0; i<svr.size(); i++) {
			long time = svr.get(i).getTime();
			Date date = new Date(time);
			String voteTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
			
			
			slower = 0; ok = 0; faster = 0;
			resultSpeed = 50.0;
			
			Map<Speed,Integer> map = svr.get(i).getEntries();
			
			for(Map.Entry<Speed,Integer> entry : map.entrySet()) {
				//System.out.println(entry.getKey() + " : " +entry.getValue());
				if(entry.getKey().equals(Speed.SLOWER)) slower = entry.getValue(); 
				if(entry.getKey().equals(Speed.OK)) ok = entry.getValue();
				if(entry.getKey().equals(Speed.FASTER)) faster = entry.getValue(); 
			}
			if(slower != 0 || ok != 0 || faster != 0) { 
				double sumofvotes = slower + ok + faster;
				//slower = slower * 1;
				ok = ok * 2 ;
				faster = faster * 3;
				resultSpeed = (double)(slower+ok+faster) / sumofvotes;
			
				resultSpeed = ((resultSpeed-1) / 2) * 100;
			}
			
			series.add(time, resultSpeed); 
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		 // Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Speed Votings", // Title
				"time", // x-axis Label
				"speedvoting", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("speedChart.jpg"), chart, 600, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
		
	}
}
