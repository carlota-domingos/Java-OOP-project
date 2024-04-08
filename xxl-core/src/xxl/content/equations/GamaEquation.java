package xxl.content.equations;

import xxl.gama.*;

/**
 * This class represents an equation whose argument is a Gama.
 */
public abstract class GamaEquation extends Equation {
    private Gama Gama;
    /**the Gama associated.*/

    GamaEquation(Gama Gama){
        super();
        this.Gama = Gama;
    }

    public Gama getGama() {
        return Gama;
    }

}
