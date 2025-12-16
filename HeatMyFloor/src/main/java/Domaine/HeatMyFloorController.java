package Domaine;

import Domaine.DTO.*;
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
    private SelectionInfoDTO derniereSelection = null;
    
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
            maPiece.genererFil();
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
    
    public MembraneDTO ObtenirMembrane() {
        return maPiece.getMembrane();
    }
    
    // Méthodes pour le thermostat
    public void AjouterThermostat(ThermostatDTO dto) {
        maPiece.AjouterThermostat(dto);
    }
    
    public ThermostatDTO ObtenirThermostat() {
        return maPiece.getThermostat();
    }
    
    // Méthodes pour le fil chauffant
    public void TracerFilChauffant(int longueurMax, int distanceMaxLigne) {
        maPiece.TracerFilChauffant(longueurMax, distanceMaxLigne);
    }
    
    public FilDTO ObtenirFilChauffant() {
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

    public SelectionInfoDTO SelectionnerElementAvecType(Point position) {
        if (maPiece == null) {
            return SelectionInfoDTO.aucun();
        }
        derniereSelection = maPiece.SelectionnerElementAvecType(position);
        return derniereSelection;
    }

    public SelectionInfoDTO getDerniereSelection() {
        return derniereSelection;
    }

    public boolean ModifierPositionDrain(Point nouveauCentreDrain) {
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        if (element instanceof MeubleAvecDrain meubleAvecDrain) {
            // Snap to boundary
            Point drainSnapped = snapDrainToMeubleBoundary(
                    meubleAvecDrain.getLargeur(),
                    meubleAvecDrain.getLongueur(),
                    nouveauCentreDrain,
                    meubleAvecDrain.getDiametreDrain()
            );
            meubleAvecDrain.setCentreDrain(drainSnapped);
            return true;
        }
        return false;
    }

    private Point snapDrainToMeubleBoundary(int largeur, int longueur, Point centreDrain, int diametreDrain) {
        int rayonDrain = diametreDrain / 2;

        int x = centreDrain.x;
        int y = centreDrain.y;

        // Valid interior zone (drain fully inside meuble)
        int xMin = rayonDrain;
        int xMax = largeur - rayonDrain;
        int yMin = rayonDrain;
        int yMax = longueur - rayonDrain;

        // If within valid interior bounds, keep it inside
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            return new Point(x, y);
        }

        // Outside: snap to closest edge with drain touching from outside
        double distGauche = Math.abs(x - 0);
        double distDroite = Math.abs(x - largeur);
        double distBas = Math.abs(y - 0);
        double distHaut = Math.abs(y - longueur);

        double minDist = Math.min(Math.min(distGauche, distDroite), Math.min(distBas, distHaut));

        if (minDist == distGauche) {
            // Outside left: center at -rayonDrain (drain touches from left)
            return new Point(-rayonDrain, Math.max(rayonDrain, Math.min(y, longueur - rayonDrain)));
        } else if (minDist == distDroite) {
            // Outside right: center at largeur + rayonDrain
            return new Point(largeur + rayonDrain, Math.max(rayonDrain, Math.min(y, longueur - rayonDrain)));
        } else if (minDist == distBas) {
            // Outside bottom: center at -rayonDrain
            return new Point(Math.max(rayonDrain, Math.min(x, largeur - rayonDrain)), -rayonDrain);
        } else {
            // Outside top: center at longueur + rayonDrain
            return new Point(Math.max(rayonDrain, Math.min(x, largeur - rayonDrain)), longueur + rayonDrain);
        }
    }
}
