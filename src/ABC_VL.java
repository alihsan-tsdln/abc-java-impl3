import java.util.Arrays;
import java.util.Random;

public class ABC_VL extends ABC{

    public int counter_of_init = 0;
    public double eucHe = 0.0;
    double[] limitEnc = new double[food_number];

    public ABC_VL(int NUMBER_OF_POPULATION, int DIMENSION, int MAXIMUM_EVALUATION, int LIMIT, double LOWER_BOUND, double UPPER_BOUND, String FUNCTION_NAME) {
        super(NUMBER_OF_POPULATION, DIMENSION, MAXIMUM_EVALUATION, LIMIT, LOWER_BOUND, UPPER_BOUND, FUNCTION_NAME);
    }

    @Override
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



        double conststd = StatisticFunctions.std(prob);
        double a = conststd * random.nextDouble();

        trial[maxIndex] = (trial[maxIndex] / prob[maxIndex] + a);


        //System.out.println(trial[maxIndex]);

        if(trial[maxIndex] >= limit)
        {
            counter_of_init++;
            //System.out.println(trial[maxIndex]);
            init(maxIndex);
        }
    }

    public int getCounter_of_init() {
        return counter_of_init;
    }
}
