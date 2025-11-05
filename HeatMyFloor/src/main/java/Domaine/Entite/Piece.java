package Domaine.Entite;

import Domaine.DTO.PieceDTO;
import java.awt.Point;
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
    
    public Point getPositionPiece()
    {
        return new Point(forme.xpoints[0], forme.ypoints[0]);
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
        meubles.add(p_meuble);
    }
    
    // TODO : Ajuster lorsque les éléments seront rajoutés 
    //        (Thermostat, element chauffant...)
    public void SelectionnerElement(Point p_position)
    {
        for (Meuble meuble : meubles)
        {
            if (meuble.getPosition().x + meuble.getLargeur() <= p_position.x &&
                meuble.getPosition().y + meuble.getLongueur() <= p_position.y)
            {
                meuble.ChangerStatut();
                break;
            }
        }
    }
     
    // TODO - Staelle : Faire une validation et trouver le meuble équivalent
    //                  à l'ID fourni
    public void SupprimerMeuble(UUID id)
    {
    
    }
}
