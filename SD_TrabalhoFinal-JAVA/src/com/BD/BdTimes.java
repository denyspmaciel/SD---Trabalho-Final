package com.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Connection.ConnectionFactory;
import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;

public class BdTimes {

	private Connection connection;

	public String AddTime(Time time) {

		String sql = "INSERT INTO Time (nome, data, titulos) VALUES (?, ?, ?)";
		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, time.getNome());
			stmt.setString(2, time.getData());
			stmt.setString(3, time.getTitulos());

			int affectedRows = stmt.executeUpdate();
			stmt.close();
			if(affectedRows > 0)
				return "true";
			return "false";

		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return "false";
	}

	public Times List() {
		String sql = "SELECT * FROM Time";
		Times.Builder times = Times.newBuilder();

		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String data = rs.getString("data");
				String titulos = rs.getString("titulos");

				Time.Builder novo = Time.newBuilder();
				novo.setId(id);
				novo.setNome(nome);
				novo.setData(data);
				novo.setTitulos(titulos);

				times.addTime(novo.build());
			}
			stmt.close();
		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return times.build();
	}

	public String RemoveTime(int id) {
		String sql = "DELETE FROM Time where id = ?";
		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);

			int affectedRows = stmt.executeUpdate();
			stmt.close();

			if(affectedRows > 0)
				return "true";
			return "false";
		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return "false";
	}

	public String AddTitulo(Time time) {
		String sql = "UPDATE Time SET nome = ?, data = ?, titulos = ? WHERE id = ?";
		this.connection = new ConnectionFactory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, time.getNome());
			stmt.setString(2, time.getData());
			stmt.setString(3, time.getTitulos());
			stmt.setInt(4, time.getId());
			int affectedRows = stmt.executeUpdate();
			stmt.close();
			if(affectedRows > 0)
				return "true";
			return "false";

		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return "false";
	}

	public Time BuscarTimeID(int id) {
		String sql = "SELECT * FROM Time WHERE id = ?";
		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();
			rs.next();

			Time.Builder time = Time.newBuilder();
			time.setId(id);
			time.setNome(rs.getString("nome"));
			time.setData(rs.getString("data"));
			time.setTitulos(rs.getString("titulos"));

			stmt.close();

			return time.build();
		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Time BuscarTimeNome(String nome) {
		String sql = "SELECT * FROM Time WHERE nome = ?";
		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, nome);

			ResultSet rs = stmt.executeQuery();

			//rs.next();
			
			if (!rs.next()) {
				Time.Builder lixo = Time.newBuilder();
				lixo.setNome("lixo");
				return lixo.build();
			
			}else {
			
			
			int id = Integer.parseInt(rs.getString("id"));
			
			Time novo = BuscarTimeID(id);

			stmt.close();
			return novo;
			}
			
			
		}

		catch(SQLException e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				this.connection.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
