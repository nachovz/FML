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

public class RunAlgorithm extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public RunAlgorithm() {
		initComponents();

		//		
	}

	private void initComponents() {

		final JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Modelo resultante", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), Color.BLACK));
		jLabel1 = new javax.swing.JLabel();
		runAlgButton = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		saveDatasetButton = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jScrollPane1 = new javax.swing.JScrollPane();
		resultArea = new javax.swing.JEditorPane();

		jLabel1.setText("Ejecutar Algoritmo");

		runAlgButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Ejecutar.png"))); // NOI18N
		runAlgButton.setMaximumSize(new java.awt.Dimension(99, 49));
		runAlgButton.setMinimumSize(new java.awt.Dimension(76, 50));
		runAlgButton.setPreferredSize(new java.awt.Dimension(76, 50));

		jLabel2.setText("Guardar Conjunto de Datos");

		saveDatasetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_Guardar.png"))); // NOI18N
		saveDatasetButton.setMinimumSize(new java.awt.Dimension(76, 50));
		saveDatasetButton.setPreferredSize(new java.awt.Dimension(76, 50));

		//        resultArea.setColumns(20);
		//        resultArea.setRows(5);
		jScrollPane1.setViewportView(resultArea);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
		setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addGap(10, 10, 10)
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jSeparator1)
												.addComponent(jScrollPane1)))
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addGap(132, 132, 132)
														.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
																.addComponent(runAlgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel1))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
																.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
																		.addComponent(saveDatasetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jLabel2))
																		.addGap(108, 108, 108)))
																		.addContainerGap())
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(saveDatasetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(7, 7, 7)
										.addComponent(jLabel2))
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(runAlgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(7, 7, 7)
												.addComponent(jLabel1)))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
												.addContainerGap())
				);



		runAlgButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RunSummaryWindow runSummaryWindow = new RunSummaryWindow(topFrame, true);
				RefineryUtilities.centerFrameOnScreen(runSummaryWindow);
				runSummaryWindow.setVisible(true);
				if(runSummaryWindow.getOk()){
					GlobalData.getInstance().setupAndRun();
					resultArea.setText(GlobalData.getInstance().getResult());
				}
			}
		});
		
		saveDatasetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(GlobalData.getInstance().getFuzzyInstances());
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
					
					int returnVal= fc.showSaveDialog(RunAlgorithm.this);
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

	}// </editor-fold>

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JEditorPane resultArea;
	private javax.swing.JButton runAlgButton;
	private javax.swing.JButton saveDatasetButton;

}
