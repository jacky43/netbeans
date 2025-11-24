package Domaine;

import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.Entite.ElementChauffant;
import Domaine.Entite.Meuble;
import Domaine.Entite.Piece;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import Domaine.Entite.ElementSelectionnable;

public class HeatMyFloorController {
    
    private Piece maPiece;
    private boolean estInitialise;
    
    public HeatMyFloorController()
    {
        maPiece = new Piece();
        estInitialise = false;
    }
    
    public void InitialiserPiece(Polygon forme)
    {
        maPiece.setForme(forme);
        estInitialise = true;
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
        maPiece.AjouterMeuble(dto);
    }
    
     public void AjouterElementChauffant(ElementChauffantDTO dto)
    {
        maPiece.AjouterElementChauffant(dto);
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
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        if(element instanceof ElementChauffant){
            if(nouvelleLargeur != null){
                element.setLargeur(nouvelleLargeur);
            }
            if(nouvelleLongueur != null){
                element.setLongueur(nouvelleLongueur);
            }
            if(nouvellePosition != null){
                nouvellePosition = maPiece.TrouverPositionSurMurLePlusProche(nouvellePosition, element.getLargeur(), element.getLongueur());
                element.setPosition(nouvellePosition);
            }
            return true;
            
        }
        return maPiece.ModifierElementSelectionne(nouvellePosition, nouvelleLargeur, nouvelleLongueur);
    }
    
     public boolean SupprimerElementSelectionne()
    {
        return maPiece.SupprimerElementSelectionne();
    }
    
    public Object ObtenirElementSelectionne()
    {
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        return construireDto(element);
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
