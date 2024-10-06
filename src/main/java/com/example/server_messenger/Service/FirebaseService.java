package com.example.server_messenger.Service;

import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseService {

    public String uploadImageToStorage(MultipartFile imageFile, String userId) throws IOException {
        // Получаем доступ к хранилищу Firebase
        Bucket bucket = StorageClient.getInstance().bucket();

        // Задаем путь для сохранения изображения
        String blobString = "profile_images/" + userId + ".jpg";

        // Создаем BlobInfo с информацией о загружаемом файле
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), blobString)
                .setContentType(imageFile.getContentType())
                .build();

        // Загружаем файл в Firebase Storage
        bucket.create(blobString, imageFile.getInputStream(), imageFile.getContentType());

        // Возвращаем ссылку на загруженное изображение
        return "gs://" + bucket.getName() + "/" + blobString;
    }
}
