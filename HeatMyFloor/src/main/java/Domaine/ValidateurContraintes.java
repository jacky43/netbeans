package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Service centralisé de validation de toutes les contraintes du fil chauffant
 * Valide les 18 contraintes définies pour le système HeatMyFloor
 */
public class ValidateurContraintes {
    
    // Constantes de contraintes (en pouces)
    private static final int DISTANCE_MIN_MURS_MEUBLES = 3;
    private static final int DISTANCE_MIN_DRAIN = 6;
    private static final int DISTANCE_MIN_TOILETTE = 10;
    private static final int DISTANCE_MIN_ELEMENT_CHAUFFANT = 8;
    private static final int DISTANCE_MIN_ENTRE_FILS = 3;
    private static final int LONGUEUR_MAX_SEGMENT = 120; // 10 pieds
    
    private Membrane membrane;
    private ArrayList<Meuble> meubles;
    private ArrayList<ElementChauffant> elementsChauffants;
    private Thermostat thermostat;
    private Fil fil;
    
    private int nombreViolations = 0;
    private int nombreAvertissements = 0;
    
    public ValidateurContraintes(Membrane membrane, ArrayList<Meuble> meubles, 
                                ArrayList<ElementSelectionnable> elements, 
                                Thermostat thermostat, Fil fil) {
        this.membrane = membrane;
        this.meubles = meubles;
        this.thermostat = thermostat;
        this.fil = fil;
        this.elementsChauffants = new ArrayList<>();
        
        if (elements != null) {
            for (ElementSelectionnable el : elements) {
                if (el instanceof ElementChauffant elementChauffant) {
                    elementsChauffants.add(elementChauffant);
                }
            }
        }
    }
    
    /**
     * Valide TOUTES les contraintes et affiche les résultats dans la console
     * @return true si toutes les contraintes sont respectées, false sinon
     */
    public boolean validerToutesLesContraintes() {
        nombreViolations = 0;
        nombreAvertissements = 0;
        
        System.out.println("\n========== VALIDATION DES CONTRAINTES ==========");
        
        if (fil == null) {
            logViolation("Aucun fil n'a été généré");
            afficherResume();
            return false;
        }
        
        // Contrainte #1-4: Distances minimales avec obstacles
        validerDistancesObstacles();
        
        // Contrainte #5: Distance entre fils parallèles
        validerDistanceEntreFils();
        
        // Contrainte #6: Longueur maximale des segments
        validerLongueurSegments();
        
        // Contrainte #7: Non-croisement
        validerNonCroisement();
        
        // Contrainte #8: Pas de passage double
        validerPassageUnique();
        
        // Contrainte #9: Longueur maximale du fil
        validerLongueurMaximale();
        
        // Contrainte #10: Thermostat comme point de départ
        validerPointDepart();
        
        // Contrainte #11: Tracé en serpentin (vérification topologique)
        validerMotifSerpentin();
        
        // Contrainte #12: Utilisation de la membrane
        validerUtilisationMembrane();
        
        afficherResume();
        return nombreViolations == 0;
    }
    
    private void logViolation(String message) {
        nombreViolations++;
        System.out.println("❌ VIOLATION: " + message);
    }
    
    private void logAvertissement(String message) {
        nombreAvertissements++;
        System.out.println("⚠️  AVERTISSEMENT: " + message);
    }
    
    private void afficherResume() {
        System.out.println("\n========== RÉSUMÉ VALIDATION ==========");
        if (nombreViolations == 0) {
            System.out.println("✅ Toutes les contraintes sont respectées!");
        } else {
            System.out.println("❌ " + nombreViolations + " violation(s) détectée(s)");
        }
        if (nombreAvertissements > 0) {
            System.out.println("⚠️  " + nombreAvertissements + " avertissement(s)");
        }
        System.out.println("=======================================\n");
    }
    
