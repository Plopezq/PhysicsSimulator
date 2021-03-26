package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.MassLossingBody;
import simulator.misc.Vector2D;

public class MassLosingBodyBuilder extends Builder<Body> {

	private MassLossingBody cuerpo;
	
	public MassLosingBodyBuilder() {
		
	}

	@Override
	protected Object createTheInstance(JSONObject info) {
		this.cuerpo = new MassLossingBody((String)info.get("id"),(Vector2D)info.get("v"),(Vector2D)info.get("p"),(double)info.get("m"),(double)info.getDouble("factor"),(double)info.getDouble("freq"));
		return cuerpo;
	}
	
	protected JSONObject createData(){
		
		//TODO
		return null;
	}
	
	
	
}
