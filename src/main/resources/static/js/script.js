document.addEventListener('DOMContentLoaded', function() {
    // 背景色を変更したい要素を取得
    const limitElements = document.querySelectorAll('.limit');
    const today = new Date();
    today.setHours(23, 59, 59, 999);
    for (let i = 1; i < limitElements.length; i++) {
        const limitElement = limitElements[i];
        const limit = new Date(limitElement.dataset.limit);
        if(today >= limit){
            limitElement.style.backgroundColor = '#FFDDDD';
        } else {
            limitElement.style.backgroundColor = '#FFF450';
        }
    }
});