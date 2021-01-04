package com.hynial.preinter.dmodel.dynproxy.annotationinject;

import com.hynial.preinter.dmodel.dynproxy.annotationinject.ifc.AnimalInterface;
import com.hynial.preinter.dmodel.dynproxy.annotationinject.impl.DogImpl;

public class AnnotationInjectorApplication {
    public static void main(String[] args) {
        AnimalInterface animalInterface = AnimalFactory.getAnimal(new DogImpl());
        System.out.println("AnimalName:" + animalInterface.getName());
        animalInterface.say();
        animalInterface.getProperty();
    }
}
