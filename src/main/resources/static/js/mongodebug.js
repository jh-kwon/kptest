var url = "http://localhost:8080";

function requestMongoInfo(){

    // DO GET
    $.ajax({
        type : "GET",
        url : url + "/test/mongoinfo",
        success : function(response) {

        //-- debug START
        console.log("response: ",response);
//        var typeR = (typeof response);
//        console.log("result type: ", typeR)
        //-- debug END

        $("#testResultPresentDiv").html("<div class='alert alert-info' role='alert'>"
                                    +JSON.stringify(response)
                                    +"</div>");
        },
        error : function(e) {
            alert("Error!") // debug --TODO remove
            console.log("ERROR: ", e); // debug
        }
    });
}

requestMongoInfo();