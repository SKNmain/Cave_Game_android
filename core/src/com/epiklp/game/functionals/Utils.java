package com.epiklp.game.functionals;

/**
 * Created by Asmei on 2018-02-05.
 */

public class Utils {

    //Głównie klasa potrzebna do porównywania stringów w GameContactListenerze - gówna wywalają, bo mogą być null, a nie mogę użyć
    //klasy Objects.equals ze zwględu na API 19.
    public static final boolean equalsWithNulls(Object a, Object b) {
        if (a==b) return true;
        if ((a==null)||(b==null)) return false;
        return a.equals(b);
    }
}
