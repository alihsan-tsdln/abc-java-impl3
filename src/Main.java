import java.sql.SQLOutput;
import java.util.Random;

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
        String[] func_names = {"sphere", "rosenbrock", "rastrigin", "griewank", "schwefel", "ackley", "step", "dixon-price" };
        for(String s : func_names) {
            for(int v : dim_sn) {
                ABC[] abcList = new ABC[RUNTIME];
                for(int i = 0; i < RUNTIME; i++) {
                    ABC abc = new ABC(100, v, 1000000, 50 * v, -32, 32, s);
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
                    /*
                    System.out.print(i);
                    System.out.print(". Result : ");
                    System.out.println(abc.getBest_solve());*/
                }
                System.out.print("FUNCTION NAME : ");
                System.out.println(s);
                System.out.print("DIMENSION : ");
                System.out.println(v);
                System.out.print("Mean : ");
                System.out.println(mean_of_abcList(abcList));
                System.out.print("STD : ");
                System.out.println(std_of_abcList(abcList));
            }

            for(int v : dim_sn) {
                ABC[] abcList = new ABC[RUNTIME];
                for(int i = 0; i < RUNTIME; i++) {
                    ABC abc = new ABC(v, 100, 1000000, v * 50, -32, 32, s);
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
                    /*
                    System.out.print(i);
                    System.out.print(". Result : ");
                    System.out.println(abc.getBest_solve());*/
                }
                System.out.print("FUNCTION NAME : ");
                System.out.println(s);
                System.out.print("NUMBER OF POPULATION : ");
                System.out.println(v);
                System.out.print("Mean : ");
                System.out.println(mean_of_abcList(abcList));
                System.out.print("STD : ");
                System.out.println(std_of_abcList(abcList));
            }
        }

    }

}