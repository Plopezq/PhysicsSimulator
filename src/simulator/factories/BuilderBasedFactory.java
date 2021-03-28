package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> constructores;

	public BuilderBasedFactory(List<Builder<T>> listaBuilder) {
		this.constructores = listaBuilder;
		for(Builder<T> b: constructores) {
			b.createInstance(b.getBuilderInfo()); //Se crean ocn la plantilla que ellos mismos generan
		}
	}

	@Override
	public T createInstance(JSONObject info) {
		/*
		 * public T createInstance(JSONObject info): recibe una structura JSON que
		 * describe el objeto a crear (ver la sintaxis más abajo), y devuelve una
		 * instancia de la clase correspondiente – una instancia de un subtipo de T. En
		 * caso de que info sea incorrecto, entonces lanza una excepción del tipo
		 * IllegalArgumentException.
		 */

		return (T) Builder<T>.createInstance(info);
		
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
