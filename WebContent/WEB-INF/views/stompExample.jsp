<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>stomp</title>
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
     <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
</head>
<body>

	<p class="newMessages">New messages</p>	

    <script type="text/javascript" >
        var url = "http://" + window.location.host + "/EEProject/stomp";
        var socket = new SockJS(url);
        var stomp = Stomp.over(socket);
        var payload = JSON.stringify({ 'message': 'stomp hello' });

        stomp.connect(
            'guest',
            'guest',
            function (frame) { stomp.send("/app/stompMessage", {}, payload); 
            					stomp.subscribe("/topic/getMessage",handleIncomingMessage);
            });
        
        function handleIncomingMessage(incoming){
        	var message = JSON.parse(incoming.body);
        	console.log('Received: ', message);
        	$('.newMessages').prepend(message.message+"<br/>");}
    </script>
</body>
</html>