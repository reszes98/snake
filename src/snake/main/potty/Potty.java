package snake.main.potty;
/**
 * 
 * @author erol
 */
public class Potty {
	public int x;
	public int y;
	public int id;

	/**
	 * Move függvény, beállítja az x és y koordinátát.
	 * @param xi a potty leendő x koordinátája
	 * @param yi a potty leendő y koordinátája
	 * */
	public void move(int xi, int yi) {
		x = xi;
		y = yi;
	}
}
