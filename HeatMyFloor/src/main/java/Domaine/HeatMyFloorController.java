package Domaine;

import Domaine.DTO.MeubleDTO;
import Domaine.DTO.PieceDTO;
import Domaine.Entite.Meuble;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.Entite.MeubleSansDrain;
import Domaine.Entite.Piece;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.UUID;

public class HeatMyFloorController {
    
    private Piece maPiece = new Piece();
    private boolean estInitialise = false;
    
    public HeatMyFloorController()
    {

    }
    
    public void InitialiserPiece(Polygon forme)
    {
        maPiece.setForme(forme);
        estInitialise = true;
    }
    
    // TODO : Créer une nouvelle pièece au clic de bouton
    public void CreerPiece(PieceDTO p_piece)
    {
        
    }
    
    public PieceDTO ObtenirPiece()
    {
        return new PieceDTO(maPiece);
    }
    
    // TODO : Ajouter un nouveau meuble
    public void AjouterMeuble(MeubleDTO dto)
    {
        if (!dto.estAvecDrain())
        {
            maPiece.AjouterMeuble(new MeubleSansDrain(dto));
        }
    }
            
    public ArrayList<MeubleDTO> ObtenirMeubles()
    {
        ArrayList<MeubleDTO> dtos = new ArrayList<>();
        for (Meuble meuble : maPiece.getMeubles())
        {
            if (meuble instanceof MeubleAvecDrain meubleAvecDrain)
                dtos.add(new MeubleDTO(meubleAvecDrain));
            else
                dtos.add(new MeubleDTO((MeubleSansDrain)meuble));
        }
        return dtos;
    }
    
    public boolean estInitialise()
    {
        return estInitialise == true;
    }
    
    // TODO : Convertir en dimension (retourner la forme)
    public Point getPositionPiece()
    {
        return maPiece.getPositionPiece();
    }
    
    public void SelectionnerElement(Point position)
    {
        maPiece.SelectionnerElement(position);
    }
    
    // TODO - Staelle : Fonction de suppression du meuble
    public void SupprimerMeuble(UUID id)
    {
        // TODO : Appeler la méthode de la pièce
    }
}
