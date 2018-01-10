package com.catherine.sort;

import java.util.Stack;

public class SortableStackPermutation {

	
	/**
	 * 栈混洗<br>
	 * 三个栈：oriStack、cacheStack、sortedStack，<br>
	 * 栈混洗只能做两件事oriStack.pop()传到cacheStack、cacheStack.pop()传到sortedStack
	 * @param oriStack 原始栈
	 * @param sortedStack 排序后的栈
	 * @return 排序后的栈是否为原始栈经过合法混洗所得
	 */
	public boolean isSortableStack(Stack<Integer> oriStack, Stack<Integer> sortedStack) {
		Stack<Integer> newS = new Stack<>();
		Stack<Integer> cacheS = new Stack<>();
		
		if(oriStack.isEmpty() || sortedStack.isEmpty())
			return false;

		while (!oriStack.isEmpty()) {

			cacheS.push(oriStack.pop());

			System.out.println(sortedStack.peek() + " vs " + cacheS.peek());
			if (sortedStack.peek() == cacheS.peek()) {
				newS.push(cacheS.pop());
				sortedStack.pop();
			}
		}

		while (!cacheS.isEmpty()) {
			System.out.println(sortedStack.peek() + " vs " + cacheS.peek());
			if (sortedStack.peek() == cacheS.peek()) {
				newS.push(cacheS.pop());
				sortedStack.pop();
			} else
				return false;
		}

		return true;

	}
}
