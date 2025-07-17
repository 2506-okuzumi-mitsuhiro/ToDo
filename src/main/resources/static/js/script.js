document.addEventListener('DOMContentLoaded', function() {
    // 背景色を変更したい要素を取得
    const limitElements = document.querySelectorAll('.limit');
    const today = new Date();
    today.setHours(23, 59, 59, 999);

    // 1週間後日付取得
    const nextWeekDay = new Date();
    nextWeekDay.setDate(nextWeekDay.getDate() + 7);
    nextWeekDay.setHours(23, 59, 59, 999);

    for (let i = 1; i < limitElements.length; i++) {
        const limitElement = limitElements[i];
        const limit = new Date(limitElement.dataset.limit);
        if(nextWeekDay < limit){
            limitElement.style.backgroundColor = '#FFFFFF';
        }else if(today >= limit){
            limitElement.style.backgroundColor = '#FFDDDD';
        } else {
            limitElement.style.backgroundColor = '#FFF450';
        }
    }
});

/*リアルタイムの時刻を表示*/
function updateCurrentTime() {
    const now = new Date();
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false // 24時間形式を使用
    };
    const formattedTime = now.toLocaleDateString('ja-JP', options).replace(':', '時') + '分';
    document.getElementById('current-time').textContent = formattedTime;
}

updateCurrentTime();

setInterval(updateCurrentTime, 1000);