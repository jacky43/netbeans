package Domaine.DTO;

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
        for (Meuble meuble : p_piece.getMeubles()) {
            if (meuble instanceof MeubleAvecDrain meubleAvecDrain) {
                meubles.add(new MeubleDTO(meubleAvecDrain));
            } else if (meuble instanceof MeubleSansDrain meubleSansDrain) {
                meubles.add(new MeubleDTO(meubleSansDrain));
            } else {
                meubles.add(new MeubleDTO(meuble));
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
