/**
 * | This program is free software: you can redistribute it and/or modify
 * | it under the terms of the GNU General Public License as published by
 * | the Free Software Foundation, either version 3 of the License.
 * |
 * | This program is distributed in the hope that it will be useful,
 * | but WITHOUT ANY WARRANTY; without even the implied warranty of
 * | MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * | GNU General Public License for more details.
 * |
 * | You should have received a copy of the GNU General Public License
 * | along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Jul 19, 2012 1:19:11 PM
 */
package org.schimpf.net.socket;

import org.schimpf.java.threads.Thread;
import org.schimpf.net.utils.Commands;
import org.schimpf.util.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Conexion establecida en un socket
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Jul 19, 2012 1:19:11 PM
 * @param <SType> Clase del socket servidor
 * @param <CType> Clase de la conexion del socket
 * @param <StageType> Enum para las etapas POST
 */
public abstract class AbstractServerMultiSocketConnection<SType extends AbstractServerMultiSocket<SType, CType, StageType>, CType extends AbstractServerMultiSocketConnection<SType, CType, StageType>, StageType extends Enum<StageType>> extends Thread {
	/**
	 * Host por defecto
	 * 
	 * @version Aug 22, 2011 3:42:35 PM
	 */
	public static InetAddress	HOST;
	static {
		try {
			// cargamos el localhost
			AbstractServerMultiSocketConnection.HOST = InetAddress.getLocalHost();
		} catch (final UnknownHostException ignored) {}
	}

	/**
	 * Bandera de autenticacion correcta
	 * 
	 * @version Oct 6, 2011 12:24:33 PM
	 */
	private boolean				autenticated	= false;

	/**
	 * Socket de conexion abierta en el puerto
	 * 
	 * @version Aug 5, 2011 9:15:52 AM
	 */
	private final Socket			connection;

	/**
	 * Fichero a enviar
	 * 
	 * @version Oct 14, 2011 1:29:46 PM
	 */
	private File					file;

	/**
	 * Nombre del fichero a recibir
	 * 
	 * @version Oct 14, 2011 1:18:59 PM
	 */
	private String					fileName;

	/**
	 * Tamano del fichero a recibir
	 * 
	 * @version Oct 14, 2011 1:19:54 PM
	 */
	private Long					fileSize;

	/**
	 * Stream de entrada de mensajes
	 * 
	 * @version Aug 5, 2011 9:16:56 AM
	 */
	private ObjectInputStream	inputStream;

	/**
	 * Ultimo comando enviado
	 * 
	 * @version Oct 6, 2011 11:46:11 AM
	 */
	private Commands				lastCommand;

	/**
	 * Etapa actual de datos
	 * 
	 * @version Oct 21, 2011 10:38:02 AM
	 */
	private Stage					localStage		= Stage.INIT;

	/**
	 * Instancia de log
	 * 
	 * @version Aug 2, 2012 10:05:25 AM
	 */
	private final Logger			log;

	/**
	 * Stream de salida de mensajes
	 * 
	 * @version Aug 5, 2011 9:17:09 AM
	 */
	private ObjectOutputStream	outputStream;

	/**
	 * Socket servidor al que pertenece esta conexion
	 * 
	 * @version Jul 19, 2012 3:14:35 PM
	 */
	private final SType			parent;

	/**
	 * Etapa POST actual
	 * 
	 * @version Oct 1, 2012 11:55:41 AM
	 */
	private StageType				stage;

	/**
	 * Etapas de transmision de datos
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 21, 2011 10:39:26 AM
	 */
	protected enum Stage {
		/**
		 * Etapa de autenticacion
		 * 
		 * @version Oct 21, 2011 10:40:49 AM
		 */
		AUTH,
		/**
		 * Etapa de transmision de ficheros
		 * 
		 * @version Oct 21, 2011 10:40:22 AM
		 */
		FILE,
		/**
		 * Etapa inicial
		 * 
		 * @version Oct 21, 2011 10:40:04 AM
		 */
		INIT,
		/**
		 * Etapa de proceso externo
		 * 
		 * @version Oct 21, 2011 10:40:38 AM
		 */
		POST;
	}

