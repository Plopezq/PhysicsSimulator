package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> lista;

	public BuilderBasedFactory(List<Builder<T>> listaBuilder) {
		//super();
		this.lista = listaBuilder;
		//addTypesList();
	}

//	private void addTypesList() {
//		lista.add(new Builder<T>() {});  
//
//	}

	@Override
	public T createInstance(JSONObject info) {
		/*
		 * public T createInstance(JSONObject info): recibe una structura JSON que
		 * describe el objeto a crear (ver la sintaxis más abajo), y devuelve una
		 * instancia de la clase correspondiente – una instancia de un subtipo de T. En
		 * caso de que info sea incorrecto, entonces lanza una excepción del tipo
		 * IllegalArgumentException.
		 */

		if (info.get("type").equals("basic"))
			;

		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		/*
		 * public List<JSONObject>getInfo(): devuelve una lista de objetos JSON, que son
		 * “plantillas” para structuras JSON válidas. Los objetos de esta lista se
		 * pueden pasar como parámetro al método createInstance. Esto es muy útil para
		 * saber cuáles son los valores válidos para una factoría concreta, sin saber
		 * mucho sobre la factoría en si misma. Por ejemplo, utilizaremos este método
		 * cuando mostremos al usuario los posibles valores de las leyes de la gravedad.
		 */

		return null;
	}

}
