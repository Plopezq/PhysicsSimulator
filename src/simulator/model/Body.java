package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	
	//Atributos
	protected String id;
	protected Vector2D v; //Vector velocidad
	protected Vector2D f; //Vector de fuerza
	protected Vector2D p; //Vector de posicion
	protected Double m; //Masa del cuerpo
	
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
		
		
	}
	public Vector2D getVelocity() { //devuelve el vector de velocidad.
	
		
	}
	public Vector2D getForce() {// devuelve el vector de fuerza.
	
		
		
	}
	public Vector2D getPosition() {// devuelve el vector de posición.
	
	
		
	}
	public double getMass() { //devuelve la masa del cuerpo.
	
		
		
	}
	void addForce(Vector2D f) {
	//añade la fuerza f al vector de fuerza del cuerpo 
		//(usando el método plus de la clase Vector2D).
	
	
	}
	void resetForce() { //pone el valor del vector de fuerza a (0, 0).
	
	
	}
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.
		//VER pagina 4 abajo
		
		
		
	
	}
	public JSONObject getState() {
		//{ “id": id, "m": m, "p": p~, "v": ~v , "f": f }
		
		
	}
	public String toString() { //devuelve getState().toString().
	
	
	}
	
	
	
	
	
	
	
	
}
