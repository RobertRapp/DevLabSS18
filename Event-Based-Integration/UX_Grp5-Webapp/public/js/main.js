/**
 * 
 */
var socket = io.connect('http://localhost:3000');
var userName = 'leer';

// Login
	
	$( "#loginButton" ).click(function() {
		userName = $('#inputUsername').val();
		 $('#user').append("<p>" + userName + "</p>");
		 $('#login').hide();
		 $('#main').show();
	 
	  socket.emit('add user', userName);	 
	return false;		
	});

  	  
