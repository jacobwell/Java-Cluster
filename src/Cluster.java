/**
 * A K-means and DBScan Clustering Program
 * 
 * authors Jacob Wellinghoff (jgw7654), Ian Dempsey (ijd8975)
 * 
 * 
 */

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Cluster {
	public static Color[] colorWheel = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.LIGHT_GRAY, Color.PINK, Color.ORANGE, Color.GRAY, Color.MAGENTA, Color.BLACK, Color.CYAN,  Color.DARK_GRAY};
	public static Scanner scanner;
	public static ArrayList<String> scanned = new ArrayList<String>();
	
	public static ArrayList<ColoredPoint> allPoints = new ArrayList<ColoredPoint>();
	public static ArrayList<ArrayList<ColoredPoint>> clusters = new ArrayList<ArrayList<ColoredPoint>>();
	public static ArrayList<ColoredPoint> centroids = new ArrayList<ColoredPoint>();
	
	public static double getDistance (ColoredPoint p1, ColoredPoint p2) { return Math.sqrt(Math.pow((p2.getX() - p1.getX()),2) + Math.pow((p2.getY() - p1.getY()),2)); }
	
	public static void printClusters() {
		int clusterCount = 0;
		for (ArrayList<ColoredPoint> cluster: clusters) {
			for (ColoredPoint p : cluster ) { System.out.println("Cluster #" + clusterCount + " has " + p); }
			clusterCount++;
		}
		for (ColoredPoint c : centroids) { System.out.println("Centroid of " + c); }
	}
	
	public static boolean getNewCentroids() {
		Boolean madeChanges = false;
		int clusterCount = 0;
		for (ArrayList<ColoredPoint> cluster: clusters) {
			int tempCount = 0;
			ColoredPoint centroid = new ColoredPoint(0,0, Color.GREEN);
			for (ColoredPoint p : cluster) {
				centroid.translate((int) p.getX(), (int) p.getY());
				tempCount++;
			}
			centroid.setLocation(centroid.getX()/tempCount, centroid.getY()/tempCount);
			if (centroids.size() < clusters.size()) {centroids.add(centroid); madeChanges=true;}
			else {
				ColoredPoint c = centroids.get(clusterCount);
				if (c.getX()==centroid.getX() && c.getY()==centroid.getY()) {/*madeChanges = false;*/}
				else { madeChanges= true;
				System.out.println("Old centroid: " + c);
				System.out.println("New centroid:" + centroid);
				centroids.set(clusterCount, centroid);
				} 
			}
			System.out.println("made a centroid at: " + centroid);
			cluster = new ArrayList<ColoredPoint>();	//resets the cluster
			clusterCount++;
		}
		return madeChanges;
	}
	
	public static void sumAndCluster() {
		Boolean madeChanges = true;
		while (madeChanges) {
			madeChanges = getNewCentroids();
			int pointCount = 0;
			for (ColoredPoint p : allPoints) { //resort cluster
				double minDistance = 9999.99;
				int minGroup = 0;
				for (int i=0; i<centroids.size();i++) {
					ColoredPoint c = centroids.get(i);
					double distance = getDistance(c,p);
					if (distance<minDistance) {minGroup = i; minDistance=distance;}
				}
				ArrayList<ColoredPoint> clusterGroup = clusters.get(minGroup); //getting relevant cluster
				p.setColor(colorWheel[minGroup]);
				//System.out.println("assinged something to group/color: " + minGroup);
				allPoints.set(pointCount, p);
				clusterGroup.add(p); 
				pointCount++;
			}
		}
		printClusters();
		Visualize visualizer = new Visualize("Results");
		visualizer.showPoints(allPoints);
	}
	
	public static void roundRobinCluster(Scanner scanning, String clusterCount) {
		int numClusters = Integer.parseInt(clusterCount);
		for (int i=0; i<numClusters; i++ ) { clusters.add( new ArrayList<ColoredPoint>() ); } //make cluster groups
		int ticker = 1;
		while (scanner.hasNextLine()) {
		    	ArrayList<ColoredPoint> cluster = clusters.get(ticker-1);
		    	String[] values = scanner.nextLine().split("\t");
		    	ColoredPoint datum = new ColoredPoint(  Integer.parseInt(values[0]), Integer.parseInt(values[1]), Color.GREEN );
		    	System.out.println("ColoredPoint: " + datum + " appened to cluster #" + ticker);
		    	cluster.add(datum);
		    	allPoints.add(datum);
		    	if (ticker+1>numClusters) ticker = 1;
		    	else ticker++; 	
		 }
		sumAndCluster();
	}
	
	public static void reverseRoundRobinCluster(Scanner scanning, String clusterCount) {
		int numClusters = Integer.parseInt(clusterCount);
		for (int i=0; i<numClusters; i++ ) { clusters.add( new ArrayList<ColoredPoint>() ); } //make cluster groups
		int ticker = 1;
		while (scanner.hasNextLine()) {scanned.add(scanner.nextLine());}
		Collections.reverse(scanned); //reversing the list this will yield different results except where entries%clusters=0
		for (String line : scanned) {
		    	ArrayList<ColoredPoint> cluster = clusters.get(ticker-1);
		    	String[] values = line.split("\t");
		    	ColoredPoint datum = new ColoredPoint(  Integer.parseInt(values[0]), Integer.parseInt(values[1]), Color.GREEN );
		    	System.out.println("ColoredPoint: " + datum + " appened to cluster #" + ticker);
		    	cluster.add(datum);
		    	allPoints.add(datum);
		    	if (ticker+1>numClusters) ticker = 1;
		    	else ticker++;   	
		}
		sumAndCluster();
	}
		
	public static void splitCluster(Scanner scanning, String clusterCount) {
		int numClusters = Integer.parseInt(clusterCount);
		for (int i=0; i<numClusters; i++ ) { clusters.add( new ArrayList<ColoredPoint>() ); } //make cluster groups
		int tracker = 1;
		int ticker = 1;
		while (scanner.hasNextLine()) {scanned.add(scanner.nextLine());}
		for (String line : scanned) {
				//if (tracker > (scanned.size()/clusterCount)*2 ) {ticker=3;}
				//else if  (tracker > (scanned.size()/3)*1 ) {ticker = 2;}
				while (tracker > (scanned.size() / numClusters)*ticker && ticker < numClusters) {ticker++;}
		    	ArrayList<ColoredPoint> cluster = clusters.get(ticker-1);
		    	String[] values = line.split("\t");
		    	ColoredPoint datum = new ColoredPoint(  Integer.parseInt(values[0]), Integer.parseInt(values[1]), Color.GREEN );
		    	System.out.println("ColoredPoint: " + datum + " appened to cluster #" + ticker);
		    	cluster.add(datum);
		    	allPoints.add(datum);
		    	 tracker++;   	
		}
		sumAndCluster();
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 4) {System.out.println("Erorr: Invalid number of arguements.");}
		else if (args.length == 4) {
			try {
				scanner = new Scanner(new FileInputStream(args[1] ));
			} catch (Exception e) { 
				System.out.println("Error: File not found."); 
				//die edge case
			}
			if ("K".compareToIgnoreCase(args[0])==0) {
				if ("1".compareTo(args[2])==0) roundRobinCluster(scanner, args[3]);
				else if ("2".compareTo(args[2])==0) reverseRoundRobinCluster(scanner, args[3]);
				else if ("3".compareTo(args[2])==0) {splitCluster(scanner, args[3]);}
				else {
					//die edge case
				}
			}
			else if ("D".compareToIgnoreCase(args[0])==0) {
				System.out.println("It is D");
				DBScan dbscanner = new DBScan(Double.parseDouble(args[2]),Integer.parseInt(args[3]));
				while (scanner.hasNextLine()) {
					String[] values = scanner.nextLine().split("\t");
			    	ColoredPoint datum = new ColoredPoint(  Integer.parseInt(values[0]), Integer.parseInt(values[1]), Color.GREEN );
			    	allPoints.add(datum);
				}
				dbscanner.setDataset(allPoints);
				
			}
			else {
				//die edge case
			}
			
		}
	}
}
