/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jacky
 */
public class GestionnaireCheminFil {
    
    private Fil fil;
    private Membrane menbrane;
    private TraceurFil traceur;
    private int indexIntersectionSelectionnee;
    
    public GestionnaireCheminFil(Fil fil, Membrane menbrane, TraceurFil traceur) {
        this.fil = fil;
        this.menbrane = menbrane;
        this.traceur = traceur;
        this.indexIntersectionSelectionnee = -1;
    }
    
   // Sélectionne une intersection du chemin du fil
    public boolean selectionnerIntersection(Point position) {
        ArrayList<Point> chemin = fil.getChemin();
        double distanceMin = Double.MAX_VALUE;
        int indexPlusProche = -1;
        
        // Trouver le point du chemin le plus proche de la position cliquée
        for (int i = 0; i < chemin.size(); i++) {
            Point point = chemin.get(i);
            double distance = position.distance(point);
            
            if (distance < distanceMin && distance < 10) { // Tolérance de 10 pixels
                distanceMin = distance;
                indexPlusProche = i;
            }
        }
        
        if (indexPlusProche != -1) {
            indexIntersectionSelectionnee = indexPlusProche;
            return true;
        }
        
        return false;
    }
    
    // Obtient l'intersection actuellement sélectionnée
    public Point getIntersectionSelectionnee() {
        if (indexIntersectionSelectionnee >= 0 && indexIntersectionSelectionnee < fil.getChemin().size()) {
            return fil.getChemin().get(indexIntersectionSelectionnee);
        }
        return null;
    }
    
    //Obtient les directions disponibles à partir de l'intersection sélectionnée

    public ArrayList<DirectionDisponible> obtenirDirectionsDisponibles() {
        ArrayList<DirectionDisponible> directions = new ArrayList<>();
        
        if (indexIntersectionSelectionnee < 0 || indexIntersectionSelectionnee >= fil.getChemin().size()) {
            return directions;
        }
        
        Point pointActuel = fil.getChemin().get(indexIntersectionSelectionnee);
        ArrayList<Point> intersections = menbrane.ObtenirIntersections();
        int espacement = menbrane.getEspacement();
        
        // Vérifier les 4 directions cardinales et les diagonales si nécessaire
        int[][] deplacements = {
            {espacement, 0},      // Droite
            {-espacement, 0},     // Gauche
            {0, espacement},      // Bas
            {0, -espacement},     // Haut
            {espacement, espacement},   // Diagonale bas-droite
            {-espacement, espacement},  // Diagonale bas-gauche
            {espacement, -espacement},  // Diagonale haut-droite
            {-espacement, -espacement}  // Diagonale haut-gauche
        };
        
        for (int[] deplacement : deplacements) {
            Point nouveauPoint = new Point(pointActuel.x + deplacement[0], pointActuel.y + deplacement[1]);
            
            // Vérifier si le point est valide
            if (estPointDisponible(nouveauPoint, intersections)) {
                String nomDirection = determinerNomDirection(deplacement);
                directions.add(new DirectionDisponible(nouveauPoint, nomDirection));
            }
        }
        
        return directions;
    }
    
    //Recalcule le chemin du fil à partir de l'intersection sélectionnée vers une nouvelle direction
 
    public Fil recalculerCheminVers(Point nouvelleDirection, int longueurMax, int distanceMaxLigne) {
        if (indexIntersectionSelectionnee < 0) {
            return fil;
        }
        
        // Créer un nouveau fil avec le chemin conservé jusqu'au point sélectionné
        Point pointDepart = fil.getPointDepart();
        Fil nouveauFil = new Fil(pointDepart, longueurMax);
        
        // Copier le chemin jusqu'au point sélectionné (exclu)
        ArrayList<Point> cheminActuel = fil.getChemin();
        for (int i = 1; i < indexIntersectionSelectionnee && i < cheminActuel.size(); i++) {
            nouveauFil.ajouterSegment(cheminActuel.get(i));
        }
        
        // Ajouter le nouveau segment vers la nouvelle direction
        Point pointActuel = cheminActuel.get(indexIntersectionSelectionnee);
        if (nouveauFil.getChemin().isEmpty() || !nouveauFil.getChemin().get(nouveauFil.getChemin().size() - 1).equals(pointActuel)) {
            nouveauFil.ajouterSegment(pointActuel);
        }
        
        nouveauFil.ajouterSegment(nouvelleDirection);
     
        return nouveauFil;
    }
    
    //Vérifie si un point est disponible pour le fil
    private boolean estPointDisponible(Point point, ArrayList<Point> intersections) {
        // Vérifier que le point fait partie de la grille
        boolean estDansGrille = false;
        for (Point intersection : intersections) {
            if (intersection.equals(point)) {
                estDansGrille = true;
                break;
            }
        }
        
        if (!estDansGrille) {
            return false;
        }
        
        // Vérifier que le point est valide selon les contraintes
        return traceur.estPointValide(point);
    }
    
    //Détermine le nom de la direction en fonction du déplacement
    private String determinerNomDirection(int[] deplacement) {
        int dx = deplacement[0];
        int dy = deplacement[1];
        
        if (dx > 0 && dy == 0) return "Droite";
        if (dx < 0 && dy == 0) return "Gauche";
        if (dx == 0 && dy > 0) return "Bas";
        if (dx == 0 && dy < 0) return "Haut";
        if (dx > 0 && dy > 0) return "Bas-Droite";
        if (dx < 0 && dy > 0) return "Bas-Gauche";
        if (dx > 0 && dy < 0) return "Haut-Droite";
        if (dx < 0 && dy < 0) return "Haut-Gauche";
        
        return "Inconnue";
    }
    
    // Désélectionne l'intersection actuelle
    public void deselectionner() {
        indexIntersectionSelectionnee = -1;
    }
    
    //Classe interne représentant une direction disponible
    public static class DirectionDisponible {
        private Point point;
        private String nom;
        
        public DirectionDisponible(Point point, String nom) {
            this.point = point;
            this.nom = nom;
        }
        
        public Point getPoint() {
            return new Point(point);
        }
        
        public String getNom() {
            return nom;
        }
        
        @Override
        public String toString() {
            return nom + " (" + point.x + ", " + point.y + ")";
        }
    }
}


