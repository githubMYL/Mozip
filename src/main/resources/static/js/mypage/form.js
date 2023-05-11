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

    /** 메인 이미지 클릭 처리 S */
    const thumbs = document.querySelectorAll(".thumb.update .inner");
    for (const thumb of thumbs) {
        thumb.addEventListener("click", function() {
            const fileNo = this.dataset.fileNo;
            const fileURL = this.dataset.url
            showImagePopup(fileNo, fileURL);
        });

    }
    /** 메인 이미지 클릭 처리 E */

    /** 이미지 본문 추가 S */
    const insertEditors = document.getElementsByClassName("insert_editor");
    for(const el of insertEditors) {
        el.addEventListener("click", insertImage);
    }

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
            insertEditor.addEventListener("click", insertImage);
            /** 이미지 본문 추가 E */

        } else { // 상단 메인 포토
            const photoTag = `<span class="thumb file_${file.fileNo}">
                                    <a href="/file/delete/${file.fileNo}" target="ifrmProcess" onclick="return confirm('정말 삭제하시겠습니까?');">
                                        <i class='remove xi-close-min'></i>
                                     </a>

                                    <span class='inner' style="background:url('${file.fileURL}'); background-size:cover;"></span>
                               </span>`;
            const dom = domParser.parseFromString(photoTag, "text/html");
            const span = dom.querySelector("span");
            thumbs.appendChild(span);
            const inner = span.querySelector(".inner");
            inner.onclick = function() {
                showImagePopup(file.fileNo, file.fileURL);
            };
        }
    }
}

function showImagePopup(fileNo, fileURL) {
        const url = `/file/view/${fileNo}`;

        let w = 300, h = 300;
        const img = new Image();
        img.src = fileURL;
        img.onload = function() {
            if (img.width > 700) {
               w = 700;
               h = parseInt(img.width * w / img.height) + 150;
            }
            mozip.popup.open(url, "이미지 보기", w, h);
        };

}

<<<<<<< HEAD
=======
function insertImage() {
   const url = this.dataset.url;
   const img = `<img src='${url}' />`;
   CKEDITOR.instances.description.insertHtml(img);
}
>>>>>>> yonggyo2

/**
* 파일 삭제 콜백 =
*
* @param fileNo : 삭제된 파일 번호
*/
function fileDeleteCallback(fileNo) {
   if (!fileNo) {
        return;
   }

   const fileEl = document.querySelector(`.file_${fileNo}`);
   if (fileEl) fileEl.parentElement.removeChild(fileEl);

}