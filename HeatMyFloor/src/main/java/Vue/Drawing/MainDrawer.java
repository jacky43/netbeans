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
    private final int LONGUEUR_DRAIN = 5;
    private final int LARGEUR_DRAIN = 5;
    
    public MainDrawer(HeatMyFloorController p_controller)
    {
        controller = p_controller;
    }

    public void draw(Graphics g, Dimension dimension)
    {
        drawPieceReguliere(g, dimension);
    }

    public void drawPieceReguliere(Graphics g, Dimension dimension)
    {        
        PieceDTO piece = controller.ObtenirPiece();
        g.setColor(new Color(243, 234, 226));
        g.fillPolygon(piece.getForme());
        g.setColor(Color.BLACK);
        g.drawPolygon(piece.getForme());
        
    }
    
    public void drawMeubles(Graphics g, ArrayList<MeubleDTO> p_meubles)
    {
        for (MeubleDTO meuble : p_meubles)
        {
            g.drawRect(meuble.getPosition().x, 
                       meuble.getPosition().y, 
                       meuble.getLargeur(), 
                       meuble.getLongueur());
            
            if (meuble.estAvecDrain())
            {
                g.drawRect(meuble.getCentreDrain().x, 
                           meuble.getCentreDrain().y, 
                           LARGEUR_DRAIN, 
                           LONGUEUR_DRAIN);
            }
        }
    }
}
