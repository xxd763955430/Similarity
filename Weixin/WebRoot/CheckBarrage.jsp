<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>弹幕审核页</title>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css">
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap-table.css">
<script src="js/bootstrap-table.min.js"></script>
</head>
<body>
<div class="container">
<form class="form-horizontal" role="form">
  <div class="form-group">
    <label class="col-sm-offset-2 col-sm-2 control-label">系统消息</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="systemMessage" placeholder="系统消息">     
    </div> 
    <div class="col-sm-2">
    <button class="btn btn-info" type="button" id="add">添加</button> 
    </div>   
  </div>
   <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-8">
	      <table class="table-bordered .table-striped" data-toggle="table" >
			    <thead>
			        <tr>
			            <th>Content</th>
			            <th>Delete</th>        
			        </tr>
			    </thead>
			    <tbody>
			    	<tr>
			            <td>1</td>
			            <td>2</td>        
			        </tr>
			    </tbody>
		  </table>   
	    </div>   
  	</div>
  	<div class="form-group">
	  	<div class="col-sm-offset-2 col-sm-2">
	    <button class="btn btn-info" type="button" id="send">发送</button> 
	    </div> 
    </div>
</form>  
</div>
</body>
<script>
sendPOST()
var textLength=0,textList;
var textListAfterChecked=new Array();
loadBarrage();
function deletetext(i)
{
	$("#tr"+i).remove();
	delete(textList[i]);
}
function loadBarrage()
{
		$("tr:gt(0)").remove();
		$.ajax({
	        type: "GET",
	        data:{"param":"check"},
	        url: "getOriginalBarrage", 
	        dataType:"json",
	        async: false,
	        success: function(data){
	        $.each(data,function(i,item){
	        	textLength++;
	        	var tr="<tr id=tr"+i+"><td><label id=label"+i+">"+item+"</label></td>"+"<td><button class='btn-sm btn-danger' id=button"+i+ " onclick='deletetext("+i+")'><span class='glyphicon glyphicon-remove'></span></button></td></tr>";	        	
	        	$("tbody").append(tr);        	
	        	});       	
	        }		
	   });
		textList=new Array(textLength);
		for(i=0;i<textLength;i++)
		{
			textList[i]=$("#label"+i).text();
		}
	
}
//发送按钮
$("#send").click(function()
		{
	if(textLength>0)
		{
			for(i=0;i<textLength;i++)
			{
				if(typeof(textList[i])!= "undefined")
				{
					//0开头为弹幕
					textList[i]="0"+textList[i];	
					textListAfterChecked.push(textList[i]);			
				}
			}	

			sendBarrageAfterChecked();		
		}
	loadBarrage();
});
//添加系统消息
$("#add").click(function()
	{
			//1开头为系统消息
			var systemMessage="1"+$("#systemMessage").val();
			textListAfterChecked.push(systemMessage);	
			sendBarrageAfterChecked();			
			loadBarrage();
			$("#systemMessage").val("");
	});
function sendBarrageAfterChecked()
{
	console.log(textListAfterChecked);
	$.ajax({
        type: "POST",
        url: "sendCheckedBarrage",
        async: false,
        data: {"textList":textListAfterChecked}, 
        traditional: true,
        success: function(data){   
        	textListAfterChecked.length=0;   	
        	textLength=0;
        }
   });		
}
function sendPOST()
{
	console.log(textListAfterChecked);
	$.ajax({
        type: "POST",
        url: "http://59.78.108.51/webui/login.aspx",
        async: false,
        data: {"username":"Y45150157","password":"921221"},
        dataType:"jsonp",
        success: function(data){   
        	console.log('post');
        }
   });		
}
</script>
</html>