package simulator.factories;
import simulator.misc.Vector2D;
import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	private Body cuerpo;
	BasicBodyBuilder(){
		
	}
	
	protected Body createTheInstance(JSONObject info){
		//PROBAR que alomejor toca poner: info.get("type".get("id"))
		this.cuerpo = new Body((String)info.get("id"),(Vector2D)info.get("v"), (Vector2D)info.get("p") ,(double)info.get("m"));
		
	
		return this.cuerpo;
	}
}
