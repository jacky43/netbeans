package Domaine.Entite;


import Domaine.DTO.MeubleDTO;
import Domaine.DTO.ElementChauffantDTO;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Piece {
    
    private Polygon forme;
  
    private ArrayList<ElementSelectionnable> elements;
    
    public Piece()
    {
       elements = new ArrayList<>();
    }
    
    public Polygon getForme()
    {
        return forme;
    }
    
    public Point getPositionPiece()
    {
        return new Point(forme.xpoints[0], forme.ypoints[0]);
    }
    
    public void setForme(Polygon p_forme)
    {
        forme = p_forme;
    }
    
    public ArrayList<Meuble> getMeubles()
    {
        ArrayList<Meuble> meubles = new ArrayList<>();
        for (ElementSelectionnable element : elements)
            if (element instanceof Meuble meuble)
                meubles.add(meuble);
        
        return meubles;
    }
    
    public ArrayList<ElementSelectionnable> getElements()
    {          
        return new ArrayList<>(elements);
    }
    
    public void AjouterMeuble(MeubleDTO dto)
    {
        if(dto.estAvecDrain())
            elements.add(new MeubleAvecDrain(dto));
        else
            elements.add(new MeubleSansDrain(dto));
    }
 
    public ElementSelectionnable SelectionnerElement(Point p_position)
    {
        ElementSelectionnable elementSelectionne = null;
        for (ElementSelectionnable element : elements)
        {
            if (contientPoint(element, p_position))
            {
                elementSelectionne = element;
                break;
            }
        }
        if(elementSelectionne != null){
            for (ElementSelectionnable element: elements)
                element.setSelectionne(element == elementSelectionne);
        }
        else
        {
            for (ElementSelectionnable element: elements)
                element.setSelectionne(false);
        }
        return elementSelectionne;
    }
     
    public boolean SupprimerElementSelectionne()
    {
        ElementSelectionnable elementSelectionne = ObtenirElementSelectionne();
        if(elementSelectionne != null)
            return SupprimerElement(elementSelectionne.getId());
        
        return false;
    }    
    
    public boolean SupprimerElement(UUID id)
    {   
        for (Iterator<ElementSelectionnable> iterator = elements.iterator(); iterator.hasNext();)
        {
            ElementSelectionnable element = iterator.next();
            if(element.getId().equals(id)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    public ElementSelectionnable ObtenirElementSelectionne()
    {
        for (ElementSelectionnable element: elements)
            if(element.estSelectionne())
                return element;
        return null;
    }
    
    public boolean ModifierElementSelectionne(Point nouvellePosition, Integer nouvelleLargeur, Integer nouvelleLongueur)
    {
        ElementSelectionnable selection = ObtenirElementSelectionne();
        if(selection == null)
            return false;
        if(nouvellePosition != null)
            selection.setPosition(nouvellePosition);
        if(nouvelleLargeur != null)
            selection.setLargeur(nouvelleLargeur);
        if(nouvelleLongueur != null)
            selection.setLongueur(nouvelleLongueur);
        return true;
    }
    
    private boolean contientPoint(ElementSelectionnable element, Point position)
    {
        int minX = element.getPosition().x;
        int maxX = element.getPosition().x + element.getLargeur();
        int minY = element.getPosition().y - element.getLongueur();
        int maxY = element.getPosition().y;
        return position.x >= minX && position.x <= maxX &&
               position.y >= minY && position.y <= maxY; 
    }
    
    public Point ObtenirOrigine()
    {
        Point position = getPositionPiece();
        return new Point(position.x, position.y + (int)forme.getBounds().getHeight());
    }
    
    public void AjouterElementChauffant(ElementChauffantDTO dto)
    {
        //placer l'élément sur le mur le plus proche de sa position devra être modifié pour les pièces irrégulières
        Point positionSurMur = TrouverPositionSurMurLePlusProche(dto.getPosition(), dto.getLargeur(), dto.getLongueur()); 
        dto = new ElementChauffantDTO(positionSurMur, dto.getLargeur(), dto.getLongueur());
        elements.add(new ElementChauffant(dto));
    }
    
    public Point TrouverPositionSurMurLePlusProche(Point position, int largeur, int longueur){
        
        if(forme == null || forme.npoints < 2)
            return position;
        
        double distanceMinimale = Double.MAX_VALUE;
        Point meilleurPoint = new Point(position);
        
        //parcours des segments
        for (int i = 0; i < forme.npoints; i++){
            int pointSuivant = (i + 1)% forme.npoints;
            
            Point p1 = new Point(forme.xpoints[i], forme.ypoints[i]);
            Point p2 = new Point(forme.xpoints[pointSuivant], forme.ypoints[pointSuivant]);
            
            Point pointProche = TrouverPointLePlusProcheSegment(position, p1, p2, largeur, longueur);
            double distance = position.distance(pointProche);
            
            if(distance < distanceMinimale){
                distanceMinimale = distance;
                meilleurPoint = pointProche;
            }
        }
        return meilleurPoint;
    }
    
    private Point TrouverPointLePlusProcheSegment(Point point, Point segmentDebut, Point segmentFin, int largeur, int longueur){
        
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;
        
        double longueurSegment = Math.sqrt(dx * dx + dy * dy);
        if(longueurSegment == 0)
            return new Point(segmentDebut);
        
        //normaliser le vecteur
        dx /= longueurSegment;
        dy /= longueurSegment;
        
        
        //placer l'élément avec sa plus longeuer dimension sur le mur
        boolean estHorizontal = Math.abs(dy) < Math.abs(dx);
        boolean estVertical = Math.abs(dx) < Math.abs(dy);
        
        double projectionX ;
        double projectionY ;
        
         if(estHorizontal){
            projectionX = Math.max(Math.min(segmentDebut.x, segmentFin.x),
                    Math.min(point.x, Math.max(segmentDebut.x, segmentFin.x)));
            projectionY = segmentDebut.y;
        }else if(estVertical){
            projectionX = Math.max(Math.min(segmentDebut.y, segmentFin.y),
                    Math.min(point.y, Math.max(segmentDebut.y, segmentFin.y)));
            projectionY = segmentDebut.y;
        }else{
            double vx = point.x - segmentDebut.x;
            double vy = point.y - segmentDebut.y;
            double projection = vx * dx + vy * dy;
            
            projection = Math.max(0, Math.min(longueurSegment, projection));
            
            projectionX = segmentDebut.x + projection * dx;
            projectionY = segmentDebut.y + projection * dy;   
        }
        
        int posX = (int) Math.round(projectionX);
        int posY = (int) Math.round(projectionY);
        
        return new Point(posX, posY);
    }
    
    public boolean VerifierAdjacentMur(ElementChauffantDTO dto)
    {
        //vérifie si deux point de element sont sur une des lignes de mur de la piece
        Point position = dto.getPosition();
        int largeur = dto.getLargeur();
        int longueur = dto.getLongueur();
        
        //verifie chaque segment de mur
        for(int i = 0; i < forme.npoints; i++){
            int p = (i + 1) % forme.npoints;
            Point position1 = new Point(forme.xpoints[i], forme.ypoints[i]);
            Point position2 = new Point(forme.xpoints[p], forme.ypoints[p]);
            
            //vérifier si l'un des coins de l'élément est proche du segment
            double tolerance = 5.0;
            if(DistancePointsegment(position,position1, position2) < tolerance ||
               DistancePointsegment(new Point(position.x + largeur, position.y), position1, position2) < tolerance ||
               DistancePointsegment(new Point(position.x, position.y + longueur), position1, position2) < tolerance ||
               DistancePointsegment(new Point(position.x + largeur, position.y + longueur), position1, position2) < tolerance){
                return true;
            }
        }
        
        return false;
    }
    
    //calcul la distance entre un point et un segment de ligne
    private double DistancePointsegment(Point point, Point segmentDebut, Point segmentFin){
        double dx = segmentFin.x - segmentDebut.x;
        double dy = segmentFin.y - segmentDebut.y;
        
        double longueurSegment = dx * dx + dy * dy;
        if(longueurSegment == 0)
            return point.distance(segmentDebut);
        
        double d = ((point.x - segmentDebut.x) * dx + (point.y - segmentDebut.y) * dy ) / longueurSegment;
        d = Math.max(0, Math.min(1, d));
        
        double projX = segmentDebut.x + d * dx;
        double projY = segmentDebut.y + d * dy;
        
        return point.distance(projX, projY);
    }
    
    public Point getOrigineAxes()
    {
        Rectangle boundingBox = forme.getBounds();
        return new Point(boundingBox.x, boundingBox.y + boundingBox.height);
    }
}
