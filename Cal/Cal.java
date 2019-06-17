import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Cal {

	public static void main(String[] args) {

		String[] src = { "2", "*", "(", "(", "-2", ")", "+", "-1", ")" };

		ArrayList<String> srcList2 = removeBrackets(src);

		String result = execute(srcList2);

		System.out.println(result);

	}

	private static ArrayList<String> removeBrackets(String[] srcList) {
		Stack<String> stack = new Stack<String>();

		// Remove the brackets
		for (int i = 0; i < srcList.length; i++) {
			String cell = srcList[i];
			if (cell == ")") {
				ArrayList<String> tmpArray = new ArrayList<String>();
				while (stack.peek() != "(") {
					tmpArray.add(stack.pop());
				}
				stack.pop();
				Collections.reverse(tmpArray);
				String unit = execute(tmpArray);
				stack.push(unit);
			} else {
				stack.push(cell);
			}
		}

		ArrayList<String> srcList2 = new ArrayList<String>(stack);
		return srcList2;
	}

	private static String execute(List<String> srcList) {

		Stack<String> stack = new Stack<String>();

		System.out.println(srcList.toString());

		// Mul & Div
		for (int i = 0; i < srcList.size(); i++) {
			String cell = srcList.get(i);
			double item;
			switch (cell) {
			case "*":
				item = Double.valueOf(stack.pop()) * Double.valueOf(srcList.get(1 + i++));
				stack.push(String.valueOf(item));
				break;
			case "/":
				item = Double.valueOf(stack.pop()) / Double.valueOf(srcList.get(1 + i++));
				stack.push(String.valueOf(item));
				break;
			default:
				stack.push(cell);
				break;
			}
		}

		while (stack.size() > 1) {
			double num1 = Double.valueOf(stack.pop());
			String op = stack.pop();
			double num2 = Double.valueOf(stack.pop());
			double item;
			switch (op) {
			case "+":
				item = num2 + num1;
				stack.push(String.valueOf(item));
				break;
			case "-":
				item = num2 - num1;
				stack.push(String.valueOf(item));
				break;
			default:

				break;
			}
		}

		return stack.pop();
	}

}
