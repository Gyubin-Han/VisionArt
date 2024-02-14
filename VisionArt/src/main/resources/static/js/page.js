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
