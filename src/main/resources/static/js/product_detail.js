//spinner
$(document).ready(function(){
	var top=3;
	var num=parseInt($(".spinner_value").val());
	
	if(num==1){
		$("#dec").attr("class","spinner_dec1");
	}
	
	$("#inc").click(function(){
		if(num<top){
			$("#dec").attr("class","spinner_dec");
			num++;
			$(".spinner_value").val(num);
			if(num==top){
				$("#inc").attr("class","spinner_inc1");
			}	
		}
		
	});
	$("#dec").click(function(){
		
		if(num>1){
			$("#inc").attr("class","spinner_inc");
			num--;
			$(".spinner_value").val(num);
			if(num==1){
				$("#dec").attr("class","spinner_dec1");
			}
		}
	});
	
	$(".spinner_value").change(function(){
		var num1=parseInt($(".spinner_value").val());
		if(num1<1){
			$(".spinner_value").val(1);
			$("#dec").attr("class","spinner_dec1");
			$("#inc").attr("class","spinner_inc");
			
		}else if(num1>top){
			$(".spinner_value").val(top);
			$("#inc").attr("class","spinner_inc1");
			$("#dec").attr("class","spinner_dec");
		}
	});
	
	
	//radio
	$(function(){
    $(".myradio input").change(function(){
//		var optionName=$(this).attr("name");
//		alert(optionName);
        $(":checked").parent().addClass("state_active").removeClass("state_normal");

		$(":checked").parent().siblings().removeClass("state_active").addClass("state_normal");
    	});
	});
	
	
	
	//mybtn

	$(function () {
             $(".mybtn").mouseover(function () {
                 if ($(this).is(":animated")) {
                     $(this).stop(true, false).fadeTo("fast", "0.6");
                 } else {
                     $(this).fadeTo("fast", "0.6");
                 }
             }).mouseleave(function () {
                 $(this).fadeTo("fast", "1");
             });
         });

	
	
});


