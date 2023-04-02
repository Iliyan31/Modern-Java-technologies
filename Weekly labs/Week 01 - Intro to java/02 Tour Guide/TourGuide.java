import java.util.Arrays;

public class TourGuide {
    public static int getBestSightseeingPairScore(int[] places) {
        if(places == null || places.length == 0) return 0;
        int[] distances = new int[places.length*places.length];
        int k=0;
        for(int i = 0; i < places.length; i++) {
            for(int j = i+1; j < places.length; j++) {
                distances[k++] = places[i] + places[j] + i - j;
            }
        }
        int max = 0;
        for (int distance : distances) {
            if (distance > max) {
                max = distance;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] places = {8, 1, 5, 2, 6};
        System.out.println(getBestSightseeingPairScore(places));
    }
}
