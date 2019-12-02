package snake.main.Snake;

import java.util.ArrayList;


import snake.main.potty.*;

public class Snake {
	public int score;
	public int die;
	public int rx;
	public int ry;
	public int dx;
	public int dy;
	public int freeze;
	public ArrayList<Potty> body;

/**
 * Konstruktor	létrehozza egy 5 hosszú kígyót, beállítja az írányt, a koordinátákat
 * @param xi	a kezdő x koordináta
 * @param yi	a kezdő y koordináta
 * @param xd	a kezdő írányvektor x komponense
 * @param yd	a kezdő irányvektro y komponense
 */
	public Snake(int xi, int yi, int xd, int yd) {
		body = new ArrayList<>();
		body.add(new S_Head(xi, yi));
		score = 0;
		die = 0;
		dx = xd;
		dy = yd;
		 freeze = 0;
		if (yd == 1) {
			body.add(new S_Body(xi, --yi));
			body.add(new S_Body(xi, --yi));
			body.add(new S_Body(xi, --yi));
			body.add(new S_Body(xi, --yi));
			setpoint();
			return;
		}
		if (yd == -1) {
			body.add(new S_Body(xi, ++yi));
			body.add(new S_Body(xi, ++yi));
			body.add(new S_Body(xi, ++yi));
			body.add(new S_Body(xi, ++yi));
			setpoint();
			return;
		}
		if (xd == 1) {
			body.add(new S_Body(--xi, yi));
			body.add(new S_Body(--xi, yi));
			body.add(new S_Body(--xi, yi));
			body.add(new S_Body(--xi, yi));
			setpoint();
			return;
		}
		if (xd == -1) {
			body.add(new S_Body(++xi, yi));
			body.add(new S_Body(++xi, yi));
			body.add(new S_Body(++xi, yi));
			body.add(new S_Body(++xi, yi));
			setpoint();
			return;
		}

	}
/**
 * setpoint() 	a kígyó hossza alapján beállítja a referencia koordinátákat, hogy a kígyó középen
 * 				legyen, mindig akkora legyen a négyzet oldalhossza mint a kígyó hossza*2, max a 
 * 				map széléig lássunk, ha a kígyó hosszabb lenne mint 10 akkor 10 hosszban 
 * 				maximalizálunk, nehogy ne férjen el a képernyőn mind a 4 "ablak"
 */
	public void setpoint() {
		int s;
		if (body.size() <= 10)
			s = body.size();
		else
			s = 10;

		if (body.get(0).x - 2 * s <= 0)
			rx = 0;
		if (body.get(0).x + 2 * s >= 99)
			rx = 99 - 4 * s;
		if (body.get(0).x - 2 * s > 0 && body.get(0).x + 2 * s < 99)
			rx = body.get(0).x - 2 * s;
		if (body.get(0).y - 2 * s <= 0)
			ry = 0;
		if (body.get(0).y + 2 * s >= 99)
			ry = 99 - 4 * s;
		if (body.get(0).y - 2 * s > 0 && body.get(0).y + 2 * s < 99)
			ry = body.get(0).y - 2 * s;

	}
/**
 * move()	minden egyes körben ezt a függvényt hívja meg a Map osztály, evvel a kígyó minden 
 * 			egyes testrészét eggyel léptetjük a megadott irányba, továbbá számoljuk a referencia
 *			pontokat, ha a freeze változó 13 akkor még lépünk egyet, mert az ütközés detekció 
 *			mindig 1 lépéssel előre nézelődik.
 */
	public void move() {
		if (die == 0) {
			if (freeze == 0 || freeze == 13) {
				int x = dx + body.get(0).x;
				int y = dy + body.get(0).y;
				int tempx;
				int tempy;
				score++;

				for (int i = 0; i < body.size(); i++) {
					tempx = body.get(i).x;
					tempy = body.get(i).y;
					body.get(i).move(x, y);
					x = tempx;
					y = tempy;

				}
				setpoint();
				if (freeze == 13)
					freeze--;
			} else {
				freeze--;
			}
		}

	}
/**
 * eat()	Amennyiben a kígyó "ütközik"/megeszik/ valakit/valamit, ezt a függvényt hívjuk,
 * 			
 *			Ha az i==1, vagyis falat eszünk, akkor a die bitet 1re helyezzük, a kígyó "meghal"
 *			
 *			Ha az i==2, vagyis almát eszünk, akkor a score 200-zal nő, és egy új testrészt kapunk,
 * 			amit a (-1,-1) koordinátákra teszünk és a következő move() függvény hívás hatására fog
 * 			megjelenni(jó helyre kerülni).
 * 			
 * 			Ha az i==3, vagyis mérget eszünk kimaradunk 12 kört(amikor 13 az értéke akkor még
 * 			lépünk különben az előre detekció során nem lépnénk a méregre pl.
 * 
 * 			Ha az i==4 || i==5, vagyis másik kígyóval találkozunk akkor 1000 pontot kapunk, és egy
 * 			újabb testrészt, itt nem áll fent a veszélye, hogy önmagunkat esszük, ugyanis az
 * 			ütközés vizsgálatban már ügyeltem rá.
 * 			
 * @param i azon Potty id értéke amivel szembe találjuk magunkat
 */
	public void eat(int i) {
		if(i==1) {
			die();
		}
		if (i == 2) {
			score += 200;
			body.add(new S_Body(-1, -1));
		}
		if (i == 3)
			freeze = 13;
		if (i == 4 || i == 5) {
			score += 1000;
			body.add(new S_Body(-1, -1));
		}
	}
/**
 * change()	a kígyó haladási irányát változtatja meg, ügyelve rá, hogy az eredeti és a új irány
 * 			ne lehessen egymással ellentétes(kellene ellenőrzés x és y-ra de mivel keyeventek
 * 			alapján csinálom, így ha nem módosítjuk azt a részt nem lehet gond. Továbbá az sincs
 * 			ellenőrizve, hogy átlósan megy-e a kígyó, ugyanazon okból.)
 * @param x	új x irány
 * @param y új y irány
 */
	public void change(int x, int y) {
		if (dx + x != 0 && dy + y != 0) {
			dx = x;
			dy = y;
		}
	}
/**
 * die()	a die változó 1 értéket vesz fel, a kígyónk hivatalason meghalt, nem tudunk semmit
 * 			csinálni.
 */
	public void die() {
		die = 1;
	}

}
