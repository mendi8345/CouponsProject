import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

	private static ConnectionPool instance;
	private static int maxConnections = 10;

	private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);

	private ConnectionPool() throws Exception {
		try {
			Class.forName(DateUtils.getDBUrl());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static ConnectionPool getInstance() throws Exception {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	public synchronized Connection getConnection() throws Exception {
		while (this.connections.size() == 0) {
			try {
				this.wait();
			} catch (Exception e) {
				throw new Exception("connection failed");
			}
		}
		Iterator<Connection> iterator = this.connections.iterator();
		Connection con = iterator.next();
		iterator.remove();
		return con;
	}

	public synchronized void returnConnection(Connection con) throws Exception {
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			throw new Exception("ERROR! Return Connection Properly Failed!");
		}
		this.connections.add(con);
		this.notify();
	}

	public synchronized void closeAllConnections(Connection connection) throws Exception {

		while (this.connections.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
		Iterator<Connection> iterator = this.connections.iterator();
		while (iterator.hasNext()) {
			try {
				iterator.next().close();
			} catch (SQLException e) {
				throw new Exception("Connections: Close All Connection: Error!");
			}
		}
	}
}
