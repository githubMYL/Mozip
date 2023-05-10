window.addEventListener("DOMContentLoaded", function() {
    const profileImage = document.getElementById("profileImage");
    profileImage.addEventListener("dragover", function(e) {
        e.preventDefault();
    });

    profileImage.addEventListener("drop", async function(e) {
        e.preventDefault();
        const files = e.dataTransfer.files;
         const gid = joinFrm.gid.value;
         console.log(files);
         if (!files || files.length == 0 || !gid) {
            return;
         }
         if (files.length > 1) {
            alert("파일을 1개만 선택해 주세요.");
            return;
         }
        /** 기존 파일 삭제 후 파일 업로드 처리 */
        mozip.fileManager.delete(gid)
            .then((result) =>  mozip.fileManager.uploads(files, gid, null, true))
            .catch((err) => console.log(err));
    });
});

function fileUploadCallback(files) {
    console.log(files);
    if (!files || files.length == 0) {
        return;
    }
    const file = files[0];
    const profileImage = document.getElementById("profileImage");

    profileImage.style.background=`url('${file.fileURL}')`;
    profileImage.style.backgroundSize="cover";
    profileImage.dataset.fileNo=file.fileNo;
}