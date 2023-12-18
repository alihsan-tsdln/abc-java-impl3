public class StatisticFunctions {
    public static double mean(double[] arr) {
        double res = 0.0;
        for(double v : arr) {
            res += v;
        }
        return res / (double) arr.length;
    }

    public static double max(double[] arr) {
        double max = 0.0;
        for(double v : arr) {
            if(v > max) {
                max = v;
            }
        }
        return max;
    }

    public static double min(double[] arr) {
        double min = arr[0];
        for(double v : arr) {
            if(v < min) {
                min = v;
            }
        }
        return min;
    }

    public static double std(double[] arr) {
        double res = 0.0;
        for(double v : arr) {
            double temp = v - mean(arr);
            res += temp * temp;
        }
        return Math.sqrt(res);
    }
}
