package fptu.prm.cookcook.utils;

public class SeperateUtil {
    private static SeperateUtil instance;
    private SeperateUtil() {
    }
    public static SeperateUtil getInstance() {
        if (instance == null) {
            instance = new SeperateUtil();
        }
        return instance;
    }

    public static String seperateNumberBetweenUnit(String s) {
        StringBuilder result = new StringBuilder();
        String stringSplit[] = s.split(" ");
        if(stringSplit[0].matches(".*\\d.*")){
            String number = stringSplit[0].replaceAll("[^0-9/.,]", "");
            String unit = stringSplit[0].replaceAll("[0-9/.,]", "");
            result.append(number).append(" ").append(unit);
        }
        for (int i = 1; i < stringSplit.length; i++) {
            result.append(" ").append(stringSplit[i]);
        }
        return result.toString();
    }
    // get only first number
    public static int getNumber(String s) {
        if(s.matches("[0-9]+")){
            return Integer.parseInt(s);
        }
        String result = seperateNumberBetweenUnit(s);
        String[] arr = result.split(" ");
        return Integer.parseInt(arr[0]);
    }
    // get only unit
    public static String getUnit(String s) {
        String result = seperateNumberBetweenUnit(s);
        String[] arr = result.split(" ");
        return arr[1];
    }
    //get only name
    public static String getName(String s) {
        String result = seperateNumberBetweenUnit(s);
        String[] arr = result.split(" ");
        StringBuilder name = new StringBuilder();
        for (int i = 2; i < arr.length; i++) {
            name.append(arr[i]).append(" ");
        }
        return name.toString();
    }
}
