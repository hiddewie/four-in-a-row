import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JPanel implements KeyListener {
	private JFrame frame = new JFrame ("Four in a Row");
	private int pos = 1;
	private int turn = 1;
	private int[] dropped = new int[8];
	private int[][] board = new int[8][7];
	private boolean oneWon = false;
	private boolean twoWon = false;

	/* MAIN */
	public static void main(String[] args) {
		new Main ();
	}

	/* INIT */
	Main() {
		frame.setBounds (250, 20, 900, 800);
		frame.setVisible (true);
		frame.setResizable (false);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		frame.add (this);

		frame.addKeyListener (this);

		init ();
	}
	private void init() {
		for (int i = 0; i < dropped.length; i++)
			dropped[i] = 1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = 0;
		}
		oneWon = false;
		twoWon = false;
		pos = 1;
		turn = 1;
	}

	/* METHODS */
	private void drop() {
		if (dropped[pos] > 6)
			return;
		if (oneWon || twoWon)
			return;

		board[pos][7 - dropped[pos]++] = turn;
		checkFIAR ();

		if (oneWon || twoWon) {
			repaint ();
			if (JOptionPane.showConfirmDialog (this, (oneWon ? "Red has won!" : "Yellow has won!") + "\n\nDo You want to play again?",
					(oneWon ? "Red has won!" : "Yellow has won!"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				reset();
			else
				System.exit (0);
		}
		nextTurn ();
	}
	private void reset() {
		init();
	}

	private void checkFIAR() {
		int i, j;
		int i0, j0;
		int count;

		for (i = 1; i <= 7; i++) {
			count = 0;
			for (j = 1; j <= 6; j++) {
				if (board[i][j] == turn)
					count++;
				if (board[i][j] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
			}
		}
		for (i = 1; i <= 6; i++) {
			count = 0;
			for (j = 1; j <= 7; j++) {
				if (board[j][i] == turn)
					count++;
				if (board[j][i] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
			}
		}
		for (j = 4; j <= 6; j++) {
			count = 0;
			i0 = 1;
			j0 = j;
			while (i0 <= 7 && j0 >= 1) {
				if (board[i0][j0] == turn)
					count++;
				if (board[i0][j0] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
				i0++;
				j0--;
			}

			count = 0;
			i0 = 7;
			j0 = j;
			while (i0 >= 1 && j0 >= 1) {
				if (board[i0][j0] == turn)
					count++;
				if (board[i0][j0] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
				i0--;
				j0--;
			}
		}
		for (i = 1; i <= 7; i++) {
			count = 0;
			i0 = i;
			j0 = 6;
			while (i0 <= 7 && j0 >= 1) {
				if (board[i0][j0] == turn)
					count++;
				if (board[i0][j0] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
				i0++;
				j0--;
			}

			count = 0;
			i0 = i;
			j0 = 6;
			while (i0 >= 1 && j0 >= 1) {
				if (board[i0][j0] == turn)
					count++;
				if (board[i0][j0] != turn)
					count = 0;
				if (count == 4) {
					if (turn == 1)
						oneWon = true;
					else if (turn == 2)
						twoWon = true;
					return;
				}
				i0--;
				j0--;
			}
		}
	}
	private void nextTurn() {
		turn = 3 - turn;
		repaint ();
	}
	private void drawRedStone(Graphics2D g2, int i, int j) {
		g2.setColor (Color.red);
		g2.fillArc (i * 100 + 5, j * 100 + 5, 90, 90, 0, 360);
		g2.setColor (new Color(225,0,0));
		g2.setStroke (new BasicStroke(10));
		g2.drawArc (i * 100 + 10, j * 100 + 10, 80, 80, 0, 360);
	}
	private void drawYellowStone(Graphics2D g2, int i, int j) {
		g2.setColor (Color.yellow);
		g2.fillArc (i * 100 + 5, j * 100 + 5, 90, 90, 0, 360);
		g2.setColor (new Color(225,180,0));
		g2.setStroke (new BasicStroke(10));
		g2.drawArc (i * 100 + 10, j * 100 + 10, 80, 80, 0, 360);
	}

	/* IMPLEMENTED */
	public void paintComponent(Graphics g) {
		super.paintComponent (g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke (new BasicStroke (5));
		for (int i = 100; i <= 900; i += 100)
			g2.drawLine (i, 100, i, 700);
		g2.drawLine (100, 700, 800, 700);

		for (int i = 1; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					drawRedStone (g2, i, j);
				} else if (board[i][j] == 2)
					drawYellowStone (g2, i, j);
			}
		}

		if (!(oneWon || twoWon)) {
			if (turn == 1)
				drawRedStone (g2, pos, 0);
			else if (turn == 2)
				drawYellowStone (g2, pos, 0);
		}
	}
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode ();
		if (code == KeyEvent.VK_RIGHT) {
			pos++;
			if (pos >= 7)
				pos = 7;
			repaint ();
		} else if (code == KeyEvent.VK_LEFT) {
			pos--;
			if (pos <= 1)
				pos = 1;
			repaint ();
		} else if (code == KeyEvent.VK_DOWN) {
			drop ();
		}

	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
