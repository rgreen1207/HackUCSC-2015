//function in order to get whether or not any individuals have fallen

function hasFallen() {
	var url = "http://tylerjackson.me/UCSC_HACK/hasFallen.php";
	var client = new XMLHttpRequest();

	client.open("PUT", url, false);
	client.setRequestHeader("Content-Type", "text/plain");
	client.send("");

if (client.status == 200)
    if (client.responseText == 1) 
		return true;
	else
		return false;
else
    return false;
}