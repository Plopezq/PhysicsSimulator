package simulator.model;

import java.util.List;

public interface SimulatorObserver {
	
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc); 
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc); 
	public void onBodyAdded(List<Body> bodies, Body b); 
	public void onAdvance(List<Body> bodies, double time); 
	public void onDeltaTimeChanged(double dt); 
	public void onForceLawsChanged(String fLawsDesc);
	
}
//Bodies es la lista de cuerpos actual
//b es un cuerpo
//time es el tiempo actual del simulador
//dt es el tiempo por paso actual del simulador
//fLawsDesc es un string que describe las leyes de fuerza actuales