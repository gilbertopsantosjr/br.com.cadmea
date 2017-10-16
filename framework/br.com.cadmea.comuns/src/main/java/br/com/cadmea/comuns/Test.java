package br.com.cadmea.comuns;

import org.apache.commons.lang3.ArrayUtils;

public class Test {

    public static void main(String [] args){
        String[] fruits = { "Orange", "Apple", "Blueberry", "Guava"
                , "Apple", "Peach", "Orange", "Strawberry" };

        boolean contains = ArrayUtils.contains(fruits, "Guava");
        if(contains)
            System.out.println("contem");
        else
            System.out.println("nao contem");

    }
}