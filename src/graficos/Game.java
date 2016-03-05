package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	int x = 0;
	int y = 0;
	int xa = 1;
	int ya = 1;
	static boolean show = true;

	public Game() {
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		setFocusable(true);
	}

	private void moveBall() {
		if (x + xa < 0)
			xa = 1;
		if (x + xa > getWidth() - 30)
			xa = -1;
		if (y + ya < 0)
			ya = 1;
		if (y + ya > getHeight() - 30)
			ya = -1;

		x = x + xa;
		y = y + ya;
	}

	@Override
	public void paint(Graphics g) {
		/*
		 * super.paint(g); Graphics2D g2d = (Graphics2D) g;
		 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		 * RenderingHints.VALUE_ANTIALIAS_ON); g.fillOval(x, y, 25, 380);
		 */
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GRAY);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/font.ttf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		g2d.setFont(new Font(font.getName(), Font.BOLD, 30));

		try {
			Image backgroundImage = ImageIO.read(new File("resources/portada.jpg"));
			g.drawImage(backgroundImage, 0, 0, this);
			if (show) {
				g2d.drawString("Pulsa INTRO para continuar", 650, 950);
			} else {
				g.drawImage(backgroundImage, 0, 0, this);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()));
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("keyReleased=" + KeyEvent.getKeyText(e.getKeyCode()));
		}
	}

	/*
	 * @Override public void paintComponent(Graphics g) {
	 * super.paintComponent(g);
	 * 
	 * try { Image backgroundImage = ImageIO.read(new
	 * File("resources/portada.jpg")); g.drawImage(backgroundImage, 0, 0, this);
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Pokemon Ada Byron");
		Game game = new Game();
		frame.add(game);
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setUndecorated(true);
		game.repaint();
		try {
			Clip clip = AudioSystem.getClip();
			File file = new File("resources/title.wav");
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		while (true) {
			Thread.sleep(400);
			show = (show==true) ? false : true;
			game.repaint();
		}

		//
		// Font font = null;
		// try {
		// font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new
		// File("resources/font.ttf")));
		// } catch (FontFormatException | IOException e) {
		// e.printStackTrace();
		// }
		// GraphicsEnvironment ge =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// ge.registerFont(font);
		// JLabel jlabel = new JLabel("Pulsa INTRO para continuar",
		// JLabel.CENTER);
		// jlabel.setVerticalTextPosition(JLabel.BOTTOM);
		// jlabel.setHorizontalTextPosition(JLabel.CENTER);
		//
		// jlabel.setFont(new Font(font.getName(), Font.PLAIN, 20));
		// jlabel.setMaximumSize(new Dimension(200, 300));
		// jlabel.setOpaque(true);
		// frame.add(jlabel);		
	}

}