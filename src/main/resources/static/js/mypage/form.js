window.addEventListener("DOMContentLoaded", function() {
    CKEDITOR.replace("description", {
        height: 350
    });

    /** 이미지 업로드 버튼 클릭 처리 s */
    const attachFiles = document.getElementsByClassName("attach_files");
    const fileEl = document.getElementById("file");
    const gid = document.getElementById("gid").value;

    for (const el of attachFiles) {
        el.addEventListener("click", function() {
            const location = this.dataset.location;
            fileEl.fileLocation = location;
            fileEl.click();
        });
    }

    fileEl.addEventListener("change", function(e) {
        const location = fileEl.fileLocation;
        const files = e.target.files;
        mozip.fileManager.uploads(files, gid, location, true);
        fileEl.value = "";
    });

    /** 이미지 업로드 버튼 클릭 처리 e */
});

/**
* 파일 업로드 콜백
*
*/
function fileUploadCallback(files) {
    if (!files || files.length == 0) {
        return;
    }

    const domParser = new DOMParser();
    const thumbs = document.getElementById("thumbs");
    const attachedFiles = document.getElementById("attached_files");
    const fileTpl = document.getElementById("file_tpl").innerHTML;
    for (const file of files) {

        if (file.location == 'editor') { // 에디터 이미지
            const img = `<img src='${file.fileURL}' />`;
            CKEDITOR.instances.description.insertHtml(img);
            let html = fileTpl;
            html = html.replace(/#\[fileNo\]/g, file.fileNo)
                       .replace(/#\[url\]/g, file.fileURL)
                       .replace(/#\[fileName\]/g, file.originalFilename);
            const dom = domParser.parseFromString(html, "text/html");
            const li = dom.querySelector("li");
            attachedFiles.appendChild(li);

            /** 이미지 본문 추가 S */
            const insertEditor = li.querySelector(".insert_editor");
            insertEditor.addEventListener("click", function() {
                const url = this.dataset.url;
                const img = `<img src='${url}' />`;
                CKEDITOR.instances.description.insertHtml(img);
            });
            /** 이미지 본문 추가 E */

        } else { // 상단 메인 포토
            const photoTag = `<span class='thumb'>
                                    <i class='remove xi-close-min' data-file-no="${file.fileNo}"></i>
                                    <span class='inner' data-url='${file.fileURL}' style="background:url('${file.fileURL}'); background-size:cover;"></span>
                               </span>`;
            const dom = domParser.parseFromString(photoTag, "text/html");
            const span = dom.querySelector("span");
            thumbs.appendChild(span);

            const remove = span.querySelector(".remove");
            remove.addEventListener("click", mozip.fileManager.delete);
        }
    }
 }

/**
* 파일 삭제 콜백 =
*
* @param fileNo : 삭제된 파일 번호
*/
function fileDeleteCallback(fileNo) {
    console.log(fileNo);
}