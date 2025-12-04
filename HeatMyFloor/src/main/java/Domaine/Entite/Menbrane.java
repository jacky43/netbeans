/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.Entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jacky
 */
public class Menbrane {
    private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    
    public Menbrane(int largeur, int longueur, int espacement, int marge){
       this.largeurPiece = largeur;
       this.longueurPiece = longueur;
       this.espacement = espacement;
       this.margeContour = marge;
    }
    
    public ArrayList<Point> ObtenirIntersections(){
        
        ArrayList<Point> intersections = new ArrayList<>();
        
        for(int x = margeContour; x <= largeurPiece - margeContour; x += espacement ){
            for(int y = margeContour; y <= longueurPiece - margeContour; y += espacement){
                intersections.add(new Point(x,y));
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
