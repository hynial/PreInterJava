package com.hynial.preinter.jbase.inheritance;

public class SubClassFieldOver {
    private String operation = "base";

    public static class SubClass extends SubClassFieldOver {
        private String operation = "sub";

        public static void main(String[] args) {
            SubClassFieldOver sub = new SubClass();
            SubClass sub2 = new SubClass();
            System.out.println(sub.operation); // base
            System.out.println(sub2.operation); // sub
            SubClass sub3 = (SubClass) sub;
            System.out.println(sub3.operation); // sub
        }
    }


}
