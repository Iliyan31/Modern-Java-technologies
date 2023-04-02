public class DataCenter {
    public static int getCommunicatingServersCount(int[][] map) {
        if(map == null || map.length == 0) return 0;
        int[] rows = new int[map.length];
        int[] cols = new int[map[0].length];
        for(int i=0; i<map.length; i++) {
            for(int j=0; j<map[i].length; j++) {
                if(map[i][j] == 1) {
                    rows[i]++;
                    cols[j]++;
                }
            }
        }

        int sum = 0;
        for(int i=0; i<map.length; i++) {
            for(int j=0; j<map[i].length; j++) {
                if(map[i][j] == 1 && (rows[i] > 1 || cols[j] > 1)) {
                    sum++;
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        System.out.println(getCommunicatingServersCount(new int[][]{{1, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}));
    }
}
