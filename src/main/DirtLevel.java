package main;

public class DirtLevel {

    private static DirtLevel DirtLevel = new DirtLevel();
    private static int[][] dirtFloor;
    private DirtLevel(){}

    public static int[][] getDirtLevel(int[][] floorPlan) {
        int row = floorPlan.length;
        int col = floorPlan[0].length;
        boolean open;
        dirtFloor = new int[row][col];
        for (int i = 0; i<row;i++){
            for(int k = 0;k<col;k++){
                open = openSpace(floorPlan[i][k]);
                if(open){
                    dirtFloor[i][k] = getRandomNum();
                }
                else {
                    dirtFloor[i][k]=0;
                }
            }
        }
        return dirtFloor;
    }
    private static boolean openSpace(int x){
        if (x==0|x==1|x==2){
            return true;
        }
        return false;
    }
    private static int getRandomNum() { //Dirt Level ranges from 1-3
        return (int) (Math.random()*3)+1;
    }
}
