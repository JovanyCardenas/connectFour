import javax.swing.*;
import java.awt.*;

public class main extends JFrame {

    private int player = 0;
    private JButton[] buttons = new JButton[49];
    private int[] columnHeights = new int[7];
    private int[][] board = new int[7][7]; // Store moves (0 = empty, 1 = player 1, 2 = player 2

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to Connect Four\nPlayer 1 is O, Player 2 is X");
        new main();
    }
    public main(){
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(500, 500);
        super.setLocationRelativeTo(null);
        super.setTitle("Connect Four");
        build();
        super.setVisible(true);
    }
    private void build(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7,7));

        for (int i = 0; i < 49; i++){
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            buttons[i].setBackground(Color.LIGHT_GRAY);
            panel.add(buttons[i]);
        }

        for (int col = 0; col < 7; col++){
            final int column = col;
            buttons[col].setText("↓");
            buttons[col].addActionListener(e -> makeMove(column));
        }
        //Top Panel
        JPanel topPanel = new JPanel();
        JLabel topLabel = new JLabel("Player 1: O\t\t");
        JLabel topLabel2 = new JLabel("Player 2: X");
        topPanel.add(topLabel);
        topPanel.add(topLabel2);

        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton exit = new JButton("Exit");
        JButton reset = new JButton("Reset");
        bottomPanel.add(exit);
        bottomPanel.add(reset);
        exit.addActionListener(e -> System.exit(0));
        reset.addActionListener(e -> resetGame());

        super.setLayout(new BorderLayout());
        super.add(topPanel, BorderLayout.NORTH);
        super.add(panel, BorderLayout.CENTER);
        super.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void makeMove(int column){
        if (columnHeights[column] == 6){
            buttons[column].setEnabled(false);
            return;
        }

        int row = 6 - columnHeights[column]; // Find the lowest available row in the column

        if (row < 0){
            return; // Column is full
        }

        int index = (row * 7) + column; // Convert (row, col) into 1D array
        buttons[index].setText(player == 0 ? "O" : "X");
        buttons[index].setBackground(player == 0 ? Color.RED : Color.YELLOW);
        board[row][column] = player + 1;
        columnHeights[column]++; // Increase piece count in this column

        if (checkWinCondition(row, column)){
            JOptionPane.showMessageDialog(this, "Player " + (player + 1) + " won!");
            resetGame();
        }else{
            player = 1 - player; // Switch players
        }
    }
    private boolean checkWinCondition(int row, int col){
        int currentPlayer = board[row][col];
        boolean bool = checkDirection(row,col,1,0,currentPlayer) || // Horizontal
                checkDirection(row,col,0,1,currentPlayer) || // Vertical
                checkDirection(row,col,1,1,currentPlayer) || // Diagonal ↘
                checkDirection(row,col,1,-1,currentPlayer); // Diagonal ↙
        return bool;
    }
    private boolean checkDirection (int row, int col, int rowDir, int colDir, int player){
        int count = 1;

        for (int i = 1; i < 4; i++){ // Check forward direction
            int r = row + (rowDir * i);
            int c = col + (colDir * i);
            if (r < 0 || r >= 7 || c < 0 || c >= 7 || board[r][c] != player){
                break;
            }else{
                count++;
            }
        }

        for (int i = 1; i < 4; i++){ // Check backward direction
            int r = row - (rowDir * i);
            int c = col - (colDir * i);
            if (r < 0 || r >= 7 || c < 0 || c >= 7 || board[r][c] != player){
                break;
            }else{
                count++;
            }
        }
        return count >= 4; // if we found 4 in a row, return true
    }
    private void resetGame(){
        for (int i = 0; i < 49; i++){
            buttons[i].setText("");
            buttons[i].setBackground(Color.WHITE);
        }
        board = new int[7][7];
        columnHeights = new int[7];
        player = 0;
        super.dispose();
        new main();
    }
}
