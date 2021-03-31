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
		if(info == null) {
			this.fuerza = new MovingTowardsFixedPoint();
		}else {
			this.fuerza = new MovingTowardsFixedPoint((Vector2D)info.get("c"), info.getDouble("g"));
		}
		
		return this.fuerza;
	}
	

	protected JSONObject createData(){
		JSONObject force = new JSONObject();
		force.put("type", "mtcp");
			JSONObject data = new JSONObject();
			data.put("c", new Vector2D(0,0).toString());
			data.put("g", 9.81);
		force.put("data", data);
		force.put("desc", "Movimiento hacia u punto fijo, la ley simula un escenario en el cual todos los cuerpos caen hacia el 'centro del universo'");
		return force;
	}
	
	
}
