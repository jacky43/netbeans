package Domaine;

import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
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
            
    public ArrayList<MeubleDTO> ObtenirMeubles()
    {
        ArrayList<MeubleDTO> dtos = new ArrayList<>();
        for (Meuble meuble : maPiece.getMeubles())
            dtos.add(construireMeubleDto(meuble));
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
    
    public MeubleDTO SelectionnerElement(Point position)
    {
        ElementSelectionnable elementSelectionne = maPiece.SelectionnerElement(position);
        return construireMeubleDto(elementSelectionne);
    }
    
    public boolean ModifierMeubleSelectionne(Point nouvellePosition, int nouvelleLargeur, int nouvelleLongueur)
    {
        return maPiece.ModifierMeubleSelectionne(nouvellePosition, nouvelleLargeur, nouvelleLongueur);
    }
    
     public boolean SupprimerMeubleSelectionne()
    {
        return maPiece.SupprimerMeubleSelectionne();
    }
    
    public MeubleDTO ObtenirmeubleSelectionne()
    {
        ElementSelectionnable element = maPiece.ObtenirElementSelectionne();
        return construireMeubleDto(element);
    }
    
     // TODO : 
    public void AfficherMessageErreur(String message)
    {
       
    }
    
    private MeubleDTO construireMeubleDto(ElementSelectionnable element)
    {
        if (element == null)
            return null;
        
        if(element instanceof Meuble meuble)
            return meuble.ToDto();
        
        return null;
    }
    
    public Point ObtenirOrigine()
    {
        return maPiece.ObtenirOrigine();
    }
}
