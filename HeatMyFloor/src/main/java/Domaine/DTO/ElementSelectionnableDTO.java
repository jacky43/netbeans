
package Domaine.DTO;

import java.awt.Point;
import java.util.UUID;


public interface ElementSelectionnableDTO {
    Point getPosition();
    int getLongueur();
    int getLargeur();
    UUID getId();
    boolean estSelectionne();
}
