 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VisionArt</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" type="text/css" href="/css/fgWrite.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
 </head>
 
</head>

<body>
    <%@ include file="header.jsp" %>
    <section id="container">
        <div class="container">
            <form action="/fg/write/save" id="postForm" method="post" enctype="multipart/form-data">
                <input type="hidden" name="memberId" value="${memberId}" />
                <input type="hidden" id="thumbnailImagePath" name="thumbnailImagePath">
                
                <div class="post">
                    <h2 id="posth2">명화갤러리 게시글 작성</h2>
                    <div class="form-group half-width">
                        <p class="p">분류</p>
                        <input type="text" id="category" name="category" required>
                        <button type="button" id="searchCategory" class="btn btn-custom">검색</button>
                    </div>
                    <div class="form-group half-width">
                        <p class="p">작가명</p>
                        <input type="text" id="author" name="author" required>
                        <button type="button" id="searchAuthor" class="btn btn-custom">검색</button>
                    </div>
                    <div class="form-group full-width">
                        <p class="p">작품명</p>
                        <input type="text" class="form-control full-width" placeholder="title" id="title" name="title" required>
                    </div>
                    <div class="form-group">
                        <p class="p">이미지 파일 업로드</p>
                        <input type="file" id="imageUpload" name="image" accept=".jpg, .jpeg, .png, .webp, .gif" required>
                    </div>
                    <div class="form-group">
                        <p class="p">내용(설명)</p>
                        <div id="postContent"></div>
                  </div> 
                <div class="buttons-container">
                    <button type="button" id="btn_cancel" class="btn btn-secondary">취소</button>
                    <input type="submit" id="btn_submit" class="btn btn-primary" value="등록">
                </div>
    </form>
</div>
  </section>

 
<script>
/**/
      $(document).ready(function() {
            $('#postContent').summernote({
                placeholder: '내용을 입력해주세요.',
                tabsize: 2,
                height: 300,
                codeviewFilter: false,
                codeviewIframeFilter: false,
                toolbar:[
                    ['style', ['style']],
                    ['fontsize', ['fontsize']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['para', ['ul', 'ol', 'paragraph']]
                ],
                fontSizes: [
                    '8', '9', '10', '11', '12', '14', '16', '18',
                    '20', '22', '24', '28', '30', '36', '50', '72',
                ],
                disableDragAndDrop: true
            });
        });

    $('#btn_cancel').click(function() {
        window.history.back();
    });

    $('#btn_submit').click(function(e) {
        e.preventDefault(); // 폼의 기본 제출을 방지

        var category = $('#category').val();
        var author = $('#author').val();
        var title = $('#title').val();
        var content = $('#postContent').summernote('code');
        
        alert(imageUpload);

        if (title.trim() === '') {
            alert("제목을 입력하세요.");
            return false;
        } else if (content.trim() === '' || content === '<p><br></p>') {
            alert("내용을 입력하세요.");
            return false;
        } else if (!$('#imageUpload').val()) {
            alert("첨부파일을 첨부해주세요.");
        // } else if () {

        } else {
            $('#postForm').append($('<input>').attr('type','hidden').attr('name','content').val(content));
            $('#postForm').submit();
        }
    });
 
</script>


</body>
</html>    