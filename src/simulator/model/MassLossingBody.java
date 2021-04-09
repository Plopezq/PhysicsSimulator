package simulator.model;


import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
	private double lossFactor; //valor entre 0 y 1 que representa el factor de perdida de masa
	private double lossFrequency; //numero positivo, que indica el intervalo de tiempo (en segundos) despues del cual el objeto pierde masa
	private double c; //contador para acumular el tiempo [parametro t de move()]
	
	
	public MassLossingBody(String id, Vector2D v, Vector2D p, double m, double lossFactor, double lossFrequency) {
		super(id, v, p, m);
		c=0.0; //inicializamos el contador a 0,0
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
	}
	
	//para la perdida de masa podemos hacerlo con un metodo aparte 
	//(que se llame dentro del move() de esta clase, o implementarlo directamente en su move()
	
	//PACKAGE PROTECTED
	@Override
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.
		super.move(t);
		looseMass(t);
	}
	public double getLossFreq() {
		return this.lossFrequency;
	}
	private void looseMass(double t) {
		this.c+=t; //anadimos al contador el tiempo que ha pasado al moverse esta vez el cuerpo
		
		if (this.c>=this.lossFrequency) { // si el contador es mayor que la frecuencia, le hacemos perder la masa y reseteamos c
			this.m = this.m *(1-this.lossFactor); // m *= (1-lossFactor)
			this.c = 0.0;
		}
	}

}
