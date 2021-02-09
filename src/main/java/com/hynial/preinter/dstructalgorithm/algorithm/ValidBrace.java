package com.hynial.preinter.dstructalgorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ValidBrace {
    /**
     * 有效的括号匹配
     * '('，')'，'{'，'}'，'['，']'
     * 考虑字符串替换的写法 TODO
     *
     * @param s
     * @return
     */
    public boolean validBrace(String s) {
        if (s == null || (s.length() & 1) != 0) return false;

        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isLeftBrace(c)) {
                characters.add(c);
                if (characters.size() > (s.length() / 2)) return false;
            } else if (characters.size() < 1) {
                return false;
            } else {
                char l = characters.get(characters.size() - 1);
                if (getRightByLeftBrace(l) == c) {
                    characters.remove(characters.size() - 1);
                } else {
                    return false;
                }
            }
        }

        if (characters.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeftBrace(char c) {
        if (c == '(' || c == '{' || c == '[') {
            return true;
        } else {
            return false;
        }
    }

    private char getRightByLeftBrace(char leftBrace){
        if(leftBrace == '(') return ')';
        if(leftBrace == '{') return '}';
        if(leftBrace == '[') return ']';

        return '0';
    }

}
