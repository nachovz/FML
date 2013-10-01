package ve.com.fml.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jfree.ui.RefineryUtilities;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.fuzzy.FuzzyDataMining;

public class DataMiningTechniqueWindow extends JPanel {

	/**
	 * Configuration window / Pantalla configuracion de datos
	 */
	private static final long serialVersionUID = 1L;

	public DataMiningTechniqueWindow(){

		final JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		//		JMenuItem mntmAbrir = new JMenuItem(GAL_GUI.language.CommonWords[3]);
		//		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		//		mntmAbrir.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				try{
		//					JFileChooser fc= new JFileChooser();
		//					int returnVal= fc.showOpenDialog(DataConfigurationWindow.this);
		//					if(returnVal == JFileChooser.APPROVE_OPTION)
		//						GlobalData.getInstance().setFuzzyInstances(new FuzzyInstances(InstancesLoader.loadFromTextFile(fc.getSelectedFile().getAbsolutePath())));
		//					//actualizar();
		//				}catch(Exception ex){
		//					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
		//				}
		//			}
		//		});
		//		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		//		mnArchivo.add(mntmAbrir);

		//--------------------------------

		setSize(630, 435);

		//		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), GAL_GUI.language.casosDeUso[4], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 255)));
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Selección de Técnica de Minería Difusa", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 255)));

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


		final JComboBox<String> dmTaskTypeList = new JComboBox<String>();		
		dmTaskTypeList.addItem("Seleccione un tipo de tarea de Minería de Datos");
		dmTaskTypeList.addItem("Predictiva");
		dmTaskTypeList.addItem("Descriptiva");

		final JComboBox<String> dmTaskList = new JComboBox<String>();
		dmTaskList.addItem("Seleccione una tarea de Minería de Datos");

		final JComboBox<String> dmTechniqueList = new JComboBox<String>();
		dmTechniqueList.addItem("Seleccione una técnica de Minería Difusa");

		dmTaskTypeList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dmTaskList.removeAllItems();
				dmTaskList.addItem("Seleccione una tarea de Minería de Datos");
				dmTaskList.setSelectedIndex(0);

				dmTechniqueList.removeAllItems();
				dmTechniqueList.addItem("Seleccione una técnica de Minería Difusa");
				dmTechniqueList.setSelectedIndex(0);

				switch(dmTaskTypeList.getSelectedIndex()){
				case 1: //Predictiva
					dmTaskList.addItem("Clasificación");
					dmTaskList.addItem("Regresión");
					break;
				case 2: //Descriptiva
					dmTaskList.addItem("Agrupación (Clustering)");
					dmTaskList.addItem("Asociación");
					break;
				}
			}
		});
		
		dmTaskList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dmTechniqueList.removeAllItems();
				dmTechniqueList.addItem("Seleccione una técnica de Minería Difusa");
				dmTechniqueList.setSelectedIndex(0);

				switch(dmTaskTypeList.getSelectedIndex()){
				case 1: //Predictiva
					switch(dmTaskList.getSelectedIndex()){
					case 1: //Clasificación
						dmTechniqueList.addItem("K-Vecinos Difusos");
						dmTechniqueList.addItem("Árboles de Decisión Difusos");
						break;
					case 2: //Regresión
						dmTechniqueList.addItem("K-Vecinos Difusos");
						break;
					}
					break;
				case 2: //Descriptiva
					switch(dmTaskList.getSelectedIndex()){
					case 1: //ClLustering
						dmTechniqueList.addItem("K-Medias Difuso");
						break;
					case 2: //Asosiación
						dmTechniqueList.addItem("Apriori Difuso");
						break;
					}
					break;
				}
			}
		});
		
		dmTechniqueList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				switch(dmTaskTypeList.getSelectedIndex()){
				case 1: //Predictiva
					switch(dmTaskList.getSelectedIndex()){
					case 1: //Clasificación
						switch(dmTechniqueList.getSelectedIndex()){
						case 1: //K-Vecinos Difusos
							GlobalData.getInstance().setCurrentTechnique(FuzzyDataMining.MODEL_FUZZY_KNN);
							break;
						case 2: //Árboles de Decisión Difusos
							GlobalData.getInstance().setCurrentTechnique(FuzzyDataMining.MODEL_FUZZY_DT);
							break;
						}
						break;
					case 2: //Regresión
						switch(dmTechniqueList.getSelectedIndex()){
						case 1: //K-Vecinos Difusos
							GlobalData.getInstance().setCurrentTechnique(FuzzyDataMining.MODEL_FUZZY_KNN);
							break;
						}
						break;
					}
					break;
				case 2: //Descriptiva
					switch(dmTaskList.getSelectedIndex()){
					case 1: //ClLustering
						switch(dmTechniqueList.getSelectedIndex()){
						case 1: //K-Vecinos Difusos
							GlobalData.getInstance().setCurrentTechnique(FuzzyDataMining.MODEL_FUZZY_KMEANS);
							break;
						}
						break;
					case 2: //Asosiación
						switch(dmTechniqueList.getSelectedIndex()){
						case 1: //K-Vecinos Difusos
							GlobalData.getInstance().setCurrentTechnique(FuzzyDataMining.MODEL_FUZZY_APRIORI);
							break;
						}
						break;
					}
					break;
				}
			}
		});
		
		dmTaskTypeList.setBounds(48, 78-50, 300, 20);
		dmTaskList.setBounds(48, 78-25, 300, 20);
		dmTechniqueList.setBounds(48, 78, 300, 20);

		/*JButton btn_AbrirConfiguracion = new JButton("");
		btn_AbrirConfiguracion.setIcon(new ImageIcon(DataMiningTechniqueWindow.class.getResource("/Images/btn_AbrirConfig.png")));
		btn_AbrirConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Arff files", "arff"));
					int returnVal= fc.showOpenDialog(DataMiningTechniqueWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GlobalData.getInstance().setFuzzyInstances(new FuzzyInstances(InstancesLoader.loadFromTextFile(fc.getSelectedFile().getAbsolutePath())));
					//actualizar();
				}catch(Exception ex){
					//					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
					JOptionPane.showMessageDialog(null, "Error al seleccionar Tarea");
				}
				actualizar();
			}
		});
		btn_AbrirConfiguracion.setBounds(48, 28, 76, 50);
		JLabel lblAbrirConf = new JLabel("Seleccionar Tarea");


		lblAbrirConf.setBounds(48, 84, 76, 14);
		lblAbrirConf.setHorizontalAlignment(SwingConstants.CENTER);*/

		JButton btn_TechniqueConfiguration = new JButton("");
		btn_TechniqueConfiguration.setIcon(new ImageIcon(DataMiningTechniqueWindow.class.getResource("/Images/btn_Parametros.png")));
		btn_TechniqueConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				if(GlobalData.getInstance().getCurrentTechnique() == null)
					JOptionPane.showMessageDialog(DataMiningTechniqueWindow.this, "Debe seleccionar una técnica");
				else{
					switch(GlobalData.getInstance().getCurrentTechnique()){
					case FuzzyDataMining.MODEL_FUZZY_KNN:
						//EditDataWindow kNNFrame = new EditDataWindow();
						EditKNNWindow kNNFrame = new EditKNNWindow(topFrame,true);
						RefineryUtilities.centerFrameOnScreen(kNNFrame);
						kNNFrame.setVisible(true);
						break;
					case FuzzyDataMining.MODEL_FUZZY_DT:
						EditDTWindow dTFrame = new EditDTWindow(topFrame,true);
						RefineryUtilities.centerFrameOnScreen(dTFrame);
						dTFrame.setVisible(true);
						break;
					case FuzzyDataMining.MODEL_FUZZY_KMEANS:
						EditKMeansWindow kMeansFrame = new EditKMeansWindow(topFrame,true);
						RefineryUtilities.centerFrameOnScreen(kMeansFrame);
						kMeansFrame.setVisible(true);
						break;
					case FuzzyDataMining.MODEL_FUZZY_APRIORI:
						EditAprioriWindow aprioriFrame = new EditAprioriWindow(topFrame,true);
						RefineryUtilities.centerFrameOnScreen(aprioriFrame);
						aprioriFrame.setVisible(true);
						break;
					}
					
				}
			}
		});
		btn_TechniqueConfiguration.setBounds(400, 28, 76, 50);

		//		JLabel lblVerResultados = new JLabel(GAL_GUI.language.botonesPrincipales[8]);
		JLabel lblVerResultados = new JLabel("Configurar Técnica");
		lblVerResultados.setBounds(365, 84, 150, 14);
		lblVerResultados.setHorizontalAlignment(SwingConstants.CENTER);

		//		JButton btn_Ejecutar = new JButton("");
		//		btn_Ejecutar.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/btn_Ejecutar.png")));
		//		btn_Ejecutar.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				int ret= JOptionPane.showOptionDialog(DataConfigurationWindow.this, GAL_GUI.language.Questions[3], GAL_GUI.language.botonesPrincipales[7], 0, JOptionPane.PLAIN_MESSAGE,
		//						null, new String[]{GAL_GUI.language.Questions[4],GAL_GUI.language.Questions[5]}, GAL_GUI.language.Questions[4]);
		//				if(ret!=-1){
		//					if(GAL_GUI.gal.runHandler(ret==0)){
		//						GeneralWindow.pnl_ProgressResultados.setBackground(new Color(0, 0, 255));
		//						ResultsViewWindow Newframe= new ResultsViewWindow();
		//						Newframe.setVisible(true);
		//					}else{
		//						GeneralWindow.pnl_ProgressResultados.setBackground(new Color(240, 240, 240));
		//					}
		//				}
		//			}
		//		});
		//		btn_Ejecutar.setBounds(48, 28, 76, 50);
		//		
		//		JLabel lblEjecutar = new JLabel(GAL_GUI.language.botonesPrincipales[7]);
		//		lblEjecutar.setBounds(48, 84, 76, 14);
		//		lblEjecutar.setHorizontalAlignment(SwingConstants.CENTER);
		//		
		//		
		//		
		//		JButton btn_Guardar = new JButton("");
		//		btn_Guardar.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/btn_Guardar.png")));
		//		btn_Guardar.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				int ret= JOptionPane.showOptionDialog(DataConfigurationWindow.this, GAL_GUI.language.Questions[0], GAL_GUI.language.botonesPrincipales[9], 0, JOptionPane.PLAIN_MESSAGE,
		//						null, new String[]{GAL_GUI.language.Questions[1],GAL_GUI.language.Questions[2]}, GAL_GUI.language.Questions[1]);
		//				if(ret==0){
		//					JFileChooser fc= new JFileChooser();
		//					int returnVal= fc.showSaveDialog(DataConfigurationWindow.this);
		//					if(returnVal == JFileChooser.APPROVE_OPTION)
		//						GAL_GUI.gal.saveAll(fc.getSelectedFile());
		//				}if(ret==1){
		//					try {
		//						if(!GAL_GUI.gal.executed())
		//							throw new Exception(GAL_GUI.language.Errors[22]);
		//						JFileChooser fc= new JFileChooser();
		//						int returnVal= fc.showSaveDialog(DataConfigurationWindow.this);
		//						if(returnVal == JFileChooser.APPROVE_OPTION)
		//							GAL_GUI.gal.saveResults(fc.getSelectedFile());
		//					} catch (Exception e1) {
		//						JOptionPane.showMessageDialog(DataConfigurationWindow.this, e1.getMessage());
		//					}
		//				}
		//			}
		//		});
		//		btn_Guardar.setBounds(284, 28, 76, 50);
		//		
		//		JLabel lblGuardar = new JLabel(GAL_GUI.language.botonesPrincipales[9]);
		//		lblGuardar.setBounds(284, 84, 76, 14);
		//		lblGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		//		
		//		
		//		
		//		
		//		
		//		JLabel lbl_help = new JLabel("");
		//		help.add(lbl_help);
		//		lbl_help.setBackground(Color.BLACK);
		//		lbl_help.setIcon(new ImageIcon(DataConfigurationWindow.class.getResource("/Images/help.png")));
		//		setLayout(null);
		//		
		//		JLabel label = new JLabel("");
		//		if(GAL_GUI.language.imageLanguage.equals("image_es"))
		//			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DataConfigurationWindow.png")));
		//		else
		//			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DataConfigurationWindow_english.png")));
		//		
		//		GroupLayout gl_picture = new GroupLayout(picture);
		//		gl_picture.setHorizontalGroup(
		//			gl_picture.createParallelGroup(Alignment.TRAILING)
		//				.addGroup(Alignment.LEADING, gl_picture.createSequentialGroup()
		//					.addContainerGap()
		//					.addComponent(label, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
		//					.addContainerGap())
		//		);
		//		gl_picture.setVerticalGroup(
		//			gl_picture.createParallelGroup(Alignment.LEADING)
		//				.addGroup(gl_picture.createSequentialGroup()
		//					.addContainerGap()
		//					.addComponent(label, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
		//					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		//		);

		setLayout(null);
		//		picture.setLayout(gl_picture);
		//		add(picture);
		//		add(lblEjecutar);
		//		add(btn_Ejecutar);
		
		add(dmTaskTypeList);
		add(dmTaskList);
		add(dmTechniqueList);
		add(lblVerResultados);
		add(btn_TechniqueConfiguration);
		//		add(lblGuardar);
		//		add(btn_Guardar);
		//add(lblAbrirConf);
		//add(btn_AbrirConfiguracion);
		//		add(help);
	}

	void actualizar(){
		//		if(GAL_GUI.gal.getGeneNames().length>0)
		//			GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(0, 128, 64));
		//		else
		//			GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(240, 240, 240));
		//		
		//		if(GAL_GUI.gal.getInterpreter(0).getValid())
		//			GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(128, 0, 128));
		//		else
		//			GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240, 240, 240));
		//
		//		
		//		GAL_GUI.gal.validateInterpreter(1);
		//		if(GAL_GUI.gal.getInterpreter(1).getValid())
		//			GeneralWindow.pnl_ProgressFuncion2.setBackground(new Color(128, 0, 128));
		//		else
		//			GeneralWindow.pnl_ProgressFuncion2.setBackground(new Color(240, 240, 240));
		//		
		//		if(GAL_GUI.gal.getSelector()!=null)
		//			GeneralWindow.pnl_ProgressConfig1.setBackground(new Color(255, 50, 0));
		//		else
		//			GeneralWindow.pnl_ProgressConfig1.setBackground(new Color(240, 240, 240));
		//		
		//		if(GAL_GUI.gal.getOperatorsNames().length>0)
		//			GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(255, 50, 0));
		//		else
		//			GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(240, 240, 240));
		//		
		//		if(GAL_GUI.gal.parametersAssigned())
		//			GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(255, 50, 0));
		//		else
		//			GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(240, 240, 240));
		//		
		//		GeneralWindow.pnl_ProgressResultados.setBackground(new Color(240, 240, 240));
		//	}

	}


}
