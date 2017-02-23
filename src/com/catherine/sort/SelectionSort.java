package com.catherine.sort;

import java.util.LinkedList;

import com.catherine.Main;
import com.catherine.utils.Analysis;
import com.catherine.utils.TrackLog;

/**
 * best: n <br>
 * average: n^2 <br>
 * worst: n^2 <br>
 * <br>
 * memory: 1
 * 
 * 挑出集合中最大（小）值，挪到最后面。<br>
 * selection sort适合用LinkedList实作，因为只要改引用而不是调整整个数组。
 * 
 * @author Catherine
 *
 */
public class SelectionSort extends BaseSort {

	@Override
	public int[] sort(int[] input, boolean isAscending) {
		TrackLog tLog = new TrackLog("SelectionSort");
		Analysis.startTracking(tLog);
		if (input == null)
			return null;
		if (input.length == 1)
			return input;

		LinkedList<Integer> outputs = new LinkedList<>();
		for (int v : input) {
			outputs.add(v);
		}
		
		if(SHOW_DEBUG_LOG)
			Main.printList("original list", outputs);

		int header = outputs.size()-1;
		int tag = 0;
		int minOrMax = outputs.getFirst();
		
		while (header >= 0) {
			for (int i = 0; i <= header; i++) {
				if (isAscending) {
					if (minOrMax > outputs.get(i)){
						minOrMax = outputs.get(i);
						tag = i;
					}
				}else{
					if (minOrMax < outputs.get(i)){
						minOrMax = outputs.get(i);
						tag = i;
					}
				}
			}

			if(SHOW_DEBUG_LOG)
				System.out.println("tag:"+tag+"/header:"+header+"/minOrMax:"+minOrMax);

			outputs.remove(tag);
			outputs.add(header--, minOrMax);
			tag = 0;
			minOrMax = outputs.get(0);
			

			if(SHOW_DEBUG_LOG)
				Main.printList("sorted list", outputs);
		}
		
		for(int i=0;i<input.length;i++)
			input[i] = outputs.pop();

		Analysis.endTracking(tLog);
		if (SHOW_DEBUG_LOG)
			Analysis.printTrack(tLog);
		return input;
	}

}
