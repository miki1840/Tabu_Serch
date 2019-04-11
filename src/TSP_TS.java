import java.util.*;

public class TSP_TS
{
    private int [][] distanceMatrix;
    private boolean matrixinit=false;
    private int bestCost;
    private int[] bestPath;
    private int cost;
    private int[] path;
    private int diversificationLimit;
    private long lifetime;
    private int cadence;
    private int neighbourhoodType;
    private long timeBest;
    private long start;


    /**
     * funkcja inicjująca zmienne - parametry zadania
     * @param cadence długość kadencji na liście tabu
     * @param lifetime - czas trwania algorytmu
     * @param diversificationLimit - ilość iteracji bez poprawy, aby użyć dywersyfikacji
     * @param distanceMatrix - macierz odległości
     */
    public void init(int cadence, long lifetime, int diversificationLimit, int[][] distanceMatrix, int neighbourhoodType)
    {
        this.distanceMatrix=distanceMatrix;
        matrixinit=true;
        this.diversificationLimit=diversificationLimit;
        this.lifetime=lifetime;
        this.cadence=cadence;
        this.neighbourhoodType=neighbourhoodType;
    }
    /**
     * Funkcja zamienia miejscami elementy x i y w tablicy path.
     * @param path tablica
     * @param x pierwsze \miejsce do zamiany
     * @param y drugie miejsce do zamiany
     */
    private void swap(int [] path, int x, int y)
    {
        int t = path[y];
        path[y]=path[x];
        path[x]=t;
    }

    /**
     * Metoda odwraca kolejność fragmentu śceżki
     * @param path ścieżka
     * @param x początek odwracania
     * @param y konec odwracania
     */
    private void invert(int[] path, int x, int y)
    {
        if (x>y)
        {
            int t =y;
            y=x;
            x=t;
        }
        int numberOfCycle=(y-x)/2;
        for (int i=0;i<numberOfCycle;i++)
        {
            int t=path[x];
            path[x]=path[y];
            path[y]=t;
        }
    }

    /**
     * funkcja realizuje sąsiedztwo insert. Wstawia element z pozycji  w pozycje y
     * @param path ścieżka
     * @param x index źródłowy elementu
     * @param y index docelowy do przeniesienia
     */
    private void insert(int[] path, int x, int y)
    {
        if (x<y)
        {
            int t= path[x];
            for (int i=x;i<y;i++) path[i]=path[i+1];
            path[y]=t;
        }
        else
        {
            int t= path[y];
            for(int i=y;i>x+1;i++)
            {
                path[i]=path[i-1];
            }
            path[x]=t;
        }
    }
    private void makeMove(int[] path, int[] move)
    {
        switch (neighbourhoodType) {
            case 1:
                swap(path, move[0], move[1]);
                break;
            case 2:
                invert(path, move[0],move[1]);
                break;
            case 3:
                insert(path, move[0], move[1]);
                break;
        }
    }

    /**
     * Funkcja liczy wartość ścieżki podanej w parametrze
     * @param tab ścieżka do policzenia
     * @return długośc ścieżki
     */

    private int rateRute(int[] tab)
    {
        int score=0;
        for (int i=1; i<distanceMatrix.length;i++)
        {
            score+=distanceMatrix[tab[i-1]][tab[i]];
        }
        return score;
    }

    /**
     * Funkcja liczy rozwiązanie problemu TSP metodą zachłann oraz ustawia rozwiązanie jako najlepsze oraz bierzące
     */
    private void greedy()
    {
        int[] path= new int[distanceMatrix.length+1];
        int cost=0;
        HashSet<Integer> vortexes = new HashSet<>();
        path[0]=0;
        for (int i=1;i<distanceMatrix.length;i++) vortexes.add(i);
        int prevVortex=0;
        for (int i=1;i<distanceMatrix.length;i++)
        {
            int bestS = Integer.MAX_VALUE;
            int bestV=0;
            for(int x : vortexes)
            {
                if(distanceMatrix[prevVortex][x]<bestS)
                {
                    bestS=distanceMatrix[prevVortex][x];
                    bestV=x;
                }
            }
            path[i]=bestV;
            cost+=distanceMatrix[prevVortex][bestV];
            prevVortex=bestV;
            vortexes.remove(bestV);
        }
        path[path.length-1]=0;
        cost+=distanceMatrix[prevVortex][0];
        this.bestCost=cost;
        this.bestPath=path;
        this.cost=bestCost;
        this.path=bestPath;
    }

