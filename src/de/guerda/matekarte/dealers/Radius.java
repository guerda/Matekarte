package de.guerda.matekarte.dealers;

/**
 * Created by Philip Gillißen on 11.10.2014.
 */
public enum Radius {
    ONE_KILOMETER(1),
    TWO_KILOMETERS(2),
    FIVE_KILOMETERS(5),
    TEN_KILOMETERS(10),
    HUNDRED_KILOMETERS(100);

    private final int kilometers;

    private Radius(int aKilometers) {
        kilometers = aKilometers;
    }

    public int getKilometers() {
        return kilometers;
    }

    }
