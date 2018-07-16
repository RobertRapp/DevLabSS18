/**
 * 
 */
var webSocket;
var userName ;


//var server = "ws://localhost:8080/socket";
var server = "ws://35.237.43.191:80/socket";

	$( "#loginButton" ).click(function() {
//		userName = $('#inputUsername').val();
		userName= googleUser.getBasicProfile().getName();
		userEmail = googleUser.getBasicProfile().getEmail();
		 $('#user').append("<p>" + googleUser.getBasicProfile().getName() + "</p>");
		 $('#login').hide();
		 $('#main').show();
//		 drawDiagram();
		 
		 return false;	
	});
	
	$( "#startButton" ).click(function() {

		 $('#startButton').hide();
		 $('#endButton').show();
		 $('#right').show();
		 $('#merkerListe').show();
		 sessionID =  Math.random().toString(36).substr(2, 9)
		 recognize("localhost:3001", userEmail , sessionID);
		 webSocket.send(JSON.stringify({
	            type: "sessionStart",
	            sessionID: sessionID,
	            userID: userEmail
	        }));

		 return false;	
	});
	
	function startSessionOfOtherUser(sessionID){
		 $('#startButton').hide();
		 $('#endButton').show();
		 $('#right').show();
		 $('#merkerListe').show();
		 $(".docInformation").css("opacity", 0);
		
		 recognize("localhost:3001", userEmail, sessionID);
	}
	
	function endSessionOfOtherUser(){
		$('#endButton').hide();
		 $('#right').hide();
		 $('#startButton').show();
		 $('#merkerListe').hide();
		 $(".docInformation").css("opacity", 0);
		 stopRec();
	}
	
	$( "#endButton" ).click(function() {
		
		 $('#endButton').hide();
		 $('#right').hide();
		 $('#startButton').show();
		 $('#merkerListe').hide();
		 $(".docInformation").css("opacity", 0);
		 stopRec();
		 webSocket.send(JSON.stringify({
	            type: "sessionEnd",
	            sessionID: sessionID,
	            userID: userEmail
	        }));
		 return false;	
	});
	

function connect(googleUser){
	userName = googleUser.getBasicProfile().getName();
	userEmail = googleUser.getBasicProfile().getEmail();
	 $('#user').append("<p>" + userName + "</p>");
	 $('#login').hide();
	 $('#main').show();
	 
	 
webSocket = new WebSocket(server);
	 //webSocket = new WebSocket("ws://localhost:8080/socket");

webSocket.onmessage = (msg) => {

	requestJson = JSON.parse(msg.data);
	switch (requestJson.type){
	case "refreshUserList":
	$("#contacts").empty();
	$.each(requestJson.users ,function(key, value) {
		        $("#contacts").append("<li class='list-group-item'>" + value + "</li>") ;
		    }
		) ;
	break;
	case "clickedOnDocument":
		 $("#test").append("<li>" + requestJson.docID + "</li>") ;
		 
	break;	
	case "newDocProposal":
		if (typeof svg == "svg is not defined") {
			drawDiagram(requestJson.docProposal);
			 
			}
			else{
				if(hoverStopDraw == false && FilterStopDraw == false){
					$("svg").first().remove();
					drawDiagram(requestJson.docProposal);
				}
			}			 
	break;
	case "sessionStarted":
//		alert("SessionID: gestartet   "+ requestJson.sessionID);
		sessionID = requestJson.sessionID;
		startSessionOfOtherUser(requestJson.sessionID);
	break;	
	case "sessionEnded":
		endSessionOfOtherUser();
//		alert("session beendet");
		break;	
	}
}


webSocket.onopen = () => webSocket.send(JSON.stringify({
    type: "join",
	username: userName
}));
}



function clickedButton(){
	webSocket.send(JSON.stringify({
    type: "clickedOnDocument",
	docID: "Dokument 123"
	}));
	
}



  	  
