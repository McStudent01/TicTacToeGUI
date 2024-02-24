import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame
{
    private static final int ROW = 3;
    private static final int COL = 3;
    private TicTacToeButton[][] buttons;
    private char currentPlayer;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(ROW, COL));

        buttonPanel.setPreferredSize(new Dimension(600, 600));
        add(buttonPanel, BorderLayout.CENTER);

        buttons = new TicTacToeButton[ROW][COL];
        currentPlayer = 'X';

        ActionListener buttonListener = e ->
        {
            TicTacToeButton clickedButton = (TicTacToeButton) e.getSource();
            if (clickedButton.getPlayer() == ' ' && !isGameFinished()) {
                clickedButton.setPlayer(currentPlayer);
                if (checkForWin(currentPlayer))
                {
                    JOptionPane.showMessageDialog(null, currentPlayer + " is the winning player!");
                    resetGame();
                } else if (isGameTie())
                {
                    JOptionPane.showMessageDialog(null, "The game is a tie!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        };

        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                TicTacToeButton button = new TicTacToeButton(i, j);
                button.addActionListener(buttonListener);
                buttons[i][j] = button;
                buttonPanel.add(button);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.SOUTH);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean checkForWin(char player)
    {
        return isColWin(player) || isRowWin(player) || isDiagonalWin(player);
    }

    private boolean isColWin(char player)
    {
        for (int i = 0; i < COL; i++) {
            if (buttons[0][i].getPlayer() == player && buttons[1][i].getPlayer() == player && buttons[2][i].getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(char player)
    {
        for (int i = 0; i < ROW; i++) {
            if (buttons[i][0].getPlayer() == player && buttons[i][1].getPlayer() == player && buttons[i][2].getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(char player)
    {
        return (buttons[0][0].getPlayer() == player && buttons[1][1].getPlayer() == player && buttons[2][2].getPlayer() == player) ||
                (buttons[0][2].getPlayer() == player && buttons[1][1].getPlayer() == player && buttons[2][0].getPlayer() == player);
    }

    private boolean isGameTie()
    {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (buttons[i][j].getPlayer() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameFinished()
    {
        return checkForWin('X') || checkForWin('O') || isGameTie();
    }

    private void resetGame()
    {
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Do you want to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION)
        {
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++)
                {
                    buttons[i][j].setPlayer(' ');
                }
            }
            currentPlayer = 'X';
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new TicTacToeFrame());
    }
}
