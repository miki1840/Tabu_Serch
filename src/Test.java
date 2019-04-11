import java.util.Scanner;
import java.io.*;
public class Test
{
    private String path;
    private int maxTime;
    private DistanceMatrixControl[] dmc;
    private int[] bestPath;
    private int bestCost = Integer.MAX_VALUE;
    public Test (String path)
    {
        this.path=path;
        this.maxTime=120;
    }
    public void run()
    {
        try {
            PrintWriter out = new PrintWriter("D:\\tsp2\\oldwynikiSwap");
            DistanceMatrixControl[] tdmc = {new DistanceMatrixControl(), new DistanceMatrixControl(), new DistanceMatrixControl()};
            dmc = tdmc;
            dmc[0].loadMatrix(path + "\\ftv47.atsp");
            dmc[1].loadMatrix(path + "\\ftv170.atsp");
            dmc[2].loadMatrix(path + "\\rbg403.atsp");

            for (int file = 0; file < 3; file++)
            {
                switch (file) {
                    case 0:
                        out.println("ftv47");
                        break;
                    case 1:
                        out.println("ftv170");
                        break;
                    case 2:
                        out.println("ftv403");
                        break;
                }

                long addTime = maxTime / 6;
                long time = addTime;
                for (int j = 0; j < 6; j++) {
                    int bestCost = 0;
                    long bestTime = Long.MAX_VALUE;
                    for (int i = 0; i < 5; i++) {
                        TSP_TS ts = new TSP_TS();
                        ts.init(Variables.cadnceLong(Variables.cadnceLong(dmc[file].getMatrix().length)), time * Variables.NS, Variables.diversificationLimit(dmc[file].getMatrix().length), dmc[file].getMatrix(), 1);
                        ts.run();
                        if(ts.getCost()<this.bestCost)
                        {
                            this.bestCost=ts.getCost();
                            this.bestPath=ts.getPath();
                        }
                        bestCost += ts.getCost();
                        if (ts.getTimeBest() < bestTime) bestTime = ts.getTimeBest();
                    }
                    out.println(time + ";" + bestCost / 5 + ";" + bestTime);
                    out.flush();
                    time += addTime;
                }
                out.println("Najlepszy koszt to: "+this.bestCost +"\n");
                for(int h=0;h<this.bestPath.length-1;h++)out.print(this.bestPath[h]+"=>");
                out.print(this.bestPath[this.bestPath.length-1]);
                out.println();
                out.println();
                maxTime+=120;
            }
            out.close();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }



    }

}
