package fptu.prm.cookcook.utils;

public class SeperateUtil {
    public static String seperateNumberBetweenUnit(String s) {
        StringBuilder result = new StringBuilder();
        String[] arr = s.split(" ");
        if (arr[0].matches("[0-9A-Za-z]+")) {
            for (int i = 0; i < arr[0].length(); i++) {
                result.append(arr[0].charAt(i));
                if (Character.isDigit(arr[0].charAt(i)) && !Character.isDigit(arr[0].charAt(i + 1))) {
                    result.append(" ").append(arr[0].charAt(i + 1));
                    break;
                }
            }
        }
        for (int i = 1; i < arr.length; i++) {
            result.append(" ").append(arr[i]);
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
