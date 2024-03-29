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
 * @version May 1, 2012 9:18:37 PM
 */
package org.schimpf.sql.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Metodos para la obtencion de datos de un sistema de base de datos
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version May 1, 2012 9:18:37 PM
 * @param <SQLConnector> Tipo de conexion a la DB
 * @param <MType> Tipo de Sistema de Base de Datos
 * @param <DType> Tipo de bases de datos
 * @param <SType> Tipo de esquemas
 * @param <TType> Tipo de Tablas
 * @param <CType> Tipo de Columnas
 */
public abstract class DBMSWrapper<SQLConnector extends SQLProcess, MType extends DBMSWrapper<SQLConnector, MType, DType, SType, TType, CType>, DType extends DataBaseWrapper<SQLConnector, MType, DType, SType, TType, CType>, SType extends SchemaWrapper<SQLConnector, MType, DType, SType, TType, CType>, TType extends TableWrapper<SQLConnector, MType, DType, SType, TType, CType>, CType extends ColumnWrapper<SQLConnector, MType, DType, SType, TType, CType>> extends BaseWrapper<SQLConnector> implements Comparable<MType> {
	/**
	 * Lista de bases de datos del sistema
	 * 
	 * @version May 1, 2012 9:26:57 PM
	 */
	private final TreeMap<String, DType>	databases	= new TreeMap<>();

	/**
	 * Nombre del sistema de bases de datos
	 * 
	 * @version May 1, 2012 9:25:36 PM
	 */
	private final String							dbmsName;

	/**
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 9:19:38 PM
	 * @param connector Conector a la DB
	 * @param dbmsName Nombre del sistema de bases de datos
	 */
	protected DBMSWrapper(final SQLConnector connector, final String dbmsName) {
		// enviamos el constructor
		super(connector);
		// almacenamos el nombre del sistema
		this.dbmsName = dbmsName;
	}

	@Override
	public final int compareTo(final MType dbms) {
		// retornamos si es el mismo sistema de base de datos
		return this.getDBMSName().equals(dbms.getDBMSName()) ? 0 : 1;
	}

	/**
	 * Retorna la base de datos
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 2, 2012 10:20:37 AM
	 * @param databaseName Nombre de la base de datos
	 * @throws SQLException Si se produjo un error al cargar la lista de las DBs
	 * @return Base de Datos o Null si no existe
	 */
	public final DType getDataBase(final String databaseName) throws SQLException {
		// cargamos las bases de datos
		this.getDataBases();
		// retornamos la bases de datos
		return this.databases.get(databaseName);
	}

	/**
	 * Retorna las bases de datos del sistema
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 9:27:25 PM
	 * @throws SQLException Si se produjo un error al cargar la lista de las DBs
	 * @return Lista de bases de datos del sistema
	 */
	public final ArrayList<DType> getDataBases() throws SQLException {
		// retornamos las bases de datos
		return this.getDataBases(false);
	}

	/**
	 * Retorna las bases de datos del sistema
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 9:29:30 PM
	 * @param reload True para recargar la lista
	 * @throws SQLException Si se produjo un error al cargar la lista de las DBs
	 * @return Lista de bases de datos del sistema
	 */
	public synchronized final ArrayList<DType> getDataBases(final boolean reload) throws SQLException {
		// verificamos si cargamos la lista
		if (this.databases.size() == 0 || reload) {
			// vaciamos la lista
			this.databases.clear();
			// recorremos las bases de datos
			for (final DType db: this.retrieveDataBases(this.getDBMSName()))
				// agregamos la base de datos
				this.databases.put(db.getDataBaseName(), db);
		}
		// retornamos las bases de datos
		return this.<DType> toArrayList(this.databases);
	}

	/**
	 * Retorna el nombre del sistema
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 9:30:13 PM
	 * @return Nombre del sistema
	 */
	public final String getDBMSName() {
		// retornamos el nombre del sistema
		return this.dbmsName;
	}

	@Override
	public String toString() {
		// retornamos el nombre del sistema
		return this.getDBMSName();
	}

	/**
	 * Retorna la lista de las bases de datos del sistema
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 9:30:43 PM
	 * @param dbmsName Nombre del sistema
	 * @throws SQLException Si se produjo un error al cargar la lista de las DBs
	 * @return Lista de bases de datos del sistema
	 */
	protected abstract ArrayList<DType> retrieveDataBases(String dbmsName) throws SQLException;
}