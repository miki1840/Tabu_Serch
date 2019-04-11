import java.util.Random;
import java.util.Scanner;
//pkt co jakiś czas odnośnik do 2 min co 20 s
public abstract class Menu
{
    public static void menu()
    {
        boolean exist=true;
        int chose;
        Scanner sc = new Scanner(System.in);
        DistanceMatrixControl matrix= new DistanceMatrixControl();
        boolean matrixinit=false;
        TSP_TS ts = new TSP_TS();
        long ttl=120;
        Test test;
        String path;
        int [] result;
        int nb=1;

        while(exist)
        {
            System.out.println("1. Wczytaj macierz\n2. Podaj warunek stopu\n3. Tabus Serch \n4. Testy\n 5. Testuj 3\n0. KONIEC");
            chose=sc.nextInt();
            switch (chose)
            {
                case 1:
                    System.out.println("Podaj sciezke ");
                    path = "D:\\tsp2\\ftv47.atsp";//sc.nextLine();
                    if(matrix.loadMatrix(path)) matrix.printMatrix();
                    break;
                case 2:
                    System.out.println("Podaj czas wykonywania w sekundach");
                    ttl=sc.nextInt();
                    break;

                case 3:
                    ts=new TSP_TS();
                    ts.init(Variables.cadnceLong(matrix.getMatrix().length),ttl*Variables.NS, Variables.diversificationLimit(matrix.getMatrix().length),matrix.getMatrix(),1);
                    System.out.println();
                    ts.run();
                    System.out.println("Najlepszy koszt to: " + ts.getCost());
                    System.out.println();
                    result = ts.getPath();
                    for (int i=0; i<result.length-1;i++) System.out.print(result[i]+"=>");
                    System.out.print(result[result.length-1]);
                    break;
                case 4:
                    test = new Test("D:\\tsp2");
                    test.run();
                    break;
                case 5:
                    nb=1;
                    int[] bsestKnown = {1776, 2755, 2465};
                    path = "D:\\tsp2\\ftv47.atsp";
                    if (matrix.loadMatrix(path))
                    {
                        ts=new TSP_TS();
                        ts.init(Variables.cadnceLong(matrix.getMatrix().length),120*Variables.NS, Variables.diversificationLimit(matrix.getMatrix().length),matrix.getMatrix(),nb);
                        ts.run();
                        System.out.println("ftv47\n cost: "+ts.getCost());
                        System.out.println("błąd to "+ ((double)ts.getCost()-(double)bsestKnown[0])*100d/(double)bsestKnown[0]+"%");
                        result = ts.getPath();
                        for (int i=0; i<result.length-1;i++) System.out.print(result[i]+"=>");
                        System.out.print(result[result.length-1]+"\n");
                    }
                    path = "D:\\tsp2\\ftv170.atsp";
                    if (matrix.loadMatrix(path))
                    {
                        ts=new TSP_TS();
                        ts.init(Variables.cadnceLong(matrix.getMatrix().length),120*Variables.NS, Variables.diversificationLimit(matrix.getMatrix().length),matrix.getMatrix(),nb);
                        ts.run();
                        System.out.println("ftv170\n cost: "+ts.getCost());
                        System.out.println("błąd to "+ ((double)ts.getCost()-(double)bsestKnown[1])*100d/(double)bsestKnown[1]+"%");
                        result = ts.getPath();
                        for (int i=0; i<result.length-1;i++) System.out.print(result[i]+"=>");
                        System.out.print(result[result.length-1]+"\n");
                    }
                    path = "D:\\tsp2\\rbg403.atsp";
                    if (matrix.loadMatrix(path))
                    {
                        ts=new TSP_TS();
                        ts.init(Variables.cadnceLong(matrix.getMatrix().length),120*Variables.NS, Variables.diversificationLimit(matrix.getMatrix().length),matrix.getMatrix(),nb);
                        ts.run();
                        System.out.println("rbg403\n cost: "+ts.getCost());
                        System.out.println("błąd to "+ ((double)ts.getCost()-(double)bsestKnown[2])/(double)bsestKnown[2]*100d+"%");
                        result = ts.getPath();
                        for (int i=0; i<result.length-1;i++) System.out.print(result[i]+"=>");
                        System.out.print(result[result.length-1]+"\n");
                    }
                    break;



                case 0:
                    exist=false;
                    break;
                    default:
                        System.out.println("Wybor poza zakresem");
                        break;

            }
        }
    }

}
