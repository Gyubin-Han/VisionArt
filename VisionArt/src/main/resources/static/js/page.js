// 총 페이지 수와 현재 페이지를 설정
// 사용하려는 페이지에서 아래 변수는 사전 정의해놓아야 함.
// 변수 : 현재 페이지(currentPage)
// 상수 : 시작 페이지(startPage), 끝 페이지(endPage), 호출하는 곳 uri 주소(uri)
// 
// let currentPage = 1;
// const totalPages = 3;  


// 페이지 번호를 생성하고, 페이지네이션 컨테이너에 추가합니다.
function setupPagination() {
    const pageNumbers = document.getElementById('page-numbers');
    pageNumbers.innerHTML = '';

    for (let i = startPage; i <= endPage; i++) {
        let btn = document.createElement('button');
        btn.textContent = i;
        if(i==currentPage){
            btn.style.fontWeight="bold";
        }
        btn.onclick = function() { goToPage(i); };
        pageNumbers.appendChild(btn);
    }
}

// 특정 페이지로 이동 
function goToPage(page) {
    location.href=uri+"?page="+page;
    // currentPage = page;
    // console.log(`이동: 페이지 ${currentPage}`);  
}

// 이전 페이지로 이동 
function goToPreviousPage() {
    if (currentPage > 1) {
        goToPage(currentPage - 1);
    }
}

// 다음 페이지로 이동 
function goToNextPage() {
    if (currentPage < endPage) {
        goToPage(currentPage + 1);
    }
}

// 페이지네이션을 설정합니다.
// setupPagination();
