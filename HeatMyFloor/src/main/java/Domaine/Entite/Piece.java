package Domaine.Entite;


import Domaine.DTO.MeubleDTO;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Piece {
    
    private Polygon forme;
  
    private ArrayList<ElementSelectionnable> elements;
    
    public Piece()
    {
       elements = new ArrayList<>();
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
        ArrayList<Meuble> meubles = new ArrayList<>();
        for (ElementSelectionnable element : elements)
            if (element instanceof Meuble meuble)
                meubles.add(meuble);
        
        return meubles;
    }
    
    public void AjouterMeuble(MeubleDTO dto)
    {
        if(dto.estAvecDrain())
            elements.add(new MeubleAvecDrain(dto));
        else
            elements.add(new MeubleSansDrain(dto));
    }
 
    public ElementSelectionnable SelectionnerElement(Point p_position)
    {
        ElementSelectionnable elementSelectionne = null;
        for (ElementSelectionnable element : elements)
        {
            if (contientPoint(element, p_position))
            {
                elementSelectionne = element;
                break;
            }
        }
        if(elementSelectionne != null){
            for (ElementSelectionnable element: elements)
                element.setSelectionne(element == elementSelectionne);
        }
        else
        {
            for (ElementSelectionnable element: elements)
                element.setSelectionne(false);
        }
        return elementSelectionne;
    }
     
    public boolean SupprimerMeubleSelectionne()
    {
        ElementSelectionnable elementSelectionne = ObtenirElementSelectionne();
        if(elementSelectionne instanceof Meuble meuble)
            return SupprimerMeuble(meuble.getId());
        
        return false;
    }    
    
    public boolean SupprimerMeuble(UUID id)
    {   
        for (Iterator<ElementSelectionnable> iterator = elements.iterator(); iterator.hasNext();)
        {
            ElementSelectionnable element = iterator.next();
            if(element instanceof Meuble meuble && meuble.getId().equals(id)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public ElementSelectionnable ObtenirElementSelectionne()
    {
        for (ElementSelectionnable element: elements)
            if(element.estSelectionne())
                return element;
        return null;
    }
    
    public boolean ModifierMeubleSelectionne(Point nouvellePosition, Integer nouvelleLargeur, Integer nouvelleLongueur)
    {
        ElementSelectionnable selection = ObtenirElementSelectionne();
        if(selection == null)
            return false;
        if(nouvellePosition != null)
            selection.setPosition(nouvellePosition);
        if(nouvelleLargeur != null)
            selection.setLargeur(nouvelleLargeur);
        if(nouvelleLongueur != null)
            selection.setLongueur(nouvelleLongueur);
        return true;
    }
    
    private boolean contientPoint(ElementSelectionnable element, Point position)
    {
        int minX = element.getPosition().x;
        int maxX = element.getPosition().x + element.getLargeur();
        int minY = element.getPosition().y;
        int maxY = element.getPosition().y + element.getLongueur();
        return position.x >= minX && position.x <= maxX &&
               position.y >= minY && position.y <= maxY; 
    }
    
    public Point ObtenirOrigine()
    {
        Point position = getPositionPiece();
        return new Point(position.x, position.y + (int)forme.getBounds().getHeight());
    }
}
