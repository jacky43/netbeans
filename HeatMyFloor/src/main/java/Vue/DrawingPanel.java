package Vue;

import Vue.Drawing.MainDrawer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final MainWindow mainWindow;
    private final MainDrawer mainDrawer;
    private double zoomFactor = 1.0;
    private double panOffsetX = 0;
    private double panOffsetY = 0;
    
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
     
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        repaint();
    }
     
    public void setPanOffset(double panOffsetX, double panOffsetY) {
        this.panOffsetX = panOffsetX;
        this.panOffsetY = panOffsetY;
        repaint();
    }
     
    public double getZoomFactor() {
        return zoomFactor;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        Dimension currentSize = getSize();
      
        if (!mainWindow.controller.estInitialise()) {
            Polygon p = creerFormeRegulierePiece(currentSize);
            mainWindow.controller.InitialiserPiece(p);
        }
        
        g2d.translate(panOffsetX, panOffsetY);
        g2d.scale(zoomFactor, zoomFactor);
        
        Dimension adjustedSize = new Dimension(
            (int)((currentSize.width - panOffsetX) / zoomFactor),
            (int)((currentSize.height - panOffsetY) / zoomFactor)
        );
        
        mainDrawer.draw(g2d, currentSize);
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