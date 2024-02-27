import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;


public class TicTacToeAI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int X = 1, O = -1;
    private static final int EMPTY = 0;
    private int board[][] = new int[3][3];
    private int currentPlayer;
    private int playerScore = 0;
    private int aiScore = 0;
    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;
    JLabel playerScoreLabel = new JLabel("Player: " + playerScore);
    JLabel aiScoreLabel = new JLabel("AI: " + aiScore);
    
    
    /**
     * Constructs a new Tic-Tac-Toe game window with the specified settings and components.
     * The game window includes a header label, a 3x3 grid of buttons for the game board,
     * a status label indicating the current player's turn, and score labels for player
     * and AI scores. The window also includes buttons for resetting the game and returning
     * to the game lobby. The game board uses X for the player and O for the AI. The player
     * can click on empty cells to make a move, and the AI makes moves automatically.
     */

    public TicTacToeAI() {
        setTitle("Tic Tac Toe");
        setSize(700, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(33, 33, 33));
        
        try {
            Image iconImage = ImageIO.read(getClass().getResourceAsStream("tictactoe.png")); 
            setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        JLabel headerLabel = new JLabel("TIC-TAC-TOE");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
        headerLabel.setForeground(Color.CYAN);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        add(headerLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(33, 33, 33));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i, col = j;
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(100, 100));
                button.setFont(new Font("Tahoma", Font.BOLD, 40));
                button.setForeground(Color.CYAN);
                button.setCursor(handCursor);
                button.setBackground(Color.GRAY);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (board[row][col] == EMPTY) {
                            board[row][col] = X; // Player's move is always X
                            updateBoard();
                            if (checkWinner() != EMPTY || isBoardFull()) {
                                endGame(checkWinner());
                            } else {
                                currentPlayer = O; // Switch to AI's turn (O)
                                aiMove(); // AI's turn
                            }
                        }
                    }
                });
                
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!isWinningLineHighlighted()) { // Check if the winning line is not highlighted
                            button.setBackground(Color.LIGHT_GRAY); // Change background color on hover
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!isWinningLineHighlighted()) { // Check if the winning line is not highlighted
                            button.setBackground(Color.GRAY); // Reset background color on exit
                        }
                    }
                });

                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(33, 33, 33));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        statusLabel = new JLabel("Player X's turn");
        statusLabel.setForeground(Color.CYAN);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 25));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        scorePanel.setBackground(new Color(33, 33, 33));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        playerScoreLabel.setForeground(Color.CYAN);
        playerScoreLabel.setFont(playerScoreLabel.getFont().deriveFont(Font.BOLD, 20));
        scorePanel.add(playerScoreLabel);


        aiScoreLabel.setForeground(Color.CYAN);
        aiScoreLabel.setFont(aiScoreLabel.getFont().deriveFont(Font.BOLD, 20));
        scorePanel.add(Box.createHorizontalStrut(100));
        scorePanel.add(aiScoreLabel);

        statusPanel.add(scorePanel, BorderLayout.NORTH);
        
       
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(33, 33, 33));
        add(bottomPanel, BorderLayout.SOUTH);
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        backButton.setForeground(Color.CYAN);
        backButton.setBackground(Color.GRAY);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	backButton.setBackground(Color.DARK_GRAY); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	backButton.setBackground(Color.GRAY); 
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new GameLobby().setVisible(true);
                        dispose(); // Close the current window
                    }
                });
            }
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(new Color(33, 33, 33));
        backButtonPanel.add(backButton);

        bottomPanel.add(backButtonPanel);

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(200, 50));
        resetButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        resetButton.setForeground(Color.CYAN);
        resetButton.setBackground(Color.GRAY);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        resetButton.setCursor(handCursor);
        
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(Color.DARK_GRAY); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.GRAY); 
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                playerScore = 0;
                aiScore = 0;
                playerScoreLabel.setText("Player: " + playerScore);
                aiScoreLabel.setText("AI: " + aiScore);
            }
        });
        bottomPanel.add(resetButton);

        currentPlayer = X;
        clearBoard();
    }
    
    /**
     * Clears the game board by resetting all cells to empty and removing any
     * displayed X's or O's from the buttons on the board. It then updates the
     * visual representation of the board.
     */
    
    private void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setText("");
            }
        }
        updateBoard();
    }
    
    /**
     * Updates the visual representation of the game board by setting the text of
     * each button based on the current state of the board. X's and O's are displayed
     * on the buttons according to their positions on the board, and empty cells are
     * represented by an empty string.
     */
    
    private void updateBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (board[i][j]) {
                    case X:
                        buttons[i][j].setText("X");
                        break;
                    case O:
                        buttons[i][j].setText("O");
                        break;
                    case EMPTY:
                        buttons[i][j].setText("");
                        break;
                }
            }
        }
    }
    
    /**
     * Checks the current state of the board to determine if there is a winner.
     * Returns the player (X or O) who has won the game, or 0 for a draw, or
     * EMPTY if the game is still ongoing.
     *
     * @return The player who has won the game, or 0 for a draw, or EMPTY if the game is ongoing.
     */
    
    private int checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] + board[i][1] + board[i][2] == 3 * X) return X;
            if (board[i][0] + board[i][1] + board[i][2] == 3 * O) return O;
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] + board[1][i] + board[2][i] == 3 * X) return X;
            if (board[0][i] + board[1][i] + board[2][i] == 3 * O) return O;
        }

        // Check diagonals
        if (board[0][0] + board[1][1] + board[2][2] == 3 * X || board[0][2] + board[1][1] + board[2][0] == 3 * X)
            return X;
        if (board[0][0] + board[1][1] + board[2][2] == 3 * O || board[0][2] + board[1][1] + board[2][0] == 3 * O)
            return O;

        // Check for a draw
        if (isBoardFull()) return 0;

        return EMPTY;
    }
    
    /**
     * Checks if the game board is full, i.e., if all cells are occupied by X or O.
     *
     * @return True if the board is full, false otherwise.
     */
    
    private boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Ends the current game and updates the UI based on the winner or a draw.
     *
     * @param winner The winner of the game (X, O, or EMPTY for a draw).
     */
    
    private void endGame(int winner) {
    	 if (winner == X) {
    	        playerScore++;
    	        // Update player score label
    	        playerScoreLabel.setText("Player: " + playerScore);
    	        statusLabel.setText("Player X wins!");
    	        highlightWinningLine(Color.GREEN, winner);
    	    } else if (winner == O) {
    	        aiScore++;
    	        // Update AI score label
    	        aiScoreLabel.setText("AI: " + aiScore);
    	        highlightWinningLine(Color.GREEN, winner);
    	    } else {
    	        statusLabel.setText("It's a draw!");
    	    }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }


        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                resetComponents();
                statusLabel.setText("Player X's turn"); // Reset the status label
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Resets the colors of all buttons to default (GRAY) and enables all buttons.
    */

    private void resetComponents() {
        // Reset button colors and enable buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackground(Color.GRAY);
                buttons[i][j].setEnabled(true);
            }
        }
    }
    
    /**
     * Highlights the winning line on the board with the specified color for the given winner (X or O).
     *
     * @param color  The color to use for highlighting the winning line.
     * @param winner The winner of the game (X or O).
     */
    
    private void highlightWinningLine(Color color, int winner) {
        if (winner == X || winner == O) {
            if (winner == X) {
                if (board[0][0] + board[1][1] + board[2][2] == 3 * X) {
                    buttons[0][0].setBackground(color);
                    buttons[1][1].setBackground(color);
                    buttons[2][2].setBackground(color);
                } else if (board[0][2] + board[1][1] + board[2][0] == 3 * X) {
                    buttons[0][2].setBackground(color);
                    buttons[1][1].setBackground(color);
                    buttons[2][0].setBackground(color);
                } else {
                    for (int i = 0; i < 3; i++) {
                        if (board[i][0] + board[i][1] + board[i][2] == 3 * X) {
                            buttons[i][0].setBackground(color);
                            buttons[i][1].setBackground(color);
                            buttons[i][2].setBackground(color);
                            break;
                        } else if (board[0][i] + board[1][i] + board[2][i] == 3 * X) {
                            buttons[0][i].setBackground(color);
                            buttons[1][i].setBackground(color);
                            buttons[2][i].setBackground(color);
                            break;
                        }
                    }
                }
            } else if (winner == O) {
                if (board[0][0] + board[1][1] + board[2][2] == 3 * O) {
                    buttons[0][0].setBackground(color);
                    buttons[1][1].setBackground(color);
                    buttons[2][2].setBackground(color);
                } else if (board[0][2] + board[1][1] + board[2][0] == 3 * O) {
                    buttons[0][2].setBackground(color);
                    buttons[1][1].setBackground(color);
                    buttons[2][0].setBackground(color);
                } else {
                    for (int i = 0; i < 3; i++) {
                        if (board[i][0] + board[i][1] + board[i][2] == 3 * O) {
                            buttons[i][0].setBackground(color);
                            buttons[i][1].setBackground(color);
                            buttons[i][2].setBackground(color);
                            break;
                        } else if (board[0][i] + board[1][i] + board[2][i] == 3 * O) {
                            buttons[0][i].setBackground(color);
                            buttons[1][i].setBackground(color);
                            buttons[2][i].setBackground(color);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Checks if any winning line is currently highlighted on the board.
     *
     * @return {@code true} if a winning line is highlighted, {@code false} otherwise.
     */
    
    private boolean isWinningLineHighlighted() {
        // Check if any button has the highlight color
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getBackground().equals(Color.GREEN)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Executes the AI's move in the game.
     */
    
    private void aiMove() {
        int[] move;

        // Check if it's AI's turn
        if (currentPlayer == O) {
            // AI's move
            if ((move = checkWinningMove()) != null) {
                board[move[0]][move[1]] = O;
            } else if ((move = checkBlockingMove()) != null) {
                board[move[0]][move[1]] = O;
            } else if ((move = checkForkingMove()) != null) {
                board[move[0]][move[1]] = O;
            } else if (board[1][1] == EMPTY) {
                board[1][1] = O;
            } else if ((move = checkOppositeCorner()) != null) {
                board[move[0]][move[1]] = O;
            } else if ((move = checkEmptySide()) != null) {
                board[move[0]][move[1]] = O;
            } else {
                move = getRandomMove();
                board[move[0]][move[1]] = O;
            }

            updateBoard();
            int winner = checkWinner();
            if (winner != EMPTY || isBoardFull()) {
                endGame(winner);
            } else {
                currentPlayer = X; // Switch to player X's turn
                statusLabel.setText("Player X's turn");
            }

            if (winner == O) {
                statusLabel.setText("AI Wins!");
            } else {
                statusLabel.setText("AI moved");
            }

            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPlayer == X) {
                        statusLabel.setText("Player X's turn");
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Checks if there is a winning move available for the AI and returns the move coordinates if found.
     * @return An array containing the row and column indices of the winning move, or null if no winning move is found.
     */

    private int[] checkWinningMove() {
        // Check for a winning move for AI
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = O;
                    if (checkWinner() == O) {
                        return new int[]{i, j};
                    }
                    board[i][j] = EMPTY;
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if there is a move available to block the player's winning move and returns the move coordinates if found.
     * @return An array containing the row and column indices of the blocking move, or null if no blocking move is found.
     */
    
    private int[] checkBlockingMove() {
        // Check for blocking the player's winning moves
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = X;
                    if (checkWinner() == X) {
                        board[i][j] = O;
                        return new int[]{i, j};
                    }
                    board[i][j] = EMPTY;
                }
            }
        }
        return null;
    }
    
    /**
     * Checks for a move that creates a forking opportunity, a situation where the AI can win regardless of the opponent's next move.
     * @return An array containing the row and column indices of the forking move, or null if no forking move is found.
     */
    private int[] checkForkingMove() {
        // Check for creating a forking opportunity
        if (board[1][1] == EMPTY) {
            return new int[]{1, 1};
        }

        // Check for other strategic moves
        // Prioritize occupying corners if the center is occupied by the opponent
        if (board[1][1] == X) {
            if (board[0][0] == EMPTY) {
                return new int[]{0, 0};
            }
            if (board[0][2] == EMPTY) {
                return new int[]{0, 2};
            }
            if (board[2][0] == EMPTY) {
                return new int[]{2, 0};
            }
            if (board[2][2] == EMPTY) {
                return new int[]{2, 2};
            }
        }

        // If opponent occupies a corner, prioritize the center or an empty side square
        if ((board[0][0] == X || board[0][2] == X || board[2][0] == X || board[2][2] == X) && board[1][1] == EMPTY) {
            return new int[]{1, 1};
        } else if ((board[0][0] == X && board[2][2] == X) || (board[0][2] == X && board[2][0] == X)) {
            // If opponent occupies opposite corners, prioritize an edge square
            if (board[0][1] == EMPTY) {
                return new int[]{0, 1};
            }
            if (board[1][0] == EMPTY) {
                return new int[]{1, 0};
            }
            if (board[1][2] == EMPTY) {
                return new int[]{1, 2};
            }
            if (board[2][1] == EMPTY) {
                return new int[]{2, 1};
            }
        }

        // If opponent occupies an edge square, prioritize the center or a corner
        if ((board[0][1] == X || board[1][0] == X || board[1][2] == X || board[2][1] == X) && board[1][1] == EMPTY) {
            return new int[]{1, 1};
        }

        // If none of the above conditions are met, return null to indicate no forking move
        return null;
    }
    
    /**
     * Checks for an opportunity to occupy the opposite corner of a corner occupied by the opponent.
     * @return An array containing the row and column indices of the opposite corner move, or null if no such move is found.
     */

    private int[] checkOppositeCorner() {
        // Check for occupying the opposite corner
        if (board[0][0] == X && board[2][2] == EMPTY) {
            return new int[]{2, 2};
        }
        if (board[0][2] == X && board[2][0] == EMPTY) {
            return new int[]{2, 0};
        }
        if (board[2][0] == X && board[0][2] == EMPTY) {
            return new int[]{0, 2};
        }
        if (board[2][2] == X && board[0][0] == EMPTY) {
            return new int[]{0, 0};
        }
        return null;
    }
    
    /**
     * Checks for an opportunity to occupy an empty side square.
     * @return An array containing the row and column indices of the empty side square move, or null if no such move is found.
     */

    private int[] checkEmptySide() {
        // Check for occupying an empty side square
        if (board[1][0] == EMPTY) {
            return new int[]{1, 0};
        }
        if (board[0][1] == EMPTY) {
            return new int[]{0, 1};
        }
        if (board[1][2] == EMPTY) {
            return new int[]{1, 2};
        }
        if (board[2][1] == EMPTY) {
            return new int[]{2, 1};
        }
        return null;
    }
    
    /**
     * Returns a random move from the list of empty cells, prioritizing winning, blocking, forking, and corner moves.
     * @return An array containing the row and column indices of the random move.
     */

    private int[] getRandomMove() {
        // Get a random move
        ArrayList<int[]> emptyCells = new ArrayList<>();
        
        // Add all empty cells to the list
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        
        // Prioritize winning, blocking, forking, and corner moves
        if (checkWinningMove() == null) {
            if (checkBlockingMove() == null) {
                if (checkForkingMove() == null) {
                    if (checkOppositeCorner() == null) {
                        if (checkEmptySide() == null) {
                            // Select a random move from the remaining empty cells
                            return emptyCells.get((int) (Math.random() * emptyCells.size()));
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * The entry point of the application. Creates and displays the TicTacToeAI frame.
     * @param args The command-line arguments.
     */	
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeAI().setVisible(true));
    }
}
