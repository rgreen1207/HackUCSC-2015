// JavaScript Document
var m = new mandrill.Mandrill('EWk0QC-7NNypdiEqVQlB9A');

// create a variable for the API call parameters
var params = {
    "message": {
        "from_email":"tjackson@csumb.edu",
        "to":[{"email":"8055400996@tmomail.net"}],
        "subject": "Alert! Your loved one has fallen!! Help is on the way.",
        "text": "Please call us immediately."
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