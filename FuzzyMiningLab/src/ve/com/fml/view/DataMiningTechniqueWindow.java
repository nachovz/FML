package ve.com.fml.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private javax.swing.JButton configButton;
	private javax.swing.JLabel configLabel;
	private javax.swing.JComboBox<String> dmTaskList;
	private javax.swing.JComboBox<String> dmTaskTypeList;
	private javax.swing.JComboBox<String> dmTechniqueList;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JPanel picPanel;
	private javax.swing.JLabel pictureLabel;

	public DataMiningTechniqueWindow(){

		setSize(630, 435);
		initComponents();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Selección de Técnica de Minería Difusa", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 255)));

	}

	private void initComponents() {

		final JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		jSeparator1 = new javax.swing.JSeparator();
		picPanel = new javax.swing.JPanel();
		pictureLabel = new javax.swing.JLabel();
		configButton = new javax.swing.JButton();
		configLabel = new javax.swing.JLabel();
		dmTaskTypeList = new javax.swing.JComboBox<String>();
		dmTaskList = new javax.swing.JComboBox<String>();
		dmTechniqueList = new javax.swing.JComboBox<String>();


		pictureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mapa_DefinirFuncion.png"))); // NOI18N

		javax.swing.GroupLayout picPanelLayout = new javax.swing.GroupLayout(picPanel);
		picPanel.setLayout(picPanelLayout);
		picPanelLayout.setHorizontalGroup(
				picPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(picPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(pictureLabel)
						.addContainerGap(12, Short.MAX_VALUE))
				);
		picPanelLayout.setVerticalGroup(
				picPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(pictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);

		configButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Parametros.png"))); // NOI18N
		configButton.setMinimumSize(new java.awt.Dimension(76, 50));
		configButton.setPreferredSize(new java.awt.Dimension(76, 50));
		configButton.addActionListener(new ActionListener() {
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

		configLabel.setText("Configurar Técnica");

		dmTaskTypeList.addItem("Seleccione un tipo de tarea de Minería de Datos");
		dmTaskTypeList.addItem("Predictiva");
		dmTaskTypeList.addItem("Descriptiva");
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


		dmTaskList.addItem("Seleccione una tarea de Minería de Datos");
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


		dmTechniqueList.addItem("Seleccione una técnica de Minería Difusa");
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

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
		setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addGap(10, 10, 10)
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(picPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jSeparator1)))
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addGap(62, 62, 62)
														.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																.addComponent(dmTaskTypeList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(dmTaskList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(dmTechniqueList, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
																		.addComponent(configButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(configLabel))
																		.addGap(61, 61, 61)))
																		.addContainerGap())
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addGap(7, 7, 7)
										.addComponent(configButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(7, 7, 7)
										.addComponent(configLabel))
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(dmTaskTypeList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(dmTaskList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(dmTechniqueList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(11, 11, 11)
												.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(picPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addContainerGap())
				);


	}


}
