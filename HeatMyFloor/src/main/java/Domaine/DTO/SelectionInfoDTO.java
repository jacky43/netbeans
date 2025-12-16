package Domaine.DTO;

import java.awt.Point;

public class SelectionInfoDTO {

    public enum TypeSelection {
        MEUBLE_BODY,
        DRAIN,
        ELEMENT_CHAUFFANT,
        THERMOSTAT,
        AUCUN
    }

    private final ElementSelectionnableDTO element;
    private final TypeSelection type;
    private final Point pointClique; // Pour les drains

    public SelectionInfoDTO(ElementSelectionnableDTO element, TypeSelection type, Point pointClique) {
        this.element = element;
        this.type = type;
        this.pointClique = pointClique;
    }

    public static SelectionInfoDTO aucun() {
        return new SelectionInfoDTO(null, TypeSelection.AUCUN, null);
    }

    public ElementSelectionnableDTO getElement() {
        return element;
    }

    public TypeSelection getType() {
        return type;
    }

    public Point getPointClique() {
        return pointClique;
    }

    public boolean estDrain() {
        return type == TypeSelection.DRAIN;
    }

    public boolean estMeubleBody() {
        return type == TypeSelection.MEUBLE_BODY;
    }

    public boolean aSelection() {
        return type != TypeSelection.AUCUN;
    }
}