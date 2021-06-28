package com.catherine.design_pattern.factory;

import java.util.NoSuchElementException;

/**
 * @author : Catherine
 * @created : 26/06/2021
 */
public class Factory {
    public final static int BLUE = 0;
    public final static int RED = 1;

    public Color getColor(int colorInt) {
        return switch (colorInt) {
            case BLUE -> new Blue();
            case RED -> new Red();
            default -> throw new NoSuchElementException("No such color");
        };
    }
}
