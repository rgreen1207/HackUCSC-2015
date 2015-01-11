var tessel = require('tessel');

var camera = require('camera-vc0706').use(tessel.port['D']);
var notificationLED = tessel.led[3];

//var servolib = require('servo-pca9685');
//var servo = servolib.use(tessel.port['A']);
//var servo1 = 1;

var ambientlib = require('ambient-attx4');
var ambient = ambientlib.use(tessel.port['C']);

var http = require('http');
var statusCode = 200;
var count = 1;

var wifi = require('wifi-cc3000');
var network = 'FBIvan3';
var pass = 'Csumb2015'; 
var timeouts = 0;

//function connect(){
//  wifi.connect({
//    security: security, ssid: network, password: pass, timeout: 30
//  });
//}
//wifi.on('timeout', function(err){
//  timeouts++;
//  if (timeouts > 2) {
//    powerCycle();
//  } else {
//    connect();
//  }
//});

//function powerCycle(){
//  wifi.reset(function(){
//    timeouts = 0;
//    setTimeout(function(){
//      if (!wifi.isConnected()) {
//        connect();
//      }
//      }, 20 *1000);
//  })
//}

//var x = 0;

//while (x == 0)
//{
//    function hasFallen() {
//        var url = "http://tylerjackson.me/UCSC_HACK/hasFallen.php";
//        var client = new XMLHttpRequest();

//        client.open("PUT", url, false);
//        client.setRequestHeader("Content-Type", "text/plain");
//        client.send("");

//        if (client.status == 200) {
//            if (client.responseText == 1) {
//                x = 1;
//                window.setTimeout(go, 60000);
//                x = 0;
//            }
//            else
//                x = 0;
//        }
//        else
//            x = 0;
//    }
//}

//servo.on('ready', function go() {
	var position = 0;
	var stop = 0;
//	servo.configure(servo1, 0.05, 0.12, function(){
		setInterval(function() {
//			servo.move(servo1,position);
/*			if(stop != 1)
			{
			    position += 0.1;
			    if(position == .5 || position == 1)
			    {
			        stop = 1;
			    }
			    else
			    {
			        stop = 0;
			    }
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
				camera.takePicture(function (err, image) {
				    if (err) {
				        console.log('error taking image', err);
				    }
				    else {
				        notificationLED.low();
				        var name = 'picture - ' + Math.floor(Date.now()) + '.jpg';
				        process.sendfile(name, image);
				        camera.disable();
				    }
				});
				stop = 1;
			}*/
		},500);
//	});
//});