    /**
     * Contraintes #1-4: Valide les distances minimales avec murs, meubles, drains, éléments chauffants
     */
    private void validerDistancesObstacles() {
        ArrayList<Point> chemin = fil.getChemin();
        
        for (int i = 0; i < chemin.size(); i++) {
            Point point = chemin.get(i);
            
            // Contrainte #1: Distance avec murs (3 pouces)
            if (!respecteDistanceMur(point)) {
                logViolation(String.format(
                    "Point %d à (%d, %d) trop proche d'un mur (< %d pouces)",
                    i, point.x, point.y, DISTANCE_MIN_MURS_MEUBLES
                ));
            }
            
            // Contrainte #1: Distance avec meubles (3 pouces)
            for (Meuble meuble : meubles) {
                if (estTropProcheMeuble(point, meuble, DISTANCE_MIN_MURS_MEUBLES)) {
                    logViolation(String.format(
                        "Point %d à (%d, %d) trop proche d'un meuble (< %d pouces)",
                        i, point.x, point.y, DISTANCE_MIN_MURS_MEUBLES
                    ));
                }
                
                // Contrainte #2-3: Distance avec drains
                if (meuble instanceof MeubleAvecDrain meubleAvecDrain) {
                    int distanceRequise = "TOILETTE".equalsIgnoreCase(meuble.getNom()) ? 
                                         DISTANCE_MIN_TOILETTE : DISTANCE_MIN_DRAIN;
                    if (estTropProcheDrain(point, meubleAvecDrain, distanceRequise)) {
                        logViolation(String.format(
                            "Point %d à (%d, %d) trop proche d'un drain %s (< %d pouces)",
                            i, point.x, point.y, meuble.getNom(), distanceRequise
                        ));
                    }
                }
            }
            
            // Contrainte #4: Distance avec éléments chauffants (8 pouces)
            for (ElementChauffant element : elementsChauffants) {
                if (estTropProcheElement(point, element, DISTANCE_MIN_ELEMENT_CHAUFFANT)) {
                    logViolation(String.format(
                        "Point %d à (%d, %d) trop proche d'un élément chauffant (< %d pouces)",
                        i, point.x, point.y, DISTANCE_MIN_ELEMENT_CHAUFFANT
                    ));
                }
            }
        }
    }
    
    /**
     * Contrainte #5: Valide la distance minimale entre fils parallèles (3 pouces)
     */
    private void validerDistanceEntreFils() {
        ArrayList<Point> chemin = fil.getChemin();
        
        for (int i = 0; i < chemin.size() - 1; i++) {
            Point p1 = chemin.get(i);
            Point p2 = chemin.get(i + 1);
            
            // Vérifier distance avec tous les autres segments
            for (int j = i + 2; j < chemin.size() - 1; j++) {
                Point p3 = chemin.get(j);
                Point p4 = chemin.get(j + 1);
                
                double distance = calculerDistanceEntreSegments(p1, p2, p3, p4);
                if (distance > 0 && distance < DISTANCE_MIN_ENTRE_FILS) {
                    logViolation(String.format(
                        "Segments [%d-%d] et [%d-%d] trop proches: %.1f pouces (< %d pouces)",
                        i, i+1, j, j+1, distance, DISTANCE_MIN_ENTRE_FILS
                    ));
                }
            }
        }
    }
    
    /**
     * Contrainte #6: Valide que aucun segment ne dépasse 120 pouces (10 pieds)
     */
    private void validerLongueurSegments() {
        ArrayList<Point> chemin = fil.getChemin();
        
        for (int i = 0; i < chemin.size() - 1; i++) {
            Point p1 = chemin.get(i);
            Point p2 = chemin.get(i + 1);
            double longueur = p1.distance(p2);
            
            if (longueur > LONGUEUR_MAX_SEGMENT) {
                logViolation(String.format(
                    "Segment [%d-%d] de (%d,%d) à (%d,%d) trop long: %.1f pouces (max %d pouces / 10 pieds)",
                    i, i+1, p1.x, p1.y, p2.x, p2.y, longueur, LONGUEUR_MAX_SEGMENT
                ));
            }
        }
    }
    
