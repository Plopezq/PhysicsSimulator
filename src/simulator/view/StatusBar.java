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
	private double time;
	private JLabel _currLaws; // for gravity laws
	private String laws = "prueba";
	private JLabel _numOfBodies; // for number of bodies
	private int bodies;
	//En los métodos que implementan el observador, es necesario modiﬁcar 
	//la correspondiente JLabel si la información cambia.
	
	StatusBar(Controller ctrl) { 
		initGUI(); 
		//ctrl.addObserver(this); //DA PROBLEMAS DE MOMENTO
	}

	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT, 30, 5 ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		//ETIQUETA DE TIEMPO
		_currTime = new JLabel("Time: " + this.time);
		//_currTime.setBounds(x,y,w,h);
		this.add(_currTime);
		//ETIQUETA DE CUERPOS
		_numOfBodies = new JLabel("Bodies: " + this.bodies);
		//_numOfBodies.setBounds(x,y,w,h);
		this.add(_numOfBodies);
		
		//ETIQUETA DE LEY DE FUERZA
		_currLaws = new JLabel("Laws: " + this.laws);
		//_currLaws.setBounds(x,y,w,h);
		this.add(_currLaws);
	}
	
	// other private/protected methods
	//...
	
	
	
	
	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
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
