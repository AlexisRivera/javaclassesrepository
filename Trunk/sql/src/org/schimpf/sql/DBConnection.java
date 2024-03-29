/**
 * @author Hermann D. Schimpf
 * @author SCHIMPF - Sistemas de Informacion y Gestion
 * @version Apr 16, 2011 12:27:11 AM
 */
package org.schimpf.sql;

import org.schimpf.net.utils.ConnectionData;
import org.schimpf.util.exceptions.MissingConnectionDataException;

/**
 * Metodos necesarios para una conexion a servidores SQL
 * 
 * @author Hermann D. Schimpf
 * @author SCHIMPF - Sistemas de Informacion y Gestion
 * @version Apr 16, 2011 12:27:11 AM
 */
public interface DBConnection {
	/**
	 * Conecta al servidor de Bases de Datos
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:12:39 PM
	 * @return True si se pudo conectar
	 * @throws MissingConnectionDataException Excepcion si faltan datos de conexion
	 */
	public boolean connect() throws MissingConnectionDataException;

	/**
	 * Almacena los datos para la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:10:59 PM
	 * @param data Datos para la conexion
	 * @param ddbb Nombre de la base de datos
	 */
	public void setConnectionData(final ConnectionData data, final String ddbb);

	/**
	 * Almacena los datos para la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 11, 2012 8:49:23 AM
	 * @param host Direccion del Servidor
	 * @param port Puerto del Servidor
	 * @param user Usuario de conexion
	 * @param pass Contraseña del usuario
	 * @param ddbb Nombre de la Base de Datos
	 */
	public void setConnectionData(final String host, final Integer port, final String user, final String pass, final String ddbb);

	/**
	 * Almacena los datos para la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:10:59 PM
	 * @param host Direccion del Servidor
	 * @param user Usuario de conexion
	 * @param pass Contraseña del usuario
	 * @param ddbb Nombre de la Base de Datos
	 */
	public void setConnectionData(final String host, final String user, final String pass, final String ddbb);

	/**
	 * Almacena el nombre de la base de datos
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:09:44 PM
	 * @param ddbb Nombre de la Base de Datos
	 */
	public void setDDBB(final String ddbb);

	/**
	 * Almacena la direccion del servidor a conectar
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:08:49 PM
	 * @param host Servidor a conectar
	 */
	public void setHost(final String host);

	/**
	 * Almacena la contraseña para la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:07:57 PM
	 * @param pass Contraseña de la conexion
	 */
	public void setPass(final String pass);

	/**
	 * Almacena el puerto del servidor a conectar
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:08:49 PM
	 * @param port Puerto del servidor a conectar
	 */
	public void setPort(final Integer port);

	/**
	 * Almacena el usuario para la conexion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 15, 2011 5:04:18 PM
	 * @param user Usuario de Conexion
	 */
	public void setUser(final String user);
}