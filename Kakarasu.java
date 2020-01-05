import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Kakarasu implements PuzzleGame {


    private Frame f;
    private JLabel label;
    public int[][] grid;//= new int[4][4];
    private int[] possValue = {0,1};
    private int[] rowTotal;// = //{1,7,3,7};//{1,4,6,4};
    private int[] colTotal;//= //{6,5,4,6};//{5,3,4,5};
    private KakarasuButton[][] kb;

    public Kakarasu(Frame f) {
    this.f = f;
    //reading in file and setting correct sizes
    ArrayList<Integer> a = readIntsToArrayList("kakarasuNumbers22.txt");
    getSizes(a);
    assignTargets(a);

    }

//read file
    public ArrayList<Integer> readIntsToArrayList(String fn) {
        ArrayList<Integer> strs = new ArrayList<Integer>();
        File f = new File(fn);

        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextInt()) {
                if (sc.hasNextInt()) {
                    int s = sc.nextInt();
                    strs.add(s);
                } else sc.next();
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Kakarasu file not found. Closing");
            System.exit(1);
            e.printStackTrace();
        }
        return strs;
    }
//get size of grid
            public void getSizes(ArrayList<Integer> a) {
                int size = a.size() / 2;
                rowTotal = new int[size];
                colTotal = new int[size];
                grid = new int[size][size];
                kb = new KakarasuButton[size][size];
            }
//assign row and col targets
            public void assignTargets (ArrayList<Integer> a) {
        for (int i = 0 ; i < a.size()/2; i++) {
            rowTotal[i] = a.get(i);
        }

        for (int i = a.size()/2 ; i < a.size(); i++) {
            colTotal[i - a.size()/2] = a.get(i);
            }
        }
//gets kakarasu grid
    @Override
    public void getGrid() {
        Container ct = f.getContentPane();
        JPanel centerGrid = new JPanel(new GridLayout(grid.length+2,grid.length+2,5,5));
        ct.add(centerGrid, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.decode("#F0C808"));
        ct.add(topPanel, BorderLayout.NORTH);


        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(Color.decode("#F0C808"));
        ct.add(leftBorder, BorderLayout.WEST);


        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(Color.decode("#F0C808"));
        ct.add(rightBorder, BorderLayout.EAST);


        JPanel botPanel = new JPanel();
        botPanel.setBackground(Color.decode("#F0C808"));
        ct.add(botPanel, BorderLayout.SOUTH);

        Font myFont = new Font("Serif",Font.BOLD, 36);
        Font myFont1 = new Font("Serif",Font.BOLD, 24);
        Font labelFont = new Font("Serif",Font.BOLD, 10);


        int h = 0;
        int h1 = 0;
        int row = 1;
        int col = 1;
        //sets up center grid and puts the number within the grid
        for (int i = 0; i < grid.length+2; i++) {
            for (int j = 0; j < grid.length+2; j++) {
               if (i == 0 && j == 0 || i == grid.length+1 && j == 0 || i == grid.length+1 && j == grid.length+1 || i == 0 && j == grid.length+1) {
                   centerGrid.add(Box.createVerticalGlue());
               }
               else if (i == 0) {
                   JLabel num = new JLabel (Integer.toString(row++));
                   num.setFont(myFont1);
                   num.setHorizontalAlignment(JLabel.CENTER);
                   num.setVerticalAlignment(JLabel.BOTTOM);
                   centerGrid.add(num);
               }
               else if (j == 0) {
                   JLabel num = new JLabel (Integer.toString(col++));
                   num.setFont(myFont1);
                   num.setHorizontalAlignment(JLabel.RIGHT);
                   num.setVerticalAlignment(JLabel.CENTER);
                   centerGrid.add(num);
               }
               else if (j == grid.length+1) {
                    JLabel num = new JLabel(Integer.toString(rowTotal[h++]));
                   num.setFont(myFont);
                   num.setHorizontalAlignment(JLabel.LEFT);
                   num.setVerticalAlignment(JLabel.CENTER);
                    centerGrid.add(num);
                }
                else if (i == grid.length+1) {
                    JLabel num = new JLabel(Integer.toString(colTotal[h1++]));
                   num.setFont(myFont);
                   num.setHorizontalAlignment(JLabel.CENTER);
                   num.setVerticalAlignment(JLabel.TOP);
                    centerGrid.add(num);
                }

                else {
                    JButton jb = new KakarasuButton(i-1, j-1, this);
                    centerGrid.add(jb);
                    kb[i-1][j-1] = (KakarasuButton) jb;

                }
            }
        }

//adding side buttons
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
    //empties grid
    @Override
    public void emptyGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = 0;
                if(kb[row][col] != null)
                    kb[row][col].setEmpty();
            }
        }
    }
    //fills grid
    @Override
    public void fillGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (this.isSelect(row, col)) {
                    kb[row][col].setCorrect();
                }
                if(!this.isSelect(row,col)) {
                    kb[row][col].setEmpty();
                }

            }
        }
    }

    @Override
    public boolean checkCurrent() {

        // this checks the rows
        for(int row = 0; row < grid.length; row++) {
            int sumRow = 0; //have to do this for each row
            for (int col = 0; col < grid[row].length; col++) {
                if (isSelect(row, col))
                    sumRow = sumRow + col + 1;
            }
            if(sumRow > rowTotal[row])
                return false;
        }
        //this checks columns
        for(int col = 0; col < grid[0].length; col++) {
            int sumCol = 0; //have to do this for each row
            for (int row = 0; row < grid.length; row++) {
                if (isSelect(row, col))
                    sumCol = sumCol + row + 1;
            }
            if(sumCol > colTotal[col])
                return false;
        }

        return true;
    }

    @Override
    public boolean checkAll(){

        // this checks the rows
    for(int row = 0; row < grid.length; row++) {
        int sumRow = 0; //have to do this for each row
        for (int col = 0; col < grid[row].length; col++) {
            if (isSelect(row, col))
                sumRow = sumRow + col + 1;
        }
        if(sumRow != rowTotal[row])
            return false;
    }
    //this checks columns
        for(int col = 0; col < grid[0].length; col++) {  //negative one because the sides areeeee
            int sumCol = 0; //have to do this for each row
            for (int row = 0; row < grid.length; row++) {
                if (isSelect(row, col))
                    sumCol = sumCol + row + 1;
            }
            if(sumCol != colTotal[col])
                return false;
        }

        return true;
    }
    @Override
    public void setSpot (int r, int c) {
        if (grid[r][c] == 0)
        grid[r][c] = 1;
        else
            grid[r][c] = 0;

    }

    public boolean isSelect (int r, int c) {
        if (grid[r][c] == 1) {
            return true;
        } else {
            return false;
        }
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