    /**
     * Setter tablicy odległości
     * @param distanceMatrix gotowa tablica odległości
     */
    public void initMatrix(int[][] distanceMatrix)
    {
        this.distanceMatrix=distanceMatrix;
        matrixinit=true;
    }

    /**
     * losuje dwie liczby które interpretowane są jako ruch
     * @return tablica int z 2 różnymi liczbami
     */
    private int[] randomMove()
    {
        Random random = new Random();
        int[] move = new int[2];
        move[0]= random.nextInt(distanceMatrix.length-2)+1;
        do
        {
            move[1]=random.nextInt(distanceMatrix.length-2)+1;
        }while(move[0]==move[1]);
        return move;
    }

    /**
     * funkcja "restartuje algoprytm w nowym punkcie startowym.
     */
    private void diversification()
    {
        int[] newsolution = new int[path.length];
        ArrayList<Integer> vortexes = new ArrayList<>();
        for (int i=1;i<newsolution.length-1;i++) vortexes.add(i);
        Random random = new Random();
        newsolution[0]=0;
        newsolution[newsolution.length-1]=0;
        for(int i=1;i<newsolution.length-2;i++)
        {
            int a = random.nextInt(vortexes.size()-1);
            newsolution[i]=vortexes.get(a);
            vortexes.remove(a);
        }
        newsolution[newsolution.length-1]=vortexes.get(0);
    }

    /**
     * metoda odopwiadająca za uruchomienie algorytmu i nadzorowanie jego działania
     */
    public void run()
    {
        start=System.nanoTime();
        lifetime+=start;
        int counter = 0; //licznik do dywersyfikacji
        int stackCounter =0; //licznik który zapobiega zapełnieniu listy.
        boolean exist=true;
        if (matrixinit)
        {
            TabuList tabulist = new TabuList(distanceMatrix.length, cadence);
            greedy();
            while (exist)
            {
                int[] move=randomMove();
                if(!tabulist.isOnTabuList(move[0], move[1])) //sprawdzamy czy ruch jest dostępny
                {
                    int[] nextpath = path.clone();
                    int nextcost;
                    counter++;
                    stackCounter=0; //resetowanie licznika zapchania tabulisty
                    makeMove(nextpath, move);
                    nextcost=rateRute(nextpath);
                    tabulist.increaseCadence();
                    tabulist.addMove(move[0],move[1]);
                    if(nextcost<cost)
                    {
                        path=nextpath;
                        bestCost=nextcost;
                        counter=0;

                    if (cost<bestCost)
                    {
                        timeBest=System.nanoTime()-start;
                        bestPath=path;
                        bestCost=cost;

                    }
                    }
                }
                else //sprawdzam kryterium aspiracji;
                {
                    //TODO trzeba opracować kare (z karą jest gorzej)
                    stackCounter++;
                    int[] nextPath = path.clone();
                    makeMove(nextPath, move);
                    int nextcost = rateRute(nextPath);
                    if(nextcost < cost) {
                        path = nextPath;
                        cost = nextcost;
                        counter = 0;

                        if (cost < bestCost) {
                            timeBest = System.nanoTime() - start;
                            bestPath = path;
                            bestCost = cost;
                        }
                    }

                }
                counter++;
                if (counter>diversificationLimit) //jeżeli wynik nie poprawił się przez zadaną ilość iteracji, algorytm "restartuje" się w nowym punkcie
                {
                    diversification();
                    tabulist.reset();
                    counter=0;
                }
                if(stackCounter>Variables.stackFunction(distanceMatrix.length))
                {
                    tabulist.increaseCadence(); //jeśli długo nie można wylosować niezablokowanego ruchu, "popychamy" listę}
                    stackCounter=0;
                }
                if(System.nanoTime()>lifetime) exist = false; //jeśli czas zostanie przekroczony, pętla zakńczy działanie
            }

        }
        else System.out.println("Nie zainicjowano tablicy");

    }

    public int[] getPath() {
        return bestPath;
    }
    public int getCost()
    {
        return bestCost;
    }

    public long getTimeBest()
    {
        return timeBest;
    }
}
