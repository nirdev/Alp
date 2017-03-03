package com.company;

import java.util.*;

public class ClipsParser {

//
//    (deftemplate DO_1
//            (slot value (type INTEGER))
//            (slot cos (type SYMBOL) (default TRUE))
//            (multislot coord (type INTEGER) (default 6 1 3))
//            (slot type (type SYMBOL) (default MODEL_INT)))

    /**
     * Important stuff
     * CLIPSObject:
     * <p>
     * deftemplate(Name): DO_1
     * multislot coord (Type + Id ): (type INTEGER) (default 6 1 3)
     */

    public static ClipsObject parseDeftemplateLine(String line) throws Exception {
        //Will throw exception if not valid
        validetDeftemplateLine(line);


        ClipsObject clipsObject = new ClipsObject();
        clipsObject.setName(getWordAfterSpecificWord(line, "deftemplate"));

        String firstMultiSlotString = getMultislotsStringList(line).get(0);
        clipsObject.setType(getWordAfterSpecificWord(firstMultiSlotString, "type"));
        clipsObject.setId(getIdString(firstMultiSlotString, "default"));


        return clipsObject;
    }

    private static String getWordAfterSpecificWord(String validLine, String firstWord) {
        String cleanLine = validLine.replaceAll("\\p{P}", "");
        String[] words = cleanLine.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(firstWord)) {
                return words[i + 1];
            }
        }

        return null;
    }

    private static String getIdString(String validLine, String firstWord) {
        String[] idArray = new String[3];
        idArray[0] = getWordAfterSpecificWord(validLine, firstWord);
        StringBuffer id = new StringBuffer(idArray[0]);


        for (int i = 1; i < 3; i++) {
            idArray[i] = getWordAfterSpecificWord(validLine, idArray[i - 1]);
            id.append(idArray[i]);
        }
        return id.toString();
    }

    /**
     * Return list of strings under the multislot parenthesis
     */
    public static ArrayList<String> getMultislotsStringList(String validLine) throws Exception {

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

        if (matchList == null) {
            throw new Exception("No multislot string available in the deftamplate");
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

    private static boolean isAllBracketsClosed(String s) {
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
