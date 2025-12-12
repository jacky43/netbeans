
package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TraceurFil {
     private Membrane membrane;
    private ArrayList<Meuble> meubles;
    private ArrayList<ElementChauffant> elementsChauffants;
    private int distanceSecurite = 3; // 3 pouces  
    private static final int DISTANCE_MIN_DRAIN = 6; // 6 pouces minimum des drains
    private static final int DISTANCE_MIN_TOILETTE = 10; // 6 pouces minimum du drain toilette
    private static final int DISTANCE_MIN_ELEMENT_CHAUFFANT = 8; // 8 pouces minimum des éléments chauffants
    private static final int DISTANCE_MIN_ENTRE_FILS = 3; // 3 pouces entre fils parallèles
    private static final int LONGUEUR_MAX_SEGMENT = 120; 
    private int distanceMaxLigneDroite; // Distance max par segment de ligne droite
    
    public TraceurFil(Membrane membrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements) {
        this(membrane, meubles, elements, Integer.MAX_VALUE);
    }
    
    public TraceurFil(Membrane membrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements, int distanceMaxLigne) {
        this.membrane = membrane;
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
        return point.x >= marge && point.x <= membrane.getLargeurPiece() - marge &&
               point.y >= marge && point.y <= membrane.getLongueurPiece() - marge; 
    }
    
    private boolean estTropProcheMeuble(Point point, Meuble meuble) {
        Point posMeuble = meuble.getPosition();
        int minX = posMeuble.x - DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int maxX = posMeuble.x + meuble.getLargeur() + DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int minY = posMeuble.y - meuble.getLongueur() - DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int maxY = posMeuble.y + DISTANCE_MIN_ELEMENT_CHAUFFANT;
        
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
        
        // Distance minimale selon le type de meuble 10 pouces pour toilette 6 pouces pour autre drain
        int distanceMin = "TOILETTE".equalsIgnoreCase(meuble.getNom()) ? DISTANCE_MIN_TOILETTE : DISTANCE_MIN_DRAIN;
        
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
        ArrayList<Point> intersections = membrane.ObtenirIntersections();
        
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
                    if (ligneActuelle != inter.y) {
                        Point dernierPoint = fil.getChemin().get(fil.getChemin().size() - 1);
                        Point pointTransition = new Point(dernierPoint.x, inter.y);
                        
                        if(!pointTransition.equals(dernierPoint) && estPointValide(pointTransition) &&
                                estSegmentValide(dernierPoint, pointTransition)){
                            fil.ajouterSegment(pointTransition);
                        }
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
        ArrayList<Point> cheminExistant = fil.getChemin();
        
        // Maintenant traiter tous les points de la ligne (sans points intermédiaires)
        for (Point p : ligne) {
            // CRITIQUE: Vérifier si ce point est déjà dans le chemin (éviter superposition)
            boolean pointDejaUtilise = false;
            for (Point pointChemin : cheminExistant) {
                if (pointChemin.equals(p)) {
                    pointDejaUtilise = true;
                    break;
                }
            }
            
            if (pointDejaUtilise) {
                System.out.println("Point déjà utilisé, ignoré: " + p);
                continue; // Ne pas revisiter un point
            }
            
            // Si c'est le premier point et qu'on l'a déjà ajouté, passer
            if (dernierPoint.equals(p)) {
                continue;
            }
            
            // Vérifier si le segment respecte la distance max
            int distance = calculerDistance(dernierPoint, p);
            
            // Vérifier contrainte: segment max 10 pieds (120 pouces)
            if (distance > LONGUEUR_MAX_SEGMENT) {
                System.out.println("Segment trop long (" + distance + " > " + LONGUEUR_MAX_SEGMENT + "), ignoré");
                continue;
            }
            
            if (distance > distanceMaxLigneDroite) {
                System.out.println("Segment dépasse distance max ligne droite, ignoré");
                continue;
            }
            
            // Vérifier que le segment ne traverse aucun obstacle
            if (!estSegmentValide(dernierPoint, p)) {
                System.out.println("Segment invalide (obstacle), ignoré");
                continue;
            }
            
            // Vérifier la distance minimale avec le chemin existant (pas de superposition)
            // On doit vérifier SEGMENT par SEGMENT, pas juste point par point
            boolean tropProcheCheminExistant = false;
            
            // Pour chaque segment existant dans le chemin
            if(cheminExistant.size() >= 4){
                 for (int i = 0; i < cheminExistant.size() - 3; i++) {
                Point segmentDebutExistant = cheminExistant.get(i);
                Point segmentFinExistant = cheminExistant.get(i + 1);
                
                // Calculer la distance entre le nouveau segment [dernierPoint, p] 
                // et le segment existant [segmentDebutExistant, segmentFinExistant]
                double distancePointVersSegment = distancePointVersSegment(p, segmentDebutExistant, segmentFinExistant);
                
                // Si les segments sont trop proches (moins de 3 pouces)
                if (distancePointVersSegment < DISTANCE_MIN_ENTRE_FILS) {
                    tropProcheCheminExistant = true;
                    break;
                }
                
                
                double distanceDernierPointVersSegment = distancePointVersSegment(dernierPoint, segmentDebutExistant, segmentFinExistant);
                if (distanceDernierPointVersSegment > 0 && distanceDernierPointVersSegment < DISTANCE_MIN_ENTRE_FILS) {
                       tropProcheCheminExistant = true;
                       break;
                }
              } 
            }
                     
            if (tropProcheCheminExistant) {
                continue;
            }
            
            // Vérifier la distance entre fils parallèles
            if (!fil.respecteDistanceEntreFilsParalleles(p)) {
                System.out.println("Distance entre fils parallèles non respectée, ignoré");
                continue;
            }
            
            if (!fil.ajouterSegment(p)) {
                System.out.println("Impossible d'ajouter segment (longueur max ou croisement)");
                return false; // Longueur maximale atteinte ou croisement détecté
            }
            
            dernierPoint = p;
            cheminExistant = fil.getChemin(); // Mettre à jour le chemin existant
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
     
    //Calcule la distance minimale entre deux segments

    private double calculerDistanceEntreSegments(Point p1, Point p2, Point p3, Point p4) {
        // Si les segments sont parallèles et alignés (même ligne horizontale ou verticale)
        boolean segment1Horizontal = p1.y == p2.y;
        boolean segment2Horizontal = p3.y == p4.y;
        boolean segment1Vertical = p1.x == p2.x;
        boolean segment2Vertical = p3.x == p4.x;
        
        // Cas 1: Deux segments horizontaux (même Y)
        if (segment1Horizontal && segment2Horizontal) {
            if (p1.y == p3.y) {
                // Sur la même ligne - vérifier s'ils se chevauchent ou sont séparés
                int min1X = Math.min(p1.x, p2.x);
                int max1X = Math.max(p1.x, p2.x);
                int min2X = Math.min(p3.x, p4.x);
                int max2X = Math.max(p3.x, p4.x);
                
                // Chevauchement = distance 0
                if (!(max1X < min2X || max2X < min1X)) {
                    return 0;
                }
            } else {
                // Lignes horizontales parallèles à différentes hauteurs
                return Math.abs(p1.y - p3.y);
            }
        }
        
        // Cas 2: Deux segments verticaux (même X)
        if (segment1Vertical && segment2Vertical) {
            if (p1.x == p3.x) {
                // Sur la même colonne - vérifier s'ils se chevauchent ou sont séparés
                int min1Y = Math.min(p1.y, p2.y);
                int max1Y = Math.max(p1.y, p2.y);
                int min2Y = Math.min(p3.y, p4.y);
                int max2Y = Math.max(p3.y, p4.y);
                
                // Chevauchement = distance 0
                if (!(max1Y < min2Y || max2Y < min1Y)) {
                    return 0;
                }
            } else {
                // Lignes verticales parallèles à différentes colonnes
                return Math.abs(p1.x - p3.x);
            }
        }
        
        // Cas 3: Un segment horizontal et un vertical (perpendiculaires)
        if (segment1Horizontal && segment2Vertical) {
            // Segment 1 horizontal (p1-p2), Segment 2 vertical (p3-p4)
            int minX = Math.min(p1.x, p2.x);
            int maxX = Math.max(p1.x, p2.x);
            int minY = Math.min(p3.y, p4.y);
            int maxY = Math.max(p3.y, p4.y);
            
            // Si le point d'intersection existe
            if (p3.x >= minX && p3.x <= maxX && p1.y >= minY && p1.y <= maxY) {
                return 0; // Les segments se croisent
            }
            
            // Sinon calculer distance minimale entre les 4 extrémités
            double min = Math.min(
                Math.min(distancePointVersSegment(p1, p3, p4), distancePointVersSegment(p2, p3, p4)),
                Math.min(distancePointVersSegment(p3, p1, p2), distancePointVersSegment(p4, p1, p2))
            );
            return min;
        }
        
        if (segment1Vertical && segment2Horizontal) {
            // Segment 1 vertical (p1-p2), Segment 2 horizontal (p3-p4)
            int minX = Math.min(p3.x, p4.x);
            int maxX = Math.max(p3.x, p4.x);
            int minY = Math.min(p1.y, p2.y);
            int maxY = Math.max(p1.y, p2.y);
            
            // Si le point d'intersection existe
            if (p1.x >= minX && p1.x <= maxX && p3.y >= minY && p3.y <= maxY) {
                return 0; // Les segments se croisent
            }
            
            // Sinon calculer distance minimale entre les 4 extrémités
            double min = Math.min(
                Math.min(distancePointVersSegment(p1, p3, p4), distancePointVersSegment(p2, p3, p4)),
                Math.min(distancePointVersSegment(p3, p1, p2), distancePointVersSegment(p4, p1, p2))
            );
            return min;
        }
        
        // Cas général: calculer la distance minimale entre toutes les combinaisons
        double min = Double.MAX_VALUE;
        min = Math.min(min, distancePointVersSegment(p1, p3, p4));
        min = Math.min(min, distancePointVersSegment(p2, p3, p4));
        min = Math.min(min, distancePointVersSegment(p3, p1, p2));
        min = Math.min(min, distancePointVersSegment(p4, p1, p2));
        
        return min;
    }
    
    //Calcule la distance d'un point vers un segment

    private double distancePointVersSegment(Point point, Point segmentDebut, Point segmentFin) {
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;
        
        double longueurCarre = dx * dx + dy * dy;
        if (longueurCarre == 0) {
            return point.distance(segmentDebut);
        }
        
        // Projection du point sur le segment
        double t = ((point.x - segmentDebut.x) * dx + (point.y - segmentDebut.y) * dy) / longueurCarre;
        t = Math.max(0, Math.min(1, t));
        
        double projX = segmentDebut.x + t * dx;
        double projY = segmentDebut.y + t * dy;
        
        return point.distance(projX, projY);
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
