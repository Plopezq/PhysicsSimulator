package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {

	private double eps;
	
	//Constructora
	public EpsilonEqualStates(double eps){
		this.eps = eps;
	}
	
	public EpsilonEqualStates(){
		this.eps = 0.0;
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
		
			//Compruebo que sus MASAS son iguales modulo epsilon
			double m1 = (double) ja1.getJSONObject(i).get("m");
			double m2 = (double) ja2.getJSONObject(i).get("m");
			if( Math.abs(m1-m2) > this.eps) return false;
			
			//Compruebo que sus vectores POSICION son iguales modulo epsilon
			Vector2D v1 = new Vector2D(ja1.getJSONObject(i).getJSONArray("p").getDouble(0), ja1.getJSONObject(i).getJSONArray("p").getDouble(1) );
			Vector2D v2 = new Vector2D( ja2.getJSONObject(i).getJSONArray("p").getDouble(0), ja2.getJSONObject(i).getJSONArray("p").getDouble(1) );
			if( v1.distanceTo(v2) > this.eps) return false;
			
			//Compruebo que sus vectores VELOCIDAD son iguales modulo epsilon
			Vector2D ve1 = new Vector2D( ja1.getJSONObject(i).getJSONArray("v").getDouble(0), ja1.getJSONObject(i).getJSONArray("v").getDouble(1) );
			Vector2D ve2 = new Vector2D( ja2.getJSONObject(i).getJSONArray("v").getDouble(0), ja2.getJSONObject(i).getJSONArray("v").getDouble(1) );
			if( ve1.distanceTo(ve2) > this.eps) return false;

			//Compruebo que sus vectores FUERZA son iguales modulo epsilon
			Vector2D f1 = new Vector2D( ja1.getJSONObject(i).getJSONArray("f").getDouble(0), ja1.getJSONObject(i).getJSONArray("f").getDouble(1) );
			Vector2D f2 = new Vector2D( ja2.getJSONObject(i).getJSONArray("f").getDouble(0), ja2.getJSONObject(i).getJSONArray("f").getDouble(1) );
			if( f1.distanceTo(f2) > this.eps) return false;
		}
		
		return true;
	}

}