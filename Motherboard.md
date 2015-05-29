**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/Motherboard?tm=6&wl=en)

# Introducción #
Esta clase permite obtener el numero de serie de la placa madre
# Definición #
```
package org.schimpf.sys.motherboard

public final class Motherboard {
	/**
	 * Almacena la contraseña de root
	 * Solo necesario en sistemas Unix
	 * @param Contraseña de root
	 */
	public static void setRootPasswd(String passwd)

	/**
	 * Retorna el numero de serie de la placa madre
	 * @return Numero de serie
	 */
	public static String getSerialNo()
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/sys/src/org/schimpf/sys/motherboard/Motherboard.java) |
|:------------|:-----------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public static void main(final String[] args) {
		// en linux, debemos especificar la contraseña de root ya que se requiere ejecutar un comando en consola con privilegios
		Motherboard.setRootPassword("secret");
		// mostramos el numero de serie en consola
		System.our.println("Serial No: " + Motherboard.getSerialNo());
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_