package Domaine.Entite;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


public class Fil implements Serializable {
    private Point pointDepart;
    private ArrayList<Point> chemin;
    private int longueurMaximale;
    private int longueurActuelle;

    
    public Fil(Point thermostat, int longueurMax){
        this.pointDepart = new Point(thermostat);
        this.longueurMaximale = longueurMax;
        this.chemin = new ArrayList<>();
        chemin.add(new Point(thermostat));
        this.longueurActuelle = 0;
    }
    
    public boolean ajouterSegment(Point prochainPoint){
        if (chemin.isEmpty()) return false;
        
        Point dernierPoint =  chemin.getLast();
        int distance = calculerDistance(dernierPoint, prochainPoint);
        
        if(longueurActuelle + distance <= longueurMaximale){
                chemin.add(new Point(prochainPoint));
                longueurActuelle += distance;
                return true;
        }
        return false;
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
