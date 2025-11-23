package Vue.Drawing;

import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.HeatMyFloorController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

public class MainDrawer {

    private final HeatMyFloorController controller;

    public MainDrawer(HeatMyFloorController p_controller) {
        controller = p_controller;
    }

    public void draw(Graphics g, Dimension dimension) {
        drawPieceReguliere(g, dimension);
    }

    public void drawPieceReguliere(Graphics g, Dimension dimension) {
        PieceDTO piece = controller.ObtenirPiece();
        g.setColor(new Color(243, 234, 226));
        g.fillPolygon(piece.getForme());
        g.setColor(Color.BLACK);
        g.drawPolygon(piece.getForme());

        drawMeubles(g, piece.getMeubles());
    }

    public void drawMeubles(Graphics g, ArrayList<MeubleDTO> p_meubles) {
        
        for (MeubleDTO meuble : p_meubles) {
            boolean estElementChauffant = "ELEMENT_CHAUFFANT".equals(meuble.getNom());
            if(estElementChauffant){
                g.setColor(Color.YELLOW);
            g.fillRect(meuble.getPosition().x,
                    meuble.getPosition().y,
                    meuble.getLargeur(),
                    meuble.getLongueur());
            g.setColor(Color.BLACK);
            g.drawRect(meuble.getPosition().x,
                    meuble.getPosition().y,
                    meuble.getLargeur(),
                    meuble.getLongueur());
            }else{
                g.setColor(Color.DARK_GRAY);
            g.fillRect(meuble.getPosition().x,
                    meuble.getPosition().y,
                    meuble.getLargeur(),
                    meuble.getLongueur());
            g.setColor(Color.BLACK);
            g.drawRect(meuble.getPosition().x,
                    meuble.getPosition().y,
                    meuble.getLargeur(),
                    meuble.getLongueur());
            }
            

            if (meuble.estSelectionne()) {
                g.setColor(Color.RED);
                g.drawRect(meuble.getPosition().x,
                        meuble.getPosition().y,
                        meuble.getLargeur(),
                        meuble.getLongueur());
            }
            
            if (meuble.estAvecDrain()) {
                int taille = meuble.getDiametreDrainPixels();
                if (taille <= 0) taille = 10;
                
                int xDrain = meuble.getPosition().x + meuble.getLargeur() / 2 - taille / 2;
                int yDrain = meuble.getPosition().y + meuble.getLongueur() / 2 - taille / 2;
                
                g.setColor(Color.RED);
                g.fillOval(xDrain, yDrain, taille, taille);
                g.setColor(Color.BLACK);
                g.drawOval(xDrain, yDrain, taille, taille);
            }
        }
    }
}
