package Vue;

import Domaine.HeatMyFloorController;
import Vue.Drawing.MainDrawer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
    private final int DPI = 6;
    private final int DIMENSION_DEFAUT_PIECE_FEET = 10;
    private final int FACTEUR_CONVERSION_FEET_INCHES = 12;
    private double zoomFactor = 1.0;
    private double panOffsetX = 0;
    private double panOffsetY = 0;
    
    private Point origineAxes;
    
    public DrawingPanel(MainWindow window) {
        this.mainWindow = window;
        this.mainDrawer = new MainDrawer(mainWindow.controller);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setBackground(java.awt.Color.WHITE);
        origineAxes = new Point(0, 0);
       
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
        
        origineAxes = mainWindow.controller.getOrigineAxes();
        g2d.translate(panOffsetX, panOffsetY);
        g2d.scale(zoomFactor, zoomFactor);
        
        mainDrawer.draw(g2d);
    }
    
    private Polygon creerFormeRegulierePiece(Dimension dimension) {
        
        int taillePiecePouces = DIMENSION_DEFAUT_PIECE_FEET * FACTEUR_CONVERSION_FEET_INCHES;
        
        int largeurEcranPouces = dimension.width / DPI;
        int longueurEcranPouces = dimension.height / DPI;
        
        int x = (largeurEcranPouces - taillePiecePouces) / 2;
        int y = (longueurEcranPouces - taillePiecePouces) / 2;
        
        int[] xPoints = { x, x + taillePiecePouces, x + taillePiecePouces, x };
        int[] yPoints = { y, y, y + taillePiecePouces, y + taillePiecePouces };
        
        return new Polygon(xPoints, yPoints, 4);
    }
    
    public Point getOrigineAxes()
    {
        return origineAxes;
    }
     public void mettreAJourController(HeatMyFloorController controller){
         mainDrawer.setController(controller);
     }
}