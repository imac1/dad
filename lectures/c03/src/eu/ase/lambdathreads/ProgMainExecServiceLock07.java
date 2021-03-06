package eu.ase.lambdathreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.concurrent.TimeUnit;

/**
 * @author Benjamin Winterberg
 */
public class ProgMainExecServiceLock07 {

    private static final int NUM_INCREMENTS = 1000;

    private static ReentrantLock lock = new ReentrantLock();

    private static int count = 0;

    private static void increment() {
	try { Thread.currentThread().sleep(50); } 
	catch(InterruptedException ie) {ie.printStackTrace();}
	count++;
    }

    private static void incrementSync() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
	testLock();

	testLockSync();
    }

    private static void stop(ExecutorService executor) {
	// executor shutdown
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(55, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
			System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}

		System.out.println("Done!");
    }

    private static void testLock() {
        count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                 .forEach(i -> executor.submit(ProgMainExecServiceLock07::increment));

        stop(executor);
        System.out.println(count);
    }

    private static void testLockSync() {
        count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                 .forEach(i -> executor.submit(ProgMainExecServiceLock07::incrementSync));

        stop(executor);
        System.out.println(count);
    }

} //end class
