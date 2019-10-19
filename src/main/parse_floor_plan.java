package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class parse_floor_plan
{
    private static volatile parse_floor_plan instance = null;

    private parse_floor_plan() {}

    public static parse_floor_plan getInstance()
    {
        if (instance == null)
        {
            synchronized(parse_floor_plan.class)
            {
                if (instance == null)
                {
                    instance = new parse_floor_plan();
                }
            }
        }
        return instance;
    }

    int [][] parse_func(Object obj)
    {
        JSONObject jo = (JSONObject) obj;
        JSONArray fp = (JSONArray) jo.get("floorplan");
        JSONArray index0 = (JSONArray)fp.get(0);

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
