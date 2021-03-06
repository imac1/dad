package eu.ase.threads;

class SampleThread extends Thread {
  private String message;
  private double result;

  public SampleThread(String m) {this.message = m; this.result = 0.0;}
  public void run() {
     this.result = this.doAlgorithm();
  }
  private double doAlgorithm() {
     double val = 0;
     val++; //but could be 200 lines of code running for 10 sec
     try { 
       this.sleep(3000); 
     } catch(InterruptedException ie) { ie.printStackTrace(); }
     return val;
  }
  public double getSolution() {return this.result;}
}


public class ProgMainJoin {
  public static void main(String[] args) {
   SampleThread t = new SampleThread("T1");

   t.start();
   try {
	t.join(); //if this is comment, then the solution is NOT consistent
	System.out.println("Result = " + t.getSolution());
   } catch(InterruptedException ie) { ie.printStackTrace(); }
 } 
}
