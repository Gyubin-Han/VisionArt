
	
/* 글 목록 */
$(document).ready(function() {

    $("#noticeWriteBtn").click(function(){
    	location.href ="/notice/write";
    });
    // $.ajax({url: "notice", success: function(result){             
    //     var html = "";
    //     result.forEach(function(item){
    //       html+= "<tr> <td><a href = '/notice/=" + item.idx + "'>" + item.title + "</a>"
    //     });
    //     $("#listArea").append(html);
    //     $('#example').DataTable();
    //  }});
    $("#deleteBtn").click(function(){
      location.href ="noticeWrite";
    });
} );