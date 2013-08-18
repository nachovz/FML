package ve.com.fml.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.fuzzy.FuzzyVariable;
import ve.com.fml.model.fuzzy.membership.FuzzyMembership;
import ve.com.fml.model.fuzzy.membership.SingletonFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TrapFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TriangleFuzzyMembership;

public class AddFuzzySetWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FuzzySetFormPanel options;
	private JButton saveButton = new JButton("Guardar");
	private JButton cancelButton = new JButton("Cancelar");
	private String fuzzySetLabel;

	public AddFuzzySetWindow(Integer attrIndex) {
		this(attrIndex, null);
	}

	public AddFuzzySetWindow(Integer attrIdx, String fuzzyLabel) {
		final Integer attrIndex = attrIdx;
		fuzzySetLabel = fuzzyLabel;
		
		setResizable(true);
		setTitle("Difusificación del Conjunto de Datos");
		setBounds(100, 100, 400, 400);
		setLocationRelativeTo(null);
		setLayout(null);
		//setLayout(new CardLayout());
		
		
		//Selección del tipo de función
		final JComboBox<String> fuzzySetType = new JComboBox<String>();
		fuzzySetType.setBounds(10, 10, 365, 30);
		fuzzySetType.addItem("Seleccione un tipo de función de pertenencia");
		fuzzySetType.addItem("Triangular");
		fuzzySetType.addItem("Trapezoidal");
		fuzzySetType.addItem("Singleton");

		if(fuzzySetLabel == null)
			fuzzySetType.setSelectedIndex(0);
		else{
			if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof TriangleFuzzyMembership){
				fuzzySetType.setSelectedIndex(1);
			}else if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof TrapFuzzyMembership){
				fuzzySetType.setSelectedIndex(2);
			}else if(GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel) instanceof SingletonFuzzyMembership){
				fuzzySetType.setSelectedIndex(3);
			}
			refreshFuzzySetDef(fuzzySetType.getSelectedIndex(),GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).getFuzzySets().get(fuzzySetLabel));
		}
		
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
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Validar y paja
				if(fuzzySetLabel != null){
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).removeFuzzySet(fuzzySetLabel);
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).addFuzzySet(options.name.getText(),options.save());
				}else{
					FuzzyMembership fM = options.save();
					if(!GlobalData.getInstance().getFuzzyInstances().getMembership().containsKey(attrIndex))
						GlobalData.getInstance().getFuzzyInstances().getMembership().put(attrIndex, new FuzzyVariable());
					GlobalData.getInstance().getFuzzyInstances().getMembership().get(attrIndex).addFuzzySet(options.name.getText(), fM);
				}
				dispose();
			}
		});
		saveButton.setBounds(170, 320, 100, 30);
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(275, 320, 100, 30);
		
		add(saveButton);
		add(cancelButton);
		add(fuzzySetType);
