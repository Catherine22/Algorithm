package com.catherine.design_pattern.factory;

/**
 * @author : Catherine
 * @created : 26/06/2021
 */
public class Red implements Color {
    @Override
    public void onDraw() {
        System.out.println("Drawing a red line...");
    }
}
