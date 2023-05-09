window.addEventListener("DOMContentLoaded", function() {
    const profileImage = document.getElementById("profileImage");
    profileImage.addEventListener("dragover", function(e) {
        e.preventDefault();
    });

    profileImage.addEventListener("drop", function(e) {
        e.preventDefault();
        const files = e.dataTransfer.files;
         const gid = joinFrm.gid.value;

         if (!files || files.length == 0 || !gid) {
            return;
         }
         if (files.length > 1) {
            alert("파일을 1개만 선택해 주세요.");
            return;
         }

         mozip.fileManager.uploads(files, gid, null, true);
    });
});

function fileUploadCallback(files) {
    if (!files || files.length == 0) {
        return;
    }

    const profileImage = document.getElementById("profileImage");

    const file = files[0];
    profileImage.style.background=`url('${file.fileURL}')`;
    profileImage.style.backgroundSize="cover";
    profileImage.dataset.fileNo=file.fileNo;
}