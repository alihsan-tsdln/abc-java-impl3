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
        ABC[] abcList = new ABC[RUNTIME];
        for(int i = 0; i < RUNTIME; i++) {
            ABC abc = new ABC(100, 10, 1000000, 500, -32, 32);
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
            System.out.println(abc.getCycle_count());
            abcList[i] = abc;
            System.out.print(i);
            System.out.print(". Result : ");
            System.out.println(abc.getBest_solve());
        }
        System.out.print("Mean : ");
        System.out.println(mean_of_abcList(abcList));
        System.out.print("STD : ");
        System.out.println(std_of_abcList(abcList));
    }
}