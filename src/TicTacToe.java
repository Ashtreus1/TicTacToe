import java.util.Scanner;

// Console Version

public class TicTacToe {
	
	protected static final int X = 1, O = -1;
	protected static final int EMPTY = 0;
	protected int board[][] = new int[3][3];
	protected int player;
	
	public TicTacToe() {
		clearBoard();
	}
	
	private void clearBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
			}
			player = X;
		}
	}
	
	private void move(int pos) {
		int row = (pos - 1) / 3;
		int col = (pos - 1) % 3;

		if (pos < 1 || pos > 9 || board[row][col] != EMPTY) {
			System.out.println("Invalid move. Please try again.");
			return;
		}

		board[row][col] = player;
		player = -player; // Switch player
	}
	
	private boolean isWinner(int mark) {
		return ((board[0][0] + board[0][1] + board[0][2] == mark * 3)
				|| (board[1][0] + board[1][1] + board[1][2] == mark * 3)
				|| (board[2][0] + board[2][1] + board[2][2] == mark * 3)
				|| (board[0][0] + board[1][0] + board[2][0] == mark * 3)
				|| (board[0][1] + board[1][1] + board[2][1] == mark * 3)
				|| (board[0][2] + board[1][2] + board[2][2] == mark * 3)
				|| (board[0][0] + board[1][1] + board[2][2] == mark * 3)
				|| (board[0][2] + board[1][1] + board[2][0] == mark * 3));
	}
	
	private int winner() {
		if (isWinner(X))
			return (X);
		else if (isWinner(O))
			return (O);
		else
			return (0);
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				switch (board[i][j]) {
				case X:
					s += "X";
					break;
				case O:
					s += "O";
					break;
				case EMPTY:
					s += (i * 3 + j + 1);
					break;
				}
				if (j < 2)
					s += "|";
			}
			if (i < 2)
				s += "\n-----\n";
		}
		return s;
	}

	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		Scanner input = new Scanner(System.in);
		System.out.println("========================");
		System.out.println("     TICTACTOE GAME");
		System.out.println("========================");
		
		int turn = 1;
		while (game.winner() == 0 && turn <= 9) {
			System.out.println(game.toString());
			System.out.print("Player " + (game.player == TicTacToe.X ? "X" : "O") + ", enter your move (1-9): ");
			int move = input.nextInt();
			game.move(move);
			turn++;
		}
		
		System.out.println(game.toString());
		int result = game.winner();
		if (result == TicTacToe.X)
			System.out.println("Player X wins!");
		else if (result == TicTacToe.O)
			System.out.println("Player O wins!");
		else
			System.out.println("It's a draw!");
		
		input.close();
	}

}
