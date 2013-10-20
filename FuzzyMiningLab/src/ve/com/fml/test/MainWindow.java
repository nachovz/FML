package ve.com.fml.test;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

import ve.com.fml.view.WelcomeWindow;

public class MainWindow {

	private JFrame frame;
	//private static GeneralWindow window;
	private static WelcomeWindow window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//window = new GeneralWindow();
					window = new WelcomeWindow();
					window.setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Images/icon-fml.png")).getImage()));
					RefineryUtilities.centerFrameOnScreen(window);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
