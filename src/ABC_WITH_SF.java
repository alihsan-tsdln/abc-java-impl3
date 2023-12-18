public class ABC_WITH_SF extends ABC{
    final double upper_scaler;
    final double lower_scaler;
    final double probabilty_adj;

    double[] scaling_rates = new double[food_number];
    double[] modification_rates = new double[food_number];

    public ABC_WITH_SF(int NUMBER_OF_POPULATION, int DIMENSION, int MAXIMUM_EVALUATION,
                       int LIMIT, double LOWER_BOUND, double UPPER_BOUND, float MODIFICATION_RATE, float SCALING_RATE,
                       double UPPER_SCALER, double LOWER_SCALER, double PROBABILTY_ADJESTER,
                       String FUNCTION_NAME) {
        super(NUMBER_OF_POPULATION, DIMENSION, MAXIMUM_EVALUATION, LIMIT, LOWER_BOUND, UPPER_BOUND, FUNCTION_NAME);
        upper_scaler = UPPER_SCALER;
        lower_scaler = LOWER_SCALER;
        probabilty_adj = PROBABILTY_ADJESTER;
        for(int i = 0; i < food_number; i++) {
            scaling_rates[i] = SCALING_RATE;
            modification_rates[i] = MODIFICATION_RATE;
        }
    }

    @Override
    void generate_new_solution(int index) {
        int param2change = (int) (random.nextDouble() * dimension);
        int rand_k = (int) (random.nextDouble() * food_number);
        while(rand_k == index)
            rand_k = (int) (random.nextDouble() * food_number);
        double[] new_solution = new double[dimension];
        System.arraycopy(solution[index], 0, new_solution, 0, dimension);

        if(random.nextDouble() < probabilty_adj)
            scaling_rates[index] = lower_scaler + random.nextDouble() * upper_scaler;
        if(random.nextDouble() < probabilty_adj)
            modification_rates[index] = random.nextFloat();

        new_solution[param2change] = solution[index][param2change] + scaling_rates[index] * (random.nextDouble() - 0.5) * 2.0 * (solution[index][param2change] - solution[rand_k][param2change]);

        if(new_solution[param2change] > upper_bound)
            new_solution[param2change] = upper_bound;
        else if (new_solution[param2change] < lower_bound)
            new_solution[param2change] = lower_bound;

        double result_of_new = function_calculator(new_solution);
        double fitness_of_new = fitness_calculator(result_of_new);
        if(fitness_of_new >  fitness[index] && modification_rates[index] > random.nextDouble()) {
            System.arraycopy(new_solution, 0, solution[index], 0, dimension);
            fitness[index] = fitness_of_new;
            func_res[index] = result_of_new;
            trial[index] = 0;
        }
        else
            trial[index]++;
    }
}
