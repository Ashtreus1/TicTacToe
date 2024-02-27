import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameLobby extends JFrame {

    private static final long serialVersionUID = 1L;

    public GameLobby() {
        setTitle("TicTacToe");
        setSize(500, 500);
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

        JLabel headerLabel = new JLabel("TIC-TAC-TOE");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
        headerLabel.setForeground(Color.CYAN);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 
        add(headerLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
        optionsPanel.setBackground(new Color(33, 33, 33));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); 

        JButton playerVsPlayerButton = new JButton("Player vs Player");
        playerVsPlayerButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        playerVsPlayerButton.setForeground(Color.CYAN);
        playerVsPlayerButton.setBackground(Color.GRAY);
        playerVsPlayerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playerVsPlayerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playerVsPlayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playerVsPlayerButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playerVsPlayerButton.setBackground(Color.GRAY);
            }
        });
        playerVsPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeGUI ticTacToeGUI = new TicTacToeGUI();
                ticTacToeGUI.setVisible(true);
                dispose();
            }
        });
        optionsPanel.add(playerVsPlayerButton);

        JButton playerVsAIButton = new JButton("Player vs AI");
        playerVsAIButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        playerVsAIButton.setForeground(Color.CYAN);
        playerVsAIButton.setBackground(Color.GRAY);
        playerVsAIButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playerVsAIButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playerVsAIButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playerVsAIButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playerVsAIButton.setBackground(Color.GRAY);
            }
        });
        playerVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeAI ticTacToeAI = new TicTacToeAI();
                ticTacToeAI.setVisible(true);
                dispose();
            }
        });
        optionsPanel.add(playerVsAIButton);

        add(optionsPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameLobby().setVisible(true);
            }
        });
    }
}
