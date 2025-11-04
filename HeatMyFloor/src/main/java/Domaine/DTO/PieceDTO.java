package Domaine.DTO;

import Domaine.Entite.Piece;
import java.awt.Polygon;

public class PieceDTO {
    
    private Polygon forme;
    
    public PieceDTO(Polygon p_forme)
    {   
        forme = p_forme;
    }
    
    public PieceDTO(Piece p_piece)
    {   
        forme = p_piece.getForme();
    }
    
    public Polygon getForme()
    {
        return forme;
    }
    
    public void setForme(Polygon p_forme)
    {
        forme = p_forme;
    }
    
}
