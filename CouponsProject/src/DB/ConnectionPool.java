package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Utils.DateUtils;

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

		while (this.connections.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
		Iterator<Connection> iterator = this.connections.iterator();
		while (iterator.hasNext()) {
			System.out.println("222555555555555555555555555555555555");
			try {
				iterator.next().close();
			} catch (SQLException e) {
				throw new Exception("Connections: Close All Connection: Error!");
			}
		}
	}
}