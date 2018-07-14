/**
 * 
 */
// Setup basic express server
var express = require('express');
//var $ = require('jquery');
var app = express();
var path = require('path');
const webpack = require('webpack');
var server = require('http').createServer(app);
var io = require('socket.io')(server)
var port = process.env.PORT || 3000;


//kafka
/**
 * 
 */
//let kafka = require('kafka-node');
//let client = new kafka.Client();
//
//let producer = new kafka.Producer(client);
//
//let consumer1 =  new kafka.Consumer(client,[ {topic: 'topic1', partition: 0}]);
//let consumer2 =  new kafka.Consumer(client,[ {topic: 'topic2', partition: 0}]);
//
//
//producer.on('ready', function () {
//
//    producer.send([{topic:'topic1', messages: 'topic 1 msg' ], (err,data)=>{
//        console.log(err,'1 sent');
//    });
//    producer.send([{topic:'topic2', messages: 'topic 1 msg'}], (err,data)=>{
//        console.log(err, '2 sent');
//    });
//
//});
//producer.on('error', function (err) {
//    console.log('err', err);
//})
//
//
//
//consumer1.on('message',(message) =>{
//    console.log(11, message);
//});




//ende kafka





server.listen(port, () => {
  console.log('Server listening at port %d', port);
});

// Routing
app.use(express.static(path.join(__dirname, 'public')));

//node module
app.use('/scripts', express.static(__dirname + '/node_modules'));

// Chatroom

var numUsers = 0;

io.on('connection', (socket) => {
  var addedUser = false;

  // when the client emits 'add user', this listens and executes
  socket.on('add user', (username) => {
    if (addedUser) return;

    // we store the username in the socket session for this client
    socket.username = username;
    ++numUsers;
    addedUser = true;
    socket.emit('login', {
      numUsers: numUsers
    });
    // echo globally (all clients) that a person has connected
    socket.broadcast.emit('user joined', {
      username: socket.username,
      numUsers: numUsers
    });
  });

    // when the client emits 'stop typing', we broadcast it to others
  socket.on('stop typing', () => {
    socket.broadcast.emit('stop typing', {
      username: socket.username
    });
  });

  // when the user disconnects.. perform this
  socket.on('disconnect', () => {
    if (addedUser) {
      --numUsers;

      // echo globally that this client has left
      socket.broadcast.emit('user left', {
        username: socket.username,
        numUsers: numUsers
      });
    }
  });
});