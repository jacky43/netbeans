package Domaine.DTO;

import java.awt.Point;
import java.util.ArrayList;


public class FilDTO {
     private Point pointDepart;
    private ArrayList<Point> chemin;
    private int longueurMaximale;
    private int longueurActuelle;

    
    public FilDTO(Point thermostat, int longueurMax, ArrayList<Point> chemins, int longueurAct){
        this.pointDepart = new Point(thermostat);
        this.longueurMaximale = longueurMax;
        this.chemin = chemins;
        this.longueurActuelle = longueurAct;
    }
    
        public ArrayList<Point> getChemin(){
        return new ArrayList<> (chemin);
    }
    
    public Point getPointDepart(){
        return new Point (pointDepart);
    }
    

    
     public int getLongueurMaximale(){
        return longueurMaximale;
    }
     
      public int getLongueurActuelle(){
        return longueurActuelle;
    }
    
    
}
