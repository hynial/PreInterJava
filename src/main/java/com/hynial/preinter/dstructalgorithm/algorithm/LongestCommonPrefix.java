package com.hynial.preinter.dstructalgorithm.algorithm;

public class LongestCommonPrefix {
    /**
     * let-coder 14
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0) return "";

        int[] strLens = new int[strs.length];
        int lowestIndex = 0, lowestLen = Integer.MAX_VALUE;
        for (int i = 0; i < strs.length; i++) {
            if(strs[i] == null) return "";
            if(strs[i].length() == 0) return strs[i];

            strLens[i] = strs[i].length();
            if(strLens[i] <= lowestLen){
                lowestIndex = i;
                lowestLen = strLens[i];
            }
        }

        String lowestString = strs[lowestIndex];
        for (int i = lowestString.length() - 1; i >= 0; i--) {
            String commonString = lowestString.substring(0, i + 1);
            boolean yes = true;
            for (String a : strs) {
                if(!a.startsWith(commonString)){
                    yes = false;
                    break;
                }
            }
            if(yes){
                return commonString;
            }
        }

        return "";
    }

    public String longestCommonPrefix0(String[] strs) {
        String commonString = strs[0];
        for (int i = 0; i < commonString.length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if(i == strs[j].length() || commonString.charAt(i) != strs[j].charAt(i)){
                    return commonString.substring(0, i);
                }
            }

        }

        return commonString;
    }

    /**
     * 分治法
     * @param strs
     * @return
     */
    public String longestCommonPrefix1(String[] strs){
        if(strs == null || strs.length == 0) return "";

        return divideStringArray(strs, 0, strs.length - 1);
    }

    private String divideStringArray(String[] strs, int start, int end){
        if(start == end){
            return strs[end];
        }else{
            int mid = start + end >>> 1;
            String leftString = divideStringArray(strs, start, mid);
            String rightString = divideStringArray(strs, mid + 1, end);
            return commonStringInTwo(leftString, rightString);
        }
    }

    private String commonStringInTwo(String leftString, String rightString){
        for (int i = 0; i < leftString.length(); i++) {
            if(i == rightString.length() || leftString.charAt(i) != rightString.charAt(i)){
                return leftString.substring(0, i);
            }
        }

        return leftString.length() > rightString.length() ? rightString : leftString;
    }

}
