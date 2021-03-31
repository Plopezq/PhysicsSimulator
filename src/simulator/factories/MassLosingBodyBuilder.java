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
		this.cuerpo = new MassLossingBody(info.getString("id"),(Vector2D)info.get("v"),(Vector2D)info.get("p"),info.getDouble("m"),info.getDouble("factor"),info.getDouble("freq"));
		return cuerpo;
	}
	
	protected JSONObject createData(){
		JSONObject body = new JSONObject();
		body.put("type", "mlb");
			JSONObject data = new JSONObject();
			data.put("id", "b1");
			data.put("p", new Vector2D(-3.5e10,0).toString());
			data.put("v", new Vector2D(0, 1.4e03).toString());
			data.put("m", 3.08e28);
			data.put("freq", 1e3);
			data.put("factor", 1e-3);
		body.put("data", data);
		body.put("desc", "Estos cuerpos se caracterizan porque pierden, con cierta frecuencia, masa cuando se mueven.");
		return body;
	}
	
	
	
}
