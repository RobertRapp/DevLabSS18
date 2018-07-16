/**
 * 
 */
var webSocket;
var userName ;


//var server = "ws://localhost:8080/socket";
var server = "ws://35.237.43.191:80/socket";

	// Google SignIn Button wird von User ausgeführt. Benutzername von Google wird global abgelegt
	// und im Navigationsmenü geschrieben
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
	//Benutzer startet die Session.
	//Session ID wird zufällig erstellt.
	//Funktion recognize startet den Watson. 
	//Email, als BenutzerId und SessinID wird an die Websocket gesendet
	//Elemente der Loginseite werden ausgeblendet und die der Hauptansich eingeblendet
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
	//Sobald ein anderer User die Session startet wird diese Funktion bei allen anderen Usern ausgeführt 
	//und der Watson der jeweiligen anderen Usern gestartet
	//Elemente der Loginseite werden ausgeblendet und die der Hauptansich eingeblendet
	function startSessionOfOtherUser(sessionID){
		 $('#startButton').hide();
		 $('#endButton').show();
		 $('#right').show();
		 $('#merkerListe').show();
		 $(".docInformation").css("opacity", 0);
		
		 recognize("localhost:3001", userEmail, sessionID);
	}
	//Sobald ein anderer User die Session beendet wird diese Funktion bei allen anderen Usern ausgeführt 
	//und der Watson der jeweiligen anderen Usern wird beendet
	//Elemente der Hauptseite werden ausgeblendet und die der Startseite eingeblendet
	function endSessionOfOtherUser(){
		$('#endButton').hide();
		 $('#right').hide();
		 $('#startButton').show();
		 $('#merkerListe').hide();
		 $(".docInformation").css("opacity", 0);
		 stopRec();
	}
	//Elemente der Hauptseite werden ausgeblendet und die der Startseite eingeblendet
	//Watson Aufnahme wird beendet
	//SessionID des Usern wird an die Websocket gesendet, damit die Websocket, damit die broadcast Methode 
	//der Websocket nur an die anderen Benutzer sendet
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
	
	//Der Benutzername und die Email wird als globale Variable abgelegt
	//Die Elemente der Loginseite werden ausgeblendet und die der Hauptseite eingeblendet
function connect(googleUser){
	userName = googleUser.getBasicProfile().getName();
	userEmail = googleUser.getBasicProfile().getEmail();
	 $('#user').append("<p>" + userName + "</p>");
	 $('#login').hide();
	 $('#main').show();
	 
	 // Je nach Type der Message werden die jeweiligen Aktionen ausgeführt
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


// Test Button
function clickedButton(){
	webSocket.send(JSON.stringify({
    type: "clickedOnDocument",
	docID: "Dokument 123"
	}));
	
}



  	  
