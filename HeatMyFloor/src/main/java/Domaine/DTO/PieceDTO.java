package Domaine.DTO;

import Domaine.Entite.Meuble;
import Domaine.Entite.Piece;
import java.awt.Polygon;
import java.util.ArrayList;

public class PieceDTO {
    
    private Polygon forme;
    private ArrayList<MeubleDTO> meubles = new ArrayList<>();
    
    public PieceDTO(Polygon p_forme)
    {   
        forme = p_forme;
    }
    
    public PieceDTO(Piece p_piece)
    {   
        forme = p_piece.getForme();
        for (Meuble meuble : p_piece.getMeubles())
            meubles.add(new MeubleDTO(meuble));
    }
    
    public Polygon getForme()
    {
        return forme;
    }
    
    public void setForme(Polygon p_forme)
    {
        forme = p_forme;
    }
    
    public ArrayList<MeubleDTO> getMeubles()
    {
        return meubles;
    }
}
