/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import javax.swing.*;
import java.awt.*;

public class GlassPane extends JComponent {
    private boolean dimmed = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dimmed) {
            g.setColor(new Color(0, 0, 0, 128)); // dim color
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void dimBackground() {
        dimmed = true;
        repaint();
    }

    public void undimBackground() {
        dimmed = false;
        repaint();
    }
}
