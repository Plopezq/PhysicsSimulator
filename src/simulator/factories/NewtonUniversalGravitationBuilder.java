package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	private NewtonUniversalGravitation fuerza;
	
	public NewtonUniversalGravitationBuilder() {
		
		
	}

	@Override
	protected Object createTheInstance(JSONObject info) {
		if (info == null) {
			this.fuerza = new NewtonUniversalGravitation();	
		}else {
			this.fuerza = new NewtonUniversalGravitation(info.getDouble("G"));	
		}
		return this.fuerza;
	}
	

	protected JSONObject createData(){
		
		//TODO
		return null;
	}
}
