package com.catherine.design_pattern;

/**
 * @author : Catherine
 * @created : 26/06/2021
 * <p>
 * Lazy initialisation
 */
public class BillPughSingleton {
    private BillPughSingleton() {

    }

    private static class SingletonHolder {
        private static final BillPughSingleton instance = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public void print() {
        System.out.println("Singleton:BillPughSingleton");
    }
}
