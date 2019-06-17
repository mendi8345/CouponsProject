package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Utils.DateUtils;

/**
 * @Connectionpool Singleton that has limited numbers of connections that found
 *                 in the connection queue. Contains the following methods:
 *                 getConnection(); returnConnection(); closeConnection();
 */

public class ConnectionPool {

	private static ConnectionPool instance;
	private static int maxConnections = 10;
	private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);

	private ConnectionPool() throws Exception {

		Connection con = DriverManager.getConnection(DateUtils.getDBUrl());
		while (this.connections.size() < maxConnections) {
			con = DriverManager.getConnection(DateUtils.getDBUrl());
			this.connections.offer(con);
		}
	}

	public static ConnectionPool getInstance() throws Exception {
		if (instance == null) {

			instance = new ConnectionPool();
		}
		return instance;
	}

	public synchronized Connection getConnection() throws Exception {

		try {
			Connection connection = this.connections.poll();
			connection.setAutoCommit(true);
			return connection;
		} catch (Exception e) {
			throw new Exception("connection failed");
		}
	}

	public synchronized void returnConnection(Connection con) throws Exception {
		this.connections.offer(con);

	}

	public synchronized void closeAllConnections() throws Exception {
		Connection con;
		int num = 0;
		while (this.connections.peek() != null) {
			con = this.connections.poll();

			try {
				con.close();
				System.out.println("Closes connection number " + num++);
			} catch (SQLException e) {
				throw new Exception(e);
			}
		}
		System.out.println("All existing connections have been successfully closed");

	}

}