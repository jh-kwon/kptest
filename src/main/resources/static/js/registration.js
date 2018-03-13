var rowCntOfTablePerOneCall = 100;

$( document ).ready(function() {
	var url = window.location;
	
	// SUBMIT FORM
	$("#registForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		
		requestRegist();
	});
    
    
    function requestRegist(){
      
      // PREPARE FORM DATA
      var formData = {
    		  email : $("#inputEmail").val()
      }
      
      // DO POST
      $.ajax({
      type : "POST",
      contentType : "application/json",
      url : url + "regist",
      data : JSON.stringify(formData),
      dataType : 'json',
      success : function(response) {

    	  	console.log("response: ",JSON.stringify(response)); // debug

    	  	if(response.success == true){
    	  		hideSubmitButton();
    	  		loadRegistResult(response);
    	  	}else{
    	  		$("#registResultDiv").html("<div class='alert alert-danger alert-dismissible fade show' role='alert'>"
    	  				+"Error : check your <strong>Email address!</strong>"
    	  				+"<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
    	  				+"<span aria-hidden='true'>&times;</span></button></div>");
    	  		
    	  		
    	  	}
      },
      error : function(e) {
    	alert("Regist Email Request Error!") // debug --TODO remove
        console.log("ERROR: ", e); // debug
      }
    });
      
      // Reset FormData after Posting
//      resetData(); // does need?
 
    }
    
    function loadRegistResult(result){
    		var coupon = "";
    		if(result.data != null){
  			coupon = result.data;
  		}
  		
  		// for presenting coupon
  		$("#registResultDiv").html("<div class='alert alert-info' role='alert'>" +"Your Coupon No.: " + coupon + "</div>");
    	
        //load another .js to do server request & load table
  		$.getScript('js/couponlist.js')
  		.done(function( script, textStatus ) {
  			console.log("getScript()'s Status: ",textStatus);
  			requestCouponList(0,rowCntOfTablePerOneCall);
	  	  })
	  	  .fail(function( jqxhr, settings, exception ) {
	  		console.log("getScript()'s Exception: ",exception);
	  	});
      }
    
    function hideSubmitButton(){
		$("#submitBtn").hide();
    }
    
    function resetData(){
    		$("#inputEmail").val("");
    }
})