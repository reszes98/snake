package snake.main.potty;

public class S_Body extends Potty {
	/**
	 * Konstruktor beállítja az x, y, és id változókat(mindig 4 lesz az id)
	 * @param xi az új x koordináta
	 * @param yi az új y koordináta
	 */
	public S_Body(int xi, int yi) {
		super();
		id = 4;
		x = xi;
		y = yi;
	}
}
