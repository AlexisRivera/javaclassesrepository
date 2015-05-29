**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/MultiKeyMap?tm=6&wl=en)

# Introducción #
Esta clase funciona de la misma forma que la clase [java.util.HashMap](http://docs.oracle.com/javase/6/docs/api/java/util/HashMap.html) pero permite utilizar una clave multiple para almacenar los valores
# Definición #
```
package org.schimpf.util.arrays
/**
 * @param <KType> Tipo de valor para las claves
 * @param <VType> Tipo de valor a almacenar
 */
public final class MultiKeyMap<KType, VType> implements Serializable {
	/**
	 * Vacia el mapa de claves-valor
	 */
	public void clear()

	/**
	 * Retorna true si existe un mapeo con la clave especificada
	 * @param key Clave a veificar
	 * @return True si existe la clave
	 */
	public boolean containsKey(final KType... key)

	/**
	 * Retorna true si el valor especificado esta asociado a alguna clave
	 * @param value Valor a buscar
	 * @return True si el valor tiene clave asoociada
	 */
	public boolean containsValue(final Object value)

	/**
	 * Retorna un set con los mapas de claves-valor
	 * @return Set de Claves-Valor
	 */
	public Set<Entry<ArrayList<KType>, VType>> entrySet()

	/**
	 * Retorna el valor especificado a la clave
	 * @param key Clave
	 * @return Valor asociado a la clave
	 */
	public VType get(final KType... key)

	/**
	 * Retorna si el mapa de claves-valor esta vacio
	 * @return True si esta vacio
	 */
	public boolean isEmpty()

	/**
	 * Retorna la lista de claves en este mapa
	 * @return Lista de claves
	 */
	public Set<ArrayList<KType>> keySet()

	/**
	 * Agrega un valor asociado a una clave al mapa
	 * @param value Valor a asociar con la clave
	 * @param keys Claves
	 * @return Valor anterior si la clave ya contenia asociacion o null si es una clave nueva
	 */
	public VType put(final VType value, final KType... keys)

	/**
	 * Elimina el mapa de la clave asociada si existe
	 * @param keys Claves
	 * @return Valor de la clave asociada o null si no existe el mapa
	 */
	public VType remove(final KType... keys)

	/**
	 * Retornamos el tamaño del mapa
	 * @return Tamaño de mapa
	 */
	public int size()

	/**
	 * Retorna los valores almacenados en este mapa
	 * @return Coleccion de valores de este mapa
	 */
	public Collection<VType> values()
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/util/src/org/schimpf/util/arrays/MultiKeyMap.java) |
|:------------|:--------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public static void main(final String[] args) {
		// se especifican los tipos de datos para las claves y los valores
		MultiKeyMap<Integer, String> map = new MultiKeyMap<Integer, String>();
		// almacenamos unos valores de prueba
		map.put("VALOR1", 1, 2);
		map.put("VALOR2", 1, 2, 3, 4);
		// mostramos los valores
		System.out.println(map.get(1, 2, 3)); // retorna null porque la clave {1,2,3} no existe
		System.out.println(map.get(1, 2, 3, 4)); // muestra VALOR2
		System.out.println(map.get(1, 2)); // muestra VALOR1
		System.out.println(map.put("VALOR3", 1, 2)); // muestra VALOR1
		System.out.println(map.get(1, 2)); // muestra VALOR3
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_