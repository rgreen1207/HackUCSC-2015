var tessel = require('tessel');

var camera = require('camera-vc0706').use(tessel.port['A']);
var notificationLED = tessel.led[3];

var servolib = require('servo-pca9685');
var servo = servolib.use(tessel.port['B']);
var servo1 = 1;

var ambientlib = require('ambient-attx4');
var ambient = ambientlib.use(tessel.port['C']);

var http = require('http');
var statusCode = 200;
var count = 1;

setImmediate(function start(){
	console.log('http request #' + (count++))
	http.get("http://www.csumb.edu/" + statusCode, function (res){
		console.log('# statusCode', res.statusCode)
		var bufs=[];
		res.on('data', function(data){
			bufs.push(new Buffer(data));
			console.log('# received', new Buffer(data).toString());
		})
		res.on('end', function(){
			console.log('done.');
			setImmediate(start);
		})
	}).on('error', function(e){
		console.log('not ok -', e.message, 'error event')
		setImmediate(start);
	});
});

servo.on('ready', function() {
	var position = 0;
	var stop = 0;
	servo.configure(servo1, 0.05, 0.12, function(){
		setInterval(function() {
			servo.move(servo1,position);
			if(stop != 1)
			{
				position+=0.1;
			}
			if(stop != 0)
			{
				camera.on('ready', function photo(){
					notificationLED.high();
					camera.takePicture(function(err, image){
						if(err){
							console.log('error taking image', err);
						}
						else{
							notificationLED.low();
							var name = 'picture - ' + Math.floor(Date.now())+'.jpg';
							process.sendfile(name, image);
							camera.disable();
						}
					});
				});
			}
			if (position>1){
				position=0;
				photo();
				stop = 1;
			}
		},500);
	});
});