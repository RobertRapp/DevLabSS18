window.addEventListener('load', () => {
  // Chat platform
  const chatTemplate = Handlebars.compile($('#chat-template').html());
  const chatContentTemplate = Handlebars.compile($('#chat-content-template').html());
  

  const chatEl = $('#chat');
  const formEl = $('.form');
  const messages = [];
  let username;

  // Local Video
  const localImageEl = $('#local-image');
  const localVideoEl = $('#local-video');

  // Remote Videos
  const remoteVideoTemplate = Handlebars.compile($('#remote-video-template').html());
  const remoteVideosEl = $('#remote-videos');
  let remoteVideosCount = 0;

  
  //TEST

 
  
  //Teilnehmer
  const teilnehmerTemplate = Handlebars.compile($('#teilnehmer-template').html());
  const teilnehmerListe = $('#teilnehmer');
  var teilnehmerContext = {
		  userName: "test"
  }
  
  const teilnehmerHTML = teilnehmerTemplate(teilnehmerContext);


  
  
  // Hide cameras until they are initialized
  localVideoEl.hide();

  // Add validation rules to Create/Join Room Form
  formEl.form({
    fields: {
      roomName: 'empty',
      username: 'empty',
    },
  });

  // create our webrtc connection
  const webrtc = new SimpleWebRTC({
    // the id/element dom element that will hold "our" video
    localVideoEl: 'local-video',
    // the id/element dom element that will hold remote videos
    remoteVideosEl: 'remote-videos',
    // immediately ask for camera access
    autoRequestMedia: true,
    debug: false,
    detectSpeakingEvents: true,
    autoAdjustMic: true,
    nick: 'Patrick',
    url: 'localhost:3080'
	  
  });
  
  
  // We got access to local camera
  webrtc.on('localStream', () => {
    localImageEl.hide();
    localVideoEl.show();
  });

  // Remote video was added
  webrtc.on('videoAdded', (video, peer) => {
    // eslint-disable-next-line no-console
    const id = webrtc.getDomId(peer);
    const html = remoteVideoTemplate('remoteVideoDIV');
   
    
    
//    if (remoteVideosCount === 0) {
      remoteVideosEl.html(html);
//    } else {
//      remoteVideosEl.append(html);
//    }
     
    $(`#remoteVideoDIV`).html(video);
    $(`#remoteVideoDIV`).addClass('ui image medium'); // Make video element responsive
//    remoteVideosCount += 1;
    var vol = document.createElement('meter');
    vol.id = 'volume_' + peer.id;
    vol.className = 'volume';
    vol.min = -45;
    vol.max = -20;
    vol.low = -40;
    vol.high = -25;
    $(`#remoteVideoDIV`).append(vol);
    // add muted and paused elements
    var muted = document.createElement('span');
    vol.className = 'muted';
    $(`#remoteVideoDIV`).append(muted);

    var muted = document.createElement('span');
    vol.className = 'muted';
    $(`#remoteVideoDIV`).append(muted);
    
    
    
//    if (peer && peer.pc) {
//        var connstate = document.createElement('div');
//        connstate.className = 'connectionstate';
//        $(`#remoteVideoDIV`).append(connstate);
//        peer.pc.on('iceConnectionStateChange', function (event) {
//            switch (peer.pc.iceConnectionState) {
//            case 'checking':
//            	$(`#remoteVideoDIV`).text() = 'Connecting to peer...';
//                break;
//            case 'connected':
//            case 'completed': // on caller side
//            	$(`#remoteVideoDIV`).text() = 'Connection established.';
//                break;
//            case 'disconnected':
//            	$(`#remoteVideoDIV`).text() = 'Disconnected.';
//                break;
//            case 'failed':
//                break;
//            case 'closed':
//            	$(`#remoteVideoDIV`).text() = 'Connection closed.';
//                break;
//            }
//        });
//    }
  });

  // Update Chat Messages
  const updateChatMessages = () => {
    const html = chatContentTemplate({ messages });
    const chatContentEl = $('#chat-content');
    chatContentEl.html(html); 
    // automatically scroll downwards
    const scrollHeight = chatContentEl.prop('scrollHeight');
    chatContentEl.animate({ scrollTop: scrollHeight }, 'slow');
  };

  // Post Local Message
  const postMessage = (message) => {
    const chatMessage = {
      username,
      message,
      postedOn: new Date().toLocaleString('en-GB'),
    };
    // Send to all peers
    webrtc.sendToAll('chat', chatMessage);
    // Update messages locally
    messages.push(chatMessage);
    $('#post-message').val('');
    updateChatMessages();
  };

  // Display Chat Interface
  const showChatRoom = (room) => {
    formEl.hide();
    const html = chatTemplate({ room });
    chatEl.html(html);
    const postForm = $('form');
    postForm.form({
      message: 'empty',
    });
    $('#post-btn').on('click', () => {
      const message = $('#post-message').val();
      postMessage(message);
    });
    $('#post-message').on('keyup', (event) => {
      if (event.keyCode === 13) {
        const message = $('#post-message').val();
        postMessage(message);
      }
    });
  };

  // Register new Chat Room
  const createRoom = (roomName) => {
    // eslint-disable-next-line no-console
    console.info(`Creating new room: ${roomName}`);
    webrtc.createRoom(roomName, (err, name) => {
      formEl.form('clear');
      showChatRoom(name);
      postMessage(`${username} created chatroom`);
    });
  };

  // Join existing Chat Room
  const joinRoom = (roomName) => {
	  teilnehmerListe.append(teilnehmerHTML);
    // eslint-disable-next-line no-console
    console.log(`Joining Room: ${roomName}`);
    webrtc.joinRoom(roomName);
    showChatRoom(roomName);
  
//    postMessage(`${username} joined chatroom`);

  };
//Empfange Information welcher User online
 webrtc.connection.on('joined', (data) => {
	 
	 window.alert(data)
//   if (data.type === 'chat') {
//     const message = data.payload;
//     messages.push(message);
//     updateChatMessages();
   
 });
  
  
  // Receive message from remote user
  webrtc.connection.on('message', (data) => {
    if (data.type === 'chat') {
      const message = data.payload;
      messages.push(message);
      updateChatMessages();
    }
  });
  webrtc.on('volumeChange', function (volume, treshold) {
	    showVolume(document.getElementById('localVolume'), volume);
	});
  
  webrtc.on('videoRemoved', function (video, peer) {
	    console.log('video removed ', peer);
	    var remotes = document.getElementById('remote-videos');
	    var el = document.getElementById(peer ? 'teilnehmer' + webrtc.getDomId(peer) : 'localScreenContainer');
	    if (remotes && el) {
	        remotes.removeChild(el);
	    }
	});
  
  webrtc.on('mute', function (data) { // show muted symbol
	    webrtc.getPeers(data.id).forEach(function (peer) {
	        if (data.name == 'audio') {
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' .muted').show();
	        } else if (data.name == 'video') {
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' .paused').show();
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' video').hide();
	        }
	    });
	});
	webrtc.on('unmute', function (data) { // hide muted symbol
	    webrtc.getPeers(data.id).forEach(function (peer) {
	        if (data.name == 'audio') {
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' .muted').hide();
	        } else if (data.name == 'video') {
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' video').show();
	            $('#videocontainer_' + webrtc.getDomId(peer) + ' .paused').hide();
	        }
	    });
	});
  
  

  // Room Submit Button Handler
  $('.submit').on('click', (event) => {
    if (!formEl.form('is valid')) {
      return false;
    }
  
    username = $('#username').val();
    $('nickname').html('Hallo ' & username); 
    const roomName = $('#roomName').val().toLowerCase();
   // const roomName = "default";
    $('#loginPage').hide();
  	$('#videoPage').show();	
//  	$('#remote-video-template').show();
//  	$('#chat-content-template').show();
//  	$('#chat-template').show();
    if (event.target.id === 'create-btn') {
      createRoom(roomName);
    } else {
      joinRoom(roomName);
    }
    return false;
  });
});

function showVolume(el, volume) {
    //console.log('showVolume', volume, el);
    if (!el) return;
    if (volume < -45) volume = -45; // -45 to -20 is
    if (volume > -20) volume = -20; // a good range
    el.value = volume;
}

