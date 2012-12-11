package opgave2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CaosApp {

	private String username = "root";
	private String password = "";

	// MySql
	private String dbDriver = "com.mysql.jdbc.Driver";
	private String dbName = "jdbc:mysql://localhost:3306/Carletti";

	// MsSql
	// private String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// private String dbName =
	// "com.microsoft.sqlserver.jdbc.SQLServerDriver:jdbc:sqlserver://localhost:3306\\SQLEXPRESS;databaseName=Carletti";

	public static void main(String[] args) {
		new CaosApp();
	}

	public CaosApp() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String input;
		try {
			System.out.println("PunktA: \nIndtast vare id: ");
			input = reader.readLine();
			punktA(input);

			System.out.println("PunktB: \nTryk enter for at gå videre!");
			input = reader.readLine();
			punktB();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void punktA(String id){
		Connection con;
		try {
			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbName, username, password);

			Statement stmt = con.createStatement();
			String query = "Set Transaction Isolation level SERIALIZABLE " +
					"DECLARE @Punkta VARCHAR(20); " +
					"SELECT @Punkta = 'ny delbehandling';" +
					"BEGIN TRANSACTION @Punkta;" +
					"USE Carletti;" +
					"DECLARE @Ny_ID int;" +
					"SELECT @Ny_ID = (SELECT MAX(ID) From dbo.TOERRETID)+1;" +
					"DECLARE @Idag int;" +
					"SELECT @Idag = (SELECT DAGE From dbo.MELLEMVARELAGER);" +
					"declare @NæsteDelbehandling varchar (255);" +
					"select @NæsteDelbehandling = (select top 1 IkkeAfsluttet.NAVN From(" +
					"SELECT d.navn FROM DELBEHANDLING d, BEHANDLING b, BEHANDLING_DELBEHANDLING bxd, MELLEMVARE m, PRODUKTTYPE p" +
					"where d.NAVN = bxd.delbehandlinger_NAVN" +
					"and b.navn=bxd.Behandling_NAVN" +
					"and p.BEHANDLING_NAVN=b.NAVN" +
					"and m.PRODUKTTYPE_NAVN=p.NAVN" +
					"and m.ID="+ "'"+id+"'" +
					"except" +
					"SELECT d.navn FROM DELBEHANDLING d, MELLEMVARE m, TOERRETID t, MELLEMVARE_TOERRETID mxt" +
					"where d.NAVN = t.DELBEHANDLING_NAVN" +
					"and t.ID = mxt.toerretider_ID" +
					"and mxt.Mellemvare_ID=m.ID" +
					"and m.ID=" + "'"+id+"'" +") IkkeAfsluttet order by IkkeAfsluttet.NAVN desc)" +
					"Insert Into dbo.TOERRETID (ID, TID, DELBEHANDLING_NAVN)" +
					"values (@Ny_ID, @Idag, @NæsteDelbehandling)" +
					"Insert Into dbo.MELLEMVARE_TOERRETID(Mellemvare_ID, toerretider_ID)" +
					"values (" + "'"+id+"'" +" ,@Ny_ID)" +
					"COMMIT TRANSACTION Punkta;" +
					"GO";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String s = rs.getString(1);
				String s1 = rs.getString(2);
				System.out.println("id: " + s + "\t produkttype: " + s1);
			}
			
		} catch (SQLException e) {
			System.out.println("Fejl: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void punktB() {

	}
}
