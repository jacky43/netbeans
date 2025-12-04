/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.DTO;

import java.awt.Point;
import java.util.UUID;

/**
 *
 * @author jacky
 */
public class ThermostatDTO implements ElementSelectionnableDTO{
    
    private Point position;
    private int longueur;
    private int largeur;
    private boolean estSelectionne;
    private UUID id;
    
      public ThermostatDTO(Point p_position,
            int p_longueur,
            int p_largeur) {
        //getion erreur plus tard 
        position = new Point(p_position);
        longueur = p_longueur;
        largeur = p_largeur;
        estSelectionne = false;
        id = null;
    }

    @Override
    public Point getPosition() {
        return new Point(position);
    }

    @Override
    public int getLongueur() {
        return longueur;
    }

    @Override
    public int getLargeur() {
        return largeur;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean estSelectionne() {
        return estSelectionne;
    }
    
}
