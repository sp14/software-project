<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

	</head>

    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->
    	
    	<div class="container" style="text-align: center">
			<#--Select how many AI-->
                <h1 >Top Trumps Games</h1><br/>
				<hr>
				<h3>How many AI you want to play with?</h3>
                <form>
                    <input  type="radio" name="AI" value="1">1<br/>
                    <input type="radio" name="AI" value="2">2<br/>
                    <input type="radio" name="AI" value="3">3<br/>
                    <input type="radio" name="AI" value="4">4<br/>
                    <input type="submit" value="Submit">
                </form>
        </div>

                <#--image card-->
		<#--<div class="card" style="width:400px">-->
			<#--<img class="float-left"  src="http://dcs.gla.ac.uk/~richardm/TopTrumps/m50.jpg" alt="Card image" style="width:100%">-->
			<#--<div class="card-body">-->
                <#--<h2 class="card-title">You</h2><hr/>-->
                <#--<h3 class="card-title">m50</h3>-->
                <#--<p class="card-text">m50 1 10 2 2 0</p>-->

                <#--<hr>-->

			<#--</div>-->

            <#--<img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/350r.jpg" alt="Card image" style="width:100%">-->
            <#--<div class="card-body">-->
                <#--<h2 class="card-title" >AI player1</h2><hr/>-->
                <#--<h4 class="card-title">350r</h4>-->
                <#--<p class="card-text">350r 1 10 2 2 0</p>-->
            <#--</div>-->
        <#--</div>-->

        <div class="float-left" style="width:400px">
                    <img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/m50.jpg" alt="Card image" style="width:100%;height:200px ">
                    <div class="card-body">
                        <h2 class="card-title" >You</h2><hr/>
                        <h4 class="card-title">m50</h4>
                        <p class="card-text"> 1 10 2 2 0</p>
                    </div>
        </div>

        <div class="float-left" style="width:400px">
            <img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/350r.jpg" alt="Card image" style="width:100%;height:200px">
            <div class="card-body">
                <h2 class="card-title" >AI player1</h2><hr/>
                <h4 class="card-title">350r</h4>
                <p class="card-text">350r 1 10 2 2 0</p>


            </div>

        </div>

        <div class="float-left" style="width:400px">
            <img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Avenger.jpg" alt="Card image" style="width:100%;height:200px">
            <div class="card-body">
                <h2 class="card-title" >AI player2</h2><hr/>
                <h4 class="card-title">350r</h4>
                <p class="card-text">350r 1 10 2 2 0</p>


            </div>
        </div>

        <div class="float-left" style="width:400px">
            <img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Carrack.jpg" alt="Card image" style="width:100%;height:200px">
            <div class="card-body">
                <h2 class="card-title" >AI player3</h2><hr/>
                <h4 class="card-title">350r</h4>
                <p class="card-text">350r 1 10 2 2 0</p>


            </div>
        </div>

        <div class="float-left" style="width:400px">
            <img class="card-img-top" src="http://dcs.gla.ac.uk/~richardm/TopTrumps/Constellation.jpg" alt="Card image" style="width:100%;height:200px">
            <div class="card-body">
                <h2 class="card-title" >AI player4</h2><hr/>
                <h4 class="card-title">350r</h4>
                <p class="card-text">350r 1 10 2 2 0</p>


            </div>

        </div>


			<!-- Add your HTML Here -->
		

		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
				// helloJSONList();

				helloWord("You will Start Game Here");
				
			}
			
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------
		
			// This is a reusable method for creating a CORS request. Do not edit this.
			function createCORSRequest(method, url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">
		
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloJSONList() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloWord(word) {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>