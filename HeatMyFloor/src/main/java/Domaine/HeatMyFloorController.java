package Domaine;

import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.Entite.ElementChauffant;
import Domaine.Entite.Meuble;
import Domaine.Entite.Piece;
import Domaine.Entite.ElementSelectionnable;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.Entite.MeubleSansDrain;
import java.awt.Point;
import java.awt.Polygon;
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
        if(element instanceof ElementChauffant){
            if(nouvelleLargeur != null){
                element.setLargeur(nouvelleLargeur);
            }
            if(nouvelleLongueur != null){
                element.setLongueur(nouvelleLongueur);
            }
            if(nouvellePosition != null){
                nouvellePosition = maPiece.TrouverPositionSurMurLePlusProche(nouvellePosition, element.getLongueur(),element.getLargeur());
                element.setPosition(nouvellePosition);
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
}
