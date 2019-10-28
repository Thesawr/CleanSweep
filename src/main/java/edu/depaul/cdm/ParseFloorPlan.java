package main.java.edu.depaul.cdm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class ParseFloorPlan
{
    private static volatile ParseFloorPlan instance = null;

    private ParseFloorPlan() {}

    public static ParseFloorPlan getInstance()
    {
        if (instance == null)
        {
            synchronized(ParseFloorPlan.class)
            {
                if (instance == null)
                {
                    instance = new ParseFloorPlan();
                }
            }
        }
        return instance;
    }

    int [][] parse_func(Object obj)
    {
        JSONObject jo = (JSONObject) obj;
        JSONArray fp = (JSONArray) jo.get("floorplan");

        int fp_size = fp.size();
        int fp_width = ((JSONArray)fp.get(0)).size();

        // Convert json array to int array obj
        int[][] matrix = new int[fp_size][fp_width];

        // Temporary array object for each "row"
        JSONArray tmpArray;

        for (int i=0; i<fp_size; ++i)
        {
            tmpArray = (JSONArray) fp.get(i);
            for (int j=0; j<fp_width; ++j)
            {
                matrix[i][j] = ((Long)tmpArray.get(j)).intValue();
            }
        }

        return matrix;
    }
}
