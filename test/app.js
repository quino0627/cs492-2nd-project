// app.js

// var spawn = require('child_process').spawn,
// py = spawn('python', ['test.py']),
// data
// var {PythonShell} = require('python-shell')
// var options = {

//   mode: 'text',

//   pythonPath: '',

//   pythonOptions: ['-u'],

//   scriptPath: '/root/workspace/test/python',

//   args: ['value1', 'value2', 'value3']

// };
// PythonShell.run('test.py', options, function (err, results) {
//   if (err) throw err;
//   console.log('results: %j', results);
// });

// [LOAD PACKAGES]
var express     = require('express');
var app         = express();
var bodyParser  = require('body-parser');
var mongoose    = require('mongoose');

// [CONFIGURE APP TO USE bodyParser]
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser({limit : '50mb'}));
app.use(bodyParser.json());
app.use('/storage', express.static('storage/'));


// [CONFIGURE SERVER PORT]
var port = process.env.PORT || 80;
// DEFINE MODEL
var User = require('./models/user');
var Post = require('./models/post');

// MANAMGE SERVER STATE
// var users = require('./status/connectedUser');

// users.login("testUser");

// [CONFIGURE ROUTER]
// var url;
var userRouter = require('./routes/userRouter')(app, User);
var postRouter = require('./routes/postRouter')(app, Post);

// [RUN SERVER]
var server = app.listen(port, function(){
 console.log("Express server has started on port " + port)
});
// CONNECT TO MONGODB SERVER
var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
    // CONNECTED TO MONGODB SERVER
    console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost:27017/local', { useNewUrlParser: true });



// ......