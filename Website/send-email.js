// JavaScript Document
var m = new mandrill.Mandrill('');

// create a variable for the API call parameters
var params = {
    }
};

function sendTheMail() {
// Send the email!

    m.messages.send(params, function(res) {
        log(res);
    }, function(err) {
        log(err);
    });
}