import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkyscraperButton extends JButton implements ActionListener, PuzzleButton {
    private Skyscraper s;
    private int row;
    private int col;

    public SkyscraperButton(Skyscraper s, int row, int col) {
    this.s = s;
    this.row = row;
    this.col = col;
    this.addActionListener(this);
    }
    @Override
    public void setCorrect () {
        if (s.getSpot(row,col) == 0)
            this.setText("p");
        else
            this.setText(Integer.toString(s.getSpot(row, col)));

    }

    @Override
    public void setEmpty() {
        this.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        s.setSpot(row, col);
        if(s.getSpot(row,col) == 0)
            this.setText("none");
        else if (s.getSpot(row,col) > 0)
            this.setText(Integer.toString(s.getSpot(row, col)));
    }

}
