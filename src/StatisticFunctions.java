public class StatisticFunctions {
    public double mean(double[] fitt) {
        double res = 0.0;
        for(double v : fitt) {
            res += v;
        }
        return res / (double) fitt.length;
    }

    public static double max(double[] fitt) {
        double max = 0.0;
        for(double v : fitt) {
            if(v > max) {
                max = v;
            }
        }
        return max;
    }

    public static double min(double[] fitt) {
        double min = fitt[0];
        for(double v : fitt) {
            if(v < min) {
                min = v;
            }
        }
        return min;
    }

    public double std(double[] fitt) {
        double res = 0.0;
        for(double v : fitt) {
            double temp = v - mean(fitt);
            res += temp * temp;
        }
        return Math.sqrt(res);
    }
}
