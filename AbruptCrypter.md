**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/AbruptCrypter?tm=6&wl=en)

# Introducción #
Implementacion de Base64Crypter con cifrado mas complejo
# Definición #
```
package org.schimpf.util.crypter
public final class AbruptCrypter extends Base64Crypter {
	/**
	 * Fuerza del cifrado
	 */
	public enum Strength {
		/**
		 * Utilizar Fuerza Brutal
		 */
		BRUTAL,
		/**
		 * Utilzar Fuerza Alta
		 */
		HIGH,
		/**
		 * Utilizar Fuerza Normal
		 */
		NORMAL,
		/**
		 * Utilizat Fuerza Baja
		 */
		LOW;
	}

	/**
	 * Retorna la instancia del crypter con la clave secreta
	 * @param secretKey Clave secreta a utilizar
	 * @return Crypter
	 */
	public static AbruptCrypter getInstance(String secretKey)

	/**
	 * Retorna la instancia del crypter con la clave secreta
	 * @param secretKey Clave secreta a utilizar
	 * @param strength Fuerza del cifrado
	 * @return Crypter
	 */
	public static AbruptCrypter getInstance(String secretKey, Strength strength)

	/**
	 * Desencripta una cadena de texto
	 * @param string Cadena a desencriptar
	 * @return Cadena de Texto desencriptada
	 */
	public String decrypt(String string)

	/**
	 * Encripta una cadena de texto
	 * @param string Cadena a encriptar
	 * @return Cadena de Texto encriptada
	 */
	public String encrypt(String string)
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/util/src/org/schimpf/util/crypt/AbruptCrypter.java) |
|:------------|:---------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public static void main(String[] args) {
		// obtenemos el des/encriptador con fuerta alta
		AbruptCrypter crypter = AbruptCrypter.getInstance("contraseña", Strength.HIGH);
		// ciframos una cadena
		String texto = crypter.encrypt("Mensaje de Prueba");
		// mostramos la cadena cifrada
		System.out.println(texto);
		// mostramos la cadena desifrada
		System.out.println(crypter.decrypt(texto));
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_