window.addEventListener("DOMContentLoaded", function() {
    const pages = document.querySelectorAll(".mozip_html .page a");
    const targetHTML = document.querySelector(".mozip_html");
    for (const el of pages) {
        el.addEventListener("click", function(e) {
            e.preventDefault();
            const href = this.href;
            const pattern = /page=(\d+)/
            const result = pattern.exec(href);
            if (!result) return;

            const page = Number(result[1]);


            const url = `/ajax/mozip/${page}`;
            commonLib.ajaxLoad(url)
                .then((res) => {
                    res = res.replace(/<html*>/igm, "")
                             .replace(/<!DOCTYPE*>/igm, "")
                             .trim();

                    targetHTML.innerHTML = res;
                })
                .catch((err) => {
                    console.error(err);
                });
        });
    }
});
