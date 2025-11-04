package Domaine.Entite;

import Domaine.DTO.MeubleDTO;
import java.awt.Point;

public class MeubleAvecDrain extends Meuble{
    
    private Point centreDrain;
            
    public MeubleAvecDrain(MeubleDTO dto)
    {
        super(dto);
        centreDrain = dto.getCentreDrain();
    }
    
    public Point getCentreDrain()
    {
        return centreDrain;
    }
    
    public void setCentreDrain(Point p_centreDrain)
    {
        centreDrain = p_centreDrain;
    }
}
