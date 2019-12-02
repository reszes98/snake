package snake.main.Map;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import snake.main.Snake.Snake;
import snake.main.potty.*;

/**
 * Map osztály Nagyon sok mindent itt valósítottam meg, kiterjeszti a JPanel
 * osztályt, és az ActionListener osztályt megvalósítja.
 */
public class Map extends JPanel implements ActionListener {
	private static final long serialVersionUID = -5414121732887716310L;
	final int x = 100;
	final int y = 100;
	ArrayList<Snake> S;
	int s_n;
	int[][] map;
	int[][] map2;
	Timer delay;
	private Image Apple;
	private Image Poison;
	private Image Head;
	private Image Body;
	private Image Wall;
	private Image Empty;
	int start;
	ArrayList<Apple> A;
	ArrayList<Mereg> M;

	/**
	 * MyKeyListener 	osztály egy Override miatt van rá szükségünk amúgy a
	 *					KeyAdaptert specializálja
	 * 
	 * @author eszes
	 *
	 */
	public class MyKeyListener extends KeyAdapter {
		/**
		 * keyPressed() 	Itt figyelem a billentyűzet leütéseit, amennyiben bizonyosak pl:
		 * 					WASD akkor az P1 kígyója kap módosítást UHJK akkor az P2 kígyója kap
		 * 					módosítást Arrows akkor az P3 kígyója kap módosítást Numpad Arrows akkor az
		 * 					P4 kígyója kap módosítást Ha F2,F3,F4 lesz leütve akkor új játék 2-3-4
		 * 					játékossal F1-re indul a játék
		 * 
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			int c = e.getKeyCode();
			switch (c) {
			// wasd
			default:
				break;
			case KeyEvent.VK_W:
				S.get(0).change(0, -1);
				break;
			case KeyEvent.VK_S:
				S.get(0).change(0, 1);
				break;
			case KeyEvent.VK_D:
				S.get(0).change(1, 0);
				break;
			case KeyEvent.VK_A:
				S.get(0).change(-1, 0);
				break;
			// ujhk
			case KeyEvent.VK_U:
				S.get(1).change(0, -1);
				break;
			case KeyEvent.VK_J:
				S.get(1).change(0, 1);
				break;
			case KeyEvent.VK_K:
				S.get(1).change(1, 0);
				break;
			case KeyEvent.VK_H:
				S.get(1).change(-1, 0);
				break;
			// nyilak
			case KeyEvent.VK_UP:
				if (s_n > 2)
					S.get(2).change(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				if (s_n > 2)
					S.get(2).change(0, 1);
				break;
			case KeyEvent.VK_RIGHT:
				if (s_n > 2)
					S.get(2).change(1, 0);
				break;
			case KeyEvent.VK_LEFT:
				if (s_n > 2)
					S.get(2).change(-1, 0);
				break;
			case KeyEvent.VK_NUMPAD8:
				if (s_n > 3)
					S.get(3).change(0, -1);
				break;
			case KeyEvent.VK_NUMPAD2:
				if (s_n > 3)
					S.get(3).change(0, 1);
				break;
			case KeyEvent.VK_NUMPAD6:
				if (s_n > 3)
					S.get(3).change(1, 0);
				break;
			case KeyEvent.VK_NUMPAD4:
				if (s_n > 3)
					S.get(3).change(-1, 0);
				break;
			case KeyEvent.VK_F2:
				start(2);
				break;
			case KeyEvent.VK_F3:
				start(3);
				break;
			case KeyEvent.VK_F4:
				start(4);
				break;
			case KeyEvent.VK_F1:
				{delay.start();
				Graphics2D g2 = (Graphics2D) getGraphics();
				g2.scale(7, 7);
				g2.clearRect(60, 39, 120, 12);
				}
				break;
			}
		}

	}

	/**
	 * fest() 	A függvény kiüríti a map változójú mátrixot és a falakat
	 * 			visszarajzolja a map2 segítségével
	 */
	public void fest() {
		for (int k = 0; k < y; k++)
			for (int i = 0; i < x; i++) {
				map[k][i] = map2[k][i];
			}
	}

