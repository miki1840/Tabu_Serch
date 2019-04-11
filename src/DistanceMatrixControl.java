import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class DistanceMatrixControl {
    private int[][] matrix;
    private int bestScore;
    private String name;
    private int size;

    public boolean loadMatrix(String path)
    {
        boolean toReturn =false;
        try {
            Scanner reader=new Scanner(new File(path));
            name=reader.nextLine();
            reader.nextLine();
            reader.nextLine();
            reader.next();
            size=reader.nextInt();
            reader.nextLine();
            reader.nextLine();
            reader.nextLine();
            matrix=new int[size][size];
            String n=reader.next();
            for (int i=0;i<size;i++) for (int j=0;j<size;j++)matrix[i][j]=reader.nextInt();
            toReturn=true;
        }catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
        return toReturn;

    }
    public int[][] gen(int size)
    {
        Random gen = new Random();
        matrix = new int[size][size];
        for (int i=0;i<size; i++) matrix[i][i]=0;
        for (int i=0; i<size;i++) for (int j=0; j<size; j++) if(i!=j) matrix[i][j]= gen.nextInt(200);
        return matrix;
    }

    public void printMatrix()
    {

        for(int i=0;i<matrix.length;i++)
        {
            System.out.print("\n");
            for (int j=0;j<matrix.length; j++)
            {
                System.out.printf("%10d ",matrix[i][j]);
            }
        }    }
    public int[][] getMatrix()
    {return  matrix;}

}
