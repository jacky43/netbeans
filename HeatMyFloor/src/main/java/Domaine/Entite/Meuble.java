package Domaine.Entite;

import Domaine.DTO.MeubleDTO;
import java.awt.Point;
import java.util.UUID;

public abstract class Meuble implements ElementSelectionnable {
    private Point position;
    private int longueur;
    private int largeur;
    private String nom;
    private boolean estSelectionne = false;
    
    public final UUID id = UUID.randomUUID();
    
    public Meuble(MeubleDTO dto)
    {
        position = new Point(dto.getPosition());
        longueur = dto.getLongueur();
        largeur = dto.getLargeur();
        nom = dto.getNom();
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
    
    public String getNom()
    {
        return nom;
    }
    
    public void setNom(String newNom)
    {
        nom = newNom;
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
     
    public abstract MeubleDTO ToDto();
}
