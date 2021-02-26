package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
	protected double lossFactor;
	protected double lossFrequency;
	protected double c; //contador para acumular el tiempo [parametro t de move()]
	
	
	MassLossingBody(String id, Vector2D v, Vector2D p, Double m, double lossFactor, double lossFrequency) {
		super(id, v, p, m);
		c=0.0; //inicializamos el contador a 0,0
	}
	
	//para la perdida de masa podemos hacerlo con un metodo aparte 
	//(que se llame dentro del move() de esta clase, o implementarlo directamente en su move()
	
	@Override
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.
		super.move(t);
		looseMass(t);
	}
	
	private void looseMass(double t) {
		c+=t; //anadimos al contador el tiempo que ha pasado al moverse esta vez el cuerpo
		
		if (c>=lossFrequency) { // si el contador es mayor que la frecuencia, le hacemos perder la masa y reseteamos c
		m = m *(1-lossFactor); // m *= (1-lossFactor)
		c = 0.0;
		}
		
	}

}
