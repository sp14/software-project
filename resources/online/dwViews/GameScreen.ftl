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




    <style>
        body{
            background-color: aliceblue;
        }
        #navbar{
            text-align: center;
        }
        #gameInfo{
            text-align: center;
        }
        #gameButton{
            text-align: center;
        }


    </style>

</head>

<body onload="initalize()"> <!-- Call the initalize method when the page loads -->
<!--header-->
    <div  id="navbar">
        <h1 >Top Trumps Games</h1><br/>
        <hr>


        <!--assume 4 AI for now-->


        <#--<h3>How many AI you want to play with?</h3>-->
        <#--<!-- AI players &ndash;&gt;-->
        <#--<form>-->
            <#--<input  type="radio" name="AI" value="1">1<br/>-->
            <#--<input type="radio" name="AI" value="2">2<br/>-->
            <#--<input type="radio" name="AI" value="3">3<br/>-->
            <#--<input type="radio" name="AI" value="4">4<br/>-->
        <#--&lt;#&ndash;<input type="submit" value="Start">&ndash;&gt;-->
        <#--</form>-->
    </div>

    <!--Display game infomation here-->
    <div id="gameInfo">
        <p id="currentPlayer">Who's turn</p>
        <p id="currentRound">round: 0</p>
        <p id="winner">Winner: </p>



    </div>


    <!-- Game Button here-->
    <div id="gameButton">
        <!-- Button: Start Game-->
        <button onclick="startGame()"  type="button" class="btn-primary  btn-lg " id="startButton" >Start Game!</button></a>

        <br/>
        <br/>
        <!-- Button next round -->
        <button type="button"  onclick="nextRound()" class="btn-primary  btn-lg " id="roundButton">Next Round</button></a>

        <!-- Choose category -->

    </div>



    <hr/>

    <!--card-->

    <div class="container" >
        <div class="row">
            <!--Player 1-->
            <div class="col" id="P1">
                <img class="card-img-top" id="P1CardImage" src="" alt="Card image" style="width:100%;height:200px ">
                <div id="P1Info">
                    <h2 class="PlayerInfo" >Player 1</h2><hr/>
                </div>
                <div class="cardInfo" id="P1CardInfo">
                    <div id="cardName">
                        <h3 id="P1CardName"></h3><hr/>

                    </div>
                    <p id="P1Cat1"></p>
                    <p id="P1Cat2"></p>
                    <p id="P1Cat3"></p>
                    <p id="P1Cat4"></p>
                    <p id="P1Cat5"></p><hr/>
                </div>
            </div>

            <!--AI 1-->
            <div class="col" id="A1">
                <img class="card-img-top" id="A1CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A1Info">
                    <h2 class="AI1Info" >AI 1</h2><hr/>
                </div>
                <div class="cardInfo" id="A1CardInfo">
                    <div id="cardName">
                        <h3 id="A1CardName"></h3><hr/>

                    </div>
                    <p id="A1Cat1"></p>
                    <p id="A1Cat2"></p>
                    <p id="A1Cat3"></p>
                    <p id="A1Cat4"></p>
                    <p id="A1Cat5"></p><hr/>
                </div>

            </div>

            <!--AI 2-->

            <div class="col" id="A2">
                <img class="card-img-top" id="A2CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A2Info">
                    <h2 class="A21Info" >AI 2</h2><hr/>
                </div>
                <div class="cardInfo" id="A2CardInfo">
                    <div id="cardName">
                        <h3 id="A2CardName"></h3><hr/>

                    </div>
                    <p id="A2Cat1"></p>
                    <p id="A2Cat2"></p>
                    <p id="A2Cat3"></p>
                    <p id="A2Cat4"></p>
                    <p id="A2Cat5"></p><hr/>
                </div>

            </div>
            <!--AI 3-->
            <div class="col" id="A3">
                <img class="card-img-top" id="A3CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A3Info">
                    <h2 class="A31Info" >AI 3</h2><hr/>
                </div>
                <div class="cardInfo" id="A3CardInfo">
                    <div id="cardName">
                        <h3 id="A3CardName"></h3><hr/>

                    </div>
                    <p id="A3Cat1"></p>
                    <p id="A3Cat2"></p>
                    <p id="A3Cat3"></p>
                    <p id="A3Cat4"></p>
                    <p id="A3Cat5"></p><hr/>
                </div>

            </div>
            <!--AI 4-->
            <div class="col" id="A4">
                <img class="card-img-top" id="A4CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A4Info">
                    <h2 class="AI4Info" >AI 4</h2><hr/>
                </div>
                <div class="cardInfo" id="A4CardInfo">
                    <div id="cardName">
                        <h3 id="A4CardName"></h3><hr/>

                    </div>
                    <p id="A4Cat1"></p>
                    <p id="A4Cat2"></p>
                    <p id="A4Cat3"></p>
                    <p id="A4Cat4"></p>
                    <p id="A4Cat5"></p><hr/>
                </div>

            </div>


        </div>






    </div>











<!-- Add your HTML Here -->








<script type="text/javascript">

    var gameNum = 0;
    var currentPlayer = " ";
    var currentRound = 0;


    // Method that is called on page load
    function initalize() {

        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------

        // For example, lets call our sample methods



    }

    // -----------------------------------------
    // Add your other Javascript methods Here
    // -----------------------------------------
    // This calls the startGame REST method from TopTrumpsRESTAPI
    function startGame() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/startGame"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response; // the text of the response
            // getP1CardName();

            gameNum = JSON.parse(responseText);


            //test
            alert("start game here");
            alert("The game number is:" + gameNum);

            //------------------------
            //start function here
            //------------------------
            displayUserCard(gameNum);



        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    //display the contents of the user's top card: set the player1 image and name
    function displayUserCard(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/displayUserCard?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here
            var p1CardName = JSON.parse(responseText);

            setImage("P1CardImage",p1CardName);
            document.getElementById("P1CardName").innerHTML = p1CardName;

            //set category
            getUserTopCardCategories(gameNum);
            getCurrentInfo(gameNum);

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    //get player1's card categories
    function getUserTopCardCategories(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getUserTopCardCategories?gameNum="+gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here

            var categoryArray = JSON.parse(responseText);

            alert("categoryArray: "+categoryArray);
            for (var i= 0; i < categoryArray.length; i++) {

                //
                // id="p1_cat1"
                setCategory("P1Cat"+ (i+1),categoryArray[i]);
            }

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }


    // set the Category to html by id
    function setCategory(id,value) {

        document.getElementById(id).innerHTML = " " + value;


    }


    //if the current player is AI,
    //if the current player is player1, let the player 1 choose category
    function selectCategory() {
        if (currentPlayer != "You")

    }


    //next round
    function nextRound() {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/nextRound"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }
    //------------------
    //get current Info: currentPlayer , currentRound
    //------------------

    function getCurrentInfo(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCurrentInfo?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;

            //code here
            var currentInfo = JSON.parse(responseText);
            currentPlayer = currentInfo[0];
            currentRound = currentInfo[1];
            document.getElementById("currentPlayer").innerHTML = "Who's turn: " + currentPlayer;
            document.getElementById("currentRound").innerHTML = "Round: " + currentRound;


        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }







    /*
    set the Image to each card
     */
    function setImage(id, imageName) {
        document.getElementById(id).src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + imageName + ".jpg";

    }



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
            var responseText = xhr.response;
            var test = JSON.parse(responseText);
            alert(test);
                                                    // the text of the response
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