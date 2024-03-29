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
 * @version Apr 26, 2012 7:34:56 PM
 */
package org.schimpf.sql.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Metodos para obtencion de datos de columnas
 * 
 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
 * @author <B>Schimpf.NET</B>
 * @version Apr 26, 2012 7:34:56 PM
 * @param <SQLConnector> Conector a la DB
 * @param <MType> Tipo de Sistema de Base de Datos
 * @param <DType> Tipo de Base de Datos
 * @param <SType> Tipo de esquema
 * @param <TType> Tipo de tabla
 * @param <CType> Tipo de columna
 */
public abstract class ColumnWrapper<SQLConnector extends SQLProcess, MType extends DBMSWrapper<SQLConnector, MType, DType, SType, TType, CType>, DType extends DataBaseWrapper<SQLConnector, MType, DType, SType, TType, CType>, SType extends SchemaWrapper<SQLConnector, MType, DType, SType, TType, CType>, TType extends TableWrapper<SQLConnector, MType, DType, SType, TType, CType>, CType extends ColumnWrapper<SQLConnector, MType, DType, SType, TType, CType>> extends BaseWrapper<SQLConnector> implements Comparable<CType> {
	/**
	 * Nombre fisico de la columna
	 * 
	 * @version Apr 26, 2012 7:37:18 PM
	 */
	private final String		columnName;

	/**
	 * Posicion de la columna
	 * 
	 * @version Oct 4, 2012 9:16:54 AM
	 */
	private final Integer	columnPosition;

	/**
	 * Clase de la columna
	 * 
	 * @version Oct 10, 2012 2:23:15 PM
	 */
	private Class<?>			dataClass;

	/**
	 * Tamaño maximo del campo
	 * 
	 * @version Oct 13, 2012 11:18:11 PM
	 */
	private Integer			dataLength;

	/**
	 * Tamaño de precision del campo
	 * 
	 * @version Nov 19, 2012 6:12:05 PM
	 */
	private Integer			dataPrecision;

	/**
	 * Tipo de dato de la columna
	 * 
	 * @version May 1, 2012 10:53:13 PM
	 */
	private String				dataType;

	/**
	 * Valor por defecto del campo
	 * 
	 * @version May 1, 2012 11:01:58 PM
	 */
	private String				defaultValue;

	/**
	 * Bandera de columna con incremento automatico
	 * 
	 * @version Oct 4, 2012 11:20:50 AM
	 */
	private Boolean			isAutoIncrement;

	/**
	 * Bandera de columna nuleable
	 * 
	 * @version May 1, 2012 11:00:40 PM
	 */
	private Boolean			isNullable;

	/**
	 * Bandera para identificar si la columna es clave primaria
	 * 
	 * @version May 2, 2012 12:08:53 AM
	 */
	private Boolean			isPrimaryKey;

	/**
	 * Bandera para identificar si la columna es de valor unico
	 * 
	 * @version May 2, 2012 1:53:48 AM
	 */
	private Boolean			isUnique;

	/**
	 * Tabla a la cual pertenece la columna
	 * 
	 * @version May 2, 2012 12:31:54 AM
	 */
	private final TType		table;

	/**
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:37:45 PM
	 * @param table Tabla a la que pertenece la columna
	 * @param columnName Nombre de la columna en la DB
	 * @param columnPosition Posicion fisica de la columna
	 */
	protected ColumnWrapper(final TType table, final String columnName, final Integer columnPosition) {
		// enviamos el constructor
		super(table.getSQLConnector());
		// almacenamos la tabla
		this.table = table;
		// almacenamos el nombre de la columna
		this.columnName = columnName;
		// almacenamos la pocision
		this.columnPosition = columnPosition;
	}

	@Override
	public int compareTo(final CType column) {
		// retornamos si es la misma columna
		return this.getTable().compareTo(column.getTable()) == 0 && this.getColumnName().equals(column.getColumnName()) ? 0 : 1;
	}

	/**
	 * Retorna el nombre de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Apr 26, 2012 7:38:09 PM
	 * @return Nombre de la columna
	 */
	public final String getColumnName() {
		// retornamos el nombre de la columna
		return this.columnName;
	}

