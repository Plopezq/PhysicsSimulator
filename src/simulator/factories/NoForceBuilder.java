package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	private NoForce fuerza;
	
	public NoForceBuilder() {
		
		
	}
	
	@Override
	protected Object createTheInstance(JSONObject obj) {
		this.fuerza = new NoForce();
		return this.fuerza;
	}
	
	protected JSONObject createData(){
		JSONObject force = new JSONObject();
		force.put("type", "ng"); //nf
			JSONObject data = new JSONObject();
		force.put("data", data);
		force.put("desc", "No force");
		return force;
	}

}
