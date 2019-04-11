import java.util.LinkedList;
import java.util.List;

public class TabuList
{
    private int cadence=0;
    private int cadenceLong;
    private int instanceSize;
    private int[][] tabu;
    public TabuList(int size, int cadence)
    {
        this.instanceSize=size;
        this.tabu=new int[size][size];
        this.cadenceLong=cadence;
    }
    public void addMove(int x, int y)
    {
        tabu[x][y]=cadence+cadenceLong;
        tabu[y][x]=cadence+cadenceLong;
    }
    public boolean isOnTabuList(int x, int y)
    {
        if(tabu[x][y]<cadence)return false;
        else return true;
    }
    public void increaseCadence()
    {
        cadence++;
    }
    public void setCadenceLong(int cadence)
    {
        this.cadenceLong=cadence;
    }
    public void reset()
    {
        cadence=0;
        tabu=new int[instanceSize][instanceSize];
    }
    public boolean tabuAspiration(int x, int y)
    {
        if(tabu[x][y]<cadence+cadenceLong)return false;
        else return true;
    }
}
