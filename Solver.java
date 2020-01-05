import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Solver extends JButton implements ActionListener {

    private PuzzleGame g;
    private int[][] grid;
    private int[] possValue;


    public Solver(PuzzleGame g, int[][] grid, int[] possValue) {
        this.g = g;
        this.grid = grid;
        this.possValue = possValue;
        addActionListener(this);
    }


    private boolean label(int[][] grid, PuzzleGame g, int[] possValue, int row, int col) {


        if (row == grid.length) {
            return g.checkAll();
        }

        for(int v : possValue) {
            grid[row][col] = v;
            if (g.checkCurrent()) {
                int newcolumn = col + 1;
                int newrow = row;
                if (newcolumn == grid[row].length) {
                    newcolumn = 0;
                    newrow = row + 1;
                }
                if (label(grid, g, possValue, newrow, newcolumn)) {
                    return true;
                }
            }
        }
        grid[row][col] = 0;
        return false;
    }

    //method that gets called when "solve" button is clicked
    //uses fillGrid() to change theGUI
    public void actionPerformed(ActionEvent e) {
        g.emptyGrid();
        int row = 0;
        int col = 0;
        boolean result = label(grid, g, possValue, row, col);
        if (result) {
            g.fillGrid();
            g.solvedMessage();
        }
    }
}