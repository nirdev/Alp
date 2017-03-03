package com.company;

public class Main {

    public static void main(String[] args) {

        String line = "(deftemplate DO_1 (slot value (type INTEGER)) (slot cos (type SYMBOL) (default TRUE)) (multislot coord (type INTEGER) (default 6 1 3)) (slot type (type SYMBOL) (default MODEL_INT)))";

        ClipsObject clipsObject = null;
        try {
            clipsObject = ClipsParser.parseDeftemplateLine(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("name:" + clipsObject.getName() + " type: " + clipsObject.getType() + " id: " + clipsObject.getId());
    }
}
