package simulator.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{
	// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	//En los métodos que implementan el observador, es necesario modiﬁcar 
	//la correspondiente JLabel si la información cambia.
	
	StatusBar(Controller ctrl) { 
		initGUI(); 
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT, 30, 5 ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		//ETIQUETA DE TIEMPO
		_currTime = new JLabel("Time: 0");
		
		//_currTime.setBounds(x,y,w,h);
		this.add(_currTime);
		//ETIQUETA DE CUERPOS
		_numOfBodies = new JLabel("Bodies: 0");
		//_numOfBodies.setBounds(x,y,w,h);
		this.add(_numOfBodies);
		
		//ETIQUETA DE LEY DE FUERZA
		_currLaws = new JLabel("Laws: ");
		//_currLaws.setBounds(x,y,w,h);
		this.add(_currLaws);
	}
	
	// other private/protected methods

	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		String cadena = String.valueOf(bodies.size());
		_numOfBodies.setText("Bodies: " + cadena);
		_currLaws.setText("Laws: " + fLawsDesc);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		String cadena = String.valueOf(time);
		_currTime.setText("Time: " + cadena);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		String cadena = String.valueOf(bodies.size());
		_numOfBodies.setText("Bodies: " + cadena);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		String cadena = String.valueOf(time);
		_currTime.setText("Time: " + cadena);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText("Laws: " + fLawsDesc);
		System.out.println("Ley cambia a:" + fLawsDesc);
	}

	
}
