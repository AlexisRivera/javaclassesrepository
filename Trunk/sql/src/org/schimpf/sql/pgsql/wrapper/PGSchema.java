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
 * @version Apr 27, 2012 10:45:42 AM
 */
package org.schimpf.sql.pgsql.wrapper;

import org.schimpf.sql.base.SchemaWrapper;
import org.schimpf.sql.pgsql.PostgreSQLProcess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Datos del esquema PostgreSQL
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Apr 27, 2012 10:45:42 AM
 */
public final class PGSchema extends SchemaWrapper<PostgreSQLProcess, PGDBMS, PGDataBase, PGSchema, PGTable, PGColumn> {
	/**
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 27, 2012 10:47:16 AM
	 * @param dataBase Base de Datos a la que pertenece el esquema
	 * @param schemaName Nombre del esquema
	 */
	public PGSchema(final PGDataBase dataBase, final String schemaName) {
		// enviamos el constructor
		super(dataBase, schemaName);
	}

	@Override
	protected ArrayList<PGTable> retrieveTables(final String schemaName) throws SQLException {
		// armamos la lista de las tablas
		final ArrayList<PGTable> tables = new ArrayList<>();
		// iniciamos una transaccion
		final String trx = this.getSQLConnector().startTransaction();
		// ejecutamos el SQL para obtener las tablas
		this.getSQLConnector().executeQuery(this.getSQLConnector().prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog','information_schema') AND table_type = 'BASE TABLE' AND table_schema ILIKE '" + schemaName + "' ORDER BY table_name", trx), trx);
		// obtenemos el resultset
		final ResultSet rs = this.getSQLConnector().getResultSet(trx);
		// recorremos las tablas
		while (rs != null && rs.next())
			// agregamos una tabla
			tables.add(new PGTable(this, rs.getString(1)));
		// cancelamos la transaccion
		this.getSQLConnector().rollbackTransaction(trx);
		// retornamos las tablas
		return tables;
	}
}