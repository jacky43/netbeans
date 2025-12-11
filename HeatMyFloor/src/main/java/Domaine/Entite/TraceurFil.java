/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author jacky
 */
public class TraceurFil {
     private Menbrane menbrane;
    private ArrayList<Meuble> meubles;
    private ArrayList<ElementChauffant> elementsChauffants;
    private int distanceSecurite = 3; // 3 pouces  
    private static final int DISTANCE_MIN_DRAIN = 6; // 6 pouces minimum des drains
    private static final int DISTANCE_MIN_TOILETTE = 6; // 6 pouces minimum du drain toilette
    private int distanceMaxLigneDroite; // Distance max par segment de ligne droite
    
    public TraceurFil(Menbrane menbrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements) {
        this(menbrane, meubles, elements, Integer.MAX_VALUE);
    }
    
    public TraceurFil(Menbrane menbrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements, int distanceMaxLigne) {
        this.menbrane = menbrane;
        this.meubles = meubles;
        this.elementsChauffants = new ArrayList<>();
        this.distanceMaxLigneDroite = distanceMaxLigne;
        if(elements != null){
            for(ElementSelectionnable el : elements){
                if (el instanceof ElementChauffant elementChauffant){
                    elementsChauffants.add(elementChauffant);
                }
            }
        }
    }

    
    // Vérifie si un point est valide (pas trop proche d'un meuble ou élément chauffant)
    public boolean estPointValide(Point point) {
        
        if(!respecterDistanceMur(point)){return false;}
        // Vérifier distance avec meubles
        for (Meuble meuble : meubles) {
            if (estTropProcheMeuble(point, meuble)) {
                return false;
            }
            // Vérifier distance de 6" avec les drains
            if (meuble instanceof MeubleAvecDrain meubleAvecDrain) {
                if (estTropProcheDrain(point, meubleAvecDrain)) {
                    return false;
                }
            }
        }
        
        // Vérifier distance avec éléments chauffants
        for (ElementChauffant element : elementsChauffants) {
            if (estTropProcheElement(point, element)) {
                return false;
            }
        }     
        return true;
    }
    
    private boolean respecterDistanceMur(Point point){
        int marge = distanceSecurite;
        return point.x >= marge && point.x <= menbrane.getLargeurPiece() - marge &&
               point.y >= marge && point.y <= menbrane.getLongueurPiece() - marge; 
    }
    
