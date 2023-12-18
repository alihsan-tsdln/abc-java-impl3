public class Main {
    private static double mean_of_abcList(ABC[] abcList) {
        double res = 0.0;
        int len_of_abc = abcList.length;
        for (ABC abc : abcList) {
            res += abc.getBest_solve();
        }
        return res/(double) len_of_abc;
    }

    private static double std_of_abcList(ABC[] abcList) {
        double res = 0.0;
        double mean = mean_of_abcList(abcList);
        for (ABC abc : abcList) {
            double temp = (abc.getBest_solve() - mean);
            res += temp * temp;
        }
        return Math.sqrt(res/(double) abcList.length);
    }

    public static void main(String[] args) {
        int RUNTIME = 30;
        int[] seedBefore = {969189314, 1533861296, 838368876, 864862404, 1795706470, 1085986511, 2021568355, 385635024, 494921918, 1503595098, 554921306, 557742482, 1299332365, 1235237604, 327312245, 367212061, 1890854674, 1790623699, 195697397, 1612806371, 2003507328, 1885570321, 427720419, 1685810262, 1158033159, 2100240345, 1485324378, 707460382, 1534008519, 2107415110, 9987};
        int[] dim_sn = {10, 100, 1000};
        //int[] dim_sn = {10};
        String[] func_names = {"sphere", "rosenbrock", "rastrigin", "griewank", "schwefel", "ackley", "step", "dixon-price" };
        //String[] func_names = {"griewank", "schwefel", "ackley", "step", "dixon-price" };
        double[] bounds = {100, 100, 5.12, 600, 500, 32, 100, 10};
        //double[] bounds = {600, 500, 32, 100, 10};
        StringBuilder csvResultDim = new StringBuilder();
        StringBuilder csvResultNp = new StringBuilder();
        csvResultDim.append(",,ABC").append(",,\n,DIMENSION,10,100,1000\n");
        csvResultNp.append(",,ABC").append(",,\n,NUMBER OF POPULATION,10,100,1000\n");

        for(int s = 0; s < func_names.length; s++) {
            String upperCase = func_names[s].substring(0, 1).toUpperCase();
            csvResultDim.append(upperCase).append(func_names[s].substring(1)).append(",Mean,");
            csvResultNp.append(upperCase).append(func_names[s].substring(1)).append(",Mean,");
            StringBuilder tempString = new StringBuilder();
            tempString.append("STD,");
            for(int v : dim_sn) {
                ABC[] abcList = new ABC[RUNTIME];
                for(int i = 0; i < RUNTIME; i++) {
                    //ABC abc = new ABC(100, v, 500000, 50 * v, -bounds[s], bounds[s], func_names[s]);
                    ABC_VL2 abc = new ABC_VL2(100, v, 500000, 50 * v, -bounds[s], bounds[s], func_names[s]);
                    abc.setRandomSeed(seedBefore[i]);
                    abc.initilaze();
                    abc.memorize_best_source();

                    while(abc.stopping_condition()) {
                        abc.send_employed_bees();
                        abc.calculate_probabilites();
                        abc.send_onlooker_bees();
                        abc.memorize_best_source();
                        abc.send_scout_bees();
                        abc.increase_cycle();
                    }

                    //System.out.println(abc.getCounterOfScout());
                    //System.out.println(abc.getBest_solve());
                    abcList[i] = abc;
                }
                System.out.print("FUNCTION NAME : ");
                System.out.println(func_names[s]);
                System.out.print("DIMENSION : ");
                printV(csvResultDim, tempString, v, abcList);

            }
            csvResultDim.append("\n");
            csvResultDim.append(",").append(tempString).append("\n");
            System.out.println(csvResultDim);

            /*
            tempString = new StringBuilder();
            tempString.append("STD,");

            for(int v : dim_sn) {
                ABC[] abcList = new ABC[RUNTIME];
                for(int i = 0; i < RUNTIME; i++) {
                    //ABC abc = new ABC(v, 100, 500000, v * 50,  -bounds[s], bounds[s], func_names[s]);
                    ABC abc = new ABC_VL(v, 100, 500000, v * 50,  -bounds[s], bounds[s], func_names[s]);
                    abc.setRandomSeed(seedBefore[i]);
                    abc.initilaze();
                    abc.memorize_best_source();

                    while(abc.stopping_condition()) {
                        abc.send_employed_bees();
                        abc.calculate_probabilites();
                        abc.send_onlooker_bees();
                        abc.memorize_best_source();
                        abc.send_scout_bees();
                        abc.increase_cycle();
                    }
                    //System.out.println(abc.getCycle_count());
                    abcList[i] = abc;
                }
                System.out.print("FUNCTION NAME : ");
                System.out.println(func_names[s]);
                System.out.print("NUMBER OF POPULATION : ");
                printV(csvResultNp, tempString, v, abcList);


            }

            csvResultNp.append("\n");
            csvResultNp.append(",").append(tempString).append("\n");
            System.out.println(csvResultNp);*/
        }

        System.out.println("CSV DIMENSION");
        System.out.println(csvResultDim);
        System.out.println("CSV NUMBER OF POPULATION");
        System.out.println(csvResultNp);
    }

    private static void printV(StringBuilder csvResultDim, StringBuilder tempString, int v, ABC[] abcList) {
        System.out.println(v);
        System.out.print("Mean : ");
        System.out.println(mean_of_abcList(abcList));
        csvResultDim.append(mean_of_abcList(abcList)).append(",");
        System.out.print("STD : ");
        System.out.println(std_of_abcList(abcList));
        tempString.append(std_of_abcList(abcList)).append(",");
    }

}