package Domaine.DTO;

import Domaine.Entite.Meuble;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.Entite.MeubleSansDrain;
import java.awt.Point;
import java.util.UUID;

public class MeubleDTO {
    
    private Point position;
    private int longueur;
    private int largeur;
    private Point centreDrain;
    private String nom;
    //private final UUID id;
    
    public MeubleDTO(Point p_position, 
                     int p_longueur, 
                     int p_largeur,
                     String p_nom)
    {
        position = p_position;
        longueur = p_longueur;
        largeur = p_largeur;
        nom = p_nom;
    }
        
    public MeubleDTO(Point p_position, 
                     int p_longueur, 
                     int p_largeur, 
                     String p_nom,
                     Point p_centreDrain)
    {
        position = p_position;
        longueur = p_longueur;
        largeur = p_largeur;
        nom = p_nom;
        centreDrain = p_centreDrain;
    }
    
    // TODO : Meuble Sans Drain
    public MeubleDTO(Meuble p_meuble)
    {
        position = p_meuble.getPosition();
        longueur = p_meuble.getLongueur();
        largeur = p_meuble.getLargeur();
        nom = p_meuble.getNom();
        //id = p_meuble.getId();
    }
    
    public MeubleDTO(MeubleAvecDrain p_meuble)
    {
        position = p_meuble.getPosition();
        longueur = p_meuble.getLongueur();
        largeur = p_meuble.getLargeur();
        centreDrain = p_meuble.getCentreDrain();
    }
    
    public MeubleDTO(MeubleSansDrain p_meuble)
    {
        position = p_meuble.getPosition();
        longueur = p_meuble.getLongueur();
        largeur = p_meuble.getLargeur();
    }
    
    public boolean estAvecDrain()
    {
        return centreDrain != null;
    }
    
    public Point getPosition()
    {
        return position;
    }
    
    public void setPosition(Point newPosition)
    {
        position = newPosition;
    }
    
    public int getLongueur()
    {
        return longueur;
    }
    
    public void setLongueur(int newLongueur)
    {
        longueur = newLongueur;
    } 
    
    public int getLargeur()
    {
        return largeur;
    }
    
    public void setLargeur(int newLargeur)
    {
        largeur = newLargeur;
    }
    
    public Point getCentreDrain()
    {
        return centreDrain;
    }
    
    public void setCentreDrain(Point p_centreDrain)
    {
        centreDrain = p_centreDrain;
    }
}
