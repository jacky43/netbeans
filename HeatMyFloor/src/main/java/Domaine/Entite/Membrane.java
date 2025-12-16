
package Domaine.Entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Membrane implements Cloneable, Serializable {
    private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    private HashMap<String, Point> intersectionsTranslates; // Stocke les intersections déplacées
    private ArrayList<Point> contourPiece; // Polygone optionnel décrivant la forme réelle de la pièce
    
     public Membrane(int largeur, int longueur, int espacement, int marge){
         this.largeurPiece = largeur;
         this.longueurPiece = longueur;
         this.espacement = espacement;
         this.margeContour = marge;
         this.intersectionsTranslates = new HashMap<>();
         this.contourPiece = new ArrayList<>();
    }
    
    public ArrayList<Point> ObtenirIntersections(){
        
        ArrayList<Point> intersections = new ArrayList<>();
        
        for(int x = margeContour; x <= largeurPiece - margeContour; x += espacement ){
            for(int y = margeContour; y <= longueurPiece - margeContour; y += espacement){
                Point pointOriginal = new Point(x, y);
                String cle = genererCle(pointOriginal);
                
                // Vérifier si ce point a été translaté
                Point cible = intersectionsTranslates.containsKey(cle) ? new Point(intersectionsTranslates.get(cle))
                                                                     : pointOriginal;

                // Filtrer selon la forme réelle de la pièce si elle est définie
                if (!estDansPiece(cible, 0)) {
                    continue;
                }

                intersections.add(cible);
            }
        }
        return intersections;
    }
    
    //Translate une intersection spécifique du graphe
    public boolean translaterIntersection(Point pointOriginal, Point nouvellePosition) {
        // Vérifier que le point original est sur la grille
        if (!estSurGrille(pointOriginal)) {
            return false;
        }
        
        // Vérifier que la nouvelle position est dans les limites de la pièce
        if (nouvellePosition.x < 0 || nouvellePosition.x > largeurPiece ||
            nouvellePosition.y < 0 || nouvellePosition.y > longueurPiece) {
            return false;
        }
        
        String cle = genererCle(pointOriginal);
        intersectionsTranslates.put(cle, new Point(nouvellePosition));
        return true;
    }
    
    //Réinitialise une intersection translatée à sa position d'origine
    public void reinitialiserIntersection(Point pointOriginal) {
        String cle = genererCle(pointOriginal);
        intersectionsTranslates.remove(cle);
    }
    
    //Réinitialise toutes les intersections translatées
    public void reinitialiserToutesIntersections() {
        intersectionsTranslates.clear();
    }
    
    //Vérifie si un point est sur la grille originale   
    private boolean estSurGrille(Point point) {
        if (point.x < margeContour || point.x > largeurPiece - margeContour ||
            point.y < margeContour || point.y > longueurPiece - margeContour) {
            return false;
        }
        
        int deltaX = (point.x - margeContour) % espacement;
        int deltaY = (point.y - margeContour) % espacement;
        
        return deltaX == 0 && deltaY == 0;
    }
    
    /**
     * Génère une clé unique pour un point
     */
    private String genererCle(Point point) {
        return point.x + "," + point.y;
    }
    
    // Trouve le point de grille le plus proche d'une position donnéeui 
    public Point trouverIntersectionLaPlusProche(Point position) {
        int xGrille = Math.round((float)(position.x - margeContour) / espacement) * espacement + margeContour;
        int yGrille = Math.round((float)(position.y - margeContour) / espacement) * espacement + margeContour;
        
        // Limiter aux bornes
        xGrille = Math.max(margeContour, Math.min(xGrille, largeurPiece - margeContour));
        yGrille = Math.max(margeContour, Math.min(yGrille, longueurPiece - margeContour));
        
        return new Point(xGrille, yGrille);
    }

    
    public int getEspacement(){
        return espacement;
    }
    
    public int getMargeContour(){
        return margeContour;
    }
    
    public int getLargeurPiece(){
        return largeurPiece;
    }
    
     public int getLongueurPiece(){
        return longueurPiece;
    }

    /**
     * Définit le polygone de la pièce (points dans l'ordre, polygone simple).
     * Si non défini ou vide, on retombe sur la forme rectangulaire par défaut.
     */
    public void setContourPiece(ArrayList<Point> contour){
        if (contour == null || contour.isEmpty()) {
            this.contourPiece = new ArrayList<>();
        } else {
            this.contourPiece = new ArrayList<>(contour);
        }
    }

    public ArrayList<Point> getContourPiece(){
        return new ArrayList<>(contourPiece);
    }

    /**
     * Vérifie si un point est dans la pièce en respectant une marge de sécurité par rapport aux bords.
     * Pour un polygone, on teste l'inclusion et la distance aux arêtes.
     */
    public boolean estDansPiece(Point p, int marge){
        // Cas rectangulaire par défaut
        if (contourPiece == null || contourPiece.isEmpty()) {
            int minX = margeContour + marge;
            int maxX = largeurPiece - margeContour - marge;
            int minY = margeContour + marge;
            int maxY = longueurPiece - margeContour - marge;
            return p.x >= minX && p.x <= maxX && p.y >= minY && p.y <= maxY;
        }

        // Inclusion dans le polygone
        if (!pointDansPolygone(p, contourPiece)) {
            return false;
        }

        if (marge <= 0) {
            return true;
        }

        // Vérifier la distance minimale aux arêtes du polygone
        double margeDouble = (double) marge;
        for (int i = 0; i < contourPiece.size(); i++) {
            Point a = contourPiece.get(i);
            Point b = contourPiece.get((i + 1) % contourPiece.size());
            double dist = distancePointVersSegment(p, a, b);
            if (dist < margeDouble) {
                return false;
            }
        }
        return true;
    }

    // Test standard rayon-casting pour inclusion dans un polygone simple
    private boolean pointDansPolygone(Point p, ArrayList<Point> poly){
        boolean inside = false;
        int n = poly.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point pi = poly.get(i);
            Point pj = poly.get(j);
            boolean intersect = ((pi.y > p.y) != (pj.y > p.y)) &&
                (p.x < (double)(pj.x - pi.x) * (p.y - pi.y) / (double)(pj.y - pi.y) + pi.x);
            if (intersect) inside = !inside;
        }
        return inside;
    }

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
    
    @Override
    public Membrane clone() {
        Membrane copie = new Membrane(this.largeurPiece, this.longueurPiece, this.espacement, this.margeContour);
        // Clone la HashMap des intersections translatées
        copie.intersectionsTranslates = new HashMap<>(this.intersectionsTranslates);
        copie.contourPiece = new ArrayList<>(this.contourPiece);
        return copie;
    }
}

