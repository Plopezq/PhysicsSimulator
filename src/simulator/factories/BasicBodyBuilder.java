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
		this.cuerpo = new Body((String)info.get("id"),(Vector2D)info.get("v"), (Vector2D)info.get("p") ,(double)info.get("m"));
		return this.cuerpo;
	}
	
	protected JSONObject createData(){
		
		//TODO
		return null;
	}
}
