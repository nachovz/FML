package ve.com.fml.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		
		setResizable(true);
		setTitle("Editar datos");
		setBounds(100, 100, 800, 580);
		setLayout(null);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel buttons = new JPanel();
		buttons.setBounds(5, 5, 144, 430);
		
		normalizePanel = new JPanel();
		normalizePanel.setBounds(3, 20, 141, 30);
		
		HashMap<Integer, String> hashito = GlobalData.getInstance().getFuzzyInstances().getNormalizableAttributes();
		JComboBox<String> attributesList = new JComboBox<String>(new Vector<String>(hashito.values()));
		
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
		btnNormalize.setBounds(3, 20, 141, 30);
		btnNormalize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
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
		btnNormalize.setBounds(3, 50, 141, 30);
		btnNormalize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
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
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
	        public void componentResized(ComponentEvent e) {
	                    setSize();
	            }
	    });
		//table.setBounds(100, 100, getWidth(), getHeight());
		scrollPane = new JScrollPane(table);
		//scrollPane.setBounds(100, 100, getWidth(), getHeight());
		//table.setFillsViewportHeight(true);
		//normalizePanel.add(attributesList);
		normalizePanel.add(btnNormalize);
		buttons.add(normalizePanel);
		buttons.add(btnReplace);
		
		contentPane.add(buttons);
		contentPane.add(scrollPane);
	}
	
	public void setSize(){
//	    this.table.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
//	    this.scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
//	    this.scrollPane.revalidate();
//	    this.table.revalidate();
	}

}
