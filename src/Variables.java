public class Variables
{
    public static final int CADENCE_LONG=100;
    public static final int DIVERSIFICATION_LIMIT=1000;
    public static final long NS =1000000000;
    public static int cadnceLong(int size)
    {
        return (int)Math.pow(size,2/3);
    }
    public static int diversificationLimit(int size)
    {
        return size;
    }
    public static int stackFunction(int size)
    {
        return (int)Math.sqrt((double)size);
    }

}
