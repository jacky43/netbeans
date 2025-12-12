
package Domaine.Entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


public class Fil implements Serializable {
    private Point pointDepart;
    private ArrayList<Point> chemin;
    private int longueurMaximale;
    private int longueurActuelle;
    private static final int DISTANCE_MIN_ENTRE_FILS = 3; // 3 pouces entre fils parallèles
    private static final int LONGUEUR_MAX_SEGMENT = 120; // 10 pieds = 120 pouces

    
    public Fil(Point thermostat, int longueurMax){
        this.pointDepart = new Point(thermostat);
        this.longueurMaximale = longueurMax;
        this.chemin = new ArrayList<>();
        chemin.add(new Point(thermostat));
        this.longueurActuelle = 0;
    }
    
    public boolean ajouterSegment(Point prochainPoint){
        if (chemin.isEmpty()) return false;
        
        Point dernierPoint =  chemin.get(chemin.size() - 1);
        int distance = calculerDistance(dernierPoint, prochainPoint);
        
        if (distance > LONGUEUR_MAX_SEGMENT) {
            return false;
        }
        
        if(longueurActuelle + distance <= longueurMaximale){
                        // Vérifier le non-croisement avant d'ajouter
            if (!verifierCroisement(dernierPoint, prochainPoint)) {
                chemin.add(new Point(prochainPoint));
                longueurActuelle += distance;
                return true;
            }
        }
        return false;
    }

    /**
    * Vérifie si le nouveau segment croise un segment existant du fil
    */
    private boolean verifierCroisement(Point nouveauDebut, Point nouveauFin) {
        if (chemin.size() < 2) return false;
        
        for (int i = 0; i < chemin.size() - 1; i++) {
            Point segmentDebut = chemin.get(i);
            Point segmentFin = chemin.get(i + 1);
            
            // Ne pas vérifier avec le segment précédent immédiat
            if (segmentFin.equals(nouveauDebut)) {
                continue;
            }
            
            if (segmentsSeCroisent(nouveauDebut, nouveauFin, segmentDebut, segmentFin)) {
                return true;
            }
        }
        return false;
    }

     //Vérifie si deux segments se croisent
    private boolean segmentsSeCroisent(Point p1, Point p2, Point p3, Point p4) {
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);
        
        return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
                ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
    }

        
     //Calcule la direction du produit vectoriel
    private double direction(Point p1, Point p2, Point p3) {
        return (p3.x - p1.x) * (p2.y - p1.y) - (p2.x - p1.x) * (p3.y - p1.y);
    }

    //Vérifie si un nouveau segment respecte la distance minimale avec les fils parallèles
    public boolean respecteDistanceEntreFilsParalleles(Point nouveauPoint) {
        if (chemin.size() < 2) return true;
        
        Point dernierPoint = chemin.get(chemin.size() - 1);
        
        for (int i = 0; i < chemin.size() - 2; i++) {
            Point segmentDebut = chemin.get(i);
            Point segmentFin = chemin.get(i + 1);
            
            // Vérifier si les segments sont parallèles
            if (sontParalleles(dernierPoint, nouveauPoint, segmentDebut, segmentFin)) {
                // Calculer la distance entre les segments parallèles
                double distance = distanceEntreSegmentsParalleles(dernierPoint, nouveauPoint, segmentDebut, segmentFin);
                if (distance < DISTANCE_MIN_ENTRE_FILS) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Vérifie si deux segments sont parallèles   
    private boolean sontParalleles(Point p1, Point p2, Point p3, Point p4) {
        double dx1 = p2.x - p1.x;
        double dy1 = p2.y - p1.y;
        double dx2 = p4.x - p3.x;
        double dy2 = p4.y - p3.y;
        
        // Produit vectoriel proche de zéro = segments parallèles
        double produitVectoriel = Math.abs(dx1 * dy2 - dy1 * dx2);
        return produitVectoriel < 0.01; // Tolérance pour parallélisme
    }
    
    //Calcule la distance entre deux segments parallèles
    private double distanceEntreSegmentsParalleles(Point p1, Point p2, Point p3, Point p4) {
        // Distance du point p3 au segment [p1, p2]
        return distancePointVersSegment(p3, p1, p2);
    }
    
    //Calcule la distance d'un point vers un segment
    private double distancePointVersSegment(Point point, Point segmentDebut, Point segmentFin) {
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;
        
        double longueurCarre = dx * dx + dy * dy;
        if (longueurCarre == 0) {
            return point.distance(segmentDebut);
        }
        
        double t = ((point.x - segmentDebut.x) * dx + (point.y - segmentDebut.y) * dy) / longueurCarre;
        t = Math.max(0, Math.min(1, t));
        
        double projX = segmentDebut.x + t * dx;
        double projY = segmentDebut.y + t * dy;
        
        return point.distance(projX, projY);
    }

    
    private int calculerDistance(Point p1, Point p2){
        return (int) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
    
    public ArrayList<Point> getChemin(){
        return new ArrayList<> (chemin);
    }
    
    public Point getPointDepart(){
        return new Point (pointDepart);
    }
    
    public int getLongueurRestante(){
        return longueurMaximale - longueurActuelle;
    }
    
     public int getLongueurMaximale(){
        return longueurMaximale;
    }
     
      public int getLongueurActuelle(){
        return longueurActuelle;
    }
    
}
