package com.javainuse.websocket.config;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {
	
	H2Server h2 = new H2Server();
	boolean acceso = false;
	boolean login = false;
	String username = "";
	String password = "";
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException, SQLException {

		String payload = message.getPayload();
		JSONObject jsonObject = new JSONObject(payload);
		
		String[] credenciales = jsonObject.get("user").toString().split("\n");
		
		//Solo se verifica el usuario si no ha ingresado
		if(!acceso) {
			userMatch(credenciales);
			//Si se concede el acceso
			if(login) {
				session.sendMessage(new TextMessage("Acceso"));
			}else {
				session.sendMessage(new TextMessage("Error"));
			}
		}else {
			//Si se da el acceso a la base de datos
			for (int i = 0; i < credenciales.length; i++) {
				session.sendMessage(new TextMessage("->" + credenciales[i]));
				String print = database(credenciales[i], username, password);
				System.out.println(print);
				session.sendMessage(new TextMessage("  " + print));
			}
		}
		
		/*if(jsonObject.get("user").toString().equals("hi")) {
			session.sendMessage(new TextMessage("Hi agente perry"));
		}
		session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));*/
	}
	
	public void userMatch(String[] credenciales) {
		if (credenciales.length==2) {
			if(credenciales[0].equals("usergio") && credenciales[1].equals("1234")) {
				acceso = true;
				login = true;
				username = credenciales[0];
				password = credenciales[1];
			}
		}
	}
	
	public String database(String sql, String username, String password) throws SQLException {
		return h2.connectionToH2(sql,username, password);
	}

}