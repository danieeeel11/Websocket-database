package com.javainuse.websocket.config;
import java.sql.SQLException;
//import org.h2.util.Tool;
import org.h2.tools.Server;

public class ServerH2 {
	public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer(args).start();
    }
}
