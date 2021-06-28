package com.catherine.oop.overriding;

/**
 * @author : Catherine
 * @created : 28/06/2021
 */
public class Child extends Parent {

    // optional
    @Override
    public void func() {
        System.out.println("I'm func() in Child class");
    }

    // mandatory
    @Override
    public void absFunc() {
        System.out.println("I must override Parent's abstract function absFunc()");
    }
}