	/**
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 11:49:13 AM
	 * @param name Nombre del thread
	 * @param parent Socket servidor en el que se inicio esta conexion
	 * @param connection Socket en el que se establecio la conexion
	 */
	public AbstractServerMultiSocketConnection(final Class<? extends CType> name, final SType parent, final Socket connection) {
		// enviamos el constructor
		super(name, new Integer(connection.getPort()).toString());
		// almacenamos el socket padre
		this.parent = parent;
		// almacenamos el socket de conexion
		this.connection = connection;
		// instanciamos el logger
		this.log = new Logger(this.getName());
		// registramos el capturador de señal de apagado
		Runtime.getRuntime().addShutdownHook(new java.lang.Thread(new Runnable() {
			@Override
			public void run() {
				// realizamos el shutdown
				AbstractServerMultiSocketConnection.this.shutdownRequest();
			}
		}));
	}

	/**
	 * Retorna si existe una conexion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Nov 22, 2012 2:36:59 PM
	 * @return True si existe una conexion
	 */
	public final boolean isConnected() {
		// retornamos si existe una conexion
		return this.getConnection() != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected final boolean execute() throws InterruptedException {
		// abrimos los streams de comunicacion
		this.openStreams();
		// bandera para continuar recibiendo
		boolean continuar = true;
		// datos a recibir
		Object data;
		// ingresamos a un bucle
		do {
			// recibimos los datos y los procesamos
			data = this.receive();
			// bandera para continuar el thread
			continuar = data != null;
			// verificamos si hay datos
			if (data != null)
				// verificamos si no es la etapa final
				if (!this.getLocalStage().equals(Stage.POST)) {
					// mostramos un log
					this.getLogger().debug("=>> " + (this.getLocalStage().equals(Stage.AUTH) && this.getLastCommand().equals(Commands.DATA) && !(data instanceof Commands) ? Commands.AUTH_DATA : data));
					// verificamos si estamos en la etapa de transferencia de un fichero
					if (this.getLocalStage().equals(Stage.FILE))
						// procesamos el paso del fichero
						continuar = this.processFileStage(data);
					else
						// procesamos la etapa
						continuar = this.processStage(this.getLocalStage(), data);
					// verificamos si directamente pasamos al proceso externo
				} else {
					// mostramos un log
					this.getLogger().debug(data instanceof Commands && (((Commands) data).equals(Commands.EXIT) || ((Commands) data).equals(Commands.SHUTDOWN) || ((Commands) data).equals(Commands.FILE) || ((Commands) data).equals(Commands.BYE)) ? "=>> " + data : ">>> " + data);
					// verificamos si es un comando de finalizacion
					if (data instanceof Commands && (((Commands) data).equals(Commands.EXIT) || ((Commands) data).equals(Commands.SHUTDOWN) || ((Commands) data).equals(Commands.FILE) || ((Commands) data).equals(Commands.BYE))) {
						// verificamos si el comando es transferencia de archivo
						if (((Commands) data).equals(Commands.FILE)) {
							// respondemos OK
							this.send(Commands.ACK);
							// cambiamos al modo transferencia
							this.setLocalStage(Stage.FILE);
							// verificamos si el comando es transferencia de archivo
						} else if (((Commands) data).equals(Commands.BYE))
							// modificamos la bandera
							continuar = false;
						else {
							// modificamos la bandera
							continuar = false;
							// enviamos adios
							this.send(Commands.BYE);
						}
					} else
						// procesamos los datos
						continuar = this.processData(data);
				}
		} while (continuar);
		try {
			// cerramos la conexion
			this.getConnection().close();
		} catch (final IOException ignored) {}
		// avisamos al padre que finalizamos
		this.getParent().connectionFinished((CType) this);
		// finalizamos la conexion abierta
		return false;
	}

	/**
	 * Procesa el fichero recibido
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 12:33:00 PM
	 * @param fileReceived Fichero recibido
	 */
	protected void fileReceived(final File fileReceived) {}

	/**
	 * Retorna la etapa POST actual
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Oct 1, 2012 11:55:44 AM
	 * @return Etapa POST actual
	 */
	protected final StageType getStage() {
		// reornamos la etapa actual
		return this.stage;
	}

	/**
	 * Retorna si se requiere autenticacion para la conexion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 6, 2011 11:33:17 AM
	 * @return True para solicitar autenticacion
	 */
	protected boolean needsAuthentication() {
		// por defecto no utilizamos autenticacion
		return false;
	}

	/**
	 * Procesa los datos recibidos
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:56:52 AM
	 * @param data Datos a procesar
	 * @return True para continuar recibiendo
	 */
	protected abstract boolean processData(Object data);

	/**
	 * Envia datos al output
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:50:18 AM
	 * @param data Datos a enviar
	 * @return True si se envio correctamente
	 */
	protected final boolean send(final Object data) {
		// enviamos los datos
		return this.send(data, null);
	}

	/**
	 * Envia datos al output
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:50:18 AM
	 * @param data Datos a enviar
	 * @param overWrite Mensaje para sobreescribir
	 * @return True si se envio correctamente
	 */
	protected final synchronized boolean send(final Object data, final Commands overWrite) {
		try {
			// verificamos si la conexion esta cerrada
			if (this.getConnection().isClosed())
				// retornamos false
				return false;
			// mostramos un mensaje
			this.getLogger().debug((data instanceof Commands || overWrite != null ? "<<= " : "<<< ") + (overWrite != null ? overWrite : data));
			// verificamos si es un comando
			if (!this.getLocalStage().equals(Stage.POST) && data instanceof Commands || overWrite != null)
				// almacenamos el ultimo comando enviado
				this.lastCommand = overWrite != null ? overWrite : (Commands) data;
			// enviamos el dato
			this.getOutputStream().writeObject(data);
			// escribimos el dato
			this.getOutputStream().flush();
		} catch (final IOException e) {
			// mostramos el trace
			this.getLogger().severe(e);
			// retornamos false
			return false;
		}
		// retornamos true
		return true;
	}

	/**
	 * Envia un fichero a travez del socket
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 12:42:47 PM
	 * @param file Fichero a enviar
	 */
	protected final void sendFile(final File file) {
		// almacenamos el fichero a enviar
		this.file = file;
		// cambiamos al modo transferencia
		this.setLocalStage(Stage.FILE);
		// soliticamos el envio del fichero
		this.send(Commands.FILE);
	}

	/**
	 * Almacena la nueva etapa POST
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Oct 1, 2012 11:56:52 AM
	 * @param stage Etapa POST
	 */
	protected final void setStage(final StageType stage) {
		// almcenamos la etapa actual
		this.stage = stage;
	}

	/**
	 * Procesos a ejecutar cuando se recibe una solicitud de apagado
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Nov 1, 2011 11:04:59 AM
	 */
	protected abstract void shutdownRequest();

	/**
	 * Valida la autenticacion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 6, 2011 11:57:06 AM
	 * @param data Datos de autenticacion recibidos
	 * @return True para aceptar la validacion
	 */
	protected boolean validateAutentication(final Object data) {
		// por defecto enviamos false
		return false;
	}

	/**
	 * Retorna el fichero a enviar
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:30:50 PM
	 * @return Fichero a enviar
	 */
	private File getFile() {
		// retornamos el fichero a enviar
		return this.file;
	}

	/**
	 * Retorna el nombre del fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:19:23 PM
	 * @return Nombre del fichero
	 */
	private String getFileName() {
		// retornamos el nombre del fichero
		return this.fileName;
	}

	/**
	 * Retorna el tamano del fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:21:39 PM
	 * @return Tamano del fichero
	 */
	private Long getFileSize() {
		// retornamos el tamano del fichero
		return this.fileSize;
	}

	/**
	 * Retorna el stream de entrada
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:45:58 AM
	 * @return Stream de entrada
	 */
	private ObjectInputStream getInputStream() {
		// retornamos el stream de entrada
		return this.inputStream;
	}

	/**
	 * Retorna el Stream de salida
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:43:00 AM
	 * @return Stream de Salida
	 */
	private ObjectOutputStream getOutputStream() {
		// retornamos el stream de salida
		return this.outputStream;
	}

	/**
	 * Reorna el socket padre al que pertenece la conexion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Jul 19, 2012 3:14:59 PM
	 * @return Socket padre
	 */
	private SType getParent() {
		// retornamos el socket padre
		return this.parent;
	}

	/**
	 * Retorna si ya se realizo la autenticacion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 6, 2011 12:00:27 PM
	 * @return Bandera de autenticacion
	 */
	private boolean isAutenticated() {
		// retornamos la banderta
		return this.autenticated;
	}

	/**
	 * Abre los streams de comunicacion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:41:01 AM
	 */
	private void openStreams() {
		try {
			// mostramos un log
			this.getLogger().debug("Opening streams..");
			// abrimos el stream de salida
			this.setOutputStream(new ObjectOutputStream(this.getConnection().getOutputStream()));
			// abrimos el stream de entrada
			this.setInputStream(new ObjectInputStream(this.getConnection().getInputStream()));
		} catch (final IOException e) {}
	}

	/**
	 * Procesa los datos de la etapa de transmision de ficheros
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 21, 2011 10:52:53 AM
	 * @param data Datos
	 * @return True para continuar
	 */
	private boolean processFileStage(final Object data) {
		// verificamos si el comando anterior fue la solicitud de envio de fichero
		if (this.getLastCommand().equals(Commands.FILE)) {
			// verificamos si respondio OK
			if (((Commands) data).equals(Commands.ACK))
				// solicitamos el envio del nombre del fichero
				this.send(Commands.DATA);
			// verificamos si pedimos el nombre
		} else if (this.getLastCommand().equals(Commands.NAME)) {
			// mostramos un log
			this.getLogger().debug(">>> " + data);
			// almacenamos el nombre del fichero
			this.setFileName(data.toString());
			// solicitamos el tamano del fichero
			this.send(Commands.SIZE);
			// verificamos si solicitamos el tamano del fichero
		} else if (this.getLastCommand().equals(Commands.SIZE)) {
			// mostramos un log
			this.getLogger().debug(">>> " + data + " bytes");
			// almacenamos el tamano del fichero
			this.setFileSize(Long.parseLong(data.toString()));
			// solicitamos el fichero
			this.send(Commands.DATA);
			// obtenemos el fichero
			final File receivedFile = this.receiveFile();
			// retornamos ok
			this.send(Commands.ACK);
			// modficamos la etapa
			this.setLocalStage(Stage.POST);
			// recibimos el fichero y lo procesamos
			this.fileReceived(receivedFile);
			// verificamos si es solicitud de datos del fichero
		} else if (((Commands) data).equals(Commands.DATA)) {
			// verificamos si es solicitud de envio de nombre
			if (this.getLastCommand().equals(Commands.ACK))
				// solicitamos el nombre del fichero
				this.send(Commands.NAME);
			else
				// enviamos el fichero
				this.sendFileContents();
			// verificamos si se pidio el nombre
		} else if (((Commands) data).equals(Commands.NAME))
			// enviamos el nombre del fichero
			this.send(this.getFile().getName());
		// verificamos si se pidio el tamano del fichero
		else if (((Commands) data).equals(Commands.SIZE))
			// retornamos el tamano del fichero
			this.send(this.getFile().length());
		// verificamos si se pidio el tamano del fichero
		else if (((Commands) data).equals(Commands.ACK))
			// cambiamos al modo normal
			this.setLocalStage(Stage.POST);
		// retornamos true para continuar
		return true;
	}

	/**
	 * Procesa los datos de la etapa
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 21, 2011 11:47:51 AM
	 * @param stage Etapa actual
	 * @param data Datos
	 * @return True para continuar
	 */
	private boolean processStage(final Stage stage, final Object data) {
		// generamos una bandera
		boolean continuar = true;
		// verificamos en que etapa estamos
		switch (stage) {
			case INIT:
				// verificamos si es el saludo
				if (((Commands) data).equals(Commands.HELO))
					// saludamos
					this.send(Commands.HELO);
				// verificamos si es la solicitud de datos
				else if (((Commands) data).equals(Commands.DATA))
					// verificamos si no estamos autenticados
					if (!this.isAutenticated() && this.needsAuthentication()) {
						// modificamos la etapa al proceso de autenticacion
						this.setLocalStage(Stage.AUTH);
						// solicitamos autenticacion
						this.send(Commands.AUTH);
					} else {
						// enviamos ok para aceptar datos
						this.send(Commands.ACK);
						// modificamos la etapa al proceso externo
						this.setLocalStage(Stage.POST);
					}
				// finalizamos la etapa
				break;
			case AUTH:
				// verificamos si el comando anterior fue solicitud de autenticacion
				if (this.getLastCommand().equals(Commands.AUTH)) {
					// verificamos si se acepto la autenticacion
					if (((Commands) data).equals(Commands.ACK))
						// solicitamos los datos de autenticacion
						this.send(Commands.DATA);
					else if (((Commands) data).equals(Commands.NAK))
						// retornamos autenticacion fallida
						this.send(Commands.NAK);
					// verificamos si solicitamos los datos de autenticacion
				} else if (this.getLastCommand().equals(Commands.DATA)) {
					// validamos la autenticacion
					if (this.validateAutentication(data)) {
						// modificamos la bandera de autenticacion
						this.autenticated = true;
						// enviamos autenticacion correcta
						this.send(Commands.ACK);
					} else {
						// modificamos la bandera de autenticacion
						this.autenticated = false;
						// enviamos autenticacion fallida
						this.send(Commands.NAK);
					}
				} else if (((Commands) data).equals(Commands.DATA))
					// verificamos si estamos autenticados
					if (this.isAutenticated()) {
						// retornamos ok
						this.send(Commands.ACK);
						// cambiamos a la etapa externa
						this.setLocalStage(Stage.POST);
					} else
						// retonamos false
						this.send(Commands.NAK);
				else if (((Commands) data).equals(Commands.BYE)) {
					// modificamos la bandera
					continuar = false;
					try {
						// finalizamos la conexion
						this.getConnection().close();
					} catch (final IOException ignored) {}
				}
				// finalizamos la etapa
				break;
			// en cualquier otro caso
			default:
				if (((Commands) data).equals(Commands.BYE))
					// modificamos la bandera
					continuar = false;
				// finalizamos la etapa
				break;
		}
		// retornamos la bandera
		return continuar;
	}

	/**
	 * Recibe los datos del socket
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:58:22 AM
	 * @return Datos recibidos
	 */
	private Object receive() {
		try {
			// verificamos si la conexion esta abierta
			if (!this.getConnection().isClosed())
				// retornamos los datos
				return this.getInputStream().readObject();
		} catch (final IOException e) {
			// print the StackTrace
			this.getLogger().error(e);
		} catch (final ClassNotFoundException e) {
			// print the StackTrace
			this.getLogger().severe(e);
		}
		// retornamos null
		return null;
	}

	/**
	 * Recibe un fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 12:32:11 PM
	 * @return Datos del fichero recibido
	 */
	private File receiveFile() {
		// mostramos un log
		this.getLogger().debug("Receiving file (" + this.getFileName() + ": " + this.getFileSize() + " bytes)..");
		// creamos un fichero temporal
		File result = null;
		try {
			// creamos un fichero temporal
			result = File.createTempFile(this.getFileName(), null);
			// abrimos el fichero
			final FileOutputStream outFile = new FileOutputStream(result);
			// iniciamos un buffer
			final byte[] buff = new byte[this.getConnection().getReceiveBufferSize()];
			// iniciamos una bandera
			int bytesReceived = 0;
			// iniciamos un acumulador
			long totalReceived = 0;
			// recorremos mientras recibimos datos
			while ((bytesReceived = this.getConnection().getInputStream().read(buff)) > 0) {
				// sumamos al acumulador
				totalReceived = totalReceived + bytesReceived;
				// agregamos el pedazo al fichero temporal
				outFile.write(buff, 0, bytesReceived);
				// verificamos si es el final
				if (totalReceived >= this.getFileSize())
					// salimos
					break;
			}
			// cerramos el fichero
			outFile.close();
			// mostramos un log
			this.getLogger().info("File received");
		} catch (final SocketException e) {
			// mostramos el trace de la excepcion
			this.getLogger().severe(e);
		} catch (final IOException e) {
			// mostramos el trace de la excepcion
			this.getLogger().severe(e);
		}
		// retornamos el fichero
		return result;
	}

	/**
	 * Envia el contenido del fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:28:36 PM
	 * @throws FileNotFoundException Si el fichero no existe
	 */
	private void sendFileContents() {
		// mostramos un log
		this.getLogger().debug("Sending file (" + this.getFile().getName() + ": " + this.getFile().length() + " bytes)..");
		// iniciamos una bandera
		int bytesRead = 0;
		try {
			// abrimos el fichero
			final FileInputStream inFile = new FileInputStream(this.getFile());
			// creamos un buffer para el envio
			final byte[] buff = new byte[this.getConnection().getSendBufferSize()];
			// leemos el fichero
			while ((bytesRead = inFile.read(buff)) > 0) {
				// enviamos el buffer por el socket
				this.getConnection().getOutputStream().write(buff, 0, bytesRead);
				// vaciamos el buffer
				this.getConnection().getOutputStream().flush();
			}
			// cerramos el fichero
			inFile.close();
			// vaciamos el fichero enviado
			this.file = null;
			// mostramos un log
			this.getLogger().info("File sent");
		} catch (final SocketException e) {
			// mostramos el trace de la excepcion
			this.getLogger().severe(e);
		} catch (final FileNotFoundException e) {
			try {
				// enviamos -1 para finalizar
				this.getConnection().getOutputStream().write(-1);
			} catch (final IOException ignored) {}
		} catch (final IOException e) {
			// mostramos el trace de la excepcion
			this.getLogger().severe(e);
		}
	}

	/**
	 * Almacena el nombre del fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:17:50 PM
	 * @param fileName Nombre del fichero a recibir
	 */
	private void setFileName(final String fileName) {
		// almacenamos el nombre del fichero
		this.fileName = fileName;
	}

	/**
	 * Almacena el tamano del fichero
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 14, 2011 1:21:10 PM
	 * @param fileSize Tamano del fichero
	 */
	private void setFileSize(final Long fileSize) {
		// almacenamos el tamano del fichero
		this.fileSize = fileSize;
	}

	/**
	 * Almacena el stream de entrada
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:44:38 AM
	 * @param stream Stream de entrada
	 */
	private void setInputStream(final ObjectInputStream stream) {
		// almacenamos el stream de entrada
		this.inputStream = stream;
	}

	/**
	 * Almacena el outputStream de la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:42:16 AM
	 * @param stream Stream de salida
	 * @throws IOException Si no se puede limpiar el stream
	 */
	private void setOutputStream(final ObjectOutputStream stream) throws IOException {
		// almacenamos el stream de salida
		this.outputStream = stream;
		// limpiamos el stream
		this.getOutputStream().flush();
	}

	/**
	 * Retorna el socket con la conexion abierta para el traslado de datos
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @author Schimpf.NET
	 * @version Aug 5, 2011 9:24:11 AM
	 * @return Conexion para el traslado de datos
	 */
	final Socket getConnection() {
		// retornamos la conexion abierta
		return this.connection;
	}

	/**
	 * Retorna el ultimo comando
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 6, 2011 11:45:11 AM
	 * @return Ultimo comando enviado
	 */
	final Commands getLastCommand() {
		// retornamos el ultimo comando enviado
		return this.lastCommand;
	}

	/**
	 * Retorna la etapa actual
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 21, 2011 10:43:19 AM
	 * @return Etapa actual
	 */
	final Stage getLocalStage() {
		// retrnamos la etapa actual
		return this.localStage;
	}

	/**
	 * Retorna el logger
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Aug 2, 2012 10:00:54 AM
	 * @return Logger
	 */
	final Logger getLogger() {
		// retornamos el logger
		return this.log;
	}

	/**
	 * Almacena la nueva etapa
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 21, 2011 10:44:25 AM
	 * @param newStage Nueva etapa
	 */
	final void setLocalStage(final Stage newStage) {
		// almacenamos la nueva etapa
		this.localStage = newStage;
		// mostramos un mensaje
		this.getLogger().debug("Changing Stage to: " + this.getLocalStage());
	}
}