function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new YourUploadAdapter(loader)
    }
}

class YourUploadAdapter {
    constructor(loader) {
        // 파일 로더(loader)를 저장합니다.
        this.loader = loader;
    }

    upload() {
        // 파일 업로드 로직을 여기에 작성합니다.
        // 서버로 파일을 업로드하고, 업로드된 파일의 URL을 반환해야 합니다.

        return this.loader.file
            .then(file => {
                // 파일을 서버로 업로드하는 비동기 작업을 수행합니다.
                // 예시: Ajax 요청을 사용하여 파일을 업로드하고, 업로드된 파일의 URL을 반환합니다.

                return new Promise((resolve, reject) => {
                    // 파일 업로드에 필요한 Ajax 요청을 수행합니다.
                    // 성공적으로 업로드되면 resolve()를 호출하여 업로드된 파일의 URL을 전달합니다.
                    // 실패하면 reject()를 호출하여 실패한 이유를 전달합니다.

                    // 예시 코드:
                    const xhr = new XMLHttpRequest();
                    xhr.open('POST', '/board/upload-image', true);
                    // xhr.setRequestHeader('Content-Type', 'multipart/form-data');

                    xhr.onload = () => {
                        if (xhr.status === 200) {
                            // const response = JSON.parse(xhr.responseText);
                            const response = xhr.responseText;
                            console.log('response:' + response);
                            // const imageUrl = response;
                            // console.log('imageUrl:' + imageUrl);
                            resolve({ default: response });
                        } else {
                            reject('File upload failed');
                        }
                    };

                    xhr.onerror = () => {
                        reject('File upload failed');
                    };

                    // FormData를 사용하여 파일 데이터를 전송합니다.
                    const formData = new FormData();
                    formData.append('file', file);
                    xhr.send(formData);
                });
            });
    }

    abort() {
        // 파일 업로드 중단 로직을 추가할 수 있습니다.
    }
}