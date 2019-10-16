package main;

public class DirtLevel {

    private static DirtLevel DirtLevel;
    private DirtLevel(){}

    public static DirtLevel getDirtLevel() {
        if (DirtLevel == null){
            DirtLevel = new DirtLevel();
        }

        return DirtLevel;
    }
}
