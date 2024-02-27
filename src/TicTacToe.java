import java.util.Scanner;

/**
 * The TicTacToe class represents a console version of the Tic-Tac-Toe game.
 * It allows two players to play the game by entering their moves through the console.
 */
public class TicTacToe {
	
	/** Constants representing the marks on the board */
	protected static final int X = 1, O = -1;
	/** Constant representing an empty cell on the board */
	protected static final int EMPTY = 0;
	/** The game board */
	protected int board[][] = new int[3][3];
	/** The current player (X or O) */
	protected int player;
	
	/**
	 * Constructs a new TicTacToe game.
	 * Initializes the board and sets the starting player to X.
	 */
	public TicTacToe() {
		clearBoard();
	}
	
	/**
	 * Clears the game board and sets the starting player to X.
	 */
	private void clearBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
			}
			player = X;
		}
	}
	
	/**
	 * Makes a move on the board at the specified position.
	 * @param pos The position (1-9) on the board to make the move
	 */
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
	
	/**
	 * Checks if the specified mark has won the game.
	 * @param mark The mark to check (X or O)
	 * @return True if the mark has won, false otherwise
	 */
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
	
	/**
	 * Determines the winner of the game.
	 * @return The winning player's mark (X or O), or 0 if it's a draw
	 */
	private int winner() {
		if (isWinner(X))
			return (X);
		else if (isWinner(O))
			return (O);
		else
			return (0);
	}
	
	/**
	 * Returns a string representation of the game board.
	 * @return The string representation of the game board
	 */
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

	/**
	 * The main method to start the TicTacToe game.
	 * Allows two players to play the game by entering their moves through the console.
	 * Prints the winner or draw result after the game ends.
	 * @param args Command line arguments (not used).
	 */
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
