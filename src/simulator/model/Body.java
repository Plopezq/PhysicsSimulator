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
	public void setVelocity(Vector2D v) {
		this.v = v;
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
		this.f = this.f.plus(f);
	}
	void resetForce() { //pone el valor del vector de fuerza a (0, 0).
		this.f = new Vector2D(); //		_x = _y = 0.0;
	}
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.
		//VER pagina 4 abajo
			//	1.Calculamos la aceleracion usando las leyes de newton--> a = f/m
			Vector2D a = this.f.scale(1/t);
			// 2.Cambiamos la posicion a p+v*t + 1/2 * a *t2 
			this.p = this.p.plus(this.v.scale(t)).plus(a.scale(1/2*t*t));
			//y la velocidad a v + a*t
			this.v = this.v.plus(a.scale(t));
	}
	public JSONObject getState() {
		//{ “id": id, "m": m, "p": p~, "v": ~v , "f": f }
		JSONObject stateJSON = new JSONObject(this);//The key is formed by removing the "get" or "is"prefix. 
		return stateJSON;							//If the second remaining character is not upper case, then 
													//thefirst character is converted to lower case. 
	/*Maybe we have to do like this : 
	 * TODO
	 * // we put some keys with simple values into 'jo1'
		jo1.put("a", 1234);
		jo1.put("b", 123.3e-10);
		jo1.put("c", "Hollaaa");
	 */
	
	}
	
	public String toString() { //devuelve getState().toString().
		return this.getState().toString();
	}
	
	public static void main() {//TODO -> eliminar
		//Make some test in the future
	}
	
}
