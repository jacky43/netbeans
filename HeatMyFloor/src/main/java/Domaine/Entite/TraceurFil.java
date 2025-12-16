package Domaine.Entite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TraceurFil {
    private Membrane membrane;
    private ArrayList<Meuble> meubles;
    private ArrayList<ElementChauffant> elementsChauffants;
    private int distanceSecurite = 3; // 3 pouces murs et meubles
    private static final int DISTANCE_MIN_DRAIN = 6; // 6 pouces minimum des drains
    private static final int DISTANCE_MIN_TOILETTE = 10; // 10 pouces minimum du drain toilette
    private static final int DISTANCE_MIN_ELEMENT_CHAUFFANT = 8; // 8 pouces minimum des éléments chauffants
    private static final int DISTANCE_MIN_ENTRE_FILS = 3; // 3 pouces entre fils parallèles
    private static final int LONGUEUR_MAX_SEGMENT = 120; // 10 pieds
    private int distanceMaxLigneDroite; // Distance max par segment de ligne droite (saisie utilisateur)

    public TraceurFil(Membrane membrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements) {
        this(membrane, meubles, elements, Integer.MAX_VALUE);
    }

    public TraceurFil(Membrane membrane, ArrayList<Meuble> meubles, ArrayList<ElementSelectionnable> elements, int distanceMaxLigne) {
        this.membrane = membrane;
        this.meubles = meubles;
        this.elementsChauffants = new ArrayList<>();
        // Limite utilisateur, bornée à la limite physique de 10 pieds
        this.distanceMaxLigneDroite = (distanceMaxLigne > 0)
            ? Math.min(distanceMaxLigne, LONGUEUR_MAX_SEGMENT)
            : LONGUEUR_MAX_SEGMENT;
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

        if(!membrane.estIntersectionValide(point)){return false;}
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
        int minX = posElement.x - DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int maxX = posElement.x + element.getLargeur() + DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int minY = posElement.y - element.getLongueur() - DISTANCE_MIN_ELEMENT_CHAUFFANT;
        int maxY = posElement.y + DISTANCE_MIN_ELEMENT_CHAUFFANT;

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
                        Point dernierPoint = fil.getChemin().getLast();
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

    /**
     * NOUVELLE MÉTHODE: Connecte deux points en utilisant UNIQUEMENT des segments orthogonaux.
     * Essaie deux stratégies:
     *   1. Horizontal puis vertical (from.x→to.x, puis from.y→to.y)
     *   2. Vertical puis horizontal (from.y→to.y, puis from.x→to.x)
     *
     * @return true si la connexion a réussi, false sinon
     */
    private boolean connecterOrthogonalement(Fil fil, Point from, Point to, ArrayList<Point> cheminExistant) {
        // Cas 1: Déjà alignés
        if (from.x == to.x || from.y == to.y) {
            // Connexion directe (1 segment)
            return ajouterSegmentAvecValidation(fil, from, to, cheminExistant);
        }

        // Cas 2: Pas alignés → 2 segments en L

        // Stratégie A: Horizontal puis Vertical
        Point intermediaire1 = new Point(to.x, from.y);
        boolean strategieA = false;

        if (estPointValide(intermediaire1) &&
                estSegmentValide(from, intermediaire1) &&
                estSegmentValide(intermediaire1, to)) {

            // Vérifier proximité avec chemin existant
            boolean valide1 = verifierProximiteAvecChemin(from, intermediaire1, cheminExistant);
            boolean valide2 = verifierProximiteAvecChemin(intermediaire1, to, cheminExistant);

            if (valide1 && valide2) {
                // Ajouter les 2 segments
                if (ajouterSegmentAvecValidation(fil, from, intermediaire1, cheminExistant)) {
                    cheminExistant = fil.getChemin(); // Mettre à jour
                    if (ajouterSegmentAvecValidation(fil, intermediaire1, to, cheminExistant)) {
                        return true;
                    }
                    // Si échec sur le 2e segment, on ne peut pas revenir en arrière
                    // Le fil a déjà été modifié
                }
            }
        }

        // Stratégie B: Vertical puis Horizontal
        Point intermediaire2 = new Point(from.x, to.y);

        if (estPointValide(intermediaire2) &&
                estSegmentValide(from, intermediaire2) &&
                estSegmentValide(intermediaire2, to)) {

            boolean valide1 = verifierProximiteAvecChemin(from, intermediaire2, cheminExistant);
            boolean valide2 = verifierProximiteAvecChemin(intermediaire2, to, cheminExistant);

            if (valide1 && valide2) {
                if (ajouterSegmentAvecValidation(fil, from, intermediaire2, cheminExistant)) {
                    cheminExistant = fil.getChemin();
                    if (ajouterSegmentAvecValidation(fil, intermediaire2, to, cheminExistant)) {
                        return true;
                    }
                }
            }
        }

        return false; // Aucune stratégie n'a fonctionné
    }

    /**
     * MÉTHODE CRITIQUE: Détecte si deux segments axis-aligned s'intersectent.
     *
     * Invariants géométriques:
     * - Segments parallèles collinéaires: intersection si ranges se chevauchent
     * - Segments perpendiculaires: intersection si le point de croisement existe dans les deux ranges
     * - Segments parallèles non-collinéaires: jamais d'intersection
     *
     * Exception: Partager exactement le point 'pointPrecedent' est valide (continuité du chemin)
     *
     * @param a1 Premier point du segment A
     * @param a2 Second point du segment A
     * @param b1 Premier point du segment B
     * @param b2 Second point du segment B
     * @param pointPrecedent Point de connexion valide (peut être null)
     * @return true si les segments s'intersectent de manière invalide
     */
    private boolean segmentsIntersectent(Point a1, Point a2, Point b1, Point b2, Point pointPrecedent) {
        // Normaliser les segments pour avoir min-max ordering
        int aMinX = Math.min(a1.x, a2.x);
        int aMaxX = Math.max(a1.x, a2.x);
        int aMinY = Math.min(a1.y, a2.y);
        int aMaxY = Math.max(a1.y, a2.y);

        int bMinX = Math.min(b1.x, b2.x);
        int bMaxX = Math.max(b1.x, b2.x);
        int bMinY = Math.min(b1.y, b2.y);
        int bMaxY = Math.max(b1.y, b2.y);

        // Déterminer l'orientation des segments
        boolean aHorizontal = (a1.y == a2.y);
        boolean aVertical = (a1.x == a2.x);
        boolean bHorizontal = (b1.y == b2.y);
        boolean bVertical = (b1.x == b2.x);

        // CAS 1: Les deux segments sont horizontaux
        if (aHorizontal && bHorizontal) {
            // S'ils ne sont pas sur la même ligne Y, pas d'intersection
            if (a1.y != b1.y) {
                return false;
            }

            // Même ligne Y: vérifier si les ranges X se chevauchent
            boolean chevauchement = !(aMaxX < bMinX || bMaxX < aMinX);

            if (!chevauchement) {
                return false;
            }

            // Il y a chevauchement: vérifier si c'est juste le point de connexion
            if (pointPrecedent != null) {
                // Si le chevauchement est uniquement au point de connexion, c'est valide
                if ((aMaxX == bMinX || aMinX == bMaxX) &&
                        ((a2.equals(pointPrecedent) && b1.equals(pointPrecedent)) ||
                                (a1.equals(pointPrecedent) && b1.equals(pointPrecedent)) ||
                                (a2.equals(pointPrecedent) && b2.equals(pointPrecedent)) ||
                                (a1.equals(pointPrecedent) && b2.equals(pointPrecedent)))) {
                    return false; // Connexion valide au point précédent
                }
            }

            return true; // Chevauchement invalide
        }

        // CAS 2: Les deux segments sont verticaux
        if (aVertical && bVertical) {
            // S'ils ne sont pas sur la même colonne X, pas d'intersection
            if (a1.x != b1.x) {
                return false;
            }

            // Même colonne X: vérifier si les ranges Y se chevauchent
            boolean chevauchement = !(aMaxY < bMinY || bMaxY < aMinY);

            if (!chevauchement) {
                return false;
            }

            // Il y a chevauchement: vérifier si c'est juste le point de connexion
            if (pointPrecedent != null) {
                if ((aMaxY == bMinY || aMinY == bMaxY) &&
                        ((a2.equals(pointPrecedent) && b1.equals(pointPrecedent)) ||
                                (a1.equals(pointPrecedent) && b1.equals(pointPrecedent)) ||
                                (a2.equals(pointPrecedent) && b2.equals(pointPrecedent)) ||
                                (a1.equals(pointPrecedent) && b2.equals(pointPrecedent)))) {
                    return false; // Connexion valide au point précédent
                }
            }

            return true; // Chevauchement invalide
        }

        // CAS 3: Un segment horizontal, un vertical (perpendiculaires)
        if (aHorizontal && bVertical) {
            // Segment A horizontal (fixe sur Y = a1.y, varie sur X dans [aMinX, aMaxX])
            // Segment B vertical (fixe sur X = b1.x, varie sur Y dans [bMinY, bMaxY])

            // Point d'intersection potentiel: (b1.x, a1.y)
            // Il existe si: b1.x ∈ [aMinX, aMaxX] ET a1.y ∈ [bMinY, bMaxY]
            boolean intersecte = (b1.x >= aMinX && b1.x <= aMaxX) &&
                    (a1.y >= bMinY && a1.y <= bMaxY);

            if (!intersecte) {
                return false;
            }

            // Il y a intersection: vérifier si c'est au point de connexion
            if (pointPrecedent != null) {
                Point intersection = new Point(b1.x, a1.y);
                if (intersection.equals(pointPrecedent)) {
                    // L'intersection est exactement au point de connexion précédent
                    return false; // Valide
                }
            }

            return true; // Intersection invalide
        }

        if (aVertical && bHorizontal) {
            // Segment A vertical (fixe sur X = a1.x, varie sur Y dans [aMinY, aMaxY])
            // Segment B horizontal (fixe sur Y = b1.y, varie sur X dans [bMinX, bMaxX])

            // Point d'intersection potentiel: (a1.x, b1.y)
            boolean intersecte = (a1.x >= bMinX && a1.x <= bMaxX) &&
                    (b1.y >= aMinY && b1.y <= aMaxY);

            if (!intersecte) {
                return false;
            }

            // Il y a intersection: vérifier si c'est au point de connexion
            if (pointPrecedent != null) {
                Point intersection = new Point(a1.x, b1.y);
                if (intersection.equals(pointPrecedent)) {
                    return false; // Valide
                }
            }

            return true; // Intersection invalide
        }

        // CAS 4: Segments parallèles mais pas sur la même ligne (pas d'intersection)
        return false;
    }

    /**
     * Ajoute un segment unique avec toutes les validations nécessaires.
     * INVARIANT CRITIQUE: Le nouveau segment ne doit JAMAIS croiser un segment existant.
     */
    private boolean ajouterSegmentAvecValidation(Fil fil, Point from, Point to, ArrayList<Point> cheminExistant) {
        // Vérifier distance
        int distance = calculerDistance(from, to);

        if (distance > LONGUEUR_MAX_SEGMENT) {
            System.out.println("Segment trop long: " + distance);
            return false;
        }

        if (distance > distanceMaxLigneDroite) {
            System.out.println("Segment dépasse distance max ligne droite");
            return false;
        }

        // Vérifier que le point n'est pas déjà utilisé
        for (Point p : cheminExistant) {
            if (p.equals(to)) {
                System.out.println("Point déjà utilisé: " + to);
                return false;
            }
        }

        // Vérifier segment valide (obstacles)
        if (!estSegmentValide(from, to)) {
            System.out.println("Segment invalide (obstacle)");
            return false;
        }

        // CRITIQUE: Vérifier l'intersection avec TOUS les segments existants
        if (cheminExistant.size() >= 2) {
            Point pointPrecedent = cheminExistant.get(cheminExistant.size() - 1); // Le dernier point = 'from'

            for (int i = 0; i < cheminExistant.size() - 1; i++) {
                Point segmentDebut = cheminExistant.get(i);
                Point segmentFin = cheminExistant.get(i + 1);

                // Vérifier intersection géométrique exacte
                if (segmentsIntersectent(from, to, segmentDebut, segmentFin, pointPrecedent)) {
                    System.out.println("Segment croise un segment existant: [" + from + " -> " + to + "] croise [" + segmentDebut + " -> " + segmentFin + "]");
                    return false;
                }
            }
        }

        // Vérifier la proximité minimale (3 pouces) pour segments parallèles
        if (!verifierProximiteAvecChemin(from, to, cheminExistant)) {
            System.out.println("Segment trop proche d'un segment parallèle");
            return false;
        }

        // Ajouter le segment
        return fil.ajouterSegment(to);
    }

    /**
     * Vérifie la proximité d'un segment avec le chemin existant (règle des 3 pouces)
     */
    private boolean verifierProximiteAvecChemin(Point segmentDebut, Point segmentFin, ArrayList<Point> cheminExistant) {
        if (cheminExistant.size() < 2) {
            return true; // Pas assez de segments pour vérifier
        }

        boolean nouveauHorizontal = segmentDebut.y == segmentFin.y;
        boolean nouveauVertical = segmentDebut.x == segmentFin.x;

        // Vérifier uniquement les segments parallèles (règle des 3 pouces entre fils parallèles)
        for (int i = 0; i < cheminExistant.size() - 1; i++) {
            Point existantDebut = cheminExistant.get(i);
            Point existantFin = cheminExistant.get(i + 1);

            // Ignorer le dernier segment qui partage le point de départ
            if (i == cheminExistant.size() - 2) {
                continue;
            }

            // Ignorer les segments qui partagent une extrémité avec le nouveau segment
            if (existantDebut.equals(segmentDebut) || existantDebut.equals(segmentFin)
                    || existantFin.equals(segmentDebut) || existantFin.equals(segmentFin)) {
                continue;
            }

            boolean existantHorizontal = existantDebut.y == existantFin.y;
            boolean existantVertical = existantDebut.x == existantFin.x;

            boolean sontParalleles = (nouveauHorizontal && existantHorizontal) || (nouveauVertical && existantVertical);
            if (!sontParalleles) {
                continue; // La contrainte de 3" ne s'applique qu'aux segments parallèles
            }

            double distance = calculerDistanceEntreSegments(segmentDebut, segmentFin, existantDebut, existantFin);
            if (distance > 0 && distance < DISTANCE_MIN_ENTRE_FILS) {
                return false;
            }
        }

        return true;
    }

    /**
     * MÉTHODE REFACTORISÉE: Ajoute une ligne de points en respectant la géométrie Manhattan
     */
    private boolean ajouterLigneAvecContrainte(Fil fil, ArrayList<Point> ligne) {
        if (ligne.isEmpty()) {
            return true;
        }

        Point dernierPoint = fil.getChemin().getLast();
        ArrayList<Point> cheminExistant = fil.getChemin();

        for (Point p : ligne) {
            // Vérifier si ce point est déjà dans le chemin
            boolean pointDejaUtilise = false;
            for (Point pointChemin : cheminExistant) {
                if (pointChemin.equals(p)) {
                    pointDejaUtilise = true;
                    break;
                }
            }

            if (pointDejaUtilise) {
                System.out.println("Point déjà utilisé, ignoré: " + p);
                continue;
            }

            if (dernierPoint.equals(p)) {
                continue;
            }

            // CORRECTION CRITIQUE: Utiliser connexion orthogonale
            if (!connecterOrthogonalement(fil, dernierPoint, p, cheminExistant)) {
                System.out.println("Impossible de connecter orthogonalement à: " + p);
                continue; // Passer au point suivant
            }

            // Mise à jour pour la prochaine itération
            dernierPoint = fil.getChemin().getLast();
            cheminExistant = fil.getChemin();
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
                if(estTropProcheDrain(point, meubleAvecDrain)){
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
