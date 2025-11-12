package Vue;

import Vue.Drawing.MainDrawer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class DrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final MainWindow mainWindow;
    private final MainDrawer mainDrawer; 

    public DrawingPanel(MainWindow window) {
        this.mainWindow = window;
        this.mainDrawer = new MainDrawer(mainWindow.controller);

        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setBackground(java.awt.Color.WHITE);

       
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Polygon p = creerFormeRegulierePiece(getSize());
                mainWindow.controller.InitialiserPiece(p);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension currentSize = getSize();

      
        if (!mainWindow.controller.estInitialise()) {
            Polygon p = creerFormeRegulierePiece(currentSize);
            mainWindow.controller.InitialiserPiece(p);
        }

        
        mainDrawer.draw(g, currentSize);
    }

    
    private Polygon creerFormeRegulierePiece(Dimension dimension) {
        int inches = 10;
        int DPI = 96;
        int sizeInPixels = inches * DPI;

        int width = dimension.width;
        int height = dimension.height;

        
        if (sizeInPixels > width || sizeInPixels > height) {
            sizeInPixels = Math.min(width, height) - 20;
            if (sizeInPixels < 0) sizeInPixels = 0;
        }

        int x = (width - sizeInPixels) / 2;
        int y = (height - sizeInPixels) / 2;

        int[] xPoints = { x, x + sizeInPixels, x + sizeInPixels, x };
        int[] yPoints = { y, y, y + sizeInPixels, y + sizeInPixels };

        return new Polygon(xPoints, yPoints, 4);
    }
}
