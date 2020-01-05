import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class KakarasuButton extends JButton implements ActionListener, PuzzleButton {
    private Kakarasu k;
    private int row;
    private int col;
    ImageIcon check = new ImageIcon("checkmark1.png");
    ImageIcon x = new ImageIcon("Red_X.png");

    public KakarasuButton( int row, int col, Kakarasu k) {
        this.row = row;
        this.col = col;
        this.k = k;
        this.addActionListener(this);
    }
    @Override
    public void setCorrect() {
        this.setIcon(check);
        repaint();
    }

    @Override
    public void setEmpty() {
        this.setIcon(null);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        k.setSpot(row,col);

    if(k.isSelect(row, col))
        this.setIcon(check);
    else
        this.setIcon(x);
    repaint();
    }
}