//		JPanel jape = new JPanel();
//		jape.setBounds(1,1,100,100);
//		jape.setBorder(BorderFactory.createLineBorder(Color.black));
//		add(jape);

	}

	private void refreshFuzzySetDef(Integer index, FuzzyMembership mem){
		if(options != null)
			remove(options);
		switch(index){
		case 1:
			options = new TriangleFormPanel((TriangleFuzzyMembership)mem);
			break;
		case 2:
			options = new TrapFormPanel((TrapFuzzyMembership)mem);
			break;
		case 3:
			options = new SingletonFormPanel((SingletonFuzzyMembership)mem);
			break;
		}		
		add(options);
		repaint();
	}

	private class TriangleFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField lowerBound = new JTextField();
		private JLabel lowerBoundLabel = new JLabel("Límite inferior:");
		private JTextField upperBound = new JTextField();
		private JLabel upperBoundLabel = new JLabel("Límite superior:");
		private JTextField top = new JTextField();
		private JLabel topLabel = new JLabel("Pico triangular:");
		public TriangleFormPanel(TriangleFuzzyMembership mem) {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBounds(10,50,365,260);
			if(mem != null){
				name.setText(fuzzySetLabel);
				lowerBound.setText(""+mem.getLowerBound());
				upperBound.setText(""+mem.getUpperBound());
				top.setText(""+mem.getTopTriangle());
			}
			nameLabel.setBounds(10, 10, 100, 30);
			name.setBounds(110, 10, 100, 30);
			lowerBoundLabel.setBounds(10, 50, 100, 30);
			lowerBound.setBounds(110, 50, 100, 30);
			upperBoundLabel.setBounds(10, 90, 100, 30);
			upperBound.setBounds(110, 90, 100, 30);
			topLabel.setBounds(10, 130, 100, 30);
			top.setBounds(110, 130, 100, 30);
			add(nameLabel);
			add(name);
			add(lowerBoundLabel);
			add(lowerBound);
			add(upperBoundLabel);
			add(upperBound);
			add(topLabel);
			add(top);
		}

		public FuzzyMembership save(){
			TriangleFuzzyMembership	mem = new TriangleFuzzyMembership(0, 0, 0);
			mem.setLowerBound(Double.parseDouble(lowerBound.getText()));
			mem.setUpperBound(Double.parseDouble(upperBound.getText()));
			mem.setTopTriangle(Double.parseDouble(top.getText()));
			return mem;
		}
	}

	private class TrapFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField lowerBound = new JTextField();
		private JLabel lowerBoundLabel = new JLabel("Límite inferior:");
		private JTextField upperBound = new JTextField();
		private JLabel upperBoundLabel = new JLabel("Límite superior:");
		private JTextField top1 = new JTextField();
		private JLabel top1Label = new JLabel("Inicio tope:");
		private JTextField top2 = new JTextField();
		private JLabel top2Label = new JLabel("Final tope:");
		public TrapFormPanel(TrapFuzzyMembership mem) {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBounds(10,50,365,260);
			if(mem != null){
				name.setText(fuzzySetLabel);
				lowerBound.setText(""+mem.getLowerBound());
				upperBound.setText(""+mem.getUpperBound());
				top1.setText(""+mem.getTopTrap1());
				top2.setText(""+mem.getTopTrap2());
			}
			nameLabel.setBounds(10, 10, 100, 30);
			name.setBounds(110, 10, 100, 30);
			lowerBoundLabel.setBounds(10, 50, 100, 30);
			lowerBound.setBounds(110, 50, 100, 30);
			upperBoundLabel.setBounds(10, 90, 100, 30);
			upperBound.setBounds(110, 90, 100, 30);
			top1Label.setBounds(10, 130, 100, 30);
			top1.setBounds(110, 130, 100, 30);
			top2Label.setBounds(10, 170, 100, 30);
			top2.setBounds(110, 170, 100, 30);
			add(nameLabel);
			add(name);
			add(lowerBoundLabel);
			add(upperBoundLabel);
			add(top1Label);
			add(top2Label);
			add(lowerBound);
			add(upperBound);
			add(top1);
			add(top2);
		}

		public FuzzyMembership save(){
			TrapFuzzyMembership mem = new TrapFuzzyMembership(0, 0, 0, 0);
			mem.setLowerBound(Double.parseDouble(lowerBound.getText()));
			mem.setUpperBound(Double.parseDouble(upperBound.getText()));
			mem.setTopTrap1(Double.parseDouble(top1.getText()));
			mem.setTopTrap2(Double.parseDouble(top2.getText()));
			return mem;
		}
	}

	private class SingletonFormPanel extends FuzzySetFormPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField x = new JTextField();
		private JLabel xLabel = new JLabel("Punto singleton:");
		public SingletonFormPanel(SingletonFuzzyMembership mem) {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBounds(10,50,365,260);
			if(mem != null){
				x.setText(""+mem.getX());
				name.setText(fuzzySetLabel);	
			}
			nameLabel.setBounds(10, 10, 100, 30);
			name.setBounds(110, 10, 100, 30);
			xLabel.setBounds(10, 50, 100, 30);
			x.setBounds(110, 50, 100, 30);
			add(nameLabel);
			add(name);
			add(x);
			add(xLabel);
		}

		public FuzzyMembership save(){
			SingletonFuzzyMembership mem = new SingletonFuzzyMembership(0);
			mem.setX(Double.parseDouble(x.getText()));
			return mem;
		}
	}
	
	private abstract class FuzzySetFormPanel extends JPanel{
		/**
		 * 
		 */
		public JTextField name = new JTextField();
		protected JLabel nameLabel = new JLabel("Nombre del conjunto:");
		private static final long serialVersionUID = 1L;

		public abstract FuzzyMembership save();
	}
}
