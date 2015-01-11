var tessel = require('tessel');
var led2 = tessel.led[1].output(0);
var led3 = tessel.led[2].output(3);
setInterval(function(){
//console.log("DUDE! I'm F*CKING Blinking! (Press CTRL + C to stop)");
led2.toggle();
led3.toggle();
}, 100);