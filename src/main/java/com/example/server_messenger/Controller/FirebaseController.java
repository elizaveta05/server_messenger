package com.example.server_messenger.Controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseController.class);
    @Autowired
    private FirebaseService firebaseService;
    // Инициализация Firebase Admin SDK для взаимодействия с Firestore.
    @PostConstruct
    public void initFirebaseApp() {
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\elozo\\OneDrive\\Рабочий стол\\Диплом\\Программа\\messenger-151eb-firebase-adminsdk-qspl3-4982389381.json");

            // Создание параметров конфигурации Firebase.
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("messenger-151eb.appspot.com")
                    .build();

            // Проверка наличия инициализированного FirebaseApp перед инициализацией.
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase Admin SDK успешно проинициализирован");
            }
        } catch (IOException e) {
            logger.error("Ошибка инициализации Firebase Admin SDK", e);
        }
    }
/*
    @PostMapping("/uploadImage")
    public ResponseEntity<String> saveImageFromStorage(
            @RequestParam String userId,
            @RequestParam("image") MultipartFile imageFile) {
        logger.info("Вызван метод контроллера firebase по сохранению изображения в хранилище");

        try {
            String imageUrl = firebaseService.uploadImageToStorage(imageFile, userId);
            logger.info("Ответ от контролера ", imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

 */

}
