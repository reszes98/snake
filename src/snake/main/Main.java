package snake.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import snake.main.Map.*;

public class Main {
	/**
	 * main()	a programot ez a függvény indítja, itt van megvalósítva a JFrame, példányosítunk
	 * 			egy Map osztályt, a menüt stb.
	 * @param 	args nem csinál semmit
	 */
	public static void main(String[] args) {
		JFrame F = new JFrame("Snake");
		F.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Map m = new Map();
		F.add(m);
		F.pack();
		JMenuItem j2 = new JMenuItem("2 players     F2");
		JMenuItem j3 = new JMenuItem("3 players     F3");
		JMenuItem j4 = new JMenuItem("4 players     F4");
		JMenu jk = new JMenu("New Game");
		JMenuBar J = new JMenuBar();
		j2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.start(2);

			}
		});
		j3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.start(3);

			}
		});
		j4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.start(4);

			}
		});
		J.add(jk);
		jk.add(j2);
		jk.add(j3);
		jk.add(j4);
		F.setJMenuBar(J);

		F.pack();
		F.setExtendedState(JFrame.MAXIMIZED_BOTH);
		F.setVisible(true);

	}
}
