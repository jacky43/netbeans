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
    private boolean estSelectionne;
    private UUID id;
    
    public MeubleDTO(Point p_position, 
                     int p_longueur, 
                     int p_largeur,
                     String p_nom)
    {
        //getion erreur plus tard 
        position = new Point(p_position);
        longueur = p_longueur;
        largeur = p_largeur;
        nom = p_nom;
        estSelectionne = false;
        id = null;
    }
        
    public MeubleDTO(Point p_position, 
                     int p_longueur, 
                     int p_largeur, 
                     String p_nom,
                     Point p_centreDrain)
    {
        //getion erreur plus tard 
        position = new Point(p_position);
        longueur = p_longueur;
        largeur = p_largeur;
        nom = p_nom;
        //getion erreur plus tard
        centreDrain = new Point(centreDrain);
        estSelectionne = false;
        id = null;
    }
    
    // TODO : Meuble Sans Drain
    public MeubleDTO(Meuble p_meuble)
    {
        this(new Point(p_meuble.getPosition()),
             p_meuble.getLongueur(),
             p_meuble.getLargeur(),
             p_meuble.getNom());
        estSelectionne = p_meuble.estSelectionne();
        //ajouter une vérification 
        if(p_meuble instanceof MeubleAvecDrain meubleAvecDrain && meubleAvecDrain.getCentreDrain() != null)
                centreDrain = new Point(meubleAvecDrain.getCentreDrain());     
    }
    
    public MeubleDTO(MeubleAvecDrain p_meuble)
    {
        this((Meuble)p_meuble);
        if (p_meuble.getCentreDrain() != null)
            centreDrain = new Point(p_meuble.getCentreDrain());
    }
    
    public MeubleDTO(MeubleSansDrain p_meuble)
    {
        this((Meuble)p_meuble);
    }
    
    public boolean estAvecDrain()
    {
        return centreDrain != null;
    }
    
    public Point getPosition()
    {
        return new Point(position);
    }
      
    public int getLongueur()
    {
        return longueur;
    }
    
    public int getLargeur()
    {
        return largeur;
    }   

    public Point getCentreDrain()
    {
        return centreDrain == null ? null : new Point(centreDrain);
    }
     
    public boolean estSelectionne()
    {
        return estSelectionne;
    }
      
    public String getNom()
    {
        return nom;
    }
    
     public UUID getId()
    {
        return id;
    }
 
}
