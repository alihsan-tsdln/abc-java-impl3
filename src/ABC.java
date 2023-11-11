import java.util.Random;

public class ABC {
    private final int dimension;
    private final int max_eval;
    private final int limit;
    private final int food_number;
    private final double lower_bound;
    private final double upper_bound;
    public double[][] solution;
    private final double[] fitness;
    public double[] func_res;
    public double[] prob;
    private final int[] trial;
    private double best_solve;
    private int eval_count = 0;
    private int cycle_count = 0;
    private Random random;
    private int seed;
    private boolean randomSeed = true;




    public ABC(int NUMBER_OF_POPULATION,
               int DIMENSION,
               int MAXIMUM_EVALUATION,
               int LIMIT,
               double LOWER_BOUND,
               double UPPER_BOUND) {
        dimension = DIMENSION;
        max_eval = MAXIMUM_EVALUATION;
        limit = LIMIT;
        lower_bound = LOWER_BOUND;
        upper_bound = UPPER_BOUND;
        food_number = NUMBER_OF_POPULATION / 2;
        solution = new double[food_number][dimension];
        fitness = new double[food_number];
        func_res = new double[food_number];
        prob = new double[food_number];
        trial = new int[food_number];
    }

    private double function_calculator(double[] sol) {
        String name = "sphere";
        if(name.equals("sphere")) {
            double res = 0.0;
            for (double v : sol) {
                res = res + v * v;
            }
            return res;
        }
        return 9999.9999;
    }

    public void setRandomSeed(int seed) {
        randomSeed = false;
        this.seed = seed;
    }

    private double fitness_calculator(double func) {
        eval_count++;
        return (func < 0) ? 1.0 + Math.abs(func) :1.0/(1.0+func);
    }

    private void init(int index) {
        for(int i = 0; i < dimension; i++) {
            solution[index][i] = lower_bound + random.nextDouble()  * (upper_bound - lower_bound);
        }
        func_res[index] = function_calculator(solution[index]);
        fitness[index] = fitness_calculator(func_res[index]);
        trial[index] = 0;
    }

    public void initilaze() {
        if(randomSeed)
            random = new Random();
        else
            random = new Random(seed);
        int i = 0;
        while(i < food_number && stopping_condition()) {
            init(i);
            i++;
        }
        best_solve = func_res[0];
    }

    public void memorize_best_source() {
        for(int i = 0; i < food_number; i++) {
            if(func_res[i] < best_solve) {
                best_solve = func_res[i];
            }
        }
    }

    public boolean stopping_condition() {
        return eval_count < max_eval;
    }

    private void generate_new_solution(int index) {
        int param2change = (int) (random.nextDouble() * dimension);
        int rand_k = (int) (random.nextDouble() * food_number);

        while(rand_k == index) {
            rand_k = (int) (random.nextDouble() * food_number);
        }
        double[] new_solution = new double[dimension];
        System.arraycopy(solution[index], 0, new_solution, 0, dimension);
        new_solution[param2change] = solution[index][param2change] + (random.nextDouble() - 0.5) * 2.0 * (solution[index][param2change] - solution[rand_k][param2change]);
        if(new_solution[param2change] > upper_bound) {
            new_solution[param2change] = upper_bound;
        } else if (new_solution[param2change] < lower_bound) {
            new_solution[param2change] = lower_bound;
        }

        double result_of_new = function_calculator(new_solution);
        double fitness_of_new = fitness_calculator(result_of_new);
        if(fitness_of_new >  fitness[index]) {
            if (dimension >= 0) System.arraycopy(new_solution, 0, solution[index], 0, dimension);
            fitness[index] = fitness_of_new;
            func_res[index] = result_of_new;
            trial[index] = 0;
        }
        else {
            trial[index]++;
        }
    }

    public void send_employed_bees() {
        int i = 0;
        while(i < food_number && stopping_condition()) {
            generate_new_solution(i);
            i++;
        }
    }

    public void calculate_probabilites() {
        double maxFit = fitness[0];
        int i;
        for(i = 1; i < food_number; i++) {
            if(fitness[i] > maxFit)
                maxFit = fitness[i];
        }
        for(i = 0; i < food_number; i++) {
            prob[i] = (0.9 * (fitness[i]/maxFit)) + 0.1;
        }

    }

    public void send_onlooker_bees() {
        int i = 0,j = 0;
        while(j < food_number && stopping_condition()) {
            double r = random.nextDouble();
            if(prob[i] > r) {
                j++;
                generate_new_solution(i);
            }
            i++;
            i = i % food_number;
        }
    }

    public void send_scout_bees() {
        int maxIndex = 0;
        int maxTrial = 0;
        for(int i = 0; i < food_number; i++) {
            if(trial[i] > maxTrial) {
                maxIndex = i;
                maxTrial = trial[i];
            }
        }
        if(trial[maxIndex] >= limit)
            init(maxIndex);
    }

    public double getBest_solve() {
        return best_solve;
    }

    public void increase_cycle() {
        cycle_count++;
    }

    public int getCycle_count() {
        return cycle_count;
    }
}
