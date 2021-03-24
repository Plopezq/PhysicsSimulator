package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	private Body cuerpo;
	BasicBodyBuilder(){
		
	}
	
	protected Body createTheInstance(JSONObject info){
		//PROBAR que alomejor toca poner: info.get("type".get("id"))
		this.cuerpo = new Body(info.get("id"),info.get("v"), info.get("p") ,info.get("m"));
		return this.cuerpo;
	}
}
