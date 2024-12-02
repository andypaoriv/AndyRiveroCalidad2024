package com.fca.calidad.integracion;

import java.io.File;
import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fca.calidad.dao.DAOUserSQLite;
import com.fca.calidad.model.User.User;
import com.fca.calidad.servicio.UserService;


class UserDAOTest extends DBTestCase {
	
	DAOUserSQLite dao;
	UserService userService;
	
	public UserDAOTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql:\\\\Users\\\\ajer6\\\\Downloads\\\\DB.Browser.for.SQLite-v3.13.1-win32/user.db\"");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"");	
	
	}

	@BeforeEach
	public void setUp() {
		//crear instancia
		dao = new DAOUserSQLite();
		userService =new UserService(dao);
		
		//inicializar la base
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
			IDataSet databaseDataSet =connection.createDataSet();
			ITable actualTable = databaseDataSet.getTable("users");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			fail("fallo");
		}
	}
		protected IDataSet getDataSet() throws Exception {
			return new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/initBD.xml"));
		}
		@Test
		void createUserTest() {
			
			//ejercicio de codigo
			User usuario = userService.createUser("nombre", "email", "password");
			
			//Assertion
			int resultadoEsperado = 1;
			
			IDatabaseConnection connection;
			try {
				connection = getConnection();
				IDataSet databaseDataSet =connection.createDataSet();
				ITable tablaReal = databaseDataSet.getTable("users");
				tablaReal = databaseDataSet.getTable("users");
				String nombreReal = (String) tablaReal.getValue(0, "name");
				String nombreEsperado = "nombre";
				assertEquals(nombreReal, nombreEsperado);
				
				String emailReal = (String) tablaReal.getValue(0, "email");
				String emailEsperado = "email";
				assertEquals(emailReal, emailEsperado);
				
				String passwordReal = (String) tablaReal.getValue(0, "password");
				String passwordEsperado = "password";
				assertEquals(passwordReal, passwordEsperado);
				
				/*String idReal = (String) tablaReal.getValue(0, "id");
				String idEsperado = "id";
				assertEquals(idReal, idEsperado);*/
				
				int resultadoAcutual = tablaReal.getRowCount();
				assertEquals(resultadoEsperado, resultadoAcutual );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("fallo ");
			}
			
		
	}
}
