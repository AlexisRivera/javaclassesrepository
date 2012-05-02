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
 * @version Apr 26, 2012 7:18:00 PM
 */
package org.schimpf.sql.base.wrappers;

import org.schimpf.sql.base.SQLProcess;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Metodos para obtencion de datos de las tablas
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Apr 26, 2012 7:18:00 PM
 * @param <SQLConnector> Tipo de conexion SQL
 * @param <SType> Tipo de esquema
 * @param <TType> Tipo de tabla
 * @param <CType> Tipo de columnas
 */
public abstract class TableWrapper<SQLConnector extends SQLProcess, SType extends SchemaWrapper<SQLConnector, SType, TType, CType>, TType extends TableWrapper<SQLConnector, SType, TType, CType>, CType extends ColumnWrapper<SQLConnector, SType, TType, CType>> extends BaseWrapper<SQLConnector> {
	/**
	 * Columnas de la tablas
	 * 
	 * @version Apr 26, 2012 7:20:49 PM
	 */
	private final HashMap<String, CType>	columns	= new HashMap<String, CType>();

	/**
	 * Esquema al que pertenece la tabla
	 * 
	 * @version May 2, 2012 2:02:12 AM
	 */
	private final SType							schema;

	/**
	 * Nombre fisico de la tabla
	 * 
	 * @version Apr 26, 2012 7:32:31 PM
	 */
	private final String							tableName;

	/**
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:33:20 PM
	 * @param sqlConnector Conector SQL a la DB
	 * @param schema Esquema de la tabla
	 * @param tableName Nombre de la tabla
	 */
	protected TableWrapper(final SQLConnector sqlConnector, final SType schema, final String tableName) {
		// enviamos el conector SQL
		super(sqlConnector);
		// almacenamos el esquema
		this.schema = schema;
		// almacenamos el nombre de la tabla
		this.tableName = tableName;
	}

	/**
	 * Retorna una columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 2, 2012 10:34:48 AM
	 * @param columnName Nombre de la columna
	 * @return Columna o Null si no existe
	 */
	public final CType getColumn(final String columnName) {
		// retornamos la columna
		return this.columns.get(columnName);
	}

	/**
	 * Retorna las columnas de la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:24:25 PM
	 * @return Lista de columnas de la tabla
	 * @throws SQLException Si no se pueden obtener las columnas de la tabla
	 */
	public final ArrayList<CType> getColumns() throws SQLException {
		// retornamos las columnas de la tabla
		return this.getColumns(false);
	}

	/**
	 * Retorna las columnas de la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:26:02 PM
	 * @param reload True para recargar las columnas de la tabla
	 * @return Lista de columnas de la tabla
	 * @throws SQLException Si no se pueden obtener las columnas de la tabla
	 */
	public final ArrayList<CType> getColumns(final boolean reload) throws SQLException {
		// verificamos si ya cargamos las columnas
		if (this.columns.size() == 0 || reload) {
			// vaciamos la lista
			this.columns.clear();
			// recorremos las columnas
			for (CType column: this.retrieveColumns(this.getTableName()))
				// agregamos la columna de la tabla
				this.columns.put(column.getColumnName(), column);
		}
		// retornamos las columnas
		return this.<CType> toArrayList(this.columns);
	}

	/**
	 * Retorna el esquema al que pertenece la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 2, 2012 2:02:30 AM
	 * @return Esquema de la tabla
	 */
	public final SType getSchema() {
		// retornamos el esquema
		return this.schema;
	}

	/**
	 * Retorna el nombre de la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:33:44 PM
	 * @return Nombre de la tabla
	 */
	public final String getTableName() {
		// retornamos el nombre de la tabla
		return this.tableName;
	}

	/**
	 * Carga las columnas de la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:26:44 PM
	 * @param tableName Nombre de la tabla actual
	 * @return Lista de columnas de la tabla
	 * @throws SQLException Si se produce un error al obtener la lista de las columnas
	 */
	protected abstract ArrayList<CType> retrieveColumns(String tableName) throws SQLException;
}