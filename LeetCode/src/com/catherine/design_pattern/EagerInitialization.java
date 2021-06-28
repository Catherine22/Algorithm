package com.catherine.design_pattern;

/**
 * @author : Catherine
 * @created : 26/06/2021
 */
public class EagerInitialization {
    private static final EagerInitialization instance = new EagerInitialization();

    private EagerInitialization() {

    }

    public static EagerInitialization getInstance() {
        return instance;
    }

    public void print() {
        System.out.println("Singleton:EagerInitializingSingleton");
    }
}
