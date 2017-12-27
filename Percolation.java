import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int size;
  private WeightedQuickUnionUF uf;
  private int grid[];
  private int openCounter;
  
  public Percolation(int n) { // create n-by-n grid, with all sites blocked
    if (n <= 0) throw new IllegalArgumentException(Integer.toString(n));
    
    this.size = n;
    openCounter = 0;
    grid = new int[n*n + 2]; // size-2 is top virtual site, size-1 is bottom virtual site
    for (int i = 0; i < (n*n); i++)
      grid[i] = 0; // 0 means closed
    grid[size*size] = 1;
    grid[size*size + 1] = 1;
    uf = new WeightedQuickUnionUF(n*n + 2);
    for (int i = 0; i < size; i++) {
      uf.union(i, size*size); // connect top virtual site to top row
      uf.union((size*(size-1)) + i, size*size + 1); // connect bottom virtual site to bottom row
    }
  }
  
  public void open(int row, int col) {    // open site (row, col) if it is not open already
    if (row < 1 || row > size) throw new IllegalArgumentException(Integer.toString(row));
    if (col < 1 || col > size) throw new IllegalArgumentException(Integer.toString(col));
    
    int index = (this.size*(row-1)) + col - 1;
    if (isOpen(row, col)) return;
    openCounter++;
    grid[index] = 1;
    int remainder = index % this.size;
    if (remainder != this.size - 1)
      if (this.isOpen(row, col + 1)) this.uf.union(index, index + 1); // try to join right site
      
    if (remainder != 0)
      if (this.isOpen(row, col - 1)) this.uf.union(index, index - 1); // try to join left site
      
    if (row != this.size)
      if (this.isOpen(row + 1, col)) this.uf.union(index, index + this.size); // try to join bottom site
      
    if (row != 1)
      if (this.isOpen(row - 1, col)) this.uf.union(index, index - this.size); // try to join top site
  }
  
  public boolean isOpen(int row, int col) {  // is site (row, col) open?
    if (row < 1 || row > size) throw new IllegalArgumentException(Integer.toString(row));
    if (col < 1 || col > size) throw new IllegalArgumentException(Integer.toString(col));
    
    int index = (size*(row-1)) + col - 1;
    if (grid[index] == 1)
      return true;
    return false;
  }
  
  public boolean isFull(int row, int col) {  // is site (row, col) full?
    if (row < 1 || row > size) throw new IllegalArgumentException(Integer.toString(row));
    if (col < 1 || col > size) throw new IllegalArgumentException(Integer.toString(col));
    
    int index = (size*(row-1)) + col - 1;
    if (!isOpen(row, col)) return false;
    return uf.connected(index, size*size);
  }
  
  public int numberOfOpenSites() {       // number of open sites
    return openCounter;
  }
  
  public boolean percolates() {              // does the system percolate?
    if (uf.connected(size*size, size*size-1)) return true;
    return false;
  }
  
  public static void main(String[] args) {
//    Percolation p = new Percolation(5);
//    p.open(1, 1);
//    p.open(2, 1);
//    p.open(3, 1);
//    p.open(3, 2);
//    p.open(4, 2);
//    p.open(5, 2);
//    System.out.println(p.uf.connected(0, 10));
//    System.out.println(p.percolates());
//    
//    System.out.println("FULL " + p.isFull(4, 2));
//    
//    System.out.println(p.numberOfOpenSites());
    
  }   // test client (optional)
}