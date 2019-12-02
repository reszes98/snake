package snake.main.potty;
/**
 * Apple osztály a Potty-öt specializálja
 * @author eszes
 *
 */
public class Apple extends Potty {
	/**
	 * Konstruktor beállítja az x,y, és id változókat(id mindig 2 lesz)
	 * @param xi az új x koordináta
	 * @param yi az új y koordináta
	 */
	public Apple(int xi, int yi) {
		super();
		id = 2;
		x = xi;
		y = yi;
	}
}
