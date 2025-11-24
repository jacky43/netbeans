package Vue.Drawing;

import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.HeatMyFloorController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

public class MainDrawer {

    private final HeatMyFloorController controller;
    private final int DPI = 6;
    private Point origineAxes;

    public MainDrawer(HeatMyFloorController p_controller) {
        controller = p_controller;
    }

    public void draw(Graphics g) {
        drawPieceReguliere(g);
    }

    public void drawPieceReguliere(Graphics g) {
        PieceDTO piece = controller.ObtenirPiece();
        
        Polygon forme = piece.getForme();
        Polygon formeConvertie = convertFormValuesInchesToPixels(forme);
        origineAxes = new Point(convertToPixels(piece.getOrigine().x), 
                                convertToPixels(piece.getOrigine().y));
                
        g.setColor(new Color(243, 234, 226));
        g.fillPolygon(formeConvertie);
        g.setColor(Color.BLACK);
        g.drawPolygon(formeConvertie);
        drawMeubles(g, piece.getMeubles());
    }

    // TODO : Faire 2 méthodes séparées pour les meubles et les éléments chauffanst
    public void drawMeubles(Graphics g, ArrayList<MeubleDTO> p_meubles) {
        
        for (MeubleDTO meuble : p_meubles) {
            boolean estElementChauffant = "ELEMENT_CHAUFFANT".equals(meuble.getNom());
            Point position = meuble.getPosition();
            if(estElementChauffant)
            {
                g.setColor(Color.YELLOW);                
                g.fillRect(convertToPixels(position.x)  + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y),
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
                
                g.setColor(Color.BLACK);
                g.drawRect(convertToPixels(position.x) + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y)  ,
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
            }
            else
            {
                g.setColor(Color.DARK_GRAY);
                
                g.fillRect(convertToPixels(position.x)  + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y),
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
                
                g.setColor(Color.BLACK);
                
                g.drawRect(convertToPixels(position.x) + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y)  ,
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
            }
            
            // TODO : Ajuster avec les nouvelles valeurs
            if (meuble.estAvecDrain())
            {
                int taille = meuble.getDiametreDrain();
                
                // TODO - NDH : Utiliser le centre du drain et ajuster les réfs.
                int x = position.x + meuble.getLongueur() / 2;
                int y = position.y - meuble.getLargeur() / 2 ;
                
                int xDrain = convertToPixels(x - (int) taille / 2) + origineAxes.x;
                int yDrain = origineAxes.y - convertToPixels(y + (int) taille / 2);
                
                g.setColor(Color.RED);
                g.fillOval(xDrain, yDrain, 
                           convertToPixels(taille), convertToPixels(taille));
                g.setColor(Color.BLACK);
                g.drawOval(xDrain, yDrain, 
                           convertToPixels(taille), convertToPixels(taille));
            }
            
            if (meuble.estSelectionne()) 
            {
                g.setColor(Color.RED);
                g.drawRect(convertToPixels(position.x) + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y),
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
            }
        }
    }

    private int convertToPixels(int valeuEnPouces)
    {
        return (int)valeuEnPouces * DPI;
    }
    
    private Polygon convertFormValuesInchesToPixels(Polygon p_poly)
    {
        int[] xPoints = new int[p_poly.xpoints.length];
        int[] yPoints = new int[p_poly.ypoints.length];
        
        for (int i = 0; i < p_poly.xpoints.length; i++)
            xPoints[i] = convertToPixels(p_poly.xpoints[i]);
        
        for (int i = 0; i < p_poly.xpoints.length; i++)
            yPoints[i] = convertToPixels(p_poly.ypoints[i]);
        
        return new Polygon(xPoints, yPoints, p_poly.npoints);
    }
}
