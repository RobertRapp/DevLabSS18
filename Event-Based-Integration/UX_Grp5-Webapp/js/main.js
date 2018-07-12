'use strict';

var isInitiator;
var clientName;
var clientID;
//window.room = prompt("Wie heißt Du?");
window.clientName = prompt("Wie heißt Du");


var socket = io.connect('http://localhost:8080');


socket.emit('join', clientName);

socket.on('joined', function( clientName) {
//	$("#clientName").text("Ich bin: " & clientName);
	$('#clientName').html(clientName);
	clientID = this.clientId;
	clientName = this.clientName;
});


//old


//if (room !== "") {
//console.log('Message from client: Asking to join room ' + room);
//socket.emit('create or join', room);
//}


socket.on('created', function(room, clientId, clientName) {
  isInitiator = true;
  
});

socket.on('full', function(room) {
  console.log('Message from client: Room ' + room + ' is full :^(');
});

socket.on('ipaddr', function(ipaddr) {
  console.log('Message from client: Server IP address is ' + ipaddr);
});

//socket.on('joined', function(room, clientId, clientName) {
//  isInitiator = false;
// 
//});

socket.on('log', function(array) {
  console.log.apply(console, array);
});

socket.on('message', function(message){
//	var test = message;
//	window.alert(test);
	$('#messages').append($('<li>').text(message));
//	 $("ol").append("<li>" & message & "</li>");
});