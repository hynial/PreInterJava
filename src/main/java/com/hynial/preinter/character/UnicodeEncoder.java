package com.hynial.preinter.character;


import org.apache.commons.text.StringEscapeUtils;

public class UnicodeEncoder {

    private String unicode = "\\u547D\\u540D\\u65B9\\u5F0F: \\u8BE5vos\\u6570\\u636E\\u6E90\\u5728 gboss.vos \\u8868\\u4E2D\\u7684\\u4E3B\\u952Eid = 4\\uFF0C \\u56E0\\u6B64\\u547D\\u540D\\u4E3Avos4";

    public static void main(String[] args) {
        UnicodeEncoder unicodeEncoder = new UnicodeEncoder();

        System.out.println("StringEscapeUtils.unescapeJava(sJava):\n" + StringEscapeUtils.unescapeJava(unicodeEncoder.unicode));
        String unicode = StringEscapeUtils.escapeJava("hello world 你好");
        System.out.println(unicode);
    }
}
