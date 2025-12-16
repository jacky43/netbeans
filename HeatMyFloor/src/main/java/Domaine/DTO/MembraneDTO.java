
package Domaine.DTO;


public class MembraneDTO {
     private int espacement;
    private int largeurPiece;
    private int longueurPiece;
    private int margeContour;
    
    public MembraneDTO(int largeur, int longueur, int espacement, int marge){
       this.largeurPiece = largeur;
       this.longueurPiece = longueur;
       this.espacement = espacement;
       this.margeContour = marge;
    }
    
     public int getEspacement(){
        return espacement;
    }
    
    public int getMargeContour(){
        return margeContour;
    }
    
    public int getLargeurPiece(){
        return largeurPiece;
    }
    
     public int getLongueurPiece(){
        return longueurPiece;
    }
}
