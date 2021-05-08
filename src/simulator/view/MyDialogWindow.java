package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.ForceLaws;



public class MyDialogWindow extends JDialog {

	private JLabel help ;
	private JsonTableModel _dataTableModel;
	private JComboBox<String> _laws;
	private DefaultComboBoxModel<String> _lawsModel;
	private int _status;
	private List<JSONObject> infoLeyes;
	//private Controller _ctrl;
	
	public MyDialogWindow(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	private void initGUI() {
		_status = 0;

		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		//ANYADIMOS COMPONENTES
			//PONER NEGRITA
		this.help = new JLabel("<html><p>Select a force law and provide values for the parametes in the Value Column (default values are used for parametes with no value). </p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		//Tabla de datos JSON
		_dataTableModel = new JsonTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JPanel selectorFuerzas = new JPanel();
		selectorFuerzas.setLayout(new FlowLayout());
		//Anyado la label
		JLabel helpMsg = new JLabel("ForceLaw: ");
		//helpMsg.setAlignmentX(LEFT_ALIGNMENT);
		selectorFuerzas.add(helpMsg);
		//anyado el selector
		_lawsModel = new DefaultComboBoxModel<>();
		_laws = new JComboBox<>(_lawsModel);
		_laws.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				_dataTableModel.clear();//Limpio
				//Anyado la ley que hayan seleccionado
				String leySeleccionada = getForceLaw();
				if(leySeleccionada.equals("Newton’s law of universal gravitation")) {
					//Anyado la ley de newton a la jsontablemodel
					_dataTableModel._data[0][0] = "G";
					_dataTableModel._data[0][2] = infoLeyes.get(2).getJSONObject("data").getString("G");					
					
				}else if(leySeleccionada.equals("No force")){
					//Anyado la ley de no force a la jsontablemodel
					//No hace falta anyadir nada
					
				}else {
					//Anyado la ley de moving towards a la jsontablemodel
					_dataTableModel._data[0][0] = "c";
					_dataTableModel._data[0][2] = infoLeyes.get(1).getJSONObject("data").getString("c");					
					_dataTableModel._data[1][0] = "g";
					_dataTableModel._data[1][2] = infoLeyes.get(1).getJSONObject("data").getString("g");
					
				}
				_dataTableModel.fireTableStructureChanged();
			}
		});
		selectorFuerzas.add(_laws);	
		
		mainPanel.add(selectorFuerzas);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		
		JPanel opcionesPanel = new JPanel();
		opcionesPanel.setLayout(new FlowLayout());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				MyDialogWindow.this.setVisible(false);
			}
		});
		opcionesPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Siempre va a ser distinto de null, esta la de newton por defecto
				//if (_lawsModel.getSelectedItem() != null) {
					_status = 1;
					MyDialogWindow.this.setVisible(false);
				//}
			}
		});
		opcionesPanel.add(okButton);
		
		mainPanel.add(opcionesPanel);
		
		
		this.setPreferredSize(new Dimension(500, 300));
	    this.setLocationRelativeTo(null);		
		this.pack();
		this.setResizable(false);
		this.setVisible(false);
	}
	
	
	public int getStatus() {
		return this._status;
	}
	public JSONObject getTableData() {
		JSONObject contenidoTabla = new JSONObject(_dataTableModel.getData());
		JSONObject aux = new JSONObject();
		//Diferenciar casos
		if (getForceLaw().equals("No force")) { //NO FORCE
			aux.put("type", "ng");
			aux.put("data", contenidoTabla);
		}
		if (getForceLaw().equals("Moving towards a fixed point")) { //mvt
			aux.put("type", "mtfp");
			aux.put("data", contenidoTabla);
		}
		if (getForceLaw().equals("Newton’s law of universal gravitation")) { //mvt
			aux.put("type", "nlug");
			aux.put("data", contenidoTabla);
		}
		
		return aux;
	}
	private class JsonTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] _header = { "Key", "Value", "Description" };
		String[][] _data;

		JsonTableModel() {
			_data = new String[5][3];
			clear();
		}

		public void clear() {
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 3; j++)
					_data[i][j] = "";
			fireTableStructureChanged();
		}

		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			// si es no force devuelve {}
			//Si no rellena nada, tb se devuelve {}
			//y se usa el creador por defecto
			StringBuilder s = new StringBuilder();
			s.append('{');
			for (int i = 0; i < _data.length; i++) {
				if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}

			s.append('}');

			return s.toString();
		}
	}

	public int open(List<JSONObject> forceLawsInfo) {
		//Update the comboBox model
		this.infoLeyes = forceLawsInfo; //Almaceno el JSON completo de leyes
		_lawsModel.removeAllElements();
		for(JSONObject fl: forceLawsInfo) {
			_lawsModel.addElement((String)fl.get("desc"));
		}
		
		setLocation(//
				getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
				getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		setVisible(true);
		return _status;
	}

	public String getForceLaw() {
		return (String) _lawsModel.getSelectedItem();
	}
}
