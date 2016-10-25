
public class run {

	public static void main(String[] args) {
		SurroundedRegions surroundedRegions = new SurroundedRegions();
		String[] strArr = {"OXXOX","XOOXO","XOXOX","OXOOO","XXOXO"};
		char[][] board = StringToChar(strArr);
		System.out.println("before");
		printChar2D(board);
		surroundedRegions.solve(board);
		System.out.println("after");
		printChar2D(board);
	}

	public static char[][] StringToChar(String[] strArr) {
		char[][] char2D = new char[strArr.length][];
		for (int i = 0; i < strArr.length; i++) {
			char2D[i] = strArr[i].toCharArray();
		}
		return char2D;
	}
	
	public static void printChar2D(char[][] char2D) {
		for (char[] char1D : char2D) {
			for (char c : char1D)
				System.out.print(c + " ");
			System.out.println();
		}
	}
}
