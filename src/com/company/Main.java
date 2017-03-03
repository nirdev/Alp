package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        try {
//            ClipsParser.parseDeftemplateLine("(deftemplate DO_1 " +
//                    "(slot value (type INTEGER))" +
//                    "(slot cos (type SYMBOL) (default TRUE)) " +
//                    "(multislot coord (type INTEGER) (default 6 1 3)) " +
//                    "(slot type (type SYMBOL) (default MODEL_INT)))");
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//            e.printStackTrace();
//        }

        ArrayList<String> strings= ClipsParser.getMultislotsStringList("(deftemplate DO_1 (slot value (type INTEGER)) (slot cos (type SYMBOL) (default TRUE)) (multislot coord (type INTEGER) (default 6 1 3)) (slot type (type SYMBOL) (default MODEL_INT)))");
        strings.forEach(System.out::println);
    }
}
