import javax.swing.*;
import java.awt.*;
import java.util.Stack;
//check constraints //which ones am i currently checking ? //maybe i should check after all
public class Kurodoko implements PuzzleGame {
    private Frame f;
    private JLabel label;

    private int[][] grid = new int[7][7];
    private int[][] spots =
           /* {{11,0,0,0,0,8},
                    {0,3,0,0,0,0},
                    {0,0,0,0,5,0},
                    {0,0,0,0,0,0,0},
                    {0,0,7,0,0,0},
                    {8,0,0,0,0,3}};*/
           {{0,3,0,0,0,0,0},
            {0,0,0,0,0,2,0},
            {0,0,5,0,0,0,2},
            {0,0,0,13,0,0,0},
            {8,0,0,0,5,0,0},
            {0,7,0,0,0,0,0},
            {0,0,0,0,0,9,0}};


    private KurodokoButton[][] buttons = new KurodokoButton[7][7];
    private int[] possValue = {0,1};

    public Kurodoko(Frame f) {
        this.f = f;

    }

    @Override
    public void getGrid() {
        Container ct = f.getContentPane();

        JPanel centerGrid = new JPanel(new GridLayout(7,7,5,5));

        ct.add(centerGrid, BorderLayout.CENTER); //shows the diamonds

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode("#70C1B3"));
        ct.add(topPanel, BorderLayout.NORTH);


        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(Color.decode("#70C1B3"));
        ct.add(leftBorder, BorderLayout.WEST);


        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(Color.decode("#70C1B3"));
        ct.add(rightBorder, BorderLayout.EAST);


        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.decode("#70C1B3"));
        ct.add(botPanel, BorderLayout.SOUTH);

        Font myFont = new Font("Serif",Font.BOLD, 36);
        Font labelFont = new Font("Serif",Font.BOLD, 10);


        //sets up center grid and puts the number within the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                JButton jb = new KurodokoButton(this, i, j);
                //if there is a number then print it
                if(spots[i][j] != 0) {
                    grid[i][j] = 10; //arbitrary number
                    jb.setText(Integer.toString(spots[i][j]));
                    jb.setFont(myFont);
                    jb.setEnabled(false);
                }
                else {

                    jb.setBackground(Color.WHITE);
                    jb.setOpaque(true);
                    jb.setBorderPainted(false);
                }
                buttons[i][j] = (KurodokoButton) jb;


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
            for (int col = 0; col < grid[row].length; col++) {
                if(spots[row][col] != 0) {
                    grid[row][col] = 10; //some arbitrary number
                }
                grid[row][col] = 0;
                if(buttons[row][col] != null && spots[row][col] == 0)
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
        if(spots[r][c] != 0)
            label.setText("Can't do that!");
       else
            if(grid[r][c] == 0)
                grid[r][c] = 1;
            else
            grid[r][c] = 0;

    }

    public int getSpot(int r, int c) {
        return grid[r][c];
    }

    public boolean isSelect(int r, int c) {
        if(grid[r][c] == 1) //if a square is black
            return true;
        else
            return false;
    }

    @Override
    public boolean checkCurrent() {

        return checkSpace() && checkTouch() && checkTravelHead() && checkCurrentCount();
    }

    @Override
    public boolean checkAll() {
        if (checkTouch() == false) //this works
           return false;
         if (checkFinalCount() == false)
             return false;
        if(checkTravelHead() == false) //this does not work
            return false;

        return true;

    }

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
                }
                //checks the top right corner
                else if (row == grid.length - 1 && col == 0) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the bottom right corner
                else if (row == grid.length - 1 && col == grid[row].length - 1) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the bottom left corner
                else if (row == 0 && col == grid[row].length - 1) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the top row edge
                else if (row == 0) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the bot row edge
                else if (row == grid.length - 1) {
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the left col edge
                else if (col == 0) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col + 1) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the right col edge
                else if (col == grid[row].length - 1) {
                    if (isSelect(row - 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row + 1, col) && isSelect(row, col)) {
                        count++;
                    }
                    if (isSelect(row, col - 1) && isSelect(row, col)) {
                        count++;
                    }
                }
                //checks the middle
                else {
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
    //using recursion-start
    public boolean checkTravelHead() {
        //boolean[][] traveled = new boolean[grid.length][grid[0].length];
        int[][] traveled = new int[grid.length][grid[0].length];
        for (int row = 0; row < traveled.length; row++) {
            for (int col = 0; col < traveled[row].length; col++) {
                traveled[row][col] = grid[row][col];
            }
        }
        //start at the beginning
        if (grid[0][0] == 0) {
            if (checkTravel(traveled, 0, 0)) {

                return true;
            }
            else {
                return false;
            }
        }
        else {

            if (checkTravel(traveled, 1, 0)) {

                return true;
            }
            else {

                return false;
            }
        }
    }
    //recursive method to find if all spots are traveled
    public boolean checkTravel(int[][] traveled, int row, int col) {
        traveled[row][col] = 1;
        //checks up
        if (row - 1 >= 0 && traveled[row-1][col] == 0 )
            checkTravel(traveled, row - 1, col);

        //checks down
        if (row + 1 < grid.length && traveled[row + 1][col] == 0 )
            checkTravel(traveled, row + 1, col);
        //checks left
        if (col - 1 >= 0 && traveled[row][col - 1] == 0 )
            checkTravel(traveled, row, col - 1);
        //checks right
        if (col + 1 < grid.length && traveled[row][col + 1] == 0 )
            checkTravel(traveled, row, col + 1);
        if (checkReached(traveled)) {
            return true;
        }
        else {
            return false;
        }
    }
    //checks if all spots were reached
    public boolean checkReached(int[][] traveled) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                //if the place was not traveled but it could have been
                if (traveled[row][col] == 0 && grid[row][col] == 0)
                    return false;
            }
        }
        return true;
    }

    public boolean checkFinalCount() {
        for(int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int count = 0;
                if(spots[row][col] != 0) {
                     count = checkUp(row, col) +
                             checkDown(row, col) +
                             checkLeft(row, col) +
                             checkRight(row, col) + 1;
                    if(count != spots[row][col]) {

                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkCurrentCount() {
        for(int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int count = 0;
                if(spots[row][col] != 0) {
                    count = checkUp(row, col) +
                            checkDown(row, col) +
                            checkLeft(row, col) +
                            checkRight(row, col) + 1;

                    if(count < spots[row][col]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //these methods count how many squares they can "see"
    public int checkUp(int row, int col) {
        //checks up
        if(row - 1 >= 0) {
            if (grid[row - 1][col] == 1)
                return 0;
            else
                return 1 + checkUp(row - 1, col);
        }
        else {
            return 0;
        }
    }

    public int checkDown(int row, int col) {
        //checks down
        if (row + 1 < grid.length) {
            if (grid[row + 1][col] == 1)
                return 0;
            else
                return 1 + checkDown(row + 1, col);
        } else {
            return 0;
        }
    }

    public int checkLeft(int row, int col){
            //checks left
            if (col - 1 >= 0) {
                if (grid[row][col - 1] == 1)
                    return 0;
                else
                    return 1 + checkLeft(row, col - 1);
            } else {
                return 0;
            }
        }

    public int checkRight(int row, int col){
            //checks right
            if (col + 1 < grid.length) {
                if (grid[row][col + 1] == 1)
                    return 0;
                else
                    return 1 + checkRight(row, col + 1);
            } else {
                return 0;
            }
        }
    //makes sure a number square cant get selected
    public boolean checkSpace() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (spots[i][j] != 0 && grid[i][j] == 1) {

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
