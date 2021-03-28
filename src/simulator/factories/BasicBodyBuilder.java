package simulator.factories;
import simulator.misc.Vector2D;
import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	private Body cuerpo;
	
	public BasicBodyBuilder(){
		
	}
	
	@Override
	protected Body createTheInstance(JSONObject info){
		this.cuerpo = new Body(info.getString("id"),(Vector2D)info.get("v"), (Vector2D)info.get("p") ,info.getDouble("m"));
		return this.cuerpo;
	}
	
	protected JSONObject createData(){ //Devuelve un JSON de ejemplo, una plantilla
		JSONObject body = new JSONObject();
		body.put("type", "basic");
			JSONObject data = new JSONObject();
			data.put("id", "b1");
			data.put("p", new Vector2D(0,0));
			data.put("v", new Vector2D(0.05e04, 0));
			data.put("m", 5.97e24);
		body.put("data", data);
		body.put("desc", "Cuerpo basico que representa una entidad fisica");
		return body;
	}
}
