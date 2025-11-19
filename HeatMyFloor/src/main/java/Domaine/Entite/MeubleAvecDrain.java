package Domaine.Entite;

import Domaine.DTO.MeubleDTO;
import java.awt.Point;

public class MeubleAvecDrain extends Meuble{
    
    private Point centreDrain;
    private int diametreDrainPixels;
            
    public MeubleAvecDrain(MeubleDTO dto)
    {
        super(dto);
        centreDrain = dto.getCentreDrain();
        this.diametreDrainPixels = (int) Math.round(0.08 * java.awt.Toolkit.getDefaultToolkit().getScreenResolution());
    }
    
    public int getDiametreDrainPixels() {
        return diametreDrainPixels;
    }
    
    public void setDiametreDrainPixels(int diametre) {
        this.diametreDrainPixels = Math.max(5, diametre);
    }
    
    public Point getCentreDrain()
    {
        return centreDrain;
    }
    
    public void setCentreDrain(Point p_centreDrain)
    {
        centreDrain = p_centreDrain;
    }
    
    @Override
    public MeubleDTO ToDto()
    {
        return new MeubleDTO(this);
    }
}