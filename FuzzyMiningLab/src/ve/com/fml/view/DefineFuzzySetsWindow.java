package ve.com.fml.view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ve.com.fml.model.datasource.GlobalData;

public class DefineFuzzySetsWindow extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel normalizePanel;
	private JPanel buttons;
	/**
	 * Edit data pane
	 */
	private static final long serialVersionUID = 1L;

	public DefineFuzzySetsWindow() {

		setResizable(true);
		setTitle("Difusificación del Conjunto de Datos");
		setBounds(100, 100, 800, 580);
		setLayout(new CardLayout());
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		normalizePanel = new JPanel();
		buttons = new JPanel();


		/** Control para selección de atributo difusificable */
		final HashMap<String, Integer> numericAtts = GlobalData.getInstance().getFuzzyInstances().getNumericAttributes();
		//final JComboBox<String> attributeList = new JComboBox<String>(new Vector<String>(numericAtts.keySet()));
		final JComboBox<String> attributeList = new JComboBox<String>();
		attributeList.addItem("Seleccione un atributo numérico...");
		for (String numAttLabel : numericAtts.keySet()) {
			attributeList.addItem(numAttLabel);
		}

		/** Control para selección de conjunto difuso */
		final JComboBox<String> fuzzySetsList = new JComboBox<String>();
		fuzzySetsList.addItem("Seleccione un conjunto difuso...");
		Vector<String> currentFS = GlobalData.getInstance().getFuzzyInstances().getFuzzySets(numericAtts.get(attributeList.getSelectedItem()));
		for (String fuzzySetLabel : currentFS) {
			fuzzySetsList.addItem(fuzzySetLabel);
		}

		attributeList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fuzzySetsList.removeAllItems();
				fuzzySetsList.addItem("Seleccione un conjunto difuso...");
				if(attributeList.getSelectedIndex() != 0){
					Vector<String> currentFS = GlobalData.getInstance().getFuzzyInstances().getFuzzySets(numericAtts.get(attributeList.getSelectedItem()));
					for (String fuzzySetLabel : currentFS) {
						fuzzySetsList.addItem(fuzzySetLabel);
					}
				}
				contentPane.repaint();
			}
		});

		fuzzySetsList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO agregar el chart
				contentPane.repaint();
			}
		});

		normalizePanel.add(attributeList);
		normalizePanel.add(fuzzySetsList);

		/**Controles para agregar conjunto difuso*/
		JButton btnAddFS = new JButton("Agregar conjunto difuso");

		btnAddFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(attributeList.getSelectedIndex() != 0){
					AddFuzzySetWindow addFuzzySetWindow = new AddFuzzySetWindow(numericAtts.get(attributeList.getSelectedItem()));
					addFuzzySetWindow.setVisible(true);
					setEnabled(false);
					//repaint and enable on window close

					addFuzzySetWindow.addWindowListener(new WindowListener() {
						@Override
						public void windowClosed(WindowEvent e) {
							setEnabled(true);
							toFront();
							fuzzySetsList.removeAllItems();
							fuzzySetsList.addItem("Seleccione un conjunto difuso...");
							if(attributeList.getSelectedIndex() != 0){
								Vector<String> currentFS = GlobalData.getInstance().getFuzzyInstances().getFuzzySets(numericAtts.get(attributeList.getSelectedItem()));
								for (String fuzzySetLabel : currentFS) {
									fuzzySetsList.addItem(fuzzySetLabel);
								}
							}
							contentPane.repaint();
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowClosing(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
				}else{
					//alert, tiene que seleccionar un atribruto
				}
			}
		});

		JButton btnDelFS = new JButton("Eliminar conjunto difuso");

		btnDelFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO prompt para eliminar conjunto difuso sobre la variable seleccionada
				if(fuzzySetsList.getSelectedIndex() == 0){
					//error no se ha seleccionado conjunto difuso
				}else{
					//prompt de si está seguro
					GlobalData.getInstance().getFuzzyInstances().removeFuzzySet(numericAtts.get(attributeList.getSelectedItem()), (String) fuzzySetsList.getSelectedItem());
				}
			}
		});

		JButton btnEditFS = new JButton("Editar conjunto difuso");

		btnEditFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO prompt para eliminar conjunto difuso sobre la variable seleccionada
				if(fuzzySetsList.getSelectedIndex() == 0){
					//error no se ha seleccionado conjunto difuso
				}else{
					AddFuzzySetWindow addFuzzySetWindow = new AddFuzzySetWindow(numericAtts.get(attributeList.getSelectedItem()),(String)fuzzySetsList.getSelectedItem());
					addFuzzySetWindow.setVisible(true);
					//repaint on window close
					setEnabled(false);
					//repaint and enable on window close

					addFuzzySetWindow.addWindowListener(new WindowListener() {
						@Override
						public void windowClosed(WindowEvent e) {
							setEnabled(true);
							toFront();
							fuzzySetsList.removeAllItems();
							fuzzySetsList.addItem("Seleccione un conjunto difuso...");
							if(attributeList.getSelectedIndex() != 0){
								Vector<String> currentFS = GlobalData.getInstance().getFuzzyInstances().getFuzzySets(numericAtts.get(attributeList.getSelectedItem()));
								for (String fuzzySetLabel : currentFS) {
									fuzzySetsList.addItem(fuzzySetLabel);
								}
							}
							contentPane.repaint();
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowClosing(WindowEvent e) {
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
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
		
		
		XYDataset data = createDataset();
		
		buttons.add(btnAddFS);
		buttons.add(btnDelFS);
		buttons.add(btnEditFS);
		
		contentPane.add(normalizePanel);
		contentPane.add(buttons);
		contentPane.add(scrollPane);
		
	}
	
	private static XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        
       /* rqm.generateNumbers();
        XYSeries series = new XYSeries("Scatter");
                for(int i = 0; i < rqm.size(); i++){
                        series.add(rqm.yValueAt(i), rqm.xValueAt(i));
                }
                dataset.addSeries(series);*/
                return dataset;
    }


}
