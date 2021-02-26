package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	
	//Atributos
	protected String id;
	protected Vector2D v; //Vector velocidad
	protected Vector2D f; //Vector de fuerza
	protected Vector2D p; //Vector de posicion
	protected double m; //Masa del cuerpo
	
	//Constructora
	Body(String id,Vector2D v, Vector2D p, Double m ){
		this.id = id;
		this.v = v;
		this.p = p;
		this.m = m;
		this.f = new Vector2D();
	}
	
	//Metodos
	public String getId() { //devuelve el identiﬁcador del cuerpo.
		return this.id;
	}
	public Vector2D getVelocity() { //devuelve el vector de velocidad.
		return this.v;
	}
	public Vector2D getForce() {// devuelve el vector de fuerza.
		return this.f;
	}
	public Vector2D getPosition() {// devuelve el vector de posición.
		return this.p;
	}
	public double getMass() { //devuelve la masa del cuerpo.
		return this.m;
	}
	void addForce(Vector2D f) {
	//añade la fuerza f al vector de fuerza del cuerpo 
		//(usando el método plus de la clase Vector2D).
		this.f.plus(f);
	}
	void resetForce() { //pone el valor del vector de fuerza a (0, 0).
		this.f = new Vector2D(); //		_x = _y = 0.0;
	}
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.

	}
	public JSONObject getState() {
		//{ “id": id, "m": m, "p": p~, "v": ~v , "f": f }
		
		
	}
	public String toString() { //devuelve getState().toString().
		return this.getState().toString();
	}
	
	
	
}
