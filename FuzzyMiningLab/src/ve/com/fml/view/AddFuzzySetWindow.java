package ve.com.fml.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.fuzzy.FuzzyVariable;
import ve.com.fml.model.fuzzy.membership.FuzzyMembership;
import ve.com.fml.model.fuzzy.membership.GaussianFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.SingletonFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TrapFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TriangleFuzzyMembership;

public class AddFuzzySetWindow extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FuzzySetFormPanel options;
	private String fuzzySetLabel;
	private Integer attrIndex;

	private javax.swing.JButton cancelButton;
	private javax.swing.JComboBox<String> fuzzySetType;

	private javax.swing.JPanel jPanel1 = new JPanel();
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;

	private javax.swing.JButton saveButton;


	public AddFuzzySetWindow(Integer attrIndex, JDialog topFrame, boolean isModal) {
		this(attrIndex, null, topFrame, isModal);
	}

	public AddFuzzySetWindow(Integer attrIdx, String fuzzyLabel, JDialog topFrame, boolean isModal) {
		super(topFrame, isModal);
		attrIndex = attrIdx;
		fuzzySetLabel = fuzzyLabel;
		setTitle("Definición de conjunto difuso");
		initComponents();
		if(fuzzySetLabel != null)
			fuzzySetType.setEnabled(false);
	}

	private void refreshFuzzySetDef(Integer index, FuzzyMembership mem){
		if(options != null){
			remove(options);
			options.removeAll();
		}
			
		switch(index){
		case 1:
			options = new TriangleFormPanel((TriangleFuzzyMembership)mem,fuzzySetLabel);
			break;
		case 2:
			options = new TrapFormPanel((TrapFuzzyMembership)mem,fuzzySetLabel);
			break;
		case 3:
			options = new SingletonFormPanel((SingletonFuzzyMembership)mem,fuzzySetLabel);
			break;
		case 4:
			options = new GaussianFormPanel((GaussianFuzzyMembership)mem,fuzzySetLabel);
			break;
		}		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(options, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fuzzySetType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jSeparator1)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(saveButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cancelButton))
										.addComponent(jSeparator2))
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(fuzzySetType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(saveButton)
								.addComponent(cancelButton))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}

	private void initComponents() {

		fuzzySetType = new javax.swing.JComboBox<String>();
		fuzzySetType.addItem("Seleccione un tipo de función de pertenencia");
		fuzzySetType.addItem("Triangular");
		fuzzySetType.addItem("Trapezoidal");
		fuzzySetType.addItem("Singleton");
		fuzzySetType.addItem("Gaussiana");
		

		fuzzySetType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(fuzzySetType.getSelectedIndex() == 0){
					if(options != null){
						remove(options);
						repaint();
					}
				}else{
					if(fuzzySetLabel != null)
						refreshFuzzySetDef(fuzzySetType.getSelectedIndex(),GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel));
					else
						refreshFuzzySetDef(fuzzySetType.getSelectedIndex(),null);
				}
			}
		});

		jSeparator1 = new javax.swing.JSeparator();
		cancelButton = new javax.swing.JButton();
		saveButton = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JSeparator();


		cancelButton.setText("Cancelar");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		saveButton.setText("Guardar");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Validar 
				if(fuzzySetLabel != null){
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).removeFuzzySet(fuzzySetLabel);
					options.save();
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).addFuzzySet(options.name,options.save());
					JOptionPane.showMessageDialog(AddFuzzySetWindow.this, "El conjunto difuso \""+options.name+"\" ha sido actualizado.");
				}else{
					FuzzyMembership fM = options.save();
					if(!GlobalData.getInstance().getFuzzyInstances().getMembership().containsKey(attrIndex))
						GlobalData.getInstance().getFuzzyInstances().getMembership().put(attrIndex, new FuzzyVariable());
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).addFuzzySet(options.name, fM);
					JOptionPane.showMessageDialog(AddFuzzySetWindow.this, "El conjunto difuso \""+options.name+"\" ha sido agregado.");
				}
				dispose();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fuzzySetType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jSeparator1)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(saveButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cancelButton))
										.addComponent(jSeparator2))
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(fuzzySetType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(saveButton)
								.addComponent(cancelButton))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		if(fuzzySetLabel == null)
			fuzzySetType.setSelectedIndex(0);
		else{
			if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof TriangleFuzzyMembership){
				fuzzySetType.setSelectedIndex(1);
			}else if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof TrapFuzzyMembership){
				fuzzySetType.setSelectedIndex(2);
			}else if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof SingletonFuzzyMembership){
				fuzzySetType.setSelectedIndex(3);
			}else if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof GaussianFuzzyMembership){
				fuzzySetType.setSelectedIndex(4);
			}
			refreshFuzzySetDef(fuzzySetType.getSelectedIndex(),GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel));
		}
		pack();
	}


	private class TriangleFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;
		private javax.swing.JLabel jLabel3;
		private javax.swing.JLabel jLabel5;

		private javax.swing.JTextField nameField;
		private javax.swing.JTextField lowBoundField;
		private javax.swing.JTextField topField;
		private javax.swing.JTextField upBoundField;

		public TriangleFormPanel(TriangleFuzzyMembership mem, String label) {
			nameField = new javax.swing.JTextField();
			lowBoundField = new javax.swing.JTextField();
			topField = new javax.swing.JTextField();
			upBoundField = new javax.swing.JTextField();
			if(mem != null){
				nameField.setText(label);
				lowBoundField.setText(mem.getLowerBound()+"");
				upBoundField.setText(mem.getUpperBound()+"");
				topField.setText(mem.getTopTriangle()+"");
			}
			initComponents();
		}

		public FuzzyMembership save(){
			TriangleFuzzyMembership	mem = new TriangleFuzzyMembership(0, 0, 0);
			name = nameField.getText();
			mem.setLowerBound(Double.parseDouble(lowBoundField.getText()));
			mem.setUpperBound(Double.parseDouble(upBoundField.getText()));
			mem.setTopTriangle(Double.parseDouble(topField.getText()));
			return mem;
		}

		private void initComponents(){
			jLabel1 = new javax.swing.JLabel();
			jLabel2 = new javax.swing.JLabel();
			jLabel3 = new javax.swing.JLabel();
			jLabel5 = new javax.swing.JLabel();
			

			jLabel1.setText("Nombre del conjunto difuso:");

			jLabel2.setText("Límite inferior del triángulo:");

			jLabel3.setText("Pico del triángulo:");

			jLabel5.setText("Límite superior del triángulo:");


			javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
			this.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
									.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(upBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(topField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(lowBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addContainerGap(12, Short.MAX_VALUE))
					);
			jPanel1Layout.setVerticalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(jLabel1)
									.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
											.addComponent(jLabel2)
											.addComponent(lowBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
											.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel3)
													.addComponent(topField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
													.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
															.addComponent(jLabel5)
															.addComponent(upBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
															.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}
	}

	private class TrapFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;
		private javax.swing.JLabel jLabel3;
		private javax.swing.JLabel jLabel4;
		private javax.swing.JLabel jLabel5;

		private javax.swing.JTextField nameField;
		private javax.swing.JTextField lowBoundField;
		private javax.swing.JTextField top1Field;
		private javax.swing.JTextField top2Field;
		private javax.swing.JTextField upBoundField;

		public TrapFormPanel(TrapFuzzyMembership mem, String fuzzySetLabel) {
			nameField = new javax.swing.JTextField();
			lowBoundField = new javax.swing.JTextField();
			top1Field = new javax.swing.JTextField();
			top2Field = new javax.swing.JTextField();
			upBoundField = new javax.swing.JTextField();
			if(mem != null){
				nameField.setText(fuzzySetLabel);
				lowBoundField.setText(mem.getLowerBound()+"");
				upBoundField.setText(mem.getUpperBound()+"");
				top1Field.setText(mem.getTopTrap1()+"");
				top2Field.setText(mem.getTopTrap2()+"");
			}
			initComponents();
		}

		private void initComponents(){
			jLabel1 = new javax.swing.JLabel();
			jLabel2 = new javax.swing.JLabel();
			jLabel3 = new javax.swing.JLabel();
			jLabel4 = new javax.swing.JLabel();
			jLabel5 = new javax.swing.JLabel();
			

			jLabel1.setText("Nombre del conjunto difuso:");

			jLabel2.setText("Límite inferior del trapecio:");

			jLabel3.setText("Punto inicial de valor máx.:");

			jLabel4.setText("Punto final de valor máx.:");

			jLabel5.setText("Límite superior del trapecio:");


			javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
			this.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
									.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(upBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(top2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(top1Field, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(lowBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addContainerGap(12, Short.MAX_VALUE))
					);
			jPanel1Layout.setVerticalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(jLabel1)
									.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
											.addComponent(jLabel2)
											.addComponent(lowBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
											.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel3)
													.addComponent(top1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
													.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
															.addComponent(jLabel4)
															.addComponent(top2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
															.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
															.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																	.addComponent(jLabel5)
																	.addComponent(upBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																	.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}

		public FuzzyMembership save(){
			TrapFuzzyMembership mem = new TrapFuzzyMembership(0, 0, 0, 0);
			name = nameField.getText();
			mem.setLowerBound(Double.parseDouble(lowBoundField.getText()));
			mem.setUpperBound(Double.parseDouble(upBoundField.getText()));
			mem.setTopTrap1(Double.parseDouble(top1Field.getText()));
			mem.setTopTrap2(Double.parseDouble(top2Field.getText()));
			return mem;
		}
	}

	private class SingletonFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;

		private javax.swing.JTextField nameField;
		private javax.swing.JTextField xField;

		public SingletonFormPanel(SingletonFuzzyMembership mem, String fuzzySetLabel) {
			nameField = new javax.swing.JTextField();
			xField = new javax.swing.JTextField();
			if(mem != null){
				nameField.setText(fuzzySetLabel);
				xField.setText(mem.getX()+"");
			}
			initComponents();
		}

		public FuzzyMembership save(){
			name = nameField.getText();
			SingletonFuzzyMembership mem = new SingletonFuzzyMembership(0);
			mem.setX(Double.parseDouble(xField.getText()));
			return mem;
		}

		private void initComponents(){
			jLabel1 = new javax.swing.JLabel();
			
			jLabel2 = new javax.swing.JLabel();

			jLabel1.setText("Nombre del conjunto difuso:");

			jLabel2.setText("Punto delta singleton:");


			javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
			this.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
									.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addContainerGap(12, Short.MAX_VALUE))
					);
			jPanel1Layout.setVerticalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(jLabel1)
									.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
											.addComponent(jLabel2)
											.addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
											.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}
	}
	
	private class GaussianFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;
		private javax.swing.JLabel jLabel3;
		private javax.swing.JTextField nameField;
		private javax.swing.JTextField muField;
		private javax.swing.JTextField sigmaField;

		public GaussianFormPanel(GaussianFuzzyMembership mem, String fuzzySetLabel) {
			nameField = new javax.swing.JTextField();
			muField = new javax.swing.JTextField();
			sigmaField = new javax.swing.JTextField();
			if(mem != null){
				nameField.setText(fuzzySetLabel);
				muField.setText(mem.getMu()+"");
				sigmaField.setText(mem.getSigma()+"");
			}
			initComponents();
		}

		public FuzzyMembership save(){
			name = nameField.getText();
			GaussianFuzzyMembership mem = new GaussianFuzzyMembership(Double.parseDouble(sigmaField.getText()), Double.parseDouble(muField.getText()));
			return mem;
		}

		private void initComponents(){
			jLabel1 = new javax.swing.JLabel();
			
			jLabel2 = new javax.swing.JLabel();
			
			jLabel3 = new javax.swing.JLabel();

			jLabel1.setText("Nombre del conjunto difuso:");

			jLabel2.setText("Valor medio:");
			
			jLabel3.setText("Desviación estándar:");


			javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
			this.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
									.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
									.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(sigmaField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(muField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
											.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
											.addContainerGap(12, Short.MAX_VALUE))
					);
			jPanel1Layout.setVerticalGroup(
					jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(jPanel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
									.addComponent(jLabel1)
									.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel2)
										.addComponent(muField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3)
												.addComponent(sigmaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}
	}

	private abstract class FuzzySetFormPanel extends JPanel{
		/**
		 * 
		 */
		public String name;

		private static final long serialVersionUID = 1L;

		public abstract FuzzyMembership save();
	}
}
