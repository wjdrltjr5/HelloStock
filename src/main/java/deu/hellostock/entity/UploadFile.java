package deu.hellostock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;  //파일 덮어짐 방지를 위해



}
