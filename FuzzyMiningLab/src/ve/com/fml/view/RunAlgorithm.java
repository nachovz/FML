package ve.com.fml.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ve.com.fml.model.datasource.GlobalData;

public class RunAlgorithm extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea resultArea = new JTextArea();
	private JButton setupAndRun = new JButton();
	
	/**
	 * Create the panel.
	 */
	public RunAlgorithm() {
		setSize(630, 435);
		setupAndRun.setSize(100,50);
		setupAndRun.setText("Ejecutar");
		resultArea.setSize(100,100);

		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Modelo resultante", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), Color.BLACK));
		setLayout(null);

		JPanel picture = new JPanel();
		picture.setBounds(16, 28, 589, 390);

		JLabel label = new JLabel("");
		//		if(GAL_GUI.language.imageLanguage.equals("image_es"))
		//			label.setIcon(new ImageIcon(EsquemaDeTrabajo.class.getResource("/Images/mapa_ppal.png")));
		//		else
		//			label.setIcon(new ImageIcon(EsquemaDeTrabajo.class.getResource("/Images/mapa_ppal_english.png")));
		label.setBackground(new Color(255,255,255));
		GroupLayout gl_picture = new GroupLayout(picture);
		gl_picture.setHorizontalGroup(
				gl_picture.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_picture.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);
		gl_picture.setVerticalGroup(
				gl_picture.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_picture.createSequentialGroup()
						.addContainerGap()
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
						.addContainerGap())
				);
		
		
		setupAndRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GlobalData.getInstance().setupAndRun();
				resultArea.setText(GlobalData.getInstance().getResult());
			}
		});
		picture.add(setupAndRun);
		
		//picture.setLayout(gl_picture);
		picture.add(resultArea);
		add(picture);
		
	}

}
