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
 * @version Apr 26, 2012 8:21:58 PM
 */
package org.schimpf.sql.pgsql.wrapper;

import org.schimpf.sql.base.wrappers.DataBaseWrapper;
import org.schimpf.sql.pgsql.PostgreSQLProcess;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Datos de la Base de Datos PostgreSQL
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Apr 26, 2012 8:21:58 PM
 */
public final class PGDataBase extends DataBaseWrapper<PostgreSQLProcess, PGSchema, PGTable, PGColumn> {
	/**
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 8:24:15 PM
	 * @param connector Conector a la DB
	 * @param dbName Nombre de la DB
	 */
	public PGDataBase(final PostgreSQLProcess connector, final String dbName) {
		// enviamos el constructor
		super(connector, dbName);
	}

	@Override
	protected ArrayList<PGSchema> retrieveSchemas(final String dataBaseName) throws SQLException {
		// armamos la lista para los esquemas
		ArrayList<PGSchema> schemas = new ArrayList<PGSchema>();
		// ejecutamos el SQL para obtener los esquemas
		this.getSQLConnector().executeSQL("SELECT schema_name FROM information_schema.schemata WHERE schema_name NOT ILIKE 'pg_%' AND schema_name <> 'information_schema' AND catalog_name ILIKE '" + dataBaseName + "' ORDER BY schema_name");
		// recorremos los esquemas
		while (this.getSQLConnector().getResultSet().next())
			// agregamos el esquema
			schemas.add(new PGSchema(this.getSQLConnector(), this.getSQLConnector().getResultSet().getString("schema_name")));
		// retornamos los esquemas
		return schemas;
	}
}