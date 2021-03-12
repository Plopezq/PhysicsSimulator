package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {

	private double eps;
	
	//Constructora
	EpsilonEqualStates(double eps){
		this.eps = eps;
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		
		//Comparo que sus claves "time" son iguales
		double s1time = s1.getDouble("time");
		double s2time = s1.getDouble("time");
		if(Math.abs(s1time-s2time) > this.eps) { //Sus tiempos no son iguales modulo epsilon
			return false;
		}
		
		JSONArray ja1 = s1.getJSONArray("bodies");
		JSONArray ja2 = s2.getJSONArray("bodies");
		if(ja1.length() != ja2.length()) return false;
		for(int i =0; i < ja1.length(); i++) { //Para cada cuerpo, comparo sus vectores
			//Compruebo que los objetos i-esimos tienen el mismo id
			String id1 = (String) ja1.getJSONObject(i).get("id");
			String id2 = (String) ja2.getJSONObject(i).get("id");
			if( !id1.equals(id2)) return false;
		
			//Compruebo que sus masas son iguales modulo epsilon
			double m1 = (double) ja1.getJSONObject(i).get("m");
			double m2 = (double) ja2.getJSONObject(i).get("m");
			if( Math.abs(m1-m2) > this.eps) return false;
			
			//Compruebo que sus vectores posicion son iguales modulo epsilon
			Vector2D v1 = new Vector2D( (Vector2D) ja1.getJSONObject(i).get("p") );
			Vector2D v2 = new Vector2D( (Vector2D) ja2.getJSONObject(i).get("p") );
			if( v1.distanceTo(v2) > this.eps) return false;
			
			//Compruebo que sus vectores velocidad son iguales modulo epsilon
			Vector2D ve1 = new Vector2D( (Vector2D) ja1.getJSONObject(i).get("v") );
			Vector2D ve2 = new Vector2D( (Vector2D) ja2.getJSONObject(i).get("v") );
			if( ve1.distanceTo(ve2) > this.eps) return false;

			//Compruebo que sus vectores fuerza son iguales modulo epsilon
			Vector2D f1 = new Vector2D( (Vector2D) ja1.getJSONObject(i).get("f") );
			Vector2D f2 = new Vector2D( (Vector2D) ja2.getJSONObject(i).get("f") );
			if( f1.distanceTo(f2) > this.eps) return false;


		}
		
		return true;
	}

}