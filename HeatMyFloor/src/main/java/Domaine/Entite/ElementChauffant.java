
package Domaine.Entite;

import Domaine.DTO.ElementChauffantDTO;
import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

public class ElementChauffant implements ElementSelectionnable , Serializable {
    private Point position;
    private int longueur;
    private int largeur;
    private boolean estSelectionne = false;
    
    public final UUID id = UUID.randomUUID();
    
    public ElementChauffant(ElementChauffantDTO dto)
    {
        position = new Point(dto.getPosition());
        longueur = dto.getLongueur();
        largeur = dto.getLargeur();
    }
    
    @Override
    public Point getPosition()
    {
        return new Point (position);
    }
    
    @Override
    public void setPosition(Point newPosition)
    {
        //validation newPosition doit pas être null
        position = new Point (newPosition);
    }
    
    @Override
    public int getLongueur()
    {
        return longueur;
    }
    
    @Override
    public void setLongueur(int newLongueur)
    {
        longueur = newLongueur;
    } 
    
    @Override
    public int getLargeur()
    {
        return largeur;
    }
    
    @Override
    public void setLargeur(int newLargeur)
    {
        largeur = newLargeur;
    }  
       
    @Override
    public UUID getId()
    {
        return id;
    }
    
    @Override
    public boolean estSelectionne()
    {
        return estSelectionne;
    }   
    
    @Override
    public void ChangerStatut()
    {
        estSelectionne = !estSelectionne;
    }
    
    @Override
    public void setSelectionne(boolean selectionne)
    {
        estSelectionne = selectionne;
    }

    @Override
    public Object ToDto() {
        return new ElementChauffantDTO(getPosition(), getLongueur(), getLargeur());
    }
}