package ve.com.fml.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import ve.com.fml.model.datasource.GlobalData;
import ve.com.fml.model.fuzzy.FuzzyInstances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class EditDataWindow extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel normalizePanel;
	/**
	 * Edit data pane
	 */
	private static final long serialVersionUID = 1L;
	
	public EditDataWindow() {
		
		GlobalData.getInstance().storeInstancesBackup();
		setResizable(true);
		setTitle("Configuración de datos");
		setLayout(null);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new CardLayout());
		setContentPane(contentPane);
		
		
		
		normalizePanel = new JPanel();
		normalizePanel.setBounds(3, 20, 141, 30);
		
		
		JButton btnNormalize = new JButton("Normalizar");
		btnNormalize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Normalize normalize = new Normalize();
				try {
					normalize.setInputFormat(GlobalData.getInstance().getFuzzyInstances());
					GlobalData.getInstance().setFuzzyInstances(new FuzzyInstances(Filter.useFilter(GlobalData.getInstance().getFuzzyInstances(), normalize)));
					contentPane.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnReplace = new JButton("Reemplazar valores ausentes");
		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReplaceMissingValues replace = new ReplaceMissingValues();
				try {
					replace.setInputFormat(GlobalData.getInstance().getFuzzyInstances());
					GlobalData.getInstance().setFuzzyInstances(new FuzzyInstances(Filter.useFilter(GlobalData.getInstance().getFuzzyInstances(), replace)));
					contentPane.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnReset = new JButton("Deshacer cambios");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GlobalData.getInstance().restoreInstancesBackup();
					contentPane.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		table = new JTable(new AbstractTableModel() {
			/**
			 * Table to show from FuzzyInstance (Singleton)
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				if (GlobalData.getInstance().getFuzzyInstances().classIndex() == columnIndex){
					return ""+GlobalData.getInstance().getFuzzyInstances().classAttribute().value((int) GlobalData.getInstance().getFuzzyInstances().instance(rowIndex).classValue());
				}
				return ""+GlobalData.getInstance().getFuzzyInstances().instance(rowIndex).value(columnIndex);
			}
			
			@Override
			public int getRowCount() {
				return GlobalData.getInstance().getFuzzyInstances().numInstances();
			}
			
			@Override
			public int getColumnCount() {
				return GlobalData.getInstance().getFuzzyInstances().numAttributes();
			}
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				// TODO Revisar el tipo del atributo a editar
				GlobalData.getInstance().getFuzzyInstances().instance(rowIndex).setValue(columnIndex, Double.parseDouble(aValue.toString()));
			}
			
			@Override
			public String getColumnName(int column) {
				return GlobalData.getInstance().getFuzzyInstances().attribute(column).name();
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return true;
			}
			
		});
		//table.setBounds(100, 100, getWidth(), getHeight());
		scrollPane = new JScrollPane(table);
		//scrollPane.setBounds(100, 100, getWidth(), getHeight());
		//table.setFillsViewportHeight(true);
		//normalizePanel.add(attributesList);
		
		normalizePanel.add(btnNormalize);
		normalizePanel.add(btnReplace);
		normalizePanel.add(btnReset);
		contentPane.add(normalizePanel);
		contentPane.add(scrollPane);
		setBounds(100, 100, table.getColumnCount()*100 + 10, table.getRowHeight()*25 + 125);
	}
	

}
