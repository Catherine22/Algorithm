package com.catherine.utils;

public class TrackLog {

	private String tag = "NO TAG";
	private long startTime;
	private long endTime;
	private long duration;
	private long startMem;
	private long endMem;
	private int memUsage;
	
	public TrackLog(String tag){
		this.tag = tag;
	}

	private long getDuration() {
		return endTime - startTime;
	}

	private long getMemUsage() {
		return endMem - startMem;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setStartMem(long startMem) {
		this.startMem = startMem;
	}

	public void setEndMem(long endMem) {
		this.endMem = endMem;
	}

	public String toString() {
		return tag + " tooks " + getDuration() + "ms and increased memory " + getMemUsage();
	}

}
