
package Domaine.Entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Membrane implements Serializable {
    private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    private HashMap<String, Point> intersectionsTranslates; // Stocke les intersections déplacées
    
    public Membrane(int largeur, int longueur, int espacement, int marge){
       this.largeurPiece = largeur;
       this.longueurPiece = longueur;
       this.espacement = espacement;
       this.margeContour = marge;
       this.intersectionsTranslates = new HashMap<>();
    }
    
    public ArrayList<Point> ObtenirIntersections(){
        
        ArrayList<Point> intersections = new ArrayList<>();
        
        for(int x = margeContour; x <= largeurPiece - margeContour; x += espacement ){
            for(int y = margeContour; y <= longueurPiece - margeContour; y += espacement){
                Point pointOriginal = new Point(x, y);
                String cle = genererCle(pointOriginal);
                
                // Vérifier si ce point a été translaté
                if (intersectionsTranslates.containsKey(cle)) {
                    intersections.add(new Point(intersectionsTranslates.get(cle)));
                } else {
                    intersections.add(pointOriginal);
                }
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
}
