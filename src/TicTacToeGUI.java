import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class represents a Tic-Tac-Toe game with a graphical user interface.
 */
public class TicTacToeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int X = 1, O = -1;
    private static final int EMPTY = 0;
    private int board[][] = new int[3][3];
    private int player;
    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;
    private Color[][] buttonColors = new Color[3][3];
    private int playerXScore = 0;
    private int playerOScore = 0;
    private JLabel playerXScoreLabel;
    private JLabel playerOScoreLabel;

    /**
     * Constructs a new instance of the TicTacToeGUI class.
     */
    public TicTacToeGUI() {
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

        Border paddingBorder = BorderFactory.createEmptyBorder(40, 10, 20, 10);

        JLabel headerLabel = new JLabel("TIC-TAC-TOE");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
        headerLabel.setForeground(Color.CYAN);
        headerLabel.setBorder(paddingBorder);
        add(headerLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(33, 33, 33));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i, col = j;
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(200, 200));
                button.setFont(new Font("Tahoma", Font.BOLD, 40));
                button.setForeground(Color.CYAN);
                button.setCursor(handCursor);
                button.setBackground(Color.GRAY); // Set the default background color of the button to gray
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (board[row][col] == EMPTY) {
                            board[row][col] = player;
                            updateBoard();
                            player = -player;
                            int winner = checkWinner();
                            if (winner != EMPTY || isBoardFull()) {
                                endGame(winner);
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

                buttonColors[i][j] = button.getBackground(); // Store the original button color
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

        playerXScoreLabel = new JLabel("Player X: " + playerXScore);
        playerXScoreLabel.setForeground(Color.CYAN);
        playerXScoreLabel.setFont(playerXScoreLabel.getFont().deriveFont(Font.BOLD, 20));
        scorePanel.add(playerXScoreLabel);

        playerOScoreLabel = new JLabel("Player O: " + playerOScore);
        playerOScoreLabel.setForeground(Color.CYAN);
        playerOScoreLabel.setFont(playerOScoreLabel.getFont().deriveFont(Font.BOLD, 20));
        scorePanel.add(Box.createHorizontalStrut(100)); // Add space between labels
        scorePanel.add(playerOScoreLabel);

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
                resetComponents();
                playerXScore = 0;
                playerOScore = 0;
                playerXScoreLabel.setText("Player X: " + playerXScore);
                playerOScoreLabel.setText("Player O: " + playerOScore);
            }
        });
        bottomPanel.add(resetButton);

        player = X;
        clearBoard();
    }

    /**
     * Clears the game board.
     */
    private void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setBackground(Color.GRAY); // Reset button color
            }
        }
        updateBoard();
    }

    /**
     * Updates the game board based on the current state.
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
        statusLabel.setText("Player " + (player == X ? "O" : "X") + "'s turn");
    }

    /**
     * Checks if there is a winner in the current game state.
     *
     * @return The player (X or O) who has won, or 0 if there is no winner yet.
     */
    private int checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] + board[i][1] + board[i][2] == 3 * X ||
                    board[0][i] + board[1][i] + board[2][i] == 3 * X) {
                return X;
            }
            if (board[i][0] + board[i][1] + board[i][2] == 3 * O ||
                    board[0][i] + board[1][i] + board[2][i] == 3 * O) {
                return O;
            }
        }
        if (board[0][0] + board[1][1] + board[2][2] == 3 * X ||
                board[0][2] + board[1][1] + board[2][0] == 3 * X) {
            return X;
        }
        if (board[0][0] + board[1][1] + board[2][2] == 3 * O ||
                board[0][2] + board[1][1] + board[2][0] == 3 * O) {
            return O;
        }
        return EMPTY;
    }

    /**
     * Checks if the game board is full.
     *
     * @return True if the board is full, false otherwise.
     */
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Handles the end of the game.
     *
     * @param winner The player (X or O) who has won, or 0 if it's a tie.
     */
    private void endGame(int winner) {
        if (winner == X) {
            playerXScore++;
            playerXScoreLabel.setText("Player X: " + playerXScore);
            highlightWinningLine(Color.GREEN);
        } else if (winner == O) {
            playerOScore++;
            playerOScoreLabel.setText("Player O: " + playerOScore);
            highlightWinningLine(Color.GREEN);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }

        // Automatically reset the game after 2 seconds
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                resetComponents();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Resets the components of the game board.
     */
    private void resetComponents() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackground(buttonColors[i][j]);
                buttons[i][j].setEnabled(true);
            }
        }
    }

    /**
     * Highlights the winning line on the game board.
     *
     * @param color The color to use for highlighting.
     */
    private void highlightWinningLine(Color color) {
        int winner = checkWinner();
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
     * Checks if the winning line is currently highlighted.
     *
     * @return True if the winning line is highlighted, false otherwise.
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
     * Main method to start the Tic-Tac-Toe game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGUI().setVisible(true);
            }
        });
    }
}
