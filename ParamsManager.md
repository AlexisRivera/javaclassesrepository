**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/ParamsManager?tm=6&wl=en)

# Introducción #
Administrador abstracto de parametros para aplicaciones
# Definición #
```
package org.schimpf.util.managers
public abstract class ParamsManager {
	/**
	 * Carga la lista de parametros pasados al programa
	 * @param params Lista de parametros
	 */
	public final void loadParams(final String[] params)

	/**
	 * Retorna un parametro del programa
	 * @param paramID ID del parametro
	 * @return Valor del parametro
	 */
	protected final Object getParam(final String paramID)

	/**
	 * Agrega un parametro personalizado
	 * @param paramID ID del parametro
	 * @param param Valor del parametro
	 */
	protected final void setParam(final String paramID, final Object param)
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/util/src/org/schimpf/util/managers/ParamsManager.java) |
|:------------|:------------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public class ParametrosUtilizacion extends ParamsManager {
		public String getName() {
			// retornamos el parametro NAME
			return (String)this.getParam("NAME");
		}
	}
	
	private ParametrosUtilizacion params = new ParametrosUtilizacion();
	
	public static void main(String[] args) {
		// almacenamos los parametros en el administrador
		this.params.loadParams(args);
		// verificamos si se especifico el nombre
		if (this.params.getName() != null)
			// mostramos el nombre
			System.out.prinln(this.params.getName());
		else
			// mostramos la utilizacion
			System.err.println("Usage: java Utilizacion --name 'Josh'");
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_