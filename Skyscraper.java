import javax.swing.*;
import java.awt.*;

public class Skyscraper implements PuzzleGame {

    private Frame f;
    private JLabel label;

    private int[][] grid = new int[5][5];
    private int[] leftTargets = {3,3,2,1,2};  //{2,3,1,3,3};//{2,1,2,4,2};
    private int[] rightTargets = {2,3,2,3,1}; //{4,1,3,2,2};//{2,3,1,2,3};
    private int[] topTargets = {2,2,2,1,3};//{2,1,3,4,2};//{2,1,5,2,2};
    private int[] botTargets = {2,2,3,5,1};//{2,2,1,2,3};//{3,4,1,2,3};
    private SkyscraperButton[][] buttons = new SkyscraperButton[5][5];
    private int[] possValue = {1,2,3,4,5};

    public Skyscraper(Frame f) {
        this.f = f;

    }
    @Override
    public void getGrid() {
        Container ct = f.getContentPane();
        JPanel centerGrid = new JPanel(new GridLayout(7,7,5,5));
        ct.add(centerGrid, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode("#FFC09F"));
        ct.add(topPanel, BorderLayout.NORTH);


        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(Color.decode("#FFC09F"));
        ct.add(leftBorder, BorderLayout.WEST);


        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(Color.decode("#FFC09F"));
        ct.add(rightBorder, BorderLayout.EAST);


        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.decode("#FFC09F"));
        ct.add(botPanel, BorderLayout.SOUTH);

        Font myFont = new Font("Serif",Font.BOLD, 36);
        Font labelFont = new Font("Serif",Font.BOLD, 10);


