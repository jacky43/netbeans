package Domaine.Entite;

import Domaine.DTO.MeubleDTO;
import java.awt.Point;
import java.util.UUID;

public abstract class Meuble {
    private Point position;
    private int longueur;
    private int largeur;
    private String nom;
    
    private boolean estSelectionne = false;
    
    public final UUID id = UUID.randomUUID();
    
    public Meuble(MeubleDTO dto)
    {
        position = dto.getPosition();
        longueur = dto.getLongueur();
        largeur = dto.getLargeur();
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
    
    public String getNom()
    {
        return nom;
    }
    
    public void setNom(String newNom)
    {
        nom = newNom;
    }
    
    public UUID getId()
    {
        return id;
    }
    
    public boolean estSelectionne()
    {
        return estSelectionne == true;
    }        
    
    public void ChangerStatut()
    {
        estSelectionne = !estSelectionne;
    }
}