    /**
     * Contrainte #7: Valide que le fil ne se croise pas
     */
    private void validerNonCroisement() {
        ArrayList<Point> chemin = fil.getChemin();
        
        for (int i = 0; i < chemin.size() - 1; i++) {
            Point p1 = chemin.get(i);
            Point p2 = chemin.get(i + 1);
            
            // Vérifier croisement avec segments non adjacents
            for (int j = i + 2; j < chemin.size() - 1; j++) {
                Point p3 = chemin.get(j);
                Point p4 = chemin.get(j + 1);
                
                if (segmentsSeCroisent(p1, p2, p3, p4)) {
                    logViolation(String.format(
                        "Croisement détecté entre segments [%d-%d] et [%d-%d]",
                        i, i+1, j, j+1
                    ));
                }
            }
        }
    }
    
    /**
     * Contrainte #8: Valide que le fil ne passe pas deux fois au même point
     */
    private void validerPassageUnique() {
        ArrayList<Point> chemin = fil.getChemin();
        Set<String> pointsVisites = new HashSet<>();
        
        for (int i = 0; i < chemin.size(); i++) {
            Point point = chemin.get(i);
            String cle = point.x + "," + point.y;
            
            if (pointsVisites.contains(cle)) {
                logViolation(String.format(
                    "Point (%d, %d) visité plusieurs fois (position %d)",
                    point.x, point.y, i
                ));
            }
            pointsVisites.add(cle);
        }
    }
    
    /**
     * Contrainte #9: Valide que la longueur totale ne dépasse pas la longueur maximale
     */
    private void validerLongueurMaximale() {
        int longueurActuelle = fil.getLongueurActuelle();
        int longueurMax = fil.getLongueurMaximale();
        
        if (longueurActuelle > longueurMax) {
            logViolation(String.format(
                "Longueur du fil dépasse le maximum: %d pouces (max %d pouces)",
                longueurActuelle, longueurMax
            ));
        }
        
        // Avertissement si le fil est beaucoup plus court
        double pourcentageUtilisation = (longueurActuelle * 100.0) / longueurMax;
        if (pourcentageUtilisation < 70) {
            logAvertissement(String.format(
                "Fil sous-utilisé: %.1f%% (%d/%d pouces). Le fil ne peut pas être coupé.",
                pourcentageUtilisation, longueurActuelle, longueurMax
            ));
        }
    }
    
    /**
     * Contrainte #10: Valide que le fil commence au thermostat
     */
    private void validerPointDepart() {
        if (thermostat == null) {
            logViolation("Aucun thermostat défini");
            return;
        }
        
        Point pointDepart = fil.getPointDepart();
        Point posThermostat = thermostat.getPosition();
        
        if (!pointDepart.equals(posThermostat)) {
            logViolation(String.format(
                "Le fil ne commence pas au thermostat. Départ: (%d,%d), Thermostat: (%d,%d)",
                pointDepart.x, pointDepart.y, posThermostat.x, posThermostat.y
            ));
        }
    }
    
    /**
     * Contrainte #11: Valide que le tracé suit un motif serpentin
     * (Vérification basique: allers-retours avec changements de direction)
     */
    private void validerMotifSerpentin() {
        ArrayList<Point> chemin = fil.getChemin();
        if (chemin.size() < 3) {
            logAvertissement("Chemin trop court pour valider le motif serpentin");
            return;
        }
        
        // Compter les changements de direction pour confirmer le serpentin
        int changementsDirection = 0;
        for (int i = 1; i < chemin.size() - 1; i++) {
            Point prev = chemin.get(i - 1);
            Point curr = chemin.get(i);
            Point next = chemin.get(i + 1);
            
            // Vecteur précédent et suivant
            int dx1 = curr.x - prev.x;
            int dy1 = curr.y - prev.y;
            int dx2 = next.x - curr.x;
            int dy2 = next.y - curr.y;
            
            // Changement de direction si les vecteurs sont différents
            if (dx1 != dx2 || dy1 != dy2) {
                changementsDirection++;
            }
        }
        
        // Un serpentin doit avoir plusieurs changements de direction
        if (changementsDirection < 2) {
            logAvertissement(
                "Le tracé ne semble pas suivre un motif serpentin (peu de changements de direction)"
            );
        }
    }
    
