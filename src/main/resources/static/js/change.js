$(function(){
	$('.change').on('click', function() {
		if(!confirm("ステータス変更を行います。よろしいですか？")){
			location.reload();
	        return false;
	    }else{
	    }
	});
});
