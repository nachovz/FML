package ve.com.fml.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.ui.RefineryUtilities;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.datasource.InstancesLoader;

public class DataConfigurationWindow extends JPanel {

	/**
	 * Configuration window / Pantalla configuracion de datos
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton editButton;
	private javax.swing.JLabel editLabel;
	private javax.swing.JButton fuzzyButton;
	private javax.swing.JLabel fuzzyLabel;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JButton openButton;
	private javax.swing.JLabel openLabel;
	private javax.swing.JPanel picPanel;
	private javax.swing.JLabel pictureLabel;
	private javax.swing.JButton saveDatasetButton;
	private javax.swing.JLabel jLabel2;

	public DataConfigurationWindow(){


		setSize(630, 435);
		initComponents();
		setBorder(new TitledBorder(new LineBorder(new Color(47, 172, 67), 2), "Preparación de datos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(47, 172, 67)));

	}

	private void initComponents() {
		final JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        openLabel = new javax.swing.JLabel();
        openButton = new javax.swing.JButton();
        editLabel = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        picPanel = new javax.swing.JPanel();
        pictureLabel = new javax.swing.JLabel();
        fuzzyButton = new javax.swing.JButton();
        fuzzyLabel = new javax.swing.JLabel();
        saveDatasetButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

		openLabel.setText("Abrir Archivo");

		openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_AbrirConfig.png"))); // NOI18N
		openButton.setMinimumSize(new java.awt.Dimension(76, 50));
		openButton.setPreferredSize(new java.awt.Dimension(76, 50));
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Archivos ARFF y FARFF", "arff","farff"));
					int returnVal= fc.showOpenDialog(DataConfigurationWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						if(fc.getSelectedFile().getName().endsWith(".arff")){
							GlobalData.getInstance().setFuzzyInstances(InstancesLoader.loadFromArffFile(fc.getSelectedFile().getAbsolutePath()));	
						}else if(fc.getSelectedFile().getName().endsWith(".farff")){
							GlobalData.getInstance().setFuzzyInstances(InstancesLoader.loadFromFarffFile(fc.getSelectedFile().getAbsolutePath()));
						}

						GlobalData.getInstance().setDatasetName(fc.getSelectedFile().getName());
					}
					//actualizar();
				}catch(Exception ex){
					//					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
					JOptionPane.showMessageDialog(null, "Error al cargar archivos");
				}
			}
		});

		editLabel.setText("Editar Datos");

		editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Parametros.png"))); // NOI18N
		editButton.setMinimumSize(new java.awt.Dimension(76, 50));
		editButton.setPreferredSize(new java.awt.Dimension(76, 50));
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				if(GlobalData.getInstance().getFuzzyInstances() == null)
					JOptionPane.showMessageDialog(DataConfigurationWindow.this, "No se han cargado los datos");
				else{
					EditDataWindow editFrame= new EditDataWindow(topFrame,true);
					editFrame.setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Images/icon-fml.png")).getImage()));
					RefineryUtilities.centerFrameOnScreen(editFrame);
					editFrame.setVisible(true);				

				}
			}
		});

		pictureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/diagrama-preparacion.png"))); // NOI18N

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

		fuzzyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Resultados.png"))); // NOI18N
		fuzzyButton.setMinimumSize(new java.awt.Dimension(76, 50));
		fuzzyButton.setPreferredSize(new java.awt.Dimension(76, 50));
		fuzzyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(DataConfigurationWindow.this, GAL_GUI.language.Errors[21]);
				if(GlobalData.getInstance().getFuzzyInstances() == null)
					JOptionPane.showMessageDialog(DataConfigurationWindow.this, "No se han cargado los datos");
				else{
					DefineFuzzySetsWindow fuzzyEditFrame= new DefineFuzzySetsWindow(topFrame,true);
					fuzzyEditFrame.setIconImage((new javax.swing.ImageIcon(getClass().getResource("/Images/icon-fml.png")).getImage()));
					RefineryUtilities.centerFrameOnScreen(fuzzyEditFrame);
					fuzzyEditFrame.setVisible(true);
				}
			}
		});
		fuzzyLabel.setText("Difusificación");
		

        saveDatasetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Guardar.png"))); // NOI18N
        saveDatasetButton.setMinimumSize(new java.awt.Dimension(76, 50));
        saveDatasetButton.setPreferredSize(new java.awt.Dimension(76, 50));
        
        saveDatasetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(GlobalData.getInstance().getFuzzyInstances());
				if(GlobalData.getInstance().getFuzzyInstances() == null){
					JOptionPane.showMessageDialog(null, "Debe configurar un conjunto de datos para poder guardarlo.");
					return;
				}
				String sufix = "";
				try{
					JFileChooser fc= new JFileChooser();
					if(GlobalData.getInstance().getFuzzyInstances().getMembership().isEmpty()){
						fc.setFileFilter(new FileNameExtensionFilter("Archivos ARFF", "arff"));
						sufix = ".arff";
					}else{
						fc.setFileFilter(new FileNameExtensionFilter("Archivos FARFF", "farff"));
						sufix = ".farff";
					}
					
					int returnVal= fc.showSaveDialog(DataConfigurationWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						InstancesLoader.saveToTextFile(GlobalData.getInstance().getFuzzyInstances(), fc.getSelectedFile().getAbsolutePath()+sufix);
					}
					//actualizar();
				}catch(Exception ex){
					//					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
					JOptionPane.showMessageDialog(null, "Error al guardar el archivo");
				}
			}
		});

        jLabel2.setText("Guardar");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(picPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(openLabel))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(fuzzyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fuzzyLabel))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(saveDatasetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(openLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fuzzyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(fuzzyLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(editLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(saveDatasetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(picPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );


	}
}
