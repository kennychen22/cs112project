import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HitoriButton extends JButton implements ActionListener, PuzzleButton {

    private Hitori h;
    private int row;
    private int col;


    public HitoriButton(Hitori h, int row, int col) {
        this.row = row;
        this.col = col;
        this.h = h;
        this.addActionListener(this);
    }

    @Override
    public void setCorrect () {

            this.setBackground(Color.lightGray);
          //  this.setText(Integer.toString(h.getSpot(row,col)));
            this.setOpaque(true);

            repaint();
    }

    @Override
    public void setEmpty() {
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        h.setSpot(row, col);
        if(h.getSpot(row, col) == 1)
         this.setBackground(Color.lightGray);
            else {
                this.setBackground(Color.WHITE);
}
    }
}
