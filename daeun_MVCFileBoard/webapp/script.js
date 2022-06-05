function writeSave(){
	if(document.writeForm.writer.value == ""){
        alert("작성자를 입력하세요.");
        document.writeForm.writer.focus();
        return false;
    }
    if(document.writeForm.title.value == ""){
        alert("제목을 입력하세요.");
        document.writeForm.subject.focus();
        return false;
    }
    if(document.writeForm.content.value == ""){
        alert("내용을 입력하세요.");
        document.writeForm.content.focus();
        return false;
    }
    if(document.writeForm.uploadFile.value == ""){
        alert("파일을 선택하세요.");
        document.writeForm.uploadFile.focus();
        return false;
    }
}

function updateSave(){
	if(document.updateForm.title.value == "") {
		alert("제목을 입력하세요.");
		document.updateForm.title.focus();
		return false;
	}
	if(document.updateForm.content.value == "") {
		alert("내용을 입력하세요.");
		document.updateForm.content.focus();
		return false;
	}
	if(document.updateForm.uploadFile.value == "") {
		alert("파일을 선택하세요.");
		document.updateForm.uploadFile.focus();
		return false;
	}	
}



