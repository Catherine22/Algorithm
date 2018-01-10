package com.catherine.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Asymptotic 1. Ignore machine-dependant constants 2. Instead of the actual
 * running time, look at the growth of running time.
 * 
 * @author Catherine
 *
 */
public class Analysis {
	private static Map<Integer, Integer> counterCollection = new HashMap<>();

	public static void startTracking(TrackLog tLog) {
		tLog.setStartTime(System.currentTimeMillis());

		Runtime runtime = Runtime.getRuntime();
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		tLog.setStartMem(usedMemory);
	}

	public static void endTracking(TrackLog tLog) {
		tLog.setEndTime(System.currentTimeMillis());

		Runtime runtime = Runtime.getRuntime();
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		tLog.setEndMem(usedMemory);
	}

	public static void printTrack(TrackLog tLog) {
		System.out.println("LOG-----------");
		System.out.println(tLog.toString());
		System.out.println("LOG-----------");
	}

	/**
	 * Calculate the loop
	 * 
	 * @param methodID
	 *            自定义方法的ID，该数值不重复
	 */
	public static void count(int methodID) {
		if (counterCollection.containsKey(methodID))
			counterCollection.put(methodID, counterCollection.get(methodID) + 1);
		else
			counterCollection.put(methodID, 1);
	}

	/**
	 * Stop {{@link #count(int)}
	 * 
	 * @param methodID
	 *            自定义方法的ID，该数值不重复
	 */
	public static void stopCounting(int methodID) {
		if (counterCollection.containsKey(methodID)) {
			System.out.println(String.format("It ran %s times.", counterCollection.get(methodID)));
			counterCollection.remove(methodID);
		}
	}

	/**
	 * Get maximum time
	 * 
	 * @param n
	 *            input size
	 * @return the maximum time on any input of size n
	 */
	public double getWorstCastRunningTime(int n) {

		return 0;
	}

	/**
	 * Get average time. It's a weighted average(加权平均).
	 * 
	 * @param n
	 *            any expected time over all inputs of size n
	 * @return the average time on any input of size n
	 */
	public double getAverageCastRunningTime(int n) {

		return 0;
	}
}
