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

}
