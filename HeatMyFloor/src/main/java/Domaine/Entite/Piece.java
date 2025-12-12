package Domaine.Entite;


import Domaine.DTO.MeubleDTO;
import Domaine.DTO.ElementChauffantDTO;
import Domaine.DTO.ThermostatDTO;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Piece implements Cloneable, Serializable {
    
    private Polygon forme;
  
    private ArrayList<ElementSelectionnable> elements;
    private ArrayList<ElementChauffant> elementChauffants;
    private Membrane menbrane;
    private Thermostat thermostat;
    private Fil fil;
    private int largeurReellePouces;
    private int longueurReellePouces;
    private static final int DPI =6;
    
    public Piece()
    {
       elements = new ArrayList<>();
    }
    
    public Piece(Polygon p_forme)
    {
       forme = p_forme;
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
        if(forme != null){
            Rectangle bounds = forme.getBounds();
            largeurReellePouces = (int) Math.round(bounds.getWidth());
            longueurReellePouces = (int) Math.round(bounds.getHeight());
        }
        //regener le fil
        regenererFilSiNecessaire();
    }
    
     public void DefinirDimensionReeles(int largeurPouces, int longueurPouces){
       this.largeurReellePouces = largeurPouces;
       this.longueurReellePouces = longueurPouces;
       //regener le fil
        regenererFilSiNecessaire();
    }
    
    public int getLargeurPouces(){
        return largeurReellePouces;
    }
    
     public int getLongueurPouces(){
        return longueurReellePouces;
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
        
        //regener le fil
        regenererFilSiNecessaire();
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
                //regener le fil
                regenererFilSiNecessaire();
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
        
        //regener le fil
        regenererFilSiNecessaire();
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
        Point positionInitiale = new Point(0, dto.getLongueur());
        dto = new ElementChauffantDTO(positionInitiale, dto.getLongueur(),dto.getLargeur());
        elements.add(new ElementChauffant(dto));
    }
    
    public Point TrouverPositionSurMurLePlusProche(Point positionRelative, int longueur,int largeur){
        
        if(forme == null || forme.npoints < 2)
            return positionRelative;
        
        Rectangle bounds = forme.getBounds();
        int largeurPiece = (int)bounds.getWidth();
        int hauteurPiece = (int)bounds.getHeight();
        
        int x  = positionRelative.x;
        int y  = positionRelative.y;
        
        int distanceGauche = Math.abs(x - 0);
        int distanceDroite = Math.abs(x - (largeurPiece - largeur));
        int distanceBas = Math.abs(y - longueur);
        int distanceHaut = Math.abs(y - hauteurPiece);
        
        int distanceMin = Math.min(Math.min(distanceGauche, distanceDroite), Math.min(distanceBas, distanceHaut));
        //TODO
        if(distanceMin == distanceGauche){
            return new Point(0, Math.max(longueur, Math.min(y, hauteurPiece)));
        }else if(distanceMin == distanceDroite){
            return new Point (largeurPiece - largeur, Math.max(longueur, Math.min(y, hauteurPiece)));
        }else if (distanceMin == distanceBas){
            return new Point (Math.max(0, Math.min(x, largeurPiece - largeur)), longueur);
        }else{
            return new Point (Math.max(0, Math.min(x, largeurPiece -largeur)), hauteurPiece);
        }
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
    
    @Override
    public Piece clone() {
        Piece copie = new Piece();
        
        if (this.forme != null) {
            int[] xPoints = this.forme.xpoints.clone();
            int[] yPoints = this.forme.ypoints.clone();
            copie.forme = new Polygon(xPoints, yPoints, this.forme.npoints);
        }
        
        copie.elements = new ArrayList<>();
        for (ElementSelectionnable element : this.elements) {
            if (element instanceof MeubleAvecDrain){
                MeubleDTO dto = (MeubleDTO) element.ToDto();
                copie.elements.add(new MeubleAvecDrain(dto));
            } else if (element instanceof MeubleSansDrain) {
                MeubleDTO dto = (MeubleDTO) element.ToDto();
                copie.elements.add(new MeubleSansDrain(dto));
            } else if (element instanceof ElementChauffant) {
                ElementChauffantDTO dto = (ElementChauffantDTO) element.ToDto();
                copie.elements.add(new ElementChauffant(dto));
            }
        }
        return copie;  
    }
    
     // Méthodes pour la membrane
    public void InitialiserMembrane(int espacement, int marge) {
        if(forme == null) return;
        Rectangle bounds = forme.getBounds();
        int largeur = largeurReellePouces > 0 ? largeurReellePouces : (int) Math.round(bounds.getWidth());
        int longueur = longueurReellePouces > 0 ? longueurReellePouces : (int) Math.round(bounds.getHeight());
        //menbrane = new Membrane((int)bounds.getWidth(), (int)bounds.getHeight(), espacement, marge);
        menbrane = new Membrane(largeur, longueur, espacement, marge);
        
        //regener le fil
        regenererFilSiNecessaire();
    }
    
    public Membrane getMembrane() {
        return menbrane;
    }
    
    // Méthodes pour le thermostat
    public void AjouterThermostat(ThermostatDTO dto) {
        thermostat = new Thermostat(dto);
        elements.add(thermostat);
    }
    
    public Thermostat getThermostat() {
        return thermostat;
    }
    
    // Méthodes pour le fil chauffant
    private int longueurMaxFil = 0;
    private int distanceMaxLigneFil = Integer.MAX_VALUE;
    private boolean autoRegenerationActive = true;
    
    public void TracerFilChauffant(int longueurMax, int distanceMaxLigne) {
        if (thermostat != null && menbrane != null) {
            this.longueurMaxFil = longueurMax;
            this.distanceMaxLigneFil = distanceMaxLigne;
            TraceurFil traceur = new TraceurFil(menbrane, getMeubles(), elements, distanceMaxLigne);
            fil = traceur.tracerFilAutomatique(thermostat.getPosition(), longueurMax);
        }
    }

    //Active ou désactive la régénération automatique du fil Quand activée, le fil sera recalculé après chaque modification
    public void setAutoRegenerationFil(boolean activer) {
        this.autoRegenerationActive = activer;
    }
    
    public boolean estAutoRegenerationActive() {
        return autoRegenerationActive;
    }
    
    //Régénère automatiquement le fil si l'auto-régénération est active
    private void regenererFilSiNecessaire() {
        if (!autoRegenerationActive ) {
            return;
        }
         if (thermostat == null ) {
            return;
        }
        if (menbrane == null ) {
            return;
        }
        if (longueurMaxFil <= 0) {
            return;
        }
        TraceurFil traceur = new TraceurFil(menbrane, getMeubles(), elements, distanceMaxLigneFil);
        fil = traceur.tracerFilAutomatique(thermostat.getPosition(), longueurMaxFil);
    }

    
    public Fil getFilChauffant() {
        return fil;
    }
    
    public void SupprimerFilChauffant() {
        fil = null;
    }

    public void Sauvegarder(String path) throws IOException
    {
        
        FileOutputStream fileout = new FileOutputStream(path + "\\piece.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(this);
        out.close();
        fileout.close();     
    }
    
    public void importer(String path) throws IOException, ClassNotFoundException
    {
        Piece p = null;
        FileInputStream filein = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(filein);
        p = (Piece) in.readObject();
        in.close();
        filein.close();
        this.elementChauffants = p.elementChauffants;
        this.elements = p.elements;
        this.forme = p.forme;
        this.thermostat = p.thermostat;
        this.fil = p.fil;
        this.menbrane = p.menbrane;
        this.largeurReellePouces = p.largeurReellePouces;
        this.longueurReellePouces = p.longueurReellePouces;
    }
}
