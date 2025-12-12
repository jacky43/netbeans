/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domaine.DTO;

/**
 *
 * @author jacky
 */
public class ConfigurationFilDTO {
    private int longueurMaximale; // Longueur maximale du fil (impossible de couper)
    private int distanceMaxLigneDroite; // Distance maximale sur une ligne droite
    private int espacementMembrane; // Distance entre les intersections du graphe/membrane
    private boolean autoRegeneration; // Active/désactive la régénération automatique
    
    // Constantes de contraintes (en pouces)
    private static final int DISTANCE_MIN_MURS_MEUBLES = 3;
    private static final int DISTANCE_MIN_DRAIN = 6;
    private static final int DISTANCE_MIN_TOILETTE = 10;
    private static final int DISTANCE_MIN_ELEMENT_CHAUFFANT = 8;
    private static final int DISTANCE_MIN_ENTRE_FILS = 3;
    private static final int LONGUEUR_MAX_SEGMENT = 120; // 10 pieds
    
    public ConfigurationFilDTO(int longueurMaximale, int distanceMaxLigneDroite, int espacementMembrane) {
        this.longueurMaximale = longueurMaximale;
        this.distanceMaxLigneDroite = distanceMaxLigneDroite;
        this.espacementMembrane = espacementMembrane;
        this.autoRegeneration = false;
    }
    
    public int getLongueurMaximale() {
        return longueurMaximale;
    }
    
    public void setLongueurMaximale(int longueurMaximale) {
        this.longueurMaximale = longueurMaximale;
    }
    
    public int getDistanceMaxLigneDroite() {
        return distanceMaxLigneDroite;
    }
    
    public void setDistanceMaxLigneDroite(int distanceMaxLigneDroite) {
        this.distanceMaxLigneDroite = distanceMaxLigneDroite;
    }
    
    public int getEspacementMembrane() {
        return espacementMembrane;
    }
    
    public void setEspacementMembrane(int espacementMembrane) {
        this.espacementMembrane = espacementMembrane;
    }
    
    public boolean isAutoRegeneration() {
        return autoRegeneration;
    }
    
    public void setAutoRegeneration(boolean autoRegeneration) {
        this.autoRegeneration = autoRegeneration;
    }
    
    // Getters pour les constantes
    public static int getDistanceMinMursMeubles() {
        return DISTANCE_MIN_MURS_MEUBLES;
    }
    
    public static int getDistanceMinDrain() {
        return DISTANCE_MIN_DRAIN;
    }
    
    public static int getDistanceMinToilette() {
        return DISTANCE_MIN_TOILETTE;
    }
    
    public static int getDistanceMinElementChauffant() {
        return DISTANCE_MIN_ELEMENT_CHAUFFANT;
    }
    
    public static int getDistanceMinEntreFils() {
        return DISTANCE_MIN_ENTRE_FILS;
    }
    
    public static int getLongueurMaxSegment() {
        return LONGUEUR_MAX_SEGMENT;
    }
    
    @Override
    public String toString() {
        return "ConfigurationFilDTO{" +
                "longueurMaximale=" + longueurMaximale +
                ", distanceMaxLigneDroite=" + distanceMaxLigneDroite +
                ", espacementMembrane=" + espacementMembrane +
                ", autoRegeneration=" + autoRegeneration +
                '}';
    }
    
    //Valide la configuration
    public boolean estValide() {
        return longueurMaximale > 0 && 
               distanceMaxLigneDroite > 0 && 
               espacementMembrane > 0 &&
               distanceMaxLigneDroite <= LONGUEUR_MAX_SEGMENT;
    }
    
    // Obtient un résumé des contraintes appliquées
    public String obtenirResumeContraintes() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONTRAINTES DU FIL CHAUFFANT ===\n");
        sb.append("• Longueur maximale du fil: ").append(longueurMaximale).append(" pouces\n");
        sb.append("• Distance max ligne droite: ").append(distanceMaxLigneDroite).append(" pouces\n");
        sb.append("• Espacement membrane: ").append(espacementMembrane).append(" pouces\n");
        sb.append("• Segments max: ").append(LONGUEUR_MAX_SEGMENT).append(" pouces (10 pieds)\n");
        sb.append("• Distance min des murs/meubles: ").append(DISTANCE_MIN_MURS_MEUBLES).append(" pouces\n");
        sb.append("• Distance min des drains: ").append(DISTANCE_MIN_DRAIN).append(" pouces\n");
        sb.append("• Distance min toilette: ").append(DISTANCE_MIN_TOILETTE).append(" pouces\n");
        sb.append("• Distance min éléments chauffants: ").append(DISTANCE_MIN_ELEMENT_CHAUFFANT).append(" pouces\n");
        sb.append("• Distance min entre fils: ").append(DISTANCE_MIN_ENTRE_FILS).append(" pouces\n");
        sb.append("• Non-croisement du fil: OUI\n");
        sb.append("• Le thermostat est le point de départ\n");
        sb.append("• Tracé en serpentin (allers-retours)\n");
        sb.append("• Auto-régénération: ").append(autoRegeneration ? "ACTIVE" : "INACTIVE").append("\n");
        return sb.toString();
    }
}

