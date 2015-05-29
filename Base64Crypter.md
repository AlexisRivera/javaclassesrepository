**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/Base64Crypter?tm=6&wl=en)

# Introducción #
Clase para cifrar cadenas de texto en cifrado DES-BASE64
# Definición #
```
package org.schimpf.util.crypt
public class Base64Crypter {
	/**
	 * Retorna la instancia del crypter con la clave secreta
	 * @param secretKey Clave secreta a utilizar
	 * @return Crypter
	 */
	public static Base64Crypter getInstance(String secretKey)

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
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/util/src/org/schimpf/util/crypt/Base64Crypter.java) |
|:------------|:---------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion {
	public static void main(String[] args) {
		// obtenemos el des/encriptador
		Base64Crypter crypter = Base64Crypter.getInstance("contraseña");
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