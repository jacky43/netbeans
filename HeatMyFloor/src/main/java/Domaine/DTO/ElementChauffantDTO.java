
package Domaine.DTO;

import java.awt.Point;
import java.util.UUID;


public class ElementChauffantDTO implements ElementSelectionnableDTO {
    
     private Point position;
    private int longueur;
    private int largeur;
    private boolean estSelectionne;
    private UUID id;
    
    
      public ElementChauffantDTO(Point p_position,
            int p_longueur,
            int p_largeur) {
        //getion erreur plus tard 
        position = new Point(p_position);
        longueur = p_longueur;
        largeur = p_largeur;
        estSelectionne = false;
        id = null;
    }
      
     @Override
     public Point getPosition() {
        return new Point(position);
    }

     @Override
    public int getLongueur() {
        return longueur;
    }

     @Override
    public int getLargeur() {
        return largeur;
    }
    
     @Override
       public UUID getId() {
        return id;
    }
       
     @Override
    public boolean estSelectionne() {
       return estSelectionne;
    }

}
