import java.util.Stack;

public class Cal {

	public static void main(String[] args) {

		String[] srcList = { "1", "+", "2", "*", "3", "/", "2" };

		Stack<String> stack = new Stack<String>();

		for (int i = 0; i < srcList.length; i++) {
			String cell = srcList[i];
			int item;
			switch (cell) {
			case "*":
				item = Integer.valueOf(stack.pop()) * Integer.valueOf(srcList[1 + i++]);
				stack.push(String.valueOf(item));
				break;
			case "/":
				item = Integer.valueOf(stack.pop()) / Integer.valueOf(srcList[1 + i++]);
				stack.push(String.valueOf(item));
				break;
			default:
				stack.push(cell);
				break;
			}
		}

		while (stack.size() > 1) {
			int num1 = Integer.valueOf(stack.pop());
			String op = stack.pop();
			int num2 = Integer.valueOf(stack.pop());
			int item;
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

		System.out.println(stack.toString());

	}

}
