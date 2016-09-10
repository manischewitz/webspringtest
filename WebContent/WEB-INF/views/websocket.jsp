<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>websocket</title>
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
</head>
<body>

    <script type="text/javascript" >
        var url = "ws://" + window.location.host + "/websocket";
        var socket = new SockJS("http://"+window.location.host+"/EEProject/websocket");
        document.write(url);

        function saysmth(smth) {
            console.log("Sending: "+smth)
            socket.send(smth);
        }

        socket.onopen = function () {
            console.log('Opening');
            saysmth("hallo");
        }
        
        socket.onmessage = function (e) {
            console.log('Received message: ' + e.data);
            document.write(e.data+" <hr/>");
            setTimeout(function () { saysmth("hello");},2000);
        }
        
        socket.onclose = function () {
            console.log("Closing.");
        }
        
        
        

    </script>

</body>
</html>