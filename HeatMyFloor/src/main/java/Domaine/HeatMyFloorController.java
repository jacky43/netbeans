package Domaine;

import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.DTO.ThermostatDTO;
import Domaine.Entite.ElementChauffant;
import Domaine.Entite.Meuble;
import Domaine.Entite.Piece;
import Domaine.Entite.ElementSelectionnable;
import Domaine.Entite.Fil;
import Domaine.Entite.Membrane;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.Entite.MeubleSansDrain;
import Domaine.Entite.Thermostat;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class HeatMyFloorController {
    
    private Piece maPiece;
    private boolean estInitialise;
    private ActionHistory history;
    
    public HeatMyFloorController()
    {
        maPiece = new Piece();
        estInitialise = false;
        history = new ActionHistory();
    }
    
    public void InitialiserPiece(Polygon forme)
    {
        maPiece = new Piece(forme);
        estInitialise = true;
        history.clear();
    }
    
    public void DefinirDimensionsPiece(int largeurpouces, int longueurPouces){
        maPiece.DefinirDimensionReeles(largeurpouces, longueurPouces);
    }
    
    public void CreerPiece(PieceDTO p_piece)
    {
        
    }
    
    public PieceDTO ObtenirPiece()
    {
        return new PieceDTO(maPiece);
    }
    
    public void AjouterMeuble(MeubleDTO dto)
    {
        if (estInitialise) {
            history.saveState(maPiece);
        }
        maPiece.AjouterMeuble(dto);
    }
    
     public void AjouterElementChauffant(ElementChauffantDTO dto)
    {
        if (estInitialise) {
            history.saveState(maPiece);
        }
        maPiece.AjouterElementChauffant(dto);
    }
     
    public void saveStateBeforeDrag() {
        if (estInitialise) {
            history.saveState(maPiece);
        }
    } 
        
    public void saveStateAfterDrag() {
        if (estInitialise) {
            history.saveState(maPiece);
        }
    }     
    public ArrayList<MeubleDTO> ObtenirMeubles()
    {
        ArrayList<MeubleDTO> dtos = new ArrayList<>();
        for (Meuble meuble : maPiece.getMeubles())
            dtos.add((MeubleDTO)construireDto(meuble));
        return dtos;
    }
    
     public ArrayList<Object> ObtenirTousLesElements()
    {
        ArrayList<Object> dtos = new ArrayList<>();
        for (ElementSelectionnable element : maPiece.getMeubles())
            dtos.add((MeubleDTO)construireDto(element));
        return dtos;
    }
    
    public boolean estInitialise()
    {
        return estInitialise;
    }
    
    public Point getPositionPiece()
    {
        return maPiece.getPositionPiece();
    }
    
    public Point getOrigineAxes()
    {
        return maPiece.getOrigineAxes();
    }
    
    public Object SelectionnerElement(Point position)
    {
        ElementSelectionnable elementSelectionne = maPiece.SelectionnerElement(position);
        return construireDto(elementSelectionne);
    }
    
    public boolean ModifierElementSelectionne(Point nouvellePosition, Integer nouvelleLargeur, Integer nouvelleLongueur) 
    {  
      //  history.saveState(maPiece);
        
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        if(element instanceof ElementChauffant || element instanceof Thermostat){
            if(nouvelleLargeur != null){
                element.setLargeur(nouvelleLargeur);
            }
            if(nouvelleLongueur != null){
                element.setLongueur(nouvelleLongueur);
            }
            if(nouvellePosition != null){
                nouvellePosition = maPiece.TrouverPositionSurMurLePlusProche(nouvellePosition, element.getLongueur(),element.getLargeur());
                element.setPosition(nouvellePosition);
                
                if(element instanceof Thermostat){
                    maPiece.SupprimerFilChauffant();
                }
            }
            return true;
            
        }
        return maPiece.ModifierElementSelectionne(nouvellePosition, nouvelleLargeur, nouvelleLongueur);
    }
    
     public boolean SupprimerElementSelectionne()
    {
        history.saveState(maPiece);
        return maPiece.SupprimerElementSelectionne();
    }
    
    public Object ObtenirElementSelectionne()
    {
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        return construireDto(element);
    }
    
    public boolean undo() {
        
        if (history.canUndo()) {
            Piece ancienEtat = history.undo(maPiece);
            if (ancienEtat != null) {
               maPiece = ancienEtat;
               return true;
            }
        }
        System.out.println("Controller: undo echoue");
        return false;
    }
    
    public boolean redo() {
        if (history.canRedo()) {
            Piece nouvelEtat = history.redo(maPiece);
            if (nouvelEtat != null) {
                maPiece = nouvelEtat;
                System.out.println("Controller: maPiece remplacee, nombre d'elements: " + maPiece.getElements().size());
                return true;
            }
        }
        System.out.println("Controller: redo echoue");
        return false;
    }
    
    public boolean canUndo() {
        return history.canUndo();
    }
    
    public boolean canRedo() {
        return history.canRedo();
    }
    
     // TODO : 
    public void AfficherMessageErreur(String message)
    {
       
    }
    
    private Object construireDto(ElementSelectionnable element)
    {
        if (element == null)
            return null;
       /** Object dto = element.ToDto();
        if(dto instanceof MeubleDTO meubleDto)
            return meubleDto;
        
        if(dto instanceof ElementChauffantDTO elementChauffantDto){
            return new  ElementChauffantDTO(elementChauffantDto.getPosition(), elementChauffantDto.getLongueur(), elementChauffantDto.getLargeur());
        }**/
        return element.ToDto();
    }
    
    public Point ObtenirOrigine()
    {
        return maPiece.ObtenirOrigine();
    }
    
    public Domaine.Entite.ElementSelectionnable ObtenirElementSelectionneDirect() {
        return maPiece.ObtenirElementSelectionne();
    }
    
    // Méthodes pour la membrane
    public void InitialiserMembrane(int espacement, int marge) {
        maPiece.InitialiserMembrane(espacement, marge);
    }
    
    public Membrane ObtenirMembrane() {
        return maPiece.getMembrane();
    }
    
    // Méthodes pour le thermostat
    public void AjouterThermostat(ThermostatDTO dto) {
        maPiece.AjouterThermostat(dto);
    }
    
    public Thermostat ObtenirThermostat() {
        return maPiece.getThermostat();
    }
    
    // Méthodes pour le fil chauffant
    public void TracerFilChauffant(int longueurMax, int distanceMaxLigne) {
        maPiece.TracerFilChauffant(longueurMax, distanceMaxLigne);
    }
    
    public Fil ObtenirFilChauffant() {
        return maPiece.getFilChauffant();
    }
    
    public void SupprimerFilChauffant() {
        maPiece.SupprimerFilChauffant();
    }

    public void SauvegarderPiece(String path) throws IOException
    {
        maPiece.Sauvegarder(path);
    }
    public void ImporterPiece(String path) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        maPiece.importer(path);
    }
    
    // Active ou désactive la régénération automatique du fil Quand activée, le fil sera recalculé après chaque modification (ajout/suppression/modification d'élément)

    public void ActiverAutoRegenerationFil(boolean activer) {
        maPiece.setAutoRegenerationFil(activer);
    }
    
    public boolean EstAutoRegenerationActive() {
        return maPiece.estAutoRegenerationActive();
    }
    
    //Trouve l'intersection de la membrane la plus proche d'une position donnée
    public Point TrouverIntersectionProche(Point position, int tolerance) {
        Membrane membrane = maPiece.getMembrane();
        if (membrane == null) {
            return null;
        }
        
        ArrayList<Point> intersections = membrane.ObtenirIntersections();
        Point plusProche = null;
        double distanceMin = Double.MAX_VALUE;
        
        for (Point intersection : intersections) {
            double distance = Math.sqrt(
                Math.pow(intersection.x - position.x, 2) + 
                Math.pow(intersection.y - position.y, 2)
            );
            
            if (distance < distanceMin && distance <= tolerance) {
                distanceMin = distance;
                plusProche = intersection;
            }
        }
        
        return plusProche;
    }
    
    //Translate une intersection de la membrane vers une nouvelle position

    public boolean TranslaterIntersection(Point intersectionOriginale, Point nouvellePosition) {
        Membrane membrane = maPiece.getMembrane();
        if (membrane == null) {
            return false;
        }
        
        // Trouver le point de grille le plus proche de l'intersection originale
        Point pointGrille = membrane.trouverIntersectionLaPlusProche(intersectionOriginale);
        
        // Translater ce point vers la nouvelle position
        boolean succes = membrane.translaterIntersection(pointGrille, nouvellePosition);
        
        if (succes) {
            System.out.println("Translation réussie: " + pointGrille + " -> " + nouvellePosition);
        }
        
        return succes;
    }

}
