package Domaine.Entite;

import Domaine.DTO.PieceDTO;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.UUID;

public class Piece {
    
    private Polygon forme;
    private ArrayList<Meuble> meubles = new ArrayList<>();
    
    public Piece()
    {
        
    }
    
    public Polygon getForme()
    {
        return forme;
    }
    
    public void setForme(Polygon p_forme)
    {
        forme = p_forme;
    }
    
    public ArrayList<Meuble> getMeubles()
    {
        return meubles;
    }
    
    public void AjouterMeuble(Meuble p_meuble)
    {
    
    }
    
    public void ModifierMeuble(UUID id)
    {
    
    }
        
    public void SupprimerMeuble(UUID id)
    {
    
    }
}
