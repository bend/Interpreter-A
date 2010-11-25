/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter.grapher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author benoitdaccache
 */
public class GraphPanel extends JPanel {

    int lastx = 0;
    int lasty = 0;

    public GraphPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Image img1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("resources/graph.png"));
        gc.drawImage(img1, 0, 0, this);
        gc.finalize();
    }

    public void drawPoint(int x, int y) {
        if (x == 0) {
            lastx = 0;
            repaint();
        } else {
            if (lasty == 0)
                lasty = y;
            Graphics gc = this.getGraphics();
            gc.setColor(Color.BLUE);
            gc.drawLine(x, 254, x, y);
            gc.setColor(Color.RED);
            gc.drawLine(lastx, lasty + 1, x, y + 1);
            gc.drawLine(lastx - 1, lasty, x - 1, y);
            gc.drawLine(lastx + 1, lasty, x + 1, y);
            gc.finalize();
            lastx = x;
            lasty = y;
        }
    }
}
