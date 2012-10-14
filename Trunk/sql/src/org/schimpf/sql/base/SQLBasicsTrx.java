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
 * @version Oct 13, 2012 9:33:23 PM
 */
package org.schimpf.sql.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Metodos basicos para ejecucion de consultas en transacciones
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Oct 13, 2012 9:33:23 PM
 */
public interface SQLBasicsTrx {
	/**
	 * Ejecuta una consulta SQL en una transaccion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 13, 2012 9:44:08 PM
	 * @param query Consulta SQL
	 * @param trxName Nombre de la transaccion
	 * @return True si la consulta se ejecuto exitosamente
	 */
	public boolean executeQuery(final PreparedStatement query, final String trxName);

	/**
	 * Ejecuta una consulta SQL en una transaccion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 16, 2011 12:24:02 AM
	 * @param trxName Nombre de la transaccion
	 * @return True si la consulta se ejecuto exitosamente
	 */
	public boolean executeQuery(String trxName);

	/**
	 * Ejecuta una consulta SQL de actualizacion en una transaccion
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 13, 2012 9:45:07 PM
	 * @param query Consulta SQL
	 * @param trxName Nombre de la transaccion
	 * @return Cantidad de registros afectados, -1 si dio error
	 */
	public int executeUpdate(final PreparedStatement query, final String trxName);

	/**
	 * Ejecuta una consulta SQL de actualizacion en una transaccion
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Mar 15, 2012 13:51:02 AM
	 * @param trxName Nombre de la transaccion
	 * @return Numero de actualizaciones, -1 si dio error
	 */
	public int executeUpdate(String trxName);

	/**
	 * Retorna el resultado de la consulta ejecutada
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 16, 2011 12:24:23 AM
	 * @param trxName Nombre de la transaccion
	 * @return ResultSet de la consulta ejecutada
	 */
	public ResultSet getResultSet(String trxName);

	/**
	 * Retorna si existe mas registros
	 * 
	 * @author Hermann D. Schimpf
	 * @author SCHIMPF - Sistemas de Informacion y Gestion
	 * @version Apr 16, 2011 1:26:48 AM
	 * @param trxName Nombre de la transaccion
	 * @return True si hay mas registros
	 */
	public boolean hasNext(String trxName);
}