package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	private NewtonUniversalGravitation fuerza;
	
	public NewtonUniversalGravitationBuilder() {
		
		
	}

	@Override
	protected Object createTheInstance(JSONObject info) {
		if (info.isEmpty()) {
			this.fuerza = new NewtonUniversalGravitation();	
		}else {
			this.fuerza = new NewtonUniversalGravitation(info.getDouble("G"));	
		}
		return this.fuerza;
	}
	

	protected JSONObject createData(){
		JSONObject force = new JSONObject();
		force.put("type", "nlug");
			JSONObject data = new JSONObject();
			data.put("G","the gravitational constant (a number)"); //6.67e10-11
		force.put("data", data);
		force.put("desc", "Newton’s law of universal gravitation");
		return force;
	}
}
