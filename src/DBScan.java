/**
 * A DBScan tool, to sort points into density based clusters
 * 
 * authors Ian Dempsey (ijd8975), Jacob Wellinghoff (jgw7654),
 * 
 * 
 */

import java.util.*;

public class DBScan {
    ArrayList <ColoredPoint> data;
    HashSet <ColoredPoint> visited;
    HashSet <ColoredPoint> unvisited;
    double eps;
    int minPts;

    public DBScan(double eps_val, int minPts_val) {
        data = new ArrayList<ColoredPoint>();
        visited = new HashSet<ColoredPoint>();
        unvisited = new HashSet<ColoredPoint>();
        eps = eps_val;
        minPts = minPts_val;
    }
    
    public static double getDistance (ColoredPoint p1, ColoredPoint p2) { return Math.sqrt(Math.pow((p2.getX() - p1.getX()),2) + Math.pow((p2.getY() - p1.getY()),2)); }
    public void setEps(double e) {eps = e;}
    public void setMinPts(int m){minPts = m;}
    
    public ArrayList <ColoredPoint> regionQuery (ColoredPoint core) {
        ArrayList <ColoredPoint> result = new ArrayList <ColoredPoint> ();
        for(ColoredPoint p : data) {
            double dist = getDistance(core,p);
            if(dist <= eps && p != core)  result.add(p);
        }
        return result;
    }

    
    public void cluster() {
       //HashSet<Double[]> visited = new HashSet<Double[]>();

        for (ColoredPoint p : data) {
            if (!visited.contains(p)) {
                visited.add(p);
/*
                HashSet<Double[]> group = new HashSet<Double[]>   //get points reachable from p
                       // (getReachable(p, new HashSet<Double[]>()));

                if (group.size() < minPts) 
                    noise.addAll(group);
                else {
                    Cluster c = new Cluster(points[0].length);
                    for (Double[] d : group)
                        c.addData(toDoubArray(d));
                    clusters.add(c);
                }
                
                visited.addAll(group);
            }
       
 */
                  
    }
   }
}
    
    public void setDataset(ArrayList<ColoredPoint> p) {
        data = p;
        unvisited = new HashSet<ColoredPoint>(data);
        visited.clear();
    }

    public HashSet <ColoredPoint> getUnvisited() {return unvisited;}

    public HashSet <ColoredPoint> getVisited() {return visited;}
    public void visit(ColoredPoint p) {visited.add(p);unvisited.remove(p);}

}