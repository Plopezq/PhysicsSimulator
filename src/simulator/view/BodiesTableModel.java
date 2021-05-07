package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	
	private String[] columnNames = {"Id",
			"Mass",
			"Position",
			"Velocity",
			"Force"};
	private List<Body> _bodies;
	
	
	BodiesTableModel(Controller ctrl) {
		this._bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override 
	public int getColumnCount() { 
		return columnNames.length;
	}
	
	@Override 
	public int getRowCount() {
		return _bodies.size();
	}

	@Override 
	public String getColumnName(int column) { 
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b =_bodies.get(rowIndex);
		if (columnIndex == 0)
			return b.getId();
		if (columnIndex == 1)
			return b.getMass();
		if (columnIndex == 2)
			return b.getPosition();
		if (columnIndex == 3)
			return b.getVelocity();
		if (columnIndex == 4)
			return b.getForce();
		return null;
	}

	
	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		this._bodies = bodies;
		fireTableStructureChanged();

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		this._bodies = bodies;
		fireTableStructureChanged();
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}


}
