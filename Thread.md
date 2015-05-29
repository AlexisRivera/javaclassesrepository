**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/Thread?tm=6&wl=en)

# Introducción #
Esta clase extiende de la clase [java.lang.Thread](http://docs.oracle.com/javase/6/docs/api/java/lang/Thread.html) ampliando la funcionalidad de threads con controles de apagado e interrupcion.
# Definición #
```
package org.schimpf.java.threads
public abstract class Thread extends java.lang.Thread {
	/**
	 * Retorna si el thread fue interrumpido al finalizar su ejecucion
	 * @return True si el thread fue interrumpido
	 */
	public final boolean isInterrupted();

	/**
	 * Solicita la finalizacion del thread
	 */
	public final void shutdown();

	/**
	 * Metodo a implementar en la clase hija donde se realiza el proceso del thread
	 * Este metodo se repite mientras el thread este en ejecucion
	 * @return True para continuar con la ejecicion
	 */
	protected abstract boolean execute() throws InterruptedException;

	/**
	 * Metodo que se llama al solicitar la finalizacion del thread
	 * Puede ser sobreescrito
	 * @param interrupted True si la finalizacion del thread ocurrio de forma interrumpida
	 */
	protected synchronized void halt(final boolean interrupted);

	/**
	 * Retorna si el thread se encuentra en ejecucion
	 * @return True si el thread esta en ejecucion
	 */
	protected final boolean isRunning();
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/java/src/org/schimpf/java/threads/Thread.java) |
|:------------|:----------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class Utilizacion extends Thread {
	protected MyThread() {
		// enviamos el constructor
		super(Utilizacion.class);
	}

	@Override
	protected boolean execute() throws InterruptedException {
		// mostramos un mensaje
		System.out.println("Estamos en ejecucion");
		// esperamos 500 milisegundos
		java.lang.Thread.sleep(500);
		// retornamos si estamos en ejecucion
		return this.isRunning();
	}

	@Override
	protected synchronized void halt() {
		// mostramos un mensaje
		System.err.println("Finalizando ejecucion");
	}

	public static void main(String[] args) {
		// generamos el thread
		Utilizacion ejemplo = new Utilizacion();
		// iniciamos el thread
		ejemplo.start();
		// esperamos 5 segundos
		java.lang.Thread.sleep(5000);
		// solicitamos la finalizacion del thread
		ejemplo.shutdown();
	}
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_