const bodyParser = require('body-parser'),
    path = require('path'),
    config = require('config'),
    express = require('express'),
    cors = require('cors'),
	httpsport = process.env.SECURE_PORT || config.get('host').httpsport || 3443;
//
//  Basic Express App
//
var xirsys = config.get('xirsys');//Xirsys account info for API.
var webrtc = require('./routes/webrtc.js');//Xirsys API module
var app = express()
    .use(cors())
    .use(bodyParser.json())//json parser
    .use(bodyParser.urlencoded({ extended: true }))//urlencoded parser
    .use(express.static(path.join(__dirname, 'public')))//path to examples
    .use("/webrtc",webrtc(xirsys));//watch API calls
	//.use("/scripts", express.static(__dirname, 'node_modules'));
	
	app.use(express.static('public'));

// Provide access to node_modules folder
app.use('/scripts', express.static(`${__dirname}/node_modules/`));

// Redirect all traffic to index.html
app.use((req, res) => res.sendFile(`${__dirname}/public/index.html`));

module.exports = app;
