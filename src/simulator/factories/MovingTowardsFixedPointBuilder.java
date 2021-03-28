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
		
		//TODO
		return null;
	}
	
	
}
