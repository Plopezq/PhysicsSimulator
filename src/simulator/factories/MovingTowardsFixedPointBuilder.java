package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	private MovingTowardsFixedPoint fuerza;
	
	public MovingTowardsFixedPointBuilder() {
		
	}

	@Override
	protected Object createTheInstance(JSONObject info) {
		//Las claves c y g son opcionales
		if(info.isEmpty()) {
			this.fuerza = new MovingTowardsFixedPoint();
		}else {
			Vector2D c = new Vector2D(info.getJSONArray("c").getDouble(0), info.getJSONArray("c").getDouble(1));
			this.fuerza = new MovingTowardsFixedPoint(c, info.getDouble("g"));
		}
		
		return this.fuerza;
	}
	

	protected JSONObject createData(){
		JSONObject force = new JSONObject();
		force.put("type", "mtfp");
			JSONObject data = new JSONObject();
			data.put("c","the point towards which bodies move \"(a json list of 2 numbers, e.g., [100.0,50.0]"); //new Vector2D(0,0).toString());
			data.put("g","the length of the acceleration vector (a number)"); // 9.81
		force.put("data", data);
		force.put("desc", "Moving towards a fixed point");
		return force;
	}
	
	
}
