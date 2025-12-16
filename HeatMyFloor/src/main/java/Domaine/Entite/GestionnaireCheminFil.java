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
        
        // Uniquement les directions cardinales (pas de diagonales)
        int[][] deplacements = {
            {espacement, 0},      // Droite
            {-espacement, 0},     // Gauche
            {0, espacement},      // Bas
            {0, -espacement}      // Haut
        };
        
        for (int[] deplacement : deplacements) {
            Point nouveauPoint = new Point(pointActuel.x + deplacement[0], pointActuel.y + deplacement[1]);
            
            // Vérifier si le point est valide, joignable sans obstacle et non déjà utilisé
            if (estPointDisponible(nouveauPoint, intersections)
                && traceur.estSegmentValide(pointActuel, nouveauPoint)
                && !pointDejaDansChemin(nouveauPoint)) {
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
        
        // Ajouter le nouveau segment vers la nouvelle direction (orthogonal uniquement)
        Point pointActuel = cheminActuel.get(indexIntersectionSelectionnee);
        if (nouvelleDirection == null || !(pointActuel.x == nouvelleDirection.x || pointActuel.y == nouvelleDirection.y)) {
            System.out.println("Nouvelle direction non orthogonale, refusée");
            return fil;
        }

        if (pointDejaDansChemin(nouvelleDirection)) {
            System.out.println("Point déjà utilisé dans le chemin, refusé");
            return fil;
        }

        if (!traceur.estSegmentValide(pointActuel, nouvelleDirection)) {
            System.out.println("Segment bloqué par un obstacle, refusé");
            return fil;
        }

        if (verifierCroisementAvecChemin(pointActuel, nouvelleDirection, nouveauFil.getChemin())) {
            System.out.println("Croisement détecté, refus de la direction");
            return fil;
        }

        if (nouveauFil.getChemin().isEmpty() || !nouveauFil.getChemin().get(nouveauFil.getChemin().size() - 1).equals(pointActuel)) {
            nouveauFil.ajouterSegment(pointActuel);
        }

        if (!nouveauFil.ajouterSegment(nouvelleDirection)) {
            System.out.println("Impossible d'ajouter le segment vers la nouvelle direction");
            return fil; // Retourner l'ancien fil si échec
        }
        
        // CORRECTION CRITIQUE: Continuer la génération automatique après le nouveau segment
        nouveauFil = traceur.continuerTracageDepuis(nouveauFil, nouvelleDirection, menbrane, distanceMaxLigne);
     
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

    // Vérifie si le point existe déjà dans le chemin courant
    private boolean pointDejaDansChemin(Point p) {
        for (Point existant : fil.getChemin()) {
            if (existant.equals(p)) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si le segment croise le chemin existant (copié de TraceurFil)
    private boolean verifierCroisementAvecChemin(Point nouveauDebut, Point nouvelleFin, ArrayList<Point> chemin) {
        if (chemin.size() < 2) {
            return false;
        }
        for (int i = 0; i < chemin.size() - 2; i++) {
            Point segmentDebut = chemin.get(i);
            Point segmentFin = chemin.get(i + 1);
            if (segmentsSeCroisent(nouveauDebut, nouvelleFin, segmentDebut, segmentFin)) {
                return true;
            }
        }
        return false;
    }

    private boolean segmentsSeCroisent(Point p1, Point p2, Point p3, Point p4) {
        if (p1.equals(p3) || p1.equals(p4) || p2.equals(p3) || p2.equals(p4)) {
            return false;
        }
        int o1 = orientation(p1, p2, p3);
        int o2 = orientation(p1, p2, p4);
        int o3 = orientation(p3, p4, p1);
        int o4 = orientation(p3, p4, p2);
        return o1 != o2 && o3 != o4;
    }

    private int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
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


