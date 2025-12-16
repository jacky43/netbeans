
package Domaine.DTO;


public class MembraneDTO {
      private int espacement;
     private int largeurPiece;
     private int longueurPiece;
     private int margeContour;
     private int offsetX;
     private int offsetY;
    
     public MembraneDTO(int largeur, int longueur, int espacement, int marge, int offsetX, int offsetY){
         this.largeurPiece = largeur;
         this.longueurPiece = longueur;
         this.espacement = espacement;
         this.margeContour = marge;
         this.offsetX = offsetX;
         this.offsetY = offsetY;
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

     public int getOffsetX(){
        return offsetX;
    }

     public int getOffsetY(){
        return offsetY;
    }
}

