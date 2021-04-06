package simulator.model;


import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	
	//Atributos
	protected String id;	// Identificador
	protected Vector2D v; // Vector velocidad
	protected Vector2D f; // Vector de fuerza
	protected Vector2D p; // Vector de posicion
	protected double m; // Masa del cuerpo
	
	//Constructora
	public Body(String id,Vector2D v, Vector2D p, Double m ){
		this.id = id;
		this.v = v;
		this.p = p;
		this.m = m;
		this.f = new Vector2D(); // [0,0]
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
	//PACKAGE PROTECTED
	void addForce(Vector2D f) {
	//añade la fuerza f al vector de fuerza del cuerpo 
		//(usando el método plus de la clase Vector2D).
		this.f = this.f.plus(f);
	}
	//PACKAGE PROTECTED
	void resetForce() { //pone el valor del vector de fuerza a (0, 0).
		this.f = new Vector2D(); //		_x = _y = 0.0;
	}
	void move(double t) { // mueve el cuerpo durante t segundos utilizando los atributos del mismo.
		//VER pagina 4 abajo
			//	1.Calculamos la aceleracion usando las leyes de newton--> aceleracion = fuerza/masa
			Vector2D a = this.f.scale(1/this.m);
				if(this.m == 0) a = new Vector2D(); //pongo la aceleracion a 0 si la masa es 0
			// 2.Cambiamos la posicion a p+v*t + 1/2 * a *t2 
			this.p = this.p.plus(this.v.scale(t)).plus(a.scale(1/2*t*t));
			//y la velocidad a v + a*t
			this.v = this.v.plus(a.scale(t));
	}
	public JSONObject getState() {
		//{ “id": id, "m": m, "p": p~, "v": ~v , "f": f }
		JSONObject stateJSON = new JSONObject();
		stateJSON.put("id", this.getId());
		stateJSON.put("m", this.getMass());
		stateJSON.put("p", this.getPosition().toString());
		stateJSON.put("v", this.getVelocity().toString());
		stateJSON.put("f", this.getForce().toString());

		
	/*Maybe we have to do like this : 
	 * TODO
	 * // we put some keys with simple values into 'jo1'
		jo1.put("a", 1234);
		jo1.put("b", 123.3e-10);
		jo1.put("c", "Hollaaa");
	 */
		return stateJSON;	
	}
	
	public String toString() { //devuelve getState().toString().
		return this.getState().toString();
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
