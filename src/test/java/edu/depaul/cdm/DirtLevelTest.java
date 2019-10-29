package edu.depaul.cdm;

import edu.depaul.cdm.DirtLevel;

public class DirtLevelTest {

    public static void main(String[] args) {
        int[][] twoDArray = {   //Check legend.txt in resources folder to see what is aviliable space for dirt
                {1,4,5,2},
                {0,3,2,-1},
                {5,1,6,2},
                {1,2,2,1}
        };
        int[][] dit = DirtLevel.getDirtLevel(twoDArray);
        for (int i =0;i<dit.length;i++){
            for (int k =0;k<dit[0].length;k++){
                System.out.print(dit[i][k]+"\t");
            }
            System.out.println();
        }

    }

}

