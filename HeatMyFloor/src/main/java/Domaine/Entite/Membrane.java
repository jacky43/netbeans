package Domaine.Entite;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Membrane implements Serializable {
    private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    
    public Membrane(int largeur, int longueur, int espacement, int marge){
       this.largeurPiece = largeur;
       this.longueurPiece = longueur;
       this.espacement = espacement;
       this.margeContour = marge;
    }
    
    public ArrayList<Point> ObtenirIntersections(){

        ArrayList<Point> intersections = new ArrayList<>();

        for(int x = margeContour; x <= largeurPiece - margeContour; x += espacement ){
            for(int y = margeContour; y <= longueurPiece - margeContour; y += espacement){
                Point pointOriginal = new Point(x, y);
                intersections.add(pointOriginal);
            }
        }
        return intersections;
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
