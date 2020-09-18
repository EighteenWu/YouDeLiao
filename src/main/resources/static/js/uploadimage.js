$(document).ready(function () {
    $('#summernote').summernote({
        toolbar: [
            // [groupName, [list of button]]
            ['style', ['bold', 'italic', 'underline', 'clear']],
            ['fontsize', ['fontsize']],
            ['para', ['ul', 'ol',]],
            ['height', ['height']],
            ['insert',['picture','link','hr']],
            ['misc',['code','codeview','fullscreen']],
        ],
        lang:'zh-CN',
    });
})





