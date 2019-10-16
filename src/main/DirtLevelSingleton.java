package main;

public class DirtLevelSingleton {

    private static DirtLevelSingleton DirtLevel;
    private DirtLevelSingleton(){}

    public static DirtLevelSingleton getDirtLevel() {
        if (DirtLevel == null){
            DirtLevel = new DirtLevelSingleton();
        }

        return DirtLevel;
    }
}
