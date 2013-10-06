package ve.com.fml.view;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WelcomeWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public WelcomeWindow() {
		setResizable(false);
		setTitle("JGAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 369);
		
		JButton btnInicio = new JButton("Ingresar");
		btnInicio.setBounds(205, 307, 70, 23);
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneralWindow frame= new GeneralWindow();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 473, 239);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(WelcomeWindow.class.getResource("/Images/logo.png")));
		label.setLabelFor(this);
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setBounds(10, 250, 167, 34);
		
		JLabel lblNewLabel = new JLabel("Tutora:");
		verticalBox_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblTutora = new JLabel("Prof. Haydemar N\u00FA\u00F1ez");
		verticalBox_1.add(lblTutora);
		lblTutora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(297, 250, 167, 51);
		
		JLabel lblNewLabel_1 = new JLabel("Desarrolladores:");
		verticalBox.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Br. Lino Hern\u00e1ndez");
		verticalBox.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_3 = new JLabel("Br. Ignacio C\u00f3rdoba");
		verticalBox.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setLayout(null);
		getContentPane().add(verticalBox_1);
		getContentPane().add(verticalBox);
		getContentPane().add(btnInicio);
		getContentPane().add(label);
	}
}