    private boolean estTropProcheMeuble(Point point, Meuble meuble) {
        Point posMeuble = meuble.getPosition();
        int minX = posMeuble.x - distanceSecurite;
        int maxX = posMeuble.x + meuble.getLargeur() + distanceSecurite;
        int minY = posMeuble.y - meuble.getLongueur() - distanceSecurite;
        int maxY = posMeuble.y + distanceSecurite;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    private boolean estTropProcheElement(Point point, ElementChauffant element) {
        Point posElement = element.getPosition();
        int minX = posElement.x - distanceSecurite;
        int maxX = posElement.x + element.getLargeur() + distanceSecurite;
        int minY = posElement.y - element.getLongueur() - distanceSecurite;
        int maxY = posElement.y + distanceSecurite;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    private boolean estTropProcheDrain(Point point, MeubleAvecDrain meuble) {
        Point centreDrain = meuble.getCentreDrain();
        if (centreDrain == null) {
            return false;
        }
        
        // Calculer position absolue du drain dans la pièce
        Point posMeuble = meuble.getPosition();
        int xDrainAbsolu = posMeuble.x + centreDrain.x;
        int yDrainAbsolu = posMeuble.y - meuble.getLongueur() + centreDrain.y;
        
        // Distance minimale selon le type de meuble
        int distanceMin = "TOILETTE".equals(meuble.getNom()) ? DISTANCE_MIN_TOILETTE : DISTANCE_MIN_DRAIN;
        
        // Calculer distance euclidienne
        double distance = Math.sqrt(
            Math.pow(point.x - xDrainAbsolu, 2) + 
            Math.pow(point.y - yDrainAbsolu, 2)
        );
        
        return distance < distanceMin;
    }
    
    // Algorithme de tracé automatique (serpentin)
    public Fil tracerFilAutomatique(Point thermostat, int longueurMax) {
        Fil fil = new Fil(thermostat, longueurMax);
        ArrayList<Point> intersections = menbrane.ObtenirIntersections();
        
        // Filtrer les intersections valides
        ArrayList<Point> intersectionsValides = new ArrayList<>();
        for (Point inter : intersections) {
            if (estPointValide(inter)) {
                intersectionsValides.add(inter);
            }
        }
        
        if(intersectionsValides.isEmpty()){
            return fil;
        }
        //trouver le point de la grille le plus proche du thermostat
        Point pointDepart = trouverPointLePlusProche(thermostat, intersectionsValides);
        
        //Connecter le thermostat au premier point 
        if(pointDepart != null && estSegmentValide(thermostat, pointDepart)){
            fil.ajouterSegment(pointDepart);
            intersectionsValides.remove(pointDepart);
        }
        
        // Trier par ligne (y) puis par colonne (x) pour créer un serpentin
        Collections.sort(intersectionsValides, (Point p1, Point p2) -> {
            if (p1.y != p2.y) {
                return Integer.compare(p1.y, p2.y);
            }
            return Integer.compare(p1.x, p2.x);
        });
        
        // Tracer en serpentin
        //Point pointActuel = thermostat;
        int ligneActuelle = -1;
        boolean directionDroite = true;
        
        ArrayList<Point> ligneCourante = new ArrayList<>();
        
        for (Point inter : intersectionsValides) {
            if (ligneActuelle == -1 || inter.y != ligneActuelle) {
                // Traiter la ligne précédente
                if (!ligneCourante.isEmpty()) {
                    if (!directionDroite) {
                        Collections.reverse(ligneCourante);
                    }
                    
                    if(!ajouterLigneAvecContrainte(fil, ligneCourante)){
                        return fil;
                    }
                    
                    directionDroite = !directionDroite;
                }
                
                // Nouvelle ligne
                ligneActuelle = inter.y;
                ligneCourante = new ArrayList<>();
            }
            
            ligneCourante.add(inter);
        }
        
        // Traiter la dernière ligne
        if (!ligneCourante.isEmpty()) {
            if (!directionDroite) {
                Collections.reverse(ligneCourante);
            }
            ajouterLigneAvecContrainte(fil, ligneCourante);
        }
        
        return fil;
    }
    
    private Point trouverPointLePlusProche(Point reference, ArrayList<Point> points){
        if(points.isEmpty()){return null;}
        
        Point plusProche = points.get(0);
        int distanceMin = calculerDistance(reference, plusProche);
        
        for(Point p: points){
            int distance = calculerDistance(reference, p);
            if(distance < distanceMin){
                distanceMin = distance;
                plusProche = p;
            }
        }
        return plusProche;
    }
    
    private boolean ajouterLigneAvecContrainte(Fil fil, ArrayList<Point> ligne) {
        if (ligne.isEmpty()) {
            return true;
        }
        
        Point dernierPoint = fil.getChemin().get(fil.getChemin().size() - 1);
        
        for (Point p : ligne) {
            // Vérifier si le segment respecte la distance max
            int distance = calculerDistance(dernierPoint, p);
            
            if (distance > distanceMaxLigneDroite) {
                // Segment trop long, arrêter ici
                continue;
            }
            //verifie que le segment entre le dernier point et p ne traverse aucun obstacle
            if (!estSegmentValide(dernierPoint,p)) {
                continue; // Longueur maximale atteinte
            }
            
            if (!fil.ajouterSegment(p)) {
                return false; // Longueur maximale atteinte
            }
            dernierPoint = p;
        }
        
        return true;
    }
    
    private boolean estSegmentValide(Point p1, Point p2){
        
        if(meubles.isEmpty() && elementsChauffants.isEmpty()){
            return true;
        }
        
          if(!estPointLibreObstacles(p2)){
            return false;
        }
        //verifier plusiers point intermediaire
        int steps = Math.max(Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
        if(steps <= 6){
            return true;
        }
        
        int nbEchantillons = steps / 3;
        for(int i = 0; i <= nbEchantillons; i++){
            double ratio = (double) i / steps;
            int x = (int) Math.round(p1.x + ratio * (p2.x - p1.x));
            int y = (int) Math.round(p1.y + ratio * (p2.y - p1.y));
            
            if(!estPointLibreObstacles(new Point(x, y))){
                return false;
            }
        }
        return true;
    }
    
     private boolean estPointLibreObstacles(Point point){
         for(Meuble meuble : meubles){
             if(estTropProcheMeuble(point, meuble)){
                 return false;
             }
             
             if(meuble instanceof MeubleAvecDrain meubleAvecDrain){
                 if(estTropProcheMeuble(point, meubleAvecDrain)){
                 return false;
                }
             }
         }
          for(ElementChauffant element : elementsChauffants){
             if(estTropProcheElement(point, element)){
                 return false;
             }
         }
          return true;
    }
    
    private int calculerDistance(Point p1, Point p2) {
        return (int) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    
    public int getDistanceSecurite() {
        return distanceSecurite;
    }
    
    public void setDistanceSecurite(int distance) {
        this.distanceSecurite = distance;
    }

}
