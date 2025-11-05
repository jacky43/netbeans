package Vue;

import Vue.Drawing.MainDrawer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends JPanel{
    
    private final MainWindow mainWindow;
    
    public DrawingPanel(MainWindow window)
    {
        mainWindow = window;
        setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
        setBackground(java.awt.Color.WHITE);
    }
            
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension currentSize = getSize();
        if (!mainWindow.controller.estInitialise())
            mainWindow.controller.InitialiserPiece(CreerFormeRegulierePiece(currentSize));
            
        MainDrawer mainDrawer = new MainDrawer(mainWindow.controller);
        mainDrawer.draw(g, currentSize);
    }
    
    private Polygon CreerFormeRegulierePiece(Dimension dimension)
    {
        int inches = 10;
        int DPI = 96;
        int sizeInPixels = inches * DPI;

        int width = (int) dimension.getWidth();
	int height = (int) dimension.getHeight();
        
        if (sizeInPixels > width || sizeInPixels > height) {
            sizeInPixels = Math.min(width, height) - 20;
            if (sizeInPixels < 0) sizeInPixels = 0;
        }
        
        int x = (width - sizeInPixels) / 2;
        int y = (height - sizeInPixels) / 2;
        
        int[] xPoints = { x, x + sizeInPixels, x + sizeInPixels, x};
        int[] yPoints = { y, y, y + sizeInPixels, y + sizeInPixels};
        
        return new Polygon(xPoints, yPoints, 4);
    }
}