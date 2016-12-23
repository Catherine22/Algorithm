package com.catherine.utils;

/**
 * Asymptotic
 * 1. Ignore machine-dependant constants
 * 2. Instead of the actual running time, look at the growth of running time.
 * @author Catherine
 *
 */
public class Analysis {
	
	public static void startTracing(TraceLog tLog){
		tLog.setStartTime(System.currentTimeMillis());
		
		Runtime runtime = Runtime.getRuntime();
	    long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		tLog.setStartMem(usedMemory);
	}
	
	public static void endTracing(TraceLog tLog){
		tLog.setEndTime(System.currentTimeMillis());
		
		Runtime runtime = Runtime.getRuntime();
	    long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		tLog.setEndMem(usedMemory);
	}

	public static void printTrace(TraceLog tLog){
		System.out.println("LOG-----------");
		System.out.println(tLog.toString());
		System.out.println("LOG-----------");
	}

	/**
	 * 运行时间的上线
	 */
	public void getRunningTimeUpperBounds() {

	}

	/**
	 * Get maximum time
	 * @param n input size
	 * @return the maximum time on any input of size n
	 */
	public double getWorstCastRunningTime(int n) {

		return 0;
	}

	/**
	 * Get average time. It's a weighted average(加权平均).
	 * @param n any expected time over all inputs of size n
	 * @return the average time on any input of size n
	 */
	public double getAverageCastRunningTime(int n) {

		return 0;
	}
}
