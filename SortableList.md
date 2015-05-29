**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/SortableList?tm=6&wl=en)

# Introducción #
Implementacion de [java.util.ArrayList](http://docs.oracle.com/javase/6/docs/api/java/util/ArrayList.html) ordenable por medio de la comparacion de los elementos en la lista
# Definición #
```
package org.schimpf.util.arrays
/**
 * @param <Type> Tipo para los valores
 */
public final class SortableList<Type extends Comparable<? super Type>> extends ArrayList<Type> {
	/**
	 * Ordena la lista de forma ascendente
	 */
	public void sort()

	/**
	 * Ordena la lista
	 * @param asc True para ordenar de forma ascendente
	 */
	public void sort(final boolean asc)
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/util/src/org/schimpf/util/arrays/SortableList.java) |
|:------------|:---------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public static main(String[] args) {
		// creamos la lista ordenable
		SortableList<String> list = new SortableList<String>();
		// agregamos algunos elementos
		list.add("Texto");
		list.add("Valor");
		list.add("Arbol");
		// ordenamos la lista
		list.sort();
		// recorremos la lista
		for (String value: list)
			// mostramos el valor
			System.out.println(value);
		// resultado:
		// Arbol
		// Texto
		// Valor
		
		// agregamos mas elementos
		list.add("Perro");
		list.add("Casa");
		// ordenamos descendente
		list.sort(false);
		// recorremos la lista
		for (String value: list)
			// mostramos el valor
			System.out.println(value);
		// resultado:
		// Valor
		// Texto
		// Perro
		// Casa
		// Arbol
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_