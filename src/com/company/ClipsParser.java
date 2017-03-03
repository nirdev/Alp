package com.company;

import java.util.*;

public class ClipsParser {

//
//    (deftemplate DO_1
//            (slot value (type INTEGER))
//            (slot cos (type SYMBOL) (default TRUE))
//            (multislot coord (type INTEGER) (default 6 1 3))
//            (slot type (type SYMBOL) (default MODEL_INT)))

    public static ClipsDataModel parseDeftemplateLine(String line) throws Exception {
        //Will throw exception if not valid
        validetDeftemplateLine(line);
        ArrayList<deftemplate> deftemplates = getAllMultislots(line);


        return null;
    }

    private static ArrayList<deftemplate> getAllMultislots(String validLine) {
        ArrayList<deftemplate> deftemplates = new ArrayList<>();
        ArrayList<String> multislotStrings = getMultislotsStringList(validLine);

        for (String multislotString : multislotStrings) {
            deftemplate deftemplate = new deftemplate();

            deftemplate.setMultislotName(getNameFromMultisltString(multislotString));
            deftemplate.setMultislotDefault(getNameFromMultisltDefault(multislotString));
            deftemplate.setMultislotType(getNameFromMultisltType(multislotString));

            deftemplates.add(deftemplate);
        }

        return deftemplates;
    }

    private static String getNameFromMultisltDefault(String multislotString) {
        // TODO: 2/21/2017
        return null;
    }

    private static String getNameFromMultisltType(String multislotString) {
        // TODO: 2/21/2017
        return null;
    }

    private static String getNameFromMultisltString(String multislotString) {
        // TODO: 2/21/2017
        return null;
    }

    /**
     * Return list of strings under the multislot parenthesis
     */
    public static ArrayList<String> getMultislotsStringList(String validLine) {

        ArrayList<String> matchList = new ArrayList<String>();

        int startBracketsIndex = validLine.indexOf("multislot");
        while (startBracketsIndex >= 0) {


            int openBracketsCount = 0;
            int closeBracketsCount = 0;
            int endBracketsIndex = startBracketsIndex;

            while (closeBracketsCount <= openBracketsCount && endBracketsIndex < (validLine.length() - 1)) {
                if (validLine.charAt(endBracketsIndex) == '(') {
                    openBracketsCount++;
                } else if (validLine.charAt(endBracketsIndex) == ')') {
                    closeBracketsCount++;
                }
                endBracketsIndex++;
            }
            matchList.add(validLine.substring(startBracketsIndex, endBracketsIndex - 1));
            startBracketsIndex = validLine.indexOf("multislot", startBracketsIndex + 1);
        }

        return matchList;
    }

    /**
     * Validate if the line is Deftemplate valid line:
     * 1. No line breakers
     * 2. Contains deftemplate keyword
     * 3. No open brackets
     */
    private static void validetDeftemplateLine(String line) throws Exception {
        if (isLineBreak(line)) {
            throw new Exception("Received More than on line to parse");
        }
        if (!line.contains("deftemplate")) {
            throw new Exception("Deftemplate is not valid - Not contains 'deftemplate'");

        }
        if (!isAllBracketsClosed(line)) {
            throw new Exception("Deftemplate is not valid - Not all bracket are closed");
        }
    }

    private static boolean isLineBreak(String line) {
        return line.matches("[\\n\\r]+");
    }

    public static boolean isAllBracketsClosed(String s) {
        HashMap<Character, Character> closeBracketMap = new HashMap<Character, Character>();
        closeBracketMap.put(')', '(');
        HashSet<Character> openBracketSet = new HashSet<Character>(
                closeBracketMap.values());
        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char cur = chars[i];
            if (openBracketSet.contains(cur)) {
                stack.push(cur);
            } else if (closeBracketMap.keySet().contains(cur)) { // close brackets
                if (stack.isEmpty()) {
                    return false;
                }
                if (closeBracketMap.get(cur) != stack.peek()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

}
