import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KurodokoButton extends JButton implements ActionListener, PuzzleButton {

    private Kurodoko k;
    private int row;
    private int col;
    private Color color;


    public KurodokoButton(Kurodoko k, int row, int col) {
        this.row = row;
        this.col = col;
        this.k = k;
        this.addActionListener(this);

    }
    @Override
    public void setCorrect () {

        this.setBackground((Color.lightGray));
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
        k.setSpot(row, col);
        if(k.getSpot(row, col) == 1) {
            this.setBackground(Color.lightGray);
        }
        else {
            this.setBackground(Color.WHITE);
        }
    }
}
