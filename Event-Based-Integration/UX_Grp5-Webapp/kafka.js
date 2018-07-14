/**
 * 
 */
let kafka = require('kafka-node');
let client = new kafka.Client();

let producer = new kafka.Producer(client);

let consumer1 =  new kafka.Consumer(client,[ {topic: 'topic1', partition: 0}]);
let consumer2 =  new kafka.Consumer(client,[ {topic: 'topic2', partition: 0}]);


producer.on('ready', function () {

    producer.send([{topic:'topic1', messages: 'topic 1 msg' ], (err,data)=>{
        console.log(err,'1 sent');
    });
    producer.send([{topic:'topic2', messages: 'topic 1 msg'}], (err,data)=>{
        console.log(err, '2 sent');
    });

});
producer.on('error', function (err) {
    console.log('err', err);
})



consumer1.on('message',(message) =>{
    console.log(11, message);
});
