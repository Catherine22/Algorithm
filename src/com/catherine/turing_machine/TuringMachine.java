package com.catherine.turing_machine;

import java.util.Stack;

public class TuringMachine {
	protected final boolean SHOW_DEBUG_LOG = true;

	/**
	 * 1. move head to the last character from index 0. 2. move head to the left
	 * index and update the value.
	 * 
	 * In this function, head keep moving until the flag 'accomplished' become
	 * to true, if take away the flag and the increase codes, the user-input
	 * array would be scanned without a break.
	 * 
	 * @param array
	 * @return
	 */
	public int[] increase(int[] array) {
		// It looks like [x x array[0] array[1]... array[length-1] x x]
		boolean accomplished = false;
		boolean hasIncreased = false;
		int head = 0;
		int headState = STATE.STOP.getValue();
		int direction = DIRECTION.RIGHT.getValue();

		// stage1 - turn right
		Instruction instruction = new Instruction();
		instruction.head = head;
		instruction.headState = headState;
		instruction.chr = String.valueOf(array[head]);
		instruction.nextChr = String.valueOf(array[head + 1]);
		instruction.direction = direction;
		instruction.nextHeadState = STATE.TO_RIGHT.getValue();
		record(instruction);
		headState = instruction.nextHeadState;
		head++;
		while (!accomplished) {
			if (head < 0) {
				// head is out of symbol array and is at the left side of the
				// tape.
				// out of border
				direction = DIRECTION.RIGHT.getValue();
				instruction = new Instruction();
				instruction.head = head;
				head++;
				instruction.headState = headState;
				instruction.chr = String.valueOf("*");
				instruction.nextChr = String.valueOf(array[head]);
				instruction.direction = direction;
				instruction.nextHeadState = STATE.TO_RIGHT.getValue();
				record(instruction);
				headState = instruction.nextHeadState;
				accomplished = true;
			} else if (head > array.length - 1) {
				// head is out of symbol array and is at the right side of the
				// tape.
				// out of border
				direction = DIRECTION.LEFT.getValue();
				instruction = new Instruction();
				instruction.head = head;
				head--;
				instruction.headState = headState;
				instruction.chr = String.valueOf("*");
				instruction.nextChr = String.valueOf(array[head]);
				instruction.direction = direction;
				instruction.nextHeadState = STATE.TO_LEFT.getValue();
				record(instruction);
				headState = instruction.nextHeadState;
			} else {
				if (direction == DIRECTION.RIGHT.getValue()) {
					instruction = new Instruction();
					instruction.headState = headState;
					instruction.chr = String.valueOf(array[head]);
					if (head == array.length - 1)// at the last position
						instruction.nextChr = String.valueOf("*");
					else
						instruction.nextChr = String.valueOf(array[head + 1]);
					instruction.direction = direction;
					instruction.nextHeadState = STATE.TO_RIGHT.getValue();
					instruction.head = head;
					head++;
					record(instruction);
					headState = instruction.nextHeadState;
				} else {

					// Increase--------------------------------------------
					if (!hasIncreased) {
						/**
						 * a^b means a XOR b <br>
						 * It always returns 0 while a equals b
						 *
						 */
						array[head] = array[head] ^ 1;

						if (array[head] == 1)
							hasIncreased = true;
					}
					// Increase--------------------------------------------

					instruction = new Instruction();
					instruction.headState = headState;
					instruction.chr = String.valueOf(array[head]);
					if (head == 0)// at the first position
						instruction.nextChr = String.valueOf("*");
					else
						instruction.nextChr = String.valueOf(array[head - 1]);
					instruction.direction = direction;
					instruction.nextHeadState = STATE.TO_LEFT.getValue();
					instruction.head = head;
					head--;
					record(instruction);
					headState = instruction.nextHeadState;
				}
			}
		}

		// back to origin
		instruction = new Instruction();
		instruction.head = head;
		instruction.headState = STATE.STOP.getValue();
		instruction.chr = String.valueOf(array[0]);
		instruction.nextChr = String.valueOf(array[1]);
		instruction.direction = direction;
		instruction.nextHeadState = STATE.STOP.getValue();
		record(instruction);
		headState = instruction.nextHeadState;

		return array;
	}

	private Stack<Instruction> history = new Stack<>();

	/**
	 * Instructions stack
	 * 
	 * @param headState
	 * @param chr
	 * @param nextChr
	 * @param direction
	 * @param nextHeadState
	 */
	private void record(Instruction instruction) {
		if (SHOW_DEBUG_LOG)
			System.out.println(instruction.toString());
		history.push(instruction);
	}

	private enum STATE {
		TO_LEFT(1), TO_RIGHT(2), STOP(3);
		private final int value;

		private STATE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static STATE fromInteger(int x) {
			switch (x) {
			case 1:
				return TO_LEFT;
			case 2:
				return TO_RIGHT;
			case 3:
				return STOP;
			}
			return null;
		}
	}

	private enum DIRECTION {
		LEFT(1), RIGHT(2);
		private final int value;

		private DIRECTION(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static DIRECTION fromInteger(int x) {
			switch (x) {
			case 1:
				return LEFT;
			case 2:
				return RIGHT;
			}
			return null;
		}
	}

	private class Instruction {
		private int head;
		public int headState;
		public String chr;
		public String nextChr;
		public int direction;
		public int nextHeadState;

		@Override
		public String toString() {
			return "Instruction{" + "head='" + head + '\'' + ", chr='" + chr + '\'' + ", headState="
					+ STATE.fromInteger(headState).name() + ", nextChr='" + nextChr + '\'' + ", direction="
					+ DIRECTION.fromInteger(direction).name() + ", nextHeadState="
					+ STATE.fromInteger(nextHeadState).name() + '}';
		}

	}
}
