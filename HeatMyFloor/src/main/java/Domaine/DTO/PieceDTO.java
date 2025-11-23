package Domaine.DTO;

import Domaine.Entite.ElementChauffant;
import Domaine.Entite.ElementSelectionnable;
import Domaine.Entite.Meuble;
import Domaine.Entite.Piece;
import java.awt.Polygon;
import java.util.ArrayList;
import Domaine.Entite.MeubleAvecDrain;
import Domaine.Entite.MeubleSansDrain;

public class PieceDTO {

    private Polygon forme;
    private ArrayList<MeubleDTO> meubles;

    public PieceDTO(Polygon p_forme) {
        forme = p_forme;
        meubles = new ArrayList<>();
    }

    public PieceDTO(Piece p_piece) {
        forme = p_piece.getForme();
        meubles = new ArrayList<>();
        for (ElementSelectionnable element: p_piece.getElements()) {
            if (element instanceof MeubleAvecDrain meubleAvecDrain) {
                meubles.add(new MeubleDTO(meubleAvecDrain));
            } else if (element instanceof MeubleSansDrain meubleSansDrain) {
                meubles.add(new MeubleDTO(meubleSansDrain));
            } else if (element instanceof Meuble meuble) {
                meubles.add(new MeubleDTO(meuble));
            }else if (element instanceof ElementChauffant elementChauffant) {
                meubles.add(new MeubleDTO(elementChauffant.getPosition(), elementChauffant.getLongueur(), elementChauffant.getLargeur(), "ELEMENT_CHAUFFANT"));
            }
        }
    }

    public Polygon getForme() {
        return forme;
    }

    public ArrayList<MeubleDTO> getMeubles() {
        return meubles;
    }
}
