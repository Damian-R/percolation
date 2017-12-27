import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private Percolation p;
  private double[] thresholds;
  private int T;
  public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
    if (n <= 0) throw new IllegalArgumentException(Integer.toString(n));
    if (trials < 0) throw new IllegalArgumentException(Integer.toString(trials));
    
    T = trials;
    thresholds = new double[trials];
    for (int i = 1; i <= trials; i++) {
      p = new Percolation(n);
      
      while (!p.percolates()) {
        int row;
        int col;
        do {
          row = (int) Math.floor(StdRandom.uniform(n) + 1);
          col = (int) Math.floor(StdRandom.uniform(n) + 1);
        } while (p.isOpen(row, col));
        p.open(row, col);
      }
      // p percolates at this point
      
      double threshold = (double) p.numberOfOpenSites()/(n*n);
      
      thresholds[i-1] = threshold;
    }
  }
  
  public double mean() {                          // sample mean of percolation threshold
    return StdStats.mean(thresholds);
  }
  
  public double stddev() {                        // sample standard deviation of percolation threshold
    return StdStats.stddev(thresholds);
  }
  
  public double confidenceLo() {                  // low  endpoint of 95% confidence interval
    return (mean() - (1.96*stddev())/Math.sqrt(T));
  }
  
  public double confidenceHi() {                  // high endpoint of 95% confidence interval
    return (mean() + (1.96*stddev())/Math.sqrt(T));
  }
  
  public static void main(String[] args) {        // test client (described below)
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    PercolationStats ps = new PercolationStats(N, T);
    System.out.println("mean: " + ps.mean());
    System.out.println("stddev: " + ps.stddev());
    System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
  }
}