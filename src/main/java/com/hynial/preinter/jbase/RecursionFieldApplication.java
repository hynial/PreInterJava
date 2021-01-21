package com.hynial.preinter.jbase;

public class RecursionFieldApplication {
    static int count = 0;
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            if (count < 3){
                count++;
                main(null);
            }else{
                System.out.println(count);
                return ;
            }
        }
    }
}
