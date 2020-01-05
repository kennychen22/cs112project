import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Hitori implements PuzzleGame {

    private Frame f;
    private JLabel label;


    private int[][] grid = new int[5][5];
    private int[][] spots =
           {{3,4,5,2,2},
            {2,3,3,5,1},
            {5,2,1,3,3},
            {4,2,3,1,2},
            {1,4,4,3,4}};
            /*{{4, 5, 1, 2, 4}, //another one
            {5, 4, 3, 2, 1},
            {3, 3, 2, 1, 4},
            {1, 4, 5, 3, 2},
            {5, 1, 2, 4, 4}};*/
    private HitoriButton[][] buttons = new HitoriButton[5][5];
    private int[] possValue = {0, 1};



    public Hitori(Frame f) {
        this.f = f;
    }

    @Override
    public void getGrid() {
        Container ct = f.getContentPane();
        JPanel centerGrid = new JPanel(new GridLayout(5,5,5,5));
        ct.add(centerGrid, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode("#CCC9DC"));
        ct.add(topPanel, BorderLayout.NORTH);


        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(Color.decode("#CCC9DC"));
        ct.add(leftBorder, BorderLayout.WEST);


        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(Color.decode("#CCC9DC"));
        ct.add(rightBorder, BorderLayout.EAST);


        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.decode("#CCC9DC"));
        ct.add(botPanel, BorderLayout.SOUTH);

        Font myFont = new Font("Serif",Font.BOLD, 36);
        Font labelFont = new Font("Serif",Font.BOLD, 10);

        //sets up center grid and puts the number within the grid
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                    JButton jb = new HitoriButton(this, i, j);
                    jb.setText(Integer.toString(spots[i][j]));
                   jb.setFont(myFont);
                jb.setBackground(Color.WHITE);
                jb.setOpaque(true);
                jb.setBorderPainted(false);
                    buttons[i][j] = (HitoriButton) jb;

                centerGrid.add(jb);

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
            for (int col = 0; col < grid[0].length; col++) {
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
                if (isSelect(row, col)) {
                    buttons[row][col].setCorrect();
                }
                if(!isSelect(row,col))
                    buttons[row][col].setEmpty();
            }
        }
    }

    @Override
    public void setSpot(int r, int c) {
        if (grid[r][c] == 0)
            grid[r][c] = 1;
        else
            grid[r][c] = 0;

    }

    public int getSpot(int row, int col) {
        return grid[row][col];
    }

    public boolean isSelect(int row, int col) {
        if (grid[row][col] == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkCurrent() {
        return checkTouch() && checkTravelHead();
    }

    @Override
    public boolean checkAll() {
        if (checkTouch() == false) //this works
            return false;
        if(checkTravelHead() == false) //this does not work
            return false;
        if(checkRepeats() == false) //this works
            return false;
        return true;
    }

    public boolean checkRepeats() {
        for(int row = 0; row < grid.length; row++) {
            int[] repeat = new int[6];
            for(int col = 0; col < grid.length; col++) {
                if(!isSelect(row,col))
                repeat[spots[row][col]]++;
            }
            for(int i = 1; i < repeat.length; i++) {
                if (repeat[i] > 1) {
                    return false;
                }
            }
        }
        for(int col = 0; col < grid[0].length; col++) {
            int[] repeat = new int[6];
            for(int row = 0; row < grid.length; row++) {
                if(!isSelect(row,col))
                    repeat[spots[row][col]]++;
            }
            for(int i = 1; i < repeat.length; i++) {
                if (repeat[i] > 1) {
                    return false;
                }
            }
        }
return true;
    }
    //checks if travelled using stack
    public boolean checkTravelHead() {
        boolean[][] traveled = new boolean[grid.length][grid[0].length];
        Stack<Maze> s = new Stack<Maze>();
        Maze m = new Maze(0, 0);
        s.push(m);


        while (!s.isEmpty()) {
            m = s.peek();

            int d = m.dir;
            int i = m.x;
            int j = m.y;

            m.dir = m.dir + 1;

            s.pop();
            s.push(m);

            if (d == 0) {
                //checking up direction
                if (i - 1 >= 0 && grid[i - 1][j] == 0 &&
                        !traveled[i - 1][j]) {
                    Maze temp1 = new Maze(i - 1, j);
                    traveled[i - 1][j] = true;

                    s.push(temp1);
                }
            } else if (d == 1) {
                // Checking the left direction
                if (j - 1 >= 0 && grid[i][j - 1] == 0 &&
                        !traveled[i][j - 1]) {
                    Maze temp1 = new Maze(i, j - 1);

                    traveled[i][j - 1] = true;

                    s.push(temp1);
                }
            } else if (d == 2) {
                // Checking the bot direction
                if (i + 1 < grid.length && grid[i + 1][j] == 0 &&
                        !traveled[i + 1][j]) {
                    Maze temp1 = new Maze(i + 1, j);
                    traveled[i + 1][j] = true;

                    s.push(temp1);
                }
            } else if (d == 3) {
                // Checking the left direction
                if (j + 1 < grid.length && grid[i][j + 1] == 0 &&
                        !traveled[i][j + 1]) {
                    Maze temp1 = new Maze(i, j + 1);
                    traveled[i][j + 1] = true;

                    s.push(temp1);
                }
            }
            else {
                traveled[m.x][m.y] = true;
                s.pop();
            }
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                //if the place was not traveled but it could have been
                if (!traveled[row][col]  && grid[row][col] != 1) {

                    return false;
                }
            }
        }

        return true;
    }
    //checks if two squares are touching
    public boolean checkTouch() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int count = 0;
                //check the top left corner
                if (row == 0 && col == 0) {
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                } else if (row == grid.length - 1 && col == 0) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                } else if (row == grid.length - 1 && col == grid[row].length - 1) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                } else if (row == 0 && col == grid[row].length - 1) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                } else if (row == 0) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                } else if (row == grid.length - 1) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                } else if (col == 0) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                } else if (col == grid[row].length - 1) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                } else {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                }
                if (count >= 1) {
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