        int leftTarget = 0;
        int rightTarget = 0;
        int topTarget = 0;
        int botTarget = 0;
        //sets up center grid and puts the number within the grid
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j == 0 || i == 6 && j == 0 || i == 6 && j == 6 || i == 0 && j == 6) {
                    centerGrid.add(Box.createVerticalGlue());
                }
                else if (i == 0) {
                    JLabel num = new JLabel (Integer.toString(topTargets[topTarget]));
                    num.setFont(myFont);
                    num.setHorizontalAlignment(JTextField.CENTER);
                    centerGrid.add(num);
                    topTarget++;
                }
                else if (j == 0) {
                    JLabel num = new JLabel (Integer.toString(leftTargets[leftTarget]));
                    num.setFont(myFont);
                    num.setHorizontalAlignment(JTextField.CENTER);
                    centerGrid.add(num);
                    leftTarget++;
                }
                else if (j == 6) {
                    JLabel num = new JLabel(Integer.toString(rightTargets[rightTarget]));
                    num.setFont(myFont);
                    num.setHorizontalAlignment(JTextField.CENTER);
                    centerGrid.add(num);
                    rightTarget++;
                }
                else if (i == 6) {
                    JLabel num = new JLabel(Integer.toString(botTargets[botTarget]));
                    num.setFont(myFont);
                    num.setHorizontalAlignment(JTextField.CENTER);
                    centerGrid.add(num);
                    botTarget++;
                }

                else {
                    JButton jb = new SkyscraperButton( this, i-1, j-1);
                    jb.setFont(myFont);
                    centerGrid.add(jb);
                    buttons[i-1][j-1] = (SkyscraperButton) jb;

                }
            }
        }



        this.label = new JLabel("");
        label.setFont(labelFont);
        topPanel.add(label);

        JButton checkerButton = new Checker(this);
        checkerButton.setPreferredSize(new Dimension(100,50));
        checkerButton.setFont(labelFont);
        checkerButton.setText("Check");
        topPanel.add(checkerButton);

        JButton solverButton = new Solver(this, grid, possValue);
        solverButton.setPreferredSize(new Dimension(100,50));
        solverButton.setFont(labelFont);
        solverButton.setText("Solve");
        topPanel.add(solverButton);

        JButton resetButton = new Resetter(this);
        resetButton.setPreferredSize(new Dimension(100,50));
        resetButton.setFont(labelFont);
        resetButton.setText("Reset");
        topPanel.add(resetButton);

        JButton r  = new JButton("Go back");
        r.setPreferredSize(new Dimension(100,50));
        r.setFont(labelFont);
        r.addActionListener(new StartButton(f, 0));
        topPanel.add(r);

        ct.revalidate();
        ct.repaint();

    }

    @Override
    public void emptyGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = 0;
                if(buttons[row][col] != null)
                    buttons[row][col].setEmpty();

            }
        }
    }

    @Override
    public void fillGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                buttons[row][col].setCorrect();
            }
        }
    }

    @Override
    public void setSpot(int r, int c) {
        if (grid[r][c] == 0) {
            grid[r][c] = 1;
        }
        else {
            grid[r][c]++;
        }
        if (grid[r][c] > 5) {
            grid[r][c] = 0;
        }
    }

    public int getSpot(int r, int c) {
        return grid[r][c];
    }

    @Override
    public boolean checkCurrent() {
        boolean correct = true;

        if (!checkCurrentLeftTargets())
            correct = false;
     /*   if(!checkCurrentRightTargets()) //
          correct = false;*/ //I did not need to check all conditions...
        if(!checkRowRepeats())
            correct = false;
        if(!checkCurrentTopTargets()) //you need these two
            correct = false;
     /*   if(!checkCurrentBotTargets())
            correct = false;*/
        if(!checkColRepeats()) //something about this
        correct = false;
        return correct;
    }

    @Override
    public boolean checkAll() {
        boolean correct = true;
        if (checkFinalLeftTargets() == false)
            correct = false;
        if (checkFinalRightTargets() == false)
            correct = false;
        if (checkFinalTopTargets() == false)
            correct = false;
        if (checkFinalBotTargets() == false)
            correct = false;
        if(checkRowRepeats() == false)
            correct = false;
        if(checkColRepeats() == false)
            correct = false;
        return correct;
    }

    public boolean checkCurrentLeftTargets() {
        for (int row = 0; row < grid.length; row++) {
            int largest = 0;
            int howMany = 0;
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany > leftTargets[row] ) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFinalLeftTargets() {
        for (int row = 0; row < grid.length; row++) {
            int largest = 0;
            int howMany = 0;
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany != leftTargets[row]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCurrentRightTargets() {
        for (int row = 0; row < grid.length; row++) {
            int largest = 0;
            int howMany = 0;
            for (int col = grid[row].length - 1; col >= 0 ; col--) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany > rightTargets[row]){// && grid[row][grid[row].length-1] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFinalRightTargets() {
        for (int row = 0; row < grid.length; row++) {
            int largest = 0;
            int howMany = 0;
            for (int col = grid[row].length - 1; col >= 0 ; col--) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany != rightTargets[row]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCurrentTopTargets() {
        for (int col = 0; col < grid[0].length; col++) {
            int largest = 0;
            int howMany = 0;
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany > topTargets[col] && grid[0][col] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFinalTopTargets() {
        for (int col = 0; col < grid[0].length; col++) {
            int largest = 0;
            int howMany = 0;
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany != topTargets[col]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCurrentBotTargets() {
        for (int col = 0; col < grid[0].length; col++) {
            int largest = 0;
            int howMany = 0;
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany > botTargets[col]) {

                return false;
            }
        }
    return true;
    }

    public boolean checkFinalBotTargets() {
        for (int col = 0; col < grid[0].length; col++) {
            int largest = 0;
            int howMany = 0;
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][col] > largest) {
                    howMany++;
                    largest = grid[row][col];
                }
            }
            if (howMany != botTargets[col]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRowRepeats() {
        for (int row = 0; row < grid.length; row++) {
            int[] count = new int[grid.length + 1];
            for (int col = 0; col < grid[row].length; col++) {
                 if (grid[row][col] != 0) {
                count[grid[row][col]]++;
                }
            }
            for (int i = 1; i < count.length; i++) {
                if (count[i] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkColRepeats() {
        for (int col = 0; col < grid[0].length; col++) {
            int[] count = new int[grid.length + 1];
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] != 0) {
                    count[grid[row][col]]++;
                }
            }
            for (int i = 1; i < count.length; i++) {
                if (count[i] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void solvedMessage() {
        if (this.checkCurrent()) {
            label.setText("Keep going!");
            if (this.checkAll()) {
                label.setText("Solved!");
                JOptionPane.showMessageDialog(null, "The Puzzle is solved!");
            }

        }
        else {
            label.setText("You did something wrong!");
        }

    }
}