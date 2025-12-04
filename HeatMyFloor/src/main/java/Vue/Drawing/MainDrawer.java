package Vue.Drawing;

import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.HeatMyFloorController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.awt.Font;

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
            // Dessiner le drain
            if (meuble.estAvecDrain()) {
                int taille = meuble.getDiametreDrain();
                Point centreDrain = meuble.getCentreDrain();

                if (centreDrain != null) {
                    // centreDrain.x et centreDrain.y sont les coordonnées DANS le meuble
                    // (0,0) = coin inférieur gauche du meuble

                    // Position absolue du drain dans le système de coordonnées de la pièce
                    int xDrainAbsolu = position.x + centreDrain.x;
                    int yDrainAbsolu = position.y - meuble.getLongueur() + centreDrain.y;

                    // Conversion en pixels pour l'affichage
                    int xDrainPixels = convertToPixels(xDrainAbsolu) + origineAxes.x;
                    int yDrainPixels = origineAxes.y - convertToPixels(yDrainAbsolu);

                    // Centrer le cercle sur la position
                    int xDrainCentre = xDrainPixels - convertToPixels(taille) / 2;
                    int yDrainCentre = yDrainPixels - convertToPixels(taille) / 2;

                    System.out.println("=== DESSIN DRAIN ===");
                    System.out.println("Centre drain (relatif au meuble): " + centreDrain);
                    System.out.println("Position meuble (coin supérieur gauche): " + position);
                    System.out.println("Longueur meuble: " + meuble.getLongueur());
                    System.out.println("Position absolue drain: (" + xDrainAbsolu + ", " + yDrainAbsolu + ")");
                    System.out.println("Position écran (centre): (" + xDrainCentre + ", " + yDrainCentre + ")");

                    g.setColor(Color.RED);
                    g.fillOval(xDrainCentre, yDrainCentre,
                            convertToPixels(taille), convertToPixels(taille));
                    g.setColor(Color.BLACK);
                    g.drawOval(xDrainCentre, yDrainCentre,
                            convertToPixels(taille), convertToPixels(taille));
                }
            }
            
            if (meuble.estSelectionne()) 
            {
                g.setColor(Color.RED);
                g.drawRect(convertToPixels(position.x) + origineAxes.x,
                    origineAxes.y - convertToPixels(position.y),
                    convertToPixels(meuble.getLargeur()),
                    convertToPixels(meuble.getLongueur()));
            }
            
            if (!estElementChauffant) {
                g.setColor(Color.BLACK);
                g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
                
                int xTexte = convertToPixels(position.x) + origineAxes.x + convertToPixels(meuble.getLargeur()) + 5;
                int yTexte = origineAxes.y - convertToPixels(position.y) + convertToPixels(meuble.getLongueur()) / 2;
                
                String nomAffiche = "";
                switch(meuble.getNom()) {
                    case "BAIN":
                        nomAffiche = "Bain";
                        break;
                    case "DOUCHE":
                        nomAffiche = "Douche";
                        break;
                    case "VANITE":
                        nomAffiche = "Vanité";
                        break;
                    case "ARMOIRE":
                        nomAffiche = "Armoire";
                        break;
                    case "PLACARD":
                        nomAffiche = "Placard";
                        break;
                    case "TOILETTE":
                        nomAffiche = "Toilette";
                        break;
                    default:
                        nomAffiche = meuble.getNom();
                }
                g.drawString(nomAffiche, xTexte, yTexte);
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
