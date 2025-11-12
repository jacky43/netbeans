package Domaine.Entite;

import java.awt.Point;
import java.util.UUID;

public interface ElementSelectionnable {
    UUID getId();
    Point getPosition();
    void setPosition(Point newPosition);
    int getLargeur();
    void setLargeur(int newLargeur);
    int getLongueur();
    void setLongueur(int newLongeur);
    boolean estSelectionne();
    void setSelectionne(boolean selectionne);
    void ChangerStatut();
}
