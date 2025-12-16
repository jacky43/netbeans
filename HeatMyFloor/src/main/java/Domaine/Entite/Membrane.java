package Domaine.Entite;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;

public class Membrane implements Serializable {
    private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    private Polygon formePiece; // Utilisé pour découper la grille sur une pièce irrégulière
    private int offsetX;
    private int offsetY;
    
    public Membrane(int largeur, int longueur, int espacement, int marge, Polygon formePiece){
       this.largeurPiece = largeur;
       this.longueurPiece = longueur;
       this.espacement = espacement;
       this.margeContour = marge;
       this.formePiece = formePiece;
        this.offsetX = 0;
        this.offsetY = 0;
    }
    
    public ArrayList<Point> ObtenirIntersections(){

        ArrayList<Point> intersections = new ArrayList<>();

        for(int x = margeContour; x <= largeurPiece - margeContour; x += espacement ){
            for(int y = margeContour; y <= longueurPiece - margeContour; y += espacement){
                Point pointOriginal = new Point(x + offsetX, y + offsetY);
                if(estIntersectionValide(pointOriginal)){
                    intersections.add(pointOriginal);
                }
            }
        }
        return intersections;
    }

    public boolean estIntersectionValide(Point point){
        // Filtre rectangle/marge par défaut
        if(point.x < margeContour || point.x > largeurPiece - margeContour ||
           point.y < margeContour || point.y > longueurPiece - margeContour){
            return false;
        }

        if(formePiece == null){
            return true;
        }

        // Le point doit être dans le polygone
        if(!formePiece.contains(point)){
            return false;
        }

        // Respecter la marge en se tenant à distance du bord du polygone
        double distanceBord = distanceMinBordPolygone(formePiece, point);
        return distanceBord >= margeContour;
    }

    private double distanceMinBordPolygone(Polygon poly, Point p){
        double min = Double.MAX_VALUE;
        for(int i = 0; i < poly.npoints; i++){
            int j = (i + 1) % poly.npoints;
            Point a = new Point(poly.xpoints[i], poly.ypoints[i]);
            Point b = new Point(poly.xpoints[j], poly.ypoints[j]);
            min = Math.min(min, distancePointSegment(p, a, b));
        }
        return min;
    }

    // Distance d'un point à un segment (copie légère de Piece)
    private double distancePointSegment(Point point, Point segmentDebut, Point segmentFin){
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;

        double longueurSegment = dx * dx + dy * dy;
        if(longueurSegment == 0)
            return point.distance(segmentDebut);

        double d = ((point.x - segmentDebut.x) * dx + (point.y - segmentDebut.y) * dy ) / longueurSegment;
        d = Math.max(0, Math.min(1, d));

        double projX = segmentDebut.x + d * dx;
        double projY = segmentDebut.y + d * dy;

        return point.distance(projX, projY);
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

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffset(int dx, int dy) {
        this.offsetX = dx;
        this.offsetY = dy;
    }
}
