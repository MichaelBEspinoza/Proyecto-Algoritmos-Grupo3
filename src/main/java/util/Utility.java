package util;

import domain.Course;
import domain.Lesson;
import operations.CourseOperations;

import java.text.DecimalFormat;
import java.util.Random;

public class Utility {

    //static init
    static {
    }

    public static String format(double value){
        return new DecimalFormat("###,###,###.##").format(value);
    }
    public static String $format(double value){
        return new DecimalFormat("$###,###,###.##").format(value);
    }
    public static String show(int[] a, int size) {
        String result="";
        for (int i = 0; i < size; i++) {
            result+= a[i];
        }
        return result;
    }

    public static void fill(int[] a, int bound) {
        for (int i = 0; i < a.length; i++) {
            a[i] = new Random().nextInt(bound);
        }
    }

    public static int getRandom(int bound) {
        return new Random().nextInt(bound)+1;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1.compareTo(int2);
            case "String":
                String st1 = (String)a; String st2 = (String)b;
                return st1.compareTo(st2);
            case "Character":
                Character c1 = (Character)a; Character c2 = (Character)b;
                return c1.compareTo(c2);
            case "Course":
                Course co1 = (Course) a; Course co2 = (Course) b;
                return Integer.compare(co1.getId(), co2.getId());
            case "Lesson":
                Lesson le1 = (Lesson) a; Lesson le2 = (Lesson) b;
                return Integer.compare(le1.getId(), le2.getId());
            default:
                throw new IllegalArgumentException("Invalid comparison types.");
        }
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        if(a instanceof Course && b instanceof Course) return "Course";
        if(a instanceof Lesson && b instanceof Lesson) return "Lesson";
        return "Unknown";
    }
}
