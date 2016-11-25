package com.catherine.turing_machine;

import java.util.Stack;

public class TuringMachine {
	/**
	 * 1. move head to the character at the end
	 * 
	 * @param array
	 * @return
	 */
	public int[] increase(int[] array) {
		// It looks like [x x array[0] array[1]... array[length-1] x x]
		int[] result = new int[array.length];
		boolean accomplished = false;
		boolean hasIncreased = false;
		int head = 0;
		int headState = STATE.STOP.getValue();
		int direction = DIRECTION.RIGHT.getValue();

		// stage1 - turn right
		Instruction instruction = new Instruction();
		instruction.headState = headState;
		instruction.chr = String.valueOf(array[head]);
		instruction.nextChr = String.valueOf(array[head + 1]);
		instruction.direction = direction;
		instruction.nextHeadState = STATE.TO_RIGHT.getValue();
		record(instruction);
		headState = instruction.nextHeadState;
		head++;

		while (!accomplished) {
			if (head == 0) {
				// head is out of symbol array and is at the left side of the
				// tape.
				direction = DIRECTION.RIGHT.getValue();

				instruction = new Instruction();
				instruction.headState = headState;
				instruction.chr = String.valueOf(array[head]);
				instruction.nextChr = String.valueOf(array[head + 1]);
				instruction.direction = direction;
				instruction.nextHeadState = STATE.TO_RIGHT.getValue();
				record(instruction);
				headState = instruction.nextHeadState;
				head++;
				accomplished = true;
			} else if (head == array.length - 1) {
				// head is out of symbol array and is at the right side of the
				// tape.
				direction = DIRECTION.LEFT.getValue();

				instruction = new Instruction();
				instruction.headState = headState;
				instruction.chr = String.valueOf(array[head]);
				instruction.nextChr = String.valueOf(array[head - 1]);
				instruction.direction = direction;
				instruction.nextHeadState = STATE.TO_LEFT.getValue();
				record(instruction);
				headState = instruction.nextHeadState;
				head--;
			} else {
				if (direction == DIRECTION.RIGHT.getValue()) {
					instruction = new Instruction();
					instruction.headState = headState;
					instruction.chr = String.valueOf(array[head]);
					instruction.nextChr = String.valueOf(array[head + 1]);
					instruction.direction = direction;
					instruction.nextHeadState = STATE.TO_RIGHT.getValue();
					record(instruction);
					headState = instruction.nextHeadState;
					head++;
				} else {
//					if (!hasIncreased) {
//						/**
//						 * a^b means a XOR b <br>
//						 * It always returns 0 while a equals b
//						 * 
//						 */
//						array[head] = array[head] ^ 1;
//
//						if (array[head] == 1)
//							hasIncreased = true;
//					}

					instruction = new Instruction();
					instruction.headState = headState;
					instruction.chr = String.valueOf(array[head]);
					instruction.nextChr = String.valueOf(array[head - 1]);
					instruction.direction = direction;
					instruction.nextHeadState = STATE.TO_LEFT.getValue();
					record(instruction);
					headState = instruction.nextHeadState;
					head--;

				}
			}
		}

		return result;
	}

	/**
	 * Instructions stack
	 * 
	 * @param headState
	 * @param chr
	 * @param nextChr
	 * @param direction
	 * @param nextHeadState
	 * @return
	 */
	private Stack record(Instruction instruction) {

		System.out.println(instruction.toString());
		return null;
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
		public int headState;
		public String chr;
		public String nextChr;
		public int direction;
		public int nextHeadState;

		@Override
		public String toString() {
			return "Instruction{" + "chr='" + chr + '\'' + ", headState=" + STATE.fromInteger(headState).name()
					+ ", nextChr='" + nextChr + '\'' + ", direction=" + DIRECTION.fromInteger(direction).name()
					+ ", nextHeadState=" + STATE.fromInteger(nextHeadState).name() + '}';
		}

	}
}