	/**
	 * Retorna la posicion fisica de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Oct 4, 2012 9:34:43 AM
	 * @return Posicion Fisica de la columna
	 */
	public final Integer getColumnPosition() {
		// retornamos la posicion de la columna
		return this.columnPosition;
	}

	/**
	 * Retorna la clase de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Oct 10, 2012 2:22:35 PM
	 * @return Clase de la columna
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 */
	public final Class<?> getDataClass() throws SQLException {
		// verificamos si tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos la clase de la columna
		return this.dataClass;
	}

	/**
	 * Retorna el tamaño maximo de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version Oct 13, 2012 11:19:49 PM
	 * @return Tamaño maximo de la columna
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 */
	public final Integer getDataLength() throws SQLException {
		// verificamos si tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos el tamaño
		return this.dataLength;
	}

	/**
	 * Retorna el la precision del tamaño de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Nov 19, 2012 6:12:22 PM
	 * @return Tamaño de la precision de la columna
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 */
	public final Integer getDataPrecision() throws SQLException {
		// verificamos si tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos el tamaño
		return this.dataPrecision;
	}

	/**
	 * Retorna el tipo de dato de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 10:53:59 PM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 * @return Tipo de Dato
	 */
	public final String getDataType() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos el tipo de columna
		return this.dataType;
	}

	/**
	 * Retorna el valor por defecto del campo
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 11:02:26 PM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 * @return Valor por defecto del campo
	 */
	public final String getDefaultValue() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos el valor por defecto
		return this.defaultValue;
	}

	/**
	 * Retorna la tabla de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 2, 2012 12:35:13 AM
	 * @return Tabla
	 */
	public final TType getTable() {
		// retornamos la tabla
		return this.table;
	}

	/**
	 * Retorna si la columna es auto incrementable
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Oct 4, 2012 11:21:14 AM
	 * @return True si es autoincrementable
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 */
	public final Boolean isAutoIncrement() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos si es nulo
		return this.isAutoIncrement;
	}

	/**
	 * Retorna si el campo permite valores nulos
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 11:01:23 PM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 * @return True si permite valores nulos
	 */
	public final Boolean isNullable() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos si es nulo
		return this.isNullable;
	}

	/**
	 * Retorna si la columna es clave primaria de la tabla
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 2, 2012 12:09:57 AM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 * @return True si es clave primaria
	 */
	public final Boolean isPrimaryKey() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos si es clave primaria
		return this.isPrimaryKey;
	}

	/**
	 * Retorna si la columna debe tener valores unicos
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 10:59:18 PM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 * @return True si debe tener valores unicos
	 */
	public final Boolean isUnique() throws SQLException {
		// verificamos si no tenemos valor
		if (this.dataType == null)
			// cargamos los datos
			this.loadMetaData();
		// retornamos el tamaño de la columna
		return this.isUnique;
	}

	/**
	 * Compara fisicamente la columna actual con la columna especificada
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>HDS Solutions</B> - <FONT style="font-style:italic;">Soluci&oacute;nes Inform&aacute;ticas</FONT>
	 * @version Nov 19, 2012 6:50:14 PM
	 * @param column Columna a comparar
	 * @return True si la columna especificada es igual a la columna actual
	 * @throws SQLException Si se produjo un error al validar la columna
	 */
	public boolean physicalEquals(final CType column) throws SQLException {
		// verificamos si la columna es nula
		if (column == null)
			// retornamos false
			return false;
		// verificamos si la columna esta en la misma posicion
		if (!this.getColumnPosition().equals(column.getColumnPosition()))
			// retornamos false
			return false;
		// verificamos si las columna son de la misma clase
		if (!this.getDataClass().equals(column.getDataClass()))
			// retornamos false
			return false;
		// verificamos si las columna son del mismo tamaño
		if (!this.getDataLength().equals(column.getDataLength()))
			// retornamos false
			return false;
		// verificamos si las columna son del mismo tamaño
		if (this.getDataPrecision() != null && !this.getDataPrecision().equals(column.getDataPrecision()))
			// retornamos false
			return false;
		// verificamos si las columna son del mismo tipo
		if (!this.getDataType().equals(column.getDataType()))
			// retornamos false
			return false;
		// verificamos si alguna de las columas tiene valor por defecto
		if (this.getDefaultValue() != null || column.getDefaultValue() != null) {
			// verificamos si alguna columna tiene valor por defecto y la otra no
			if (this.getDefaultValue() != null && column.getDefaultValue() == null || this.getDefaultValue() == null && column.getDefaultValue() != null)
				// retornamos false
				return false;
			// verificamos si las columna 2 tienen el valor por defecto y son diferentes
			if (this.getDefaultValue() != null && column.getDefaultValue() != null && !this.getDefaultValue().equals(column.getDefaultValue()))
				// retornamos false
				return false;
		}
		// retornamos true
		return true;
	}

	@Override
	public final String toString() {
		try {
			// retornamos la definicion de la columna
			return this.getColumnName() + " " + this.getDataType() + (this.isUnique() && !this.isPrimaryKey() ? " UNIQUE" : "") + (this.isNullable() != null && this.isNullable() ? "" : " NOT") + " NULL" + (this.isPrimaryKey() ? " PRIMARY KEY" : "") + (this.isAutoIncrement() ? " AUTO INCREMENT" : "") + (this.getDefaultValue() != null ? " DEFAULT " + this.getDefaultValue() : "");
		} catch (final SQLException e) {}
		// retornamos el nombre de la columna
		return this.getColumnName();
	}

	/**
	 * Carga los metadatos de la columna
	 * 
	 * @author <FONT style='color:#55A; font-size:12px; font-weight:bold;'>Hermann D. Schimpf</FONT>
	 * @author <B>SCHIMPF</B> - <FONT style="font-style:italic;">Sistemas de Informaci&oacute;n y Gesti&oacute;n</FONT>
	 * @author <B>Schimpf.NET</B>
	 * @version May 1, 2012 10:49:12 PM
	 * @throws SQLException Si se produjo un error al cargar los metadatos
	 */
	private synchronized void loadMetaData() throws SQLException {
		// iniciamos una transaccion
		final String trx = this.getSQLConnector().startTransaction();
		// recorremos los metadatos
		if (this.getSQLConnector().executeQuery(this.getSQLConnector().prepareStatement("SELECT * FROM " + this.getTable().getSchema().getSchemaName() + "." + this.getTable().getTableName() + " LIMIT 1", trx), trx)) {
			// obtenemos el resultset
			final ResultSetMetaData rs = this.getSQLConnector().getResultSet(trx).getMetaData();
			// almacenamos el tipo de dato
			this.dataType = rs.getColumnTypeName(this.getColumnPosition());
			try {
				// almacenamos la clase
				this.dataClass = Class.forName(rs.getColumnClassName(this.getColumnPosition()));
			} catch (final ClassNotFoundException ignored) {}
			// almacenamos si es clave primaria
			this.isPrimaryKey = this.getTable().getPrimaryKeys().contains(this);
			// almacenamos si es unique
			this.isUnique = this.getTable().getUniqueColumns().contains(this);
			// obtenemos mas datos
			final ResultSet moreData = this.getMetadata().getColumns(this.getTable().getSchema().getDataBase().getDataBaseName(), null, this.getTable().getTableName(), this.getColumnName());
			// verificamos si tenemos datos
			if (moreData.next()) {
				// almacenamos el tamaño del campo
				this.dataLength = moreData.getInt(7);
				// almacenamos el tamaño del campo
				this.dataPrecision = moreData.getInt(9);
				// almacenamos si es nullable
				this.isNullable = moreData.getInt(11) == java.sql.DatabaseMetaData.columnNullable;
				// almacenamos el valor por defecto
				this.defaultValue = moreData.getString(13);
				// almacenamos el valor de auto increment
				this.isAutoIncrement = moreData.getMetaData().getColumnCount() >= 23 ? moreData.getString(23).equals("YES") : false;
				// verificamos si la posicion es correcta
				if (this.getColumnPosition() != moreData.getInt(17))
					// salimos con una excepcion
					throw new SQLException("Column ordinal position error! Loaded: " + this.getColumnPosition() + ", Found on: " + moreData.getInt(17));
			}
		}
		// cancelamos la transaccion
		this.getSQLConnector().rollbackTransaction(trx);
	}
}