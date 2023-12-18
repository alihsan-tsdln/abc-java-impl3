public class ABC_VL2 extends ABC{
    private int counterOfScout = 0;
    public ABC_VL2(int NUMBER_OF_POPULATION, int DIMENSION, int MAXIMUM_EVALUATION, int LIMIT, double LOWER_BOUND, double UPPER_BOUND, String FUNCTION_NAME) {
        super(NUMBER_OF_POPULATION, DIMENSION, MAXIMUM_EVALUATION, LIMIT, LOWER_BOUND, UPPER_BOUND, FUNCTION_NAME);
    }

    public void send_scout_bees() {
        int maxIndex = 0;
        double maxTrial = 0;

        for(int i = 0; i < food_number; i++) {
            if(trial[i] > maxTrial) {
                maxIndex = i;
                maxTrial = trial[i];
            }
        }

        double eucNorm = 0.0;

        for(int i = 0; i < dimension; i++) {
            eucNorm += Math.pow(solution[maxIndex][i] - globalBest[i], 2);
        }
        eucNorm = Math.sqrt(eucNorm);

        random.nextDouble();

        if(trial[maxIndex] * eucNorm * StatisticFunctions.std(prob) / prob[maxIndex] * 1.4 >= limit)
        {
            counterOfScout++;
            init(maxIndex);
        }
    }

    public int getCounterOfScout() {
        return counterOfScout;
    }
}
