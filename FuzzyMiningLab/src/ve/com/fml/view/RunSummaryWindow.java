package ve.com.fml.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.fuzzy.FuzzyDataMining;

public class RunSummaryWindow extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RunSummaryWindow(JFrame topFrame, boolean isModal) {
		super(topFrame,isModal);
		initComponents();
	}


	private void initComponents() {

		setTitle("Ejecutar algoritmo");
		ok = false;
		summaryPane = new javax.swing.JScrollPane();
		summaryArea = new javax.swing.JTextArea();
		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		titleLabel = new javax.swing.JLabel();
		summaryArea.setColumns(20);
		summaryArea.setRows(5);
		//summaryArea.setEnabled(false);
		summaryArea.setEditable(false);
		summaryPane.setViewportView(summaryArea);

		okButton.setText("Aceptar");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ok = true;
				dispose();
			}
		});

		cancelButton.setText("Cancelar");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ok = false;
				dispose();
			}
		});

		int selectedTech = GlobalData.getInstance().getCurrentTechnique();
		String summaryText = "";
		HashMap<String, Object> opts = GlobalData.getInstance().getConfiguredTechnique();
		switch(selectedTech){
		case FuzzyDataMining.MODEL_FUZZY_APRIORI:
			titleLabel.setText("Resumen de configuraci�n de Apriori Difuso");
			summaryText += "- N�mero de reglas a generar: ";
			if(opts.containsKey("nr"))
				summaryText += opts.get("nr").toString()+"\n";
			else
				summaryText += "10\n";
			summaryText += "- Confianza m�nima de una regla: ";
			if(opts.containsKey("mc"))
				summaryText += opts.get("mc").toString()+"\n";
			else
				summaryText += "0.9\n";
			break;
		case FuzzyDataMining.MODEL_FUZZY_DT:
			titleLabel.setText("Resumen de configuraci�n de �rbol de Decisi�n Difuso");
			summaryText += "- N�mero m�nimo de instancias por hoja: ";
			if(opts.containsKey("mno"))
				summaryText += opts.get("nr").toString()+"\n";
			else
				summaryText += "2\n";
			summaryText += "- Umbral m�nimo de confianza para la poda: ";
			if(opts.containsKey("mno"))
				summaryText += opts.get("mno").toString()+"\n";
			else
				summaryText += "0.25\n";
			summaryText += "- Validaci�n cruzada con 10 pliegues";
			break;
		case FuzzyDataMining.MODEL_FUZZY_KMEANS:
			titleLabel.setText("Resumen de configuraci�n de K-Medias Difuso");
			summaryText += "- N�mero de grupos (clusters): ";
			if(opts.containsKey("k"))
				summaryText += opts.get("k").toString()+"\n";
			else
				summaryText += "2\n";
			break;
		case FuzzyDataMining.MODEL_FUZZY_KNN:
			titleLabel.setText("Resumen de configuraci�n de K-Vecinos Difuso");
			summaryText += "- N�mero de vecinos m�s cercanos (K): ";
			if(opts.containsKey("k"))
				summaryText += opts.get("k").toString()+"\n";
			else
				summaryText += "1\n";
			summaryText += "- Validaci�n cruzada con 10 pliegues";
			break;
		}
		
		summaryArea.setText(summaryText);
		summaryArea.setBackground(getBackground());
		summaryArea.setFont(getFont());

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(summaryPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(okButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(cancelButton))
										.addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(titleLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(summaryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(okButton)
								.addComponent(cancelButton))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>

	private javax.swing.JButton cancelButton;
	private javax.swing.JButton okButton;
	private javax.swing.JTextArea summaryArea;
	private javax.swing.JScrollPane summaryPane;
	private javax.swing.JLabel titleLabel;
	private boolean ok;

	public boolean getOk() {
		return ok;
	}
}
