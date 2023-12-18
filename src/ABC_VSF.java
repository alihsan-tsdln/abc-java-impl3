public class ABC_VSF extends ABC{
    double scaling_factor;
    public ABC_VSF(int NUMBER_OF_POPULATION, int DIMENSION, int MAXIMUM_EVALUATION, int LIMIT, double LOWER_BOUND, double UPPER_BOUND, double SCALING_FACTOR, String FUNCTION_NAME) {
        super(NUMBER_OF_POPULATION, DIMENSION, MAXIMUM_EVALUATION, LIMIT, LOWER_BOUND, UPPER_BOUND, FUNCTION_NAME);
        scaling_factor = SCALING_FACTOR;
    }

    @Override
    void generate_new_solution(int index) {
        int param2change = (int) (random.nextDouble() * dimension);
        int rand_k = (int) (random.nextDouble() * food_number);

        while(rand_k == index) {
            rand_k = (int) (random.nextDouble() * food_number);
        }
        double[] new_solution = new double[dimension];
        System.arraycopy(solution[index], 0, new_solution, 0, dimension);

        double eucNorm = 0.0;
        for(int i = 0; i < dimension; i++) {
            eucNorm += Math.pow(globalBest[i] - solution[index][i], 2);
        }
        eucNorm = Math.sqrt(eucNorm / (dimension * (upper_bound - lower_bound)));

        new_solution[param2change] = solution[index][param2change] + (random.nextDouble() - 0.5) * 2.0 *
                (solution[index][param2change] - solution[rand_k][param2change]);
        if(new_solution[param2change] > upper_bound) {
            new_solution[param2change] = upper_bound;
        } else if (new_solution[param2change] < lower_bound) {
            new_solution[param2change] = lower_bound;
        }

        double result_of_new = function_calculator(new_solution);
        double fitness_of_new = fitness_calculator(result_of_new);
        if(fitness_of_new >  fitness[index]) {
            System.arraycopy(new_solution, 0, solution[index], 0, dimension);
            fitness[index] = fitness_of_new;
            func_res[index] = result_of_new;
            trial[index] = 0;
        }
        else {
            trial[index]++;
        }
    }
}
