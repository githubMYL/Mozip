/** 공통 라이브러리 */
const commonLib = {
    /**
    * 요청 헤더 전송용 CSRF 추출
    *
    */
    getCsrfToken() {
        const csrfEl = document.querySelector("meta[name='_csrf']")
        const csrfHeaderEl = document.querySelector("meta[name='_csrf_header']");

        return  { header : csrfHeaderEl.content, token : csrfEl.content };
    },
    /**
    * ajax 요청 편의 메서드
    *
    * @param url - 요청 메서드
    * @method - 요청방식
    * @params - body 요청 데이터
    * @type - 전송 데이터 종류
    */
    ajaxLoad(url, method, params, type) {
        return new Promise((resolve, reject) => {
            method = method || "GET";
            const xhr = new XMLHttpRequest();
            xhr.open(method, url);

            const csrfEl = document.querySelector("meta[name='_csrf']")
            const csrfHeaderEl = document.querySelector("meta[name='_csrf_header']");
            if (csrfEl && csrfHeaderEl) {
                xhr.setRequestHeader(csrfHeaderEl.content, csrfEl.content);
            }

            if (method && ['POST', 'PUT', 'PATCH'].indexOf(method) != -1) {
                type =  type.toUpperCase();
                const contentType = type == 'JSON'? 'application/json':'application/x-www-form-urlencoded';
                xhr.setRequestHeader('content-type', contentType);
                if (type != 'JSON') {
                    params = new URLSearchParams(params).toString();
                }
            } else { // GET, DELETE
                params = null;
            }


           xhr.send(params);

           xhr.onreadystatechange = () => {
                if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                    resolve(xhr.responseText);
                }
           };

           xhr.onerror = (err) => {
                reject(err);
           };

           xhr.onabort = (err) => {
                reject(err);
           };
        });
    }
};

