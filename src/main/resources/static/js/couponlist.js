//-- server request to coupon list & load table
var table = 	$('#clistTable').DataTable( {
    "pagingType": "simple_numbers",
    "pageLength": 10,
    "lengthChange": false,
    "info":false,
    "processing": true,
    "searching": false,
    
    "columns": [
        { "data": "seq" },
        { "data": "email" },
        { "data": "coupon" },
        { "data": "date" }
      ],
    
    "drawCallback": function(){
//    		var tableAPI = this.api();
//    		$('.paginate_button.next', tableAPI.table().container()).on('click', chkIsLastPageAndLoadMorePagesFunc);
//        $('.paginate_button.current', tableAPI.table().container()).on('click', chkIsLastPageAndLoadMorePagesFunc);
     }
} );


var url = window.location;
var rowNoFromServer = 0;
function requestCouponList(rowno, rowcnt){
	if(rowNoFromServer >= 0){
		  // PREPARE FORM DATA
		  var formData = {
		    page:{
		        rowno : rowno,
		        rowcnt : rowcnt
		    }
		  };
		  console.log("formData: ",formData); // debug
		  
		  var pageReset = (rowno == 0);
		  
//		  testLoadTableWithoutServer(rowcnt, pageReset);//-- ONLY TEST CLIENT
		  
		  // DO POST
		  $.ajax({
		  type : "POST",
		  contentType : "application/json",
		  url : url + "couponlist/issued",
		  data : JSON.stringify(formData),
		  dataType : 'json',
		  success : function(response) {

			  	console.log("222_response: ",JSON.stringify(response)); // debug

			  	if(response.success == true){
			  		rowNoFromServer = response.page.rowno;
			  		loadCouponListTable(response.data, pageReset);
			  	}else{
			  	    alert("Error : Fail to load list")
//			  		$("#registResultDiv").html("<div class='alert alert-danger' role='alert'> Error : Fail to load list </div>");
			  	}
		  },
		  error : function(e) {
		    alert("Load Table Request Error!") // debug --TODO remove
		    console.log("ERROR: ", e); // debug
		  }
		});
	}else{ // this means, there is more data on server
		alert("No more data for presenting table!!")
	}
}

function loadCouponListTable(list, pageReset){
	var isTableHide = $("#clistTableDiv").is(':hidden');
	if(isTableHide){
		$("#clistTableDiv").show();
	}
	
	console.log(" data len:"+table.data().length+" >> add list len:"+ list.length);
	
	if(list.length > 0){
	    for(var i = 0;i < list.length;i++){
	        var element = list[i];

	        element.date = convertTimeToformattedDateString(element.date);
	        table.row.add(element);
	    }
	    table.draw(pageReset);
//		table.rows.add(list).draw(pageReset);
	}
  }

function convertTimeToformattedDateString(time){
    var newDate = new Date(time);

    var month = (newDate.getMonth()+1);
    if(10 > month){
        month = "0"+month;
    }

    var formatted = newDate.getFullYear()+ "-" + month+"-"+newDate.getDate()
                +" "+newDate.getHours()+":"+ newDate.getMinutes() + ":" + newDate.getSeconds();

    return formatted;
}

//check is last page and load more pages....
var chkIsLastPageAndLoadMorePagesFunc = function () {
    var info = table.page.info();
    var isLastPage = (table.data().length > 0)&&(info.page == (info.pages-1)) ? true : false;
//    console.log("[PG_CHG] data len:"+table.data().length+" page:"+ info.page+" pages:"+ info.pages+" isLastPage:"+isLastPage);
	if(isLastPage){
		requestCouponList(rowNoFromServer, rowCntOfTablePerOneCall);
	}
}
$('#clistTable').on( 'page.dt',  chkIsLastPageAndLoadMorePagesFunc);


//$( document ).ready(function() { // TODO -- does need?
//})

//-- TEST
function testLoadTableWithoutServer(rowcnt, pageReset){
	  var testArray = [];
	  var totalRowCnt = table.data().length;
	  for(var i = 0;i < rowcnt;i++){
		  var idx = totalRowCnt+i;
		  var testData = {
				  "seq":idx,
				  "email":idx+"@a.com",
				  "coupon":"coupon_"+idx,
				  "date" : "2018-MM-dd HH:mm:ss"
		  };
		  testArray.push(testData);
		  rowNoFromServer = idx+1;
	  }
	  if(rowNoFromServer >= 300){
		  rowNoFromServer=-1;
	  }
	  loadCouponListTable(testArray, pageReset);
}


//function testCouponListJS(){
//	// for presenting coupon
//	$("#registResultDiv").html("<div class='alert alert-info' role='alert'>Load Another .js SUCCESS! </div>");
//}