	/**
	 * Konstruktor 	Innentől figyeljük a billentyűzet leütéseit, inicializáljuk a
	 * 				változókat, képeket töltünk be, a mátrixot a fileból beolvassuk, és
	 * 				beállítjuk a Timert
	 */
	public Map() {
		addKeyListener(new MyKeyListener());
		map2= new int[x][y];
		map = new int[x][y];
		load_map();
		Apple = new ImageIcon("src/apple.png").getImage();
		Poison = new ImageIcon("src/mereg.png").getImage();
		Body = new ImageIcon("src/body.png").getImage();
		Head = new ImageIcon("src/head.png").getImage();
		Wall = new ImageIcon("src/wall.png").getImage();
		Empty = new ImageIcon("src/empty.png").getImage();
		A = new ArrayList<>(4);
		M = new ArrayList<>(4);
		delay = new Timer(250, this);
		setFocusable(true);

	}

	/**
	 * save_map() 	a map.txt-be kiírjuk a map tartalmát csak első alkalommal használtam, később
	 * 				jó lehet ha pálya szerkesztés van engedélyezve
	 */
	public void save_map() {
		try {
			FileWriter fw = new FileWriter("map.txt");
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++)
					pw.print(map[i][j] + " ");

			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * load_map() 	a map.txt-ből beolvasunk a map és map2 változóba.
	 */
	public void load_map() {
		try {
			FileReader fr = new FileReader("map.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			br.close();
			String[] ss = line.split(" ");
			int k = 0;
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					map[i][j] = Integer.parseInt(ss[k]);
					map2[i][j] = Integer.parseInt(ss[k]);
					k++;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * update() 	első lépésben letisztítjuk a map-ot felesleges kígyó maradványok
	 * 				után kutatva, majd az S tárolón keresztül az összes kígyót felrajzoljuk a
	 * 				pályára, amenniben él még, utána következnek az almák és a mérgek ilyen
	 * 				sorrendben
	 */
	public void update() {
		int tempx;
		int tempy;
		int tempid;
		fest();
		for (int j = 0; j < S.size(); j++) {
			if (S.get(j).die == 0) {
				for (int i = 0; i < S.get(j).body.size(); i++) {
					tempx = S.get(j).body.get(i).x;
					tempy = S.get(j).body.get(i).y;
					tempid = S.get(j).body.get(i).id;
					map[tempx][tempy] = tempid;
				}
			}
		}
		for (int i = 0; i < A.size(); i++) {
			tempx = A.get(i).x;
			tempy = A.get(i).y;
			tempid = A.get(i).id;
			map[tempx][tempy] = tempid;
		}
		for (int i = 0; i < M.size(); i++) {
			tempx = M.get(i).x;
			tempy = M.get(i).y;
			tempid = M.get(i).id;
			map[tempx][tempy] = tempid;
		}

	}
/**
 * almasit()	amennyiben meghívtuk feltölti 4 darab almával a tárolót, ügyelve a random
 * 				elhelyezkedésre, és ne legyen előtte semmi ott a térképen(vagyis 0 ).
 */
	public void almasit() {
		Random r = new Random();
		for (int i = 0; i < 4;) {
			int xr = r.nextInt(99);
			int yr = r.nextInt(99);
			if (map[xr][yr] == 0) {
				A.add(new Apple(xr, yr));
				i++;
			}
		}
	}
/**
 * mergesti() 	amennyiben meghívtuk feltölti 4 darab mereggel a tárolót, ügyelve a random
 * 				elhelyezkedésre, és ne legyen előtte semmi ott a térképen(vagyis 0 ).
 */
	public void mergesit() {
		Random r = new Random();
		for (int i = 0; i < 4;) {
			int xr = r.nextInt(99);
			int yr = r.nextInt(99);
			if (map[xr][yr] == 0) {
				M.add(new Mereg(xr, yr));
				i++;
			}
		}
	}
/**
 * lep() 	szól minden kígyónak hogy lépjenek, updateli a map-ot, és szól a grafikának hogy 
 * 			rajzoljon.
 */
	public void lep() {
		for (int i = 0; i < S.size(); i++)
			S.get(i).move();
		update();
		paintComponent(getGraphics());
	}
/**
 * collision()	végig meg az összes kígyón, megnézi az irány vektorukat, és az alapján megnézi
 * 				mi lenne a következő hely ahova lépne(fejjel), amennyiben ott nem 0 van, elkezdi
 * 				vizsgálni, hogy alma, méreg, kígyó, ha alma vagy méreg, akkor megeteti, törli az
 * 				almát vagy mérget a tárolójából, és folytatja. Ha kígyó, akkor 2 részre esik a 
 * 				folytatás, megvizsgáljuk ki a másik kígyó, ha más akkor megeszi, a másik kígyót
 * 				halottá nyílvánítjuk, ha önmaga akkor csak meghal.
 */
	public void collision() {
		int e;
		int x = 0;
		int y = 0;
		for (int i = 0; i < S.size(); i++) {
			x = S.get(i).body.get(0).x + S.get(i).dx;
			y = S.get(i).body.get(0).y + S.get(i).dy;
			e = map[x][y];
			if (e == 2) {
				for (int j = 0; j < A.size(); j++)
					if (A.get(j).x == x && A.get(j).y == y) {
						S.get(i).eat(e);
						A.remove(j);
						map[x][y] = 0;
					}

			}
			if (e == 3) {
				for (int j = 0; j < M.size(); j++)
					if (M.get(j).x == x && M.get(j).y == y) {
						S.get(i).eat(e);
						M.remove(j);

						map[x][y] = 0;
					}

			}
			if (e == 1) {
				S.get(i).eat(e);

			}
			if (e == 4 || e == 5) {
				for (int j = 0; j < S.size(); j++) {
					for (int k = 0; k < S.get(j).body.size(); k++)
						if (S.get(j).body.get(k).x == x && S.get(j).body.get(k).y == y) {
							if (i != j) {
								S.get(i).eat(e);
								S.get(j).die();

							} else {
								S.get(j).die();
							}
						}
				}
			}
		}

	}
/**
 * start()	ez a függvény indítja a játékot, először megállítja a delayt, ugyanis ha játszottunk
 * 			egy játékot és egy újabbat indítunk akkor el fog indulni magától, nem várva ránk,
 * 			utána a régi játékot töröljük a képernyőről, majd a kígyókat, almákat, mérgeket
 * 			hozzáadjuk a tárolóhoz, és F1-re várva indulhat a játék
 * 			
 * @param j	játékosok száma
 */
	public void start(int j) {
		if (j < 5) {
			// fest();
			delay.stop();
			erase(getGraphics());
			S = new ArrayList<>(4);
			s_n = j;
			addKeyListener(new MyKeyListener());
			setFocusable(true);
			S.add(new Snake(10, 10, 0, -1));
			S.add(new Snake(90, 10, 0, -1));

			if (s_n > 2)
				S.add(new Snake(10, 90, 0, 1));

			if (s_n == 4)
				S.add(new Snake(90, 90, 0, 1));
			if (A.size() == 0)
				almasit();
			if (M.size() == 0)
				mergesit();
			update();
			start = 1;
			paintComponent(getGraphics());
		}

	}
/**
 * erase() 		az egész képernyőt törli
 * @param g 	a Grafikus osztály amivel rajzolunk
 */
	public void erase(Graphics g) {
		g.clearRect(0, 0, 2000, 1000);
	}
/**
 * paintComponent()	A Graphics2D-t használom, ugyanis lehet scalelni vagyis nagyítani.
 * 					Innen hívom a Draw() függvényt
 * @param g			a Grafikus osztály amivel rajzolunk
 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(7, 7);
		Draw(g2);

	}
/**
 * lastone()	megnézi a kígyók között hány játékos van még játékban
 * @return cnt	hány kígyó van még játékban
 */
	public int lastone() {
		int cnt = 0;
		for (int i = 0; i < S.size(); i++)
			if (S.get(i).die == 0)
				cnt++;
		return cnt;
	}
/**
 * mxdraw() 	szóval a függvény kissé bonyolult, alapjában ő rajzolja ki a kis ablakokat, amit
 * 				minden játékos lát a saját kígyójáról, ezt kiszámolni élvezet volt.
 * 				Nagyjából úgy csinálja a kígyóra amit meghívtuk, megnézi a hosszát, ami max 10 
 * 				legyen a kirajzolás elférése végett. 
 * 				Ezután a kígyó referencia pontjától egészen a 4*testhossz+referenciapont+1 ig
 * 				nézzük a mátrixot, és ahol van valami vagy nincs semmi ott a képeink közül
 * 				valamelyiket kirajzoljuk, az i vagy j+ kezdő koordináta-referencia koordináta 
 * 				pontra. Ez így jó.
 * 				
 * @param g 	grafikus osztály ami rajzol
 * @param s		egy kígyó
 * @param sx 	kezdő pozició x koordinátája
 * @param sy 	kezdő pozició y koordinátája
 */
	public void mxdraw(Graphics2D g, Snake s, int sx, int sy) {
		if (start == 1 && S.size() > 1) {
			int a;
			if (s.body.size() <= 10)
				a = s.body.size();
			else
				a = 10;
			for (int i = s.rx; i < 4 * a + s.rx + 1; i++) {
				for (int j = s.ry; j < 4 * a + s.ry + 1; j++) {
					if (map[i][j] == 1)
						g.drawImage(Wall, i + sx - s.rx, j + sy - s.ry, this);
					if (map[i][j] == 0)
						g.drawImage(Empty, i + sx - s.rx, j + sy - s.ry, this);
					if (map[i][j] == 2)
						g.drawImage(Apple, i + sx - s.rx, j + sy - s.ry, this);
					if (map[i][j] == 3)
						g.drawImage(Poison, i + sx - s.rx, j + sy - s.ry, this);
					if (map[i][j] == 4)
						g.drawImage(Body, i + sx - s.rx, j + sy - s.ry, this);
					if (map[i][j] == 5)
						g.drawImage(Head, i + sx - s.rx, j + sy - s.ry, this);

				}

			}
		}
	}
/**
 * Draw()	ha elindult a játék és még játékban vagyunk akkor kiírjuk a játékosok eredményét,
 * 			ügyelve, hogy csak annyit ahányan játszanak, majd ezeket töröljük is, így frissítve
 * 			az eredményt.
 * 			Ha már vége a játéknak akkor az eredményt írjuk ki.
 * 			Illetve ha még nem indult el a játék de már megadtuk hányan vagyunk, akkor kiírjuk,
 * 			hogy F1-et legyen szives.
 * @param g a grafikus osztály amivel rajzolunk
 */
	private void Draw(Graphics2D g) {
		if (start == 1 && lastone() > 1) {
			String msg;
			Font m = new Font("Times", Font.BOLD, 6);
			g.setFont(m);
			if (S.size() >= 2) {
				msg = "P1 Score: " + S.get(0).score;
				g.clearRect(10, 0, 100, 7);
				g.drawString(msg, 10, 6);
				mxdraw(g, S.get(0), 15, 10);

				msg = "P2 Score: " + S.get(1).score;
				g.clearRect(150, 0, 100, 7);
				g.drawString(msg, 150, 6);
				mxdraw(g, S.get(1), 155, 10);
			}
			if (S.size() >= 3) {
				msg = "P3 Score: " + S.get(2).score;
				g.clearRect(10, 60, 100, 7);
				g.drawString(msg, 10, 66);

				mxdraw(g, S.get(2), 15, 70);
			}
			if (S.size() == 4) {
				msg = "P4 Score: " + S.get(3).score;
				g.clearRect(150, 60, 100, 7);
				g.drawString(msg, 150, 66);
				mxdraw(g, S.get(3), 155, 70);
			}
		}
		if (start == 1 && lastone() <= 1) {
			String msg;
			Font m = new Font("Times", Font.BOLD, 14);
			g.setFont(m);
			msg = "Game Over!";
			g.drawString(msg, 60, 50);
			
		}
		if(!delay.isRunning()&& start==1) {
			String msg;
			msg = "Press F1 to start";
			Font m = new Font("Times", Font.BOLD, 14);
			g.setFont(m);
			g.drawString(msg, 60, 50);
			
		}

		Toolkit.getDefaultToolkit().sync();
	}
/**
 * actionPerformed()	ez egy override, itt van a virtuális loopunk, ahol csekkoljuk az almákat
 * 						a mergeket, hogy van-e elég, illetve ütközést vizsgálunk, majd lépünk.
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (A.size() == 0)
			almasit();
		if (M.size() == 0)
			mergesit();
		collision();
		lep();

	}
}
