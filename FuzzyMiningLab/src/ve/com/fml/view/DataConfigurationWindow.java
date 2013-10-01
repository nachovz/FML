package ve.com.fml.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.ui.RefineryUtilities;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.datasource.InstancesLoader;
import ve.com.fml.model.fuzzy.FuzzyInstances;

public class DataConfigurationWindow extends JPanel {

	/**
	 * Configuration window / Pantalla configuracion de datos
	 */
	private static final long serialVersionUID = 1L;

	public DataConfigurationWindow(){

		final JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		setSize(630, 435);

		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Carga de datos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 255)));

		//		JPanel picture = new JPanel();
		//		picture.setBounds(16, 132, 598, 286);
		//		picture.setBackground(Color.WHITE);

		//		JPanel help = new JPanel();
		//		help.addMouseListener(new MouseAdapter() {
		//			@Override
		//			public void mouseClicked(MouseEvent e) {
		//				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[3]);
		//				// Create a new frame.
		//				JFrame helpFrame = new JFrame();
		//				// Set it's size.
		//				helpFrame.setSize(800,600);
		//				// Add the created helpViewer to it.
		//				helpFrame.getContentPane().add(GAL_GUI.helpViewer);
		//				// Set a default close operation.
		//				helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//				//Ponemos en visible
		//				helpFrame.setVisible(true);
		//			}
		//		});
		//		help.setBounds(598, 7, 28, 28);
		//		help.setBackground(new Color(0, 0, 255));

		/**Controles para abrir el archivo*/

		JButton btn_AbrirConfiguracion = new JButton("");
		btn_AbrirConfiguracion.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/btn_AbrirConfig.png")));
		btn_AbrirConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Arff files", "arff"));
					int returnVal= fc.showOpenDialog(DataConfigurationWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GlobalData.getInstance().setFuzzyInstances(new FuzzyInstances(InstancesLoader.loadFromTextFile(fc.getSelectedFile().getAbsolutePath())));
						GlobalData.getInstance().setDatasetName(fc.getSelectedFile().getName());
					//actualizar();
				}catch(Exception ex){
					//					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
					JOptionPane.showMessageDialog(null, "Error al cargar archivos");
				}
				actualizar();
			}
		});
		btn_AbrirConfiguracion.setBounds(48, 28, 76, 50);



		//		JLabel lblAbrirConf = new JLabel(GAL_GUI.language.botonesPrincipales[10]);
		JLabel lblAbrirConf = new JLabel("Abrir Archivo");
		lblAbrirConf.setBounds(48, 84, 76, 14);
		lblAbrirConf.setHorizontalAlignment(SwingConstants.CENTER);



		/**Controles para editar el conjunto de datos*/

		JButton btn_VerResultados = new JButton("");
		btn_VerResultados.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/btn_Parametros.png")));
		btn_VerResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				if(GlobalData.getInstance().getFuzzyInstances() == null)
					JOptionPane.showMessageDialog(DataConfigurationWindow.this, "No se han cargado los datos");
				else{
					
					EditDataWindow editFrame= new EditDataWindow(topFrame,true);
					RefineryUtilities.centerFrameOnScreen(editFrame);
					editFrame.setVisible(true);				
					
				}
				//				if(!GAL_GUI.gal.executed())
				//					JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				//				else{
				//					ResultsViewWindow Newframe= new ResultsViewWindow();
				//					Newframe.setVisible(true);
				//				}
			}
		});
		btn_VerResultados.setBounds(166, 28, 76, 50);

		//		JLabel lblVerResultados = new JLabel(GAL_GUI.language.botonesPrincipales[8]);
		JLabel lblVerResultados = new JLabel("Editar datos");
		lblVerResultados.setBounds(166, 84, 76, 14);
		lblVerResultados.setHorizontalAlignment(SwingConstants.CENTER);

		/**Controles para difusificar los datos*/

		JButton btn_DefineFuzzySets = new JButton("");
		btn_DefineFuzzySets.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/btn_Resultados.png")));
		btn_DefineFuzzySets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				if(GlobalData.getInstance().getFuzzyInstances() == null)
					JOptionPane.showMessageDialog(DataConfigurationWindow.this, "No se han cargado los datos");
				else{
					DefineFuzzySetsWindow fuzzyEditFrame= new DefineFuzzySetsWindow(topFrame,true);
					RefineryUtilities.centerFrameOnScreen(fuzzyEditFrame);
					fuzzyEditFrame.setVisible(true);
				}
				//				if(!GAL_GUI.gal.executed())
				//					JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				//				else{
				//					ResultsViewWindow Newframe= new ResultsViewWindow();
				//					Newframe.setVisible(true);
				//				}
			}
		});
		btn_DefineFuzzySets.setBounds(280, 28, 76, 50);

		//		JLabel lblVerResultados = new JLabel(GAL_GUI.language.botonesPrincipales[8]);
		JLabel lblDefineFuzzySets = new JLabel("Difusificación");
		lblDefineFuzzySets.setBounds(280, 84, 76, 14);
		lblDefineFuzzySets.setHorizontalAlignment(SwingConstants.CENTER);



		setLayout(null);
		//		picture.setLayout(gl_picture);
		//		add(picture);
		//		add(lblEjecutar);
		//		add(btn_Ejecutar);
		add(lblDefineFuzzySets);
		add(btn_DefineFuzzySets);
		
		add(lblVerResultados);
		add(btn_VerResultados);
		//		add(lblGuardar);
		//		add(btn_Guardar);
		add(lblAbrirConf);
		add(btn_AbrirConfiguracion);
		//		add(help);
	}

	void actualizar(){
		
	}


}
