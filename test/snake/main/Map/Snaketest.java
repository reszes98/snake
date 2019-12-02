package snake.main.Map;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import snake.main.Map.Map;
import snake.main.Snake.Snake;
import snake.main.potty.Apple;
import snake.main.potty.Mereg;

public class Snaketest {
	/**
	 * snake()	az egyetlen tesztfüggvényünk, egyszerűbb így az alapoktól felépíteni mindent
	 * 			tesztelem a mátrix betöltést, hogy jó helyen vannak e a falak, és mindenhol máshol
	 * 			0 van.
	 * 			Tesztelem az új kígyó létrehozást, hogy megfelelő koordináták legyenek inicializálva
	 * 			Úgyanígy az alma és méreg létrehozsát, majd ezeket updatelem a mátrixba, és ott is
	 * 			tesztelem, továbbá a mozgást is figyelem, sőt a referencia pontokat is vizsgálom,
	 * 			nehogy túl indexeljük a mátrixot
	 * 			Vizsgálom az ütközés vizsgálatot almára és méregre, hogy megfelelő értékek legyenek
	 * 			pl alma esetén +200 a score, növünk is egyet, és eltünik a térképről, 
	 */
	@Test
	public void snake() {

		// load
		Map m = new Map();
		for (int k = 1; k < m.y - 1; k++)
			for (int i = 1; i < m.x - 1; i++) {
				Assert.assertEquals(0, m.map[k][i]);
			}
		for (int k = 0; k < 2; k++)
			for (int i = 0; i < m.x; i++) {
				if (k == 0)
					Assert.assertEquals(1, m.map[0][i]);
				if (k == 1)
					Assert.assertEquals(1, m.map[99][i]);
			}
		for (int k = 0; k < 2; k++)
			for (int i = 0; i < m.y; i++) {
				if (k == 0)
					Assert.assertEquals(1, m.map[i][0]);
				if (k == 1)
					Assert.assertEquals(1, m.map[i][99]);
			}

		Snake s = new Snake(10, 10, 0, -1);

		// snake()
		Assert.assertEquals(5, s.body.size());
		Assert.assertEquals(5, s.body.get(0).id);
		for (int i = 1; i < s.body.size(); i++)
			Assert.assertEquals(4, s.body.get(i).id);
		for (int i = 0; i < s.body.size(); i++) {
			Assert.assertEquals(10, s.body.get(i).x);
			Assert.assertEquals(10 + i, s.body.get(i).y);
		}
		m.S = new ArrayList<>();
		m.S.add(s);
		m.A = new ArrayList<>();

		// apple()
		Apple a = new Apple(10, 9);
		m.A.add(a);
		m.update();
		Assert.assertEquals(2, m.map[10][9]);

		// update()
		Assert.assertEquals(5, m.map[m.S.get(0).body.get(0).x][m.S.get(0).body.get(0).y]);
		Assert.assertEquals(4, m.map[m.S.get(0).body.get(1).x][m.S.get(0).body.get(1).y]);

		// collision()
		m.collision();
		Assert.assertEquals(6, m.S.get(0).body.size());
		Assert.assertEquals(0, m.map[10][9]);

		// eat()
		Assert.assertEquals(200, m.S.get(0).score);
		Assert.assertEquals(1, m.lastone());

		// move()
		m.S.get(0).move();
		m.update();
		for (int i = 0; i < s.body.size(); i++) {
			Assert.assertEquals(10, s.body.get(i).x);
			Assert.assertEquals(9 + i, s.body.get(i).y);
		}

		// mereg()
		Mereg ma = new Mereg(10, 8);
		m.M.add(ma);
		m.update();
		Assert.assertEquals(3, m.map[10][8]);

		// eat()
		m.collision();
		Assert.assertEquals(0, m.map[10][8]);
		Assert.assertEquals(13, m.S.get(0).freeze);

		// view
		Snake s2 = new Snake(90, 90, 1, 0);
		s2.setpoint();
		Assert.assertEquals(99 - 4 * s2.body.size(), s2.rx);
		Assert.assertEquals(99 - 4 * s2.body.size(), s2.ry);

	}

}
