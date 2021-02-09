package com.hynial.preinter.jbase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class TypeVariableSample<K extends Integer & Map, V> {
    K key;
    V value;

    public static void main(String[] args) {
        TypeVariable<Class<TypeVariableSample>>[] typeParams = TypeVariableSample.class.getTypeParameters();

        for (int i = 0; i < typeParams.length; i++) {
            String name = typeParams[i].getName();
            System.out.println("Name:" + name);

            String typeName = typeParams[i].getTypeName();
            System.out.println("TypeName:" + typeName);

            Class<TypeVariableSample> genDecl = typeParams[i].getGenericDeclaration();
            System.out.println("Declaration:" + genDecl);

            System.out.println("------------bounds----------------");
            Type[] bounds = typeParams[i].getBounds();
            for (int j = 0; j < bounds.length; j++) {
                System.out.println(bounds[j]);
            }

            System.out.println("----------annotations---------------");
            Annotation[] annotations = typeParams[i].getAnnotations();
            for (int j = 0; j < annotations.length; j++) {
                System.out.println(annotations[j]);
            }
        }
    }
}