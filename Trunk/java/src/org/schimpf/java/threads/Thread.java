/**
 * @author Hermann D. Schimpf
 * @author SCHIMPF - Sistemas de Información y Gestión
 * @version Aug 2, 2011 9:36:17 AM
 */
package org.schimpf.java.threads;

/**
 * Thread para procesos
 * 
 * @author Hermann D. Schimpf
 * @author SCHIMPF - Sistemas de Información y Gestión
 * @version Aug 2, 2011 9:36:17 AM
 */
public abstract class Thread extends java.lang.Thread {
	/**
	 * Bandera para saber si el thread fue interrumpido
	 * 
	 * @version Sep 14, 2011 1:27:55 PM
	 */
	private boolean	interrupted	= false;

	/**
	 * Bandera para el estado del thread
	 * 
	 * @version Aug 2, 2011 5:30:25 PM
	 */
	private boolean	running		= true;

	/**
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Información y Gestión
	 * @version Aug 4, 2011 12:20:44 PM
	 * @param threadClass Clase del thread
	 */
	public Thread(final Class<? extends Thread> threadClass) {
		// enviamos el nombre
		super("[" + threadClass.getName().substring(threadClass.getName().lastIndexOf(".") + 1) + "]");
	}

	/**
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Información y Gestión
	 * @version Aug 4, 2011 12:20:44 PM
	 * @param threadClass Clase del thread
	 * @param threadName Nombre del thread
	 */
	public Thread(final Class<? extends Thread> threadClass, final String threadName) {
		// enviamos la clase
		this(threadClass);
		// agregamos el nombre
		this.setName(this.getName().substring(0, this.getName().length() - 1) + ":" + threadName + "]");
	}

	@Override
	public final boolean isInterrupted() {
		// retornamos la bandera de interrupcion
		return this.interrupted;
	}

	@Override
	public final void run() {
		try {
			// bandera para continuar ejecutando
			boolean continueRunning = true;
			// mientras estemos ejecutando
			while (this.isRunning() && continueRunning) {
				// verificamos si se detuvo
				if (this.isInterrupted()) {
					// terminamos el thread
					this.shutdown();
					// salimos
					break;
				}
				// iniciamos el proceso del thread
				continueRunning = this.execute();
			}
			// finalizamos la ejecucion normal
			this.halt(false);
		} catch (final InterruptedException e) {
			// modificamos la bandera
			this.interrupted = true;
			// terminamos el thread
			this.shutdown();
		}
	}

	/**
	 * Finaliza el proceso del thread
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Aug 2, 2011 5:35:27 PM
	 */
	public final void shutdown() {
		// modificamos la bandera
		this.running = false;
		// verificamos si esta en ejecucion
		if (!this.getState().equals(Thread.State.TERMINATED) && !this.getState().equals(Thread.State.NEW))
			synchronized (this) {
				// levantamos el thread
				this.notify();
			}
		// finalizamos el proceso
		this.halt(this.isInterrupted());
	}

	/**
	 * Inicia el proceso del thread
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Aug 2, 2011 5:24:11 PM
	 * @return True para continuar ejecutando el thread
	 * @throws InterruptedException Al interrumpir el thread
	 */
	protected abstract boolean execute() throws InterruptedException;

	/**
	 * Finaliza el proceso del thread
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 2, 2011 4:41:23 PM
	 * @param interrupted True si el thread se interrumpio
	 */
	protected synchronized void halt(final boolean interrupted) {}

	/**
	 * Retorna el estado del thread
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 2, 2011 5:31:11 PM
	 * @return Estado del thread
	 */
	protected final boolean isRunning() {
		// retornamos la bandera
		return this.running;
	}
}