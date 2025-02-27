var ws;
//var conteo = 1;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
}

function connect() {
	ws = new WebSocket('ws://localhost:8080/user');
	ws.onmessage = function(data) {
		helloWorld(data.data);
	}
	setConnected(true);
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Websocket is in disconnected state");
}

function sendData() {
	var data = JSON.stringify({
		'user' : $("#user").val()
	})
	ws.send(data);
}

function helloWorld(message) {
	var estado = "" + message;
	if(estado == "Error"){
		document.getElementById("head").innerHTML = "User o password incorrecto:";
	} else if(estado == "Acceso"){
		document.getElementById("head").innerHTML = "BIENVENIDO<br>Ingrese una instruccion para la base de datos:";
	} else {
		document.getElementById("head").innerHTML = "Ingrese una instruccion para la base de datos:";
		$("#helloworldmessage").append("<br>" + message + "");
	}
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendData();
	});
});