    /**
     * Contrainte #12: Valide que tous les points du fil sont sur la grille membrane
     */
    private void validerUtilisationMembrane() {
        if (membrane == null) {
            logViolation("Aucune membrane définie");
            return;
        }
        
        ArrayList<Point> chemin = fil.getChemin();
        ArrayList<Point> intersections = membrane.ObtenirIntersections();
        
        // Le premier point (thermostat) n'est pas forcément sur la grille
        for (int i = 1; i < chemin.size(); i++) {
            Point point = chemin.get(i);
            boolean surGrille = false;
            
            for (Point intersection : intersections) {
                if (point.equals(intersection)) {
                    surGrille = true;
                    break;
                }
            }
            
            if (!surGrille) {
                logAvertissement(String.format(
                    "Point %d à (%d, %d) n'est pas sur une intersection de la membrane",
                    i, point.x, point.y
                ));
            }
        }
    }
    
    // === Méthodes utilitaires ===
    
    private boolean respecteDistanceMur(Point point) {
        int marge = DISTANCE_MIN_MURS_MEUBLES;
        return point.x >= marge && point.x <= membrane.getLargeurPiece() - marge &&
               point.y >= marge && point.y <= membrane.getLongueurPiece() - marge;
    }
    
    private boolean estTropProcheMeuble(Point point, Meuble meuble, int distanceMin) {
        Point posMeuble = meuble.getPosition();
        int minX = posMeuble.x - distanceMin;
        int maxX = posMeuble.x + meuble.getLargeur() + distanceMin;
        int minY = posMeuble.y - meuble.getLongueur() - distanceMin;
        int maxY = posMeuble.y + distanceMin;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    private boolean estTropProcheElement(Point point, ElementChauffant element, int distanceMin) {
        Point posElement = element.getPosition();
        int minX = posElement.x - distanceMin;
        int maxX = posElement.x + element.getLargeur() + distanceMin;
        int minY = posElement.y - element.getLongueur() - distanceMin;
        int maxY = posElement.y + distanceMin;
        
        return point.x >= minX && point.x <= maxX &&
               point.y >= minY && point.y <= maxY;
    }
    
    private boolean estTropProcheDrain(Point point, MeubleAvecDrain meuble, int distanceMin) {
        Point centreDrain = meuble.getCentreDrain();
        if (centreDrain == null) {
            return false;
        }
        
        Point posMeuble = meuble.getPosition();
        int xDrainAbsolu = posMeuble.x + centreDrain.x;
        int yDrainAbsolu = posMeuble.y - meuble.getLongueur() + centreDrain.y;
        
        double distance = Math.sqrt(
            Math.pow(point.x - xDrainAbsolu, 2) + 
            Math.pow(point.y - yDrainAbsolu, 2)
        );
        
        return distance < distanceMin;
    }
    
    private double calculerDistanceEntreSegments(Point p1, Point p2, Point p3, Point p4) {
        double d1 = distancePointVersSegment(p1, p3, p4);
        double d2 = distancePointVersSegment(p2, p3, p4);
        double d3 = distancePointVersSegment(p3, p1, p2);
        double d4 = distancePointVersSegment(p4, p1, p2);
        
        return Math.min(Math.min(d1, d2), Math.min(d3, d4));
    }
    
    private double distancePointVersSegment(Point point, Point segmentDebut, Point segmentFin) {
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;
        double longueurCarree = dx * dx + dy * dy;
        
        if (longueurCarree == 0) {
            return point.distance(segmentDebut);
        }
        
        double t = ((point.x - segmentDebut.x) * dx + (point.y - segmentDebut.y) * dy) / longueurCarree;
        t = Math.max(0, Math.min(1, t));
        
        double projX = segmentDebut.x + t * dx;
        double projY = segmentDebut.y + t * dy;
        
        return Math.sqrt(Math.pow(point.x - projX, 2) + Math.pow(point.y - projY, 2));
    }
    
    private boolean segmentsSeCroisent(Point p1, Point p2, Point p3, Point p4) {
        int d1 = direction(p3, p4, p1);
        int d2 = direction(p3, p4, p2);
        int d3 = direction(p1, p2, p3);
        int d4 = direction(p1, p2, p4);
        
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }
        
        return false;
    }
    
    private int direction(Point p1, Point p2, Point p3) {
        return (p3.x - p1.x) * (p2.y - p1.y) - (p2.x - p1.x) * (p3.y - p1.y);
    }
}
