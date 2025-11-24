package Domaine.Entite;

import Domaine.DTO.MeubleDTO;
import java.awt.Point;

public class MeubleAvecDrain extends Meuble{
    
    private Point centreDrain;
    private int diametreDrain;
            
    public MeubleAvecDrain(MeubleDTO dto)
    {
        super(dto);
        centreDrain = dto.getCentreDrain();
        this.diametreDrain = dto.getDiametreDrain();
    }
    
    public int getDiametreDrain() {
        return diametreDrain;
    }
    
    public void setDiametreDrain(int diametre) {
        this.diametreDrain = diametre;
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