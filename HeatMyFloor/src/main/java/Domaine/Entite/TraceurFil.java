/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author jacky
 */
public class TraceurFil {
     private Menbrane menbrane;
    private ArrayList<Meuble> meubles;
    private ArrayList<ElementChauffant> elementsChauffants;
    private int distanceSecurite = 118; // 3 pouces
    
    public TraceurFil(Menbrane menbrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements) {
        this.menbrane = menbrane;
        this.meubles = meubles;
        this.elementsChauffants = new ArrayList<>();
        if(elements != null){
            for(ElementSelectionnable el : elements){
                if (el instanceof ElementChauffant elementChauffant){
                    elementsChauffants.add(elementChauffant);
                }
            }
        }
    }
    
    // Vérifie si un point est valide (pas trop proche d'un meuble ou élément chauffant)
    public boolean estPointValide(Point point) {
        
        if(!respecterDistanceMur(point)){return false;}
        // Vérifier distance avec meubles
        for (Meuble meuble : meubles) {
            if (estTropProcheMeuble(point, meuble)) {
                return false;
            }
        }
        
        // Vérifier distance avec éléments chauffants
        for (ElementChauffant element : elementsChauffants) {
            if (estTropProcheElement(point, element)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean respecterDistanceMur(Point point){
        int marge = distanceSecurite;
        return point.x >= marge && point.x <= menbrane.getLargeurPiece() - marge &&
               point.y >= marge && point.y <= menbrane.getLongueurPiece() - marge; 
    }
    
    private boolean estTropProcheMeuble(Point point, Meuble meuble) {
        Point posMeuble = meuble.getPosition();
        int minX = posMeuble.x - distanceSecurite;
        int maxX = posMeuble.x + meuble.getLargeur() + distanceSecurite;
        int minY = posMeuble.y - meuble.getLongueur() - distanceSecurite;
        int maxY = posMeuble.y + distanceSecurite;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    private boolean estTropProcheElement(Point point, ElementChauffant element) {
        Point posElement = element.getPosition();
        int minX = posElement.x - distanceSecurite;
        int maxX = posElement.x + element.getLargeur() + distanceSecurite;
        int minY = posElement.y - element.getLongueur() - distanceSecurite;
        int maxY = posElement.y + distanceSecurite;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    // Algorithme de tracé automatique (serpentin)
    public Fil tracerFilAutomatique(Point thermostat, int longueurMax) {
        Fil fil = new Fil(thermostat, longueurMax);
        ArrayList<Point> intersections = menbrane.ObtenirIntersections();
        
        // Filtrer les intersections valides
        ArrayList<Point> intersectionsValides = new ArrayList<>();
        for (Point inter : intersections) {
            if (estPointValide(inter)) {
                intersectionsValides.add(inter);
            }
        }
        
        // Trier par ligne (y) puis par colonne (x) pour créer un serpentin
        Collections.sort(intersectionsValides, (Point p1, Point p2) -> {
            if (p1.y != p2.y) {
                return Integer.compare(p1.y, p2.y);
            }
            return Integer.compare(p1.x, p2.x);
        });
        
        // Tracer en serpentin
        Point pointActuel = thermostat;
        int ligneActuelle = -1;
        boolean directionDroite = true;
        
        ArrayList<Point> ligneCourante = new ArrayList<>();
        
        for (Point inter : intersectionsValides) {
            if (ligneActuelle == -1 || inter.y != ligneActuelle) {
                // Traiter la ligne précédente
                if (!ligneCourante.isEmpty()) {
                    if (!directionDroite) {
                        Collections.reverse(ligneCourante);
                    }
                    
                    for (Point p : ligneCourante) {
                        if (!fil.ajouterSegment(p)) {
                            return fil; // Longueur maximale atteinte
                        }
                        pointActuel = p;
                    }
                    
                    directionDroite = !directionDroite;
                }
                
                // Nouvelle ligne
                ligneActuelle = inter.y;
                ligneCourante = new ArrayList<>();
            }
            
            ligneCourante.add(inter);
        }
        
        // Traiter la dernière ligne
        if (!ligneCourante.isEmpty()) {
            if (!directionDroite) {
                Collections.reverse(ligneCourante);
            }
            
            for (Point p : ligneCourante) {
                if (!fil.ajouterSegment(p)) {
                    break;
                }
            }
        }
        
        return fil;
    }
    
    public int getDistanceSecurite() {
        return distanceSecurite;
    }
    
    public void setDistanceSecurite(int distance) {
        this.distanceSecurite = distance;
    }

}
