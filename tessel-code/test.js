var tessel = require('tessel');

var servolib = require('servo-pca9685');
var servo = servolib.use(tessel.port['A']);
var servo1 = 1;

var camera = require('camera-vc0706').use(tessel.port['D']);
var notificationLED = tessel.led[3];


var ambientlib = require('ambient-attx4');
var ambient = ambientlib.use(tessel.port['C']);

var servoready = false;
var cameraready = false;
var position = 0;

servo.on('ready', function () {
    servoready = true;
    servo.move(servo1, position);
});

camera.on('ready', function photo() {
    cameraready = true;
    notificationLED.high();
});

var conn = true;
var fallen = false;
//getconnected();
hasFallen();
loop();
var servomove = false;
var R = 0;

function loop() {
    setInterval(function () {
        if (servoready == true && cameraready == true && servomove == false && conn == true && fallen == true) {
            moveservo1();
            servomove = true;
        }
      500});
}


function moveservo1() {
    var z = 0;
    servo.configure(servo1, 0.05, 0.12, function () {
        setInterval(function () {
            console.log('Position (in range 0-1):', position);
            servo.move(servo1, position);
            position += 0.1;
            if (position == 0.5 || position == 0.9)
            {
                takepic();
            }
            if (position > 1) {
                position = 0;
                takepic();
                z++;
            }
            if(z == 4)
            {
                console.log('checking to see if still fallen');
                R++;
                hasFallen();
                if (R == 2)
                {
                    fallen = false;
                }
                z = 0;
                loop();
            }
        }, 1000);
    });
}
var c = 0;
function takepic() {
    camera.takePicture(function (err, image) {
        if (err) {
            console.log('error taking image', err);
        }
        else {
            notificationLED.low();
            var name = 'picture - ' + c + '.jpg';
            c++;
            process.sendfile(name, image);
        }
    });
}

/*
function getconnected() {

    console.log('Testing Connection');

    var http = require('http');
    var statusCode = 200;
    var count = 1;

    var wifi = require('wifi-cc3000');
    var network = 'FBIvan3';
    var pass = 'Csumb2015';
    var timeouts = 0;

    function connect(){
      wifi.connect({
        security: security, ssid: network, password: pass, timeout: 30
      });
    }
    wifi.on('timeout', function(err){
      timeouts++;
      if (timeouts > 2) {
        powerCycle();
      } else {
          connect();
          conn = true;
      }
    });

    function powerCycle() {
        wifi.reset(function () {
            timeouts = 0;
            setTimeout(function () {
                if (!wifi.isConnected()) {
                    connect();
                }
            }, 20 * 1000);
        })
    }
}
*/

function hasFallen() {
    //setInterval(function () {
        var url = "http://tylerjackson.me/UCSC_HACK/hasFallen.php";
        console.log("check fallen");
        fallen = true;
    //    var client = new XMLHttpRequest();

    //    client.open("PUT", url, false);
    //    client.setRequestHeader("Content-Type", "text/plain");
    //    client.send("");

    //    if (client.status == 200) {
    //        if (client.responseText == 1) {
    //            x = 1;
    //            window.setTimeout(go, 60000);
    //            return fallen = true;
    //            x = 0;
    //        }
    //        else
    //            x = 0;
    //        return fallen = false;
    //    }
    //    else
    //        x = 0;
    //    return fallen = false;
    //},500);
}