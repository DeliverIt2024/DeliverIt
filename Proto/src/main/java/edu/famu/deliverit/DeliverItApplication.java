package edu.famu.deliverit;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class DeliverItApplication {

    public static void main(String[] args) throws IOException {
        //This code links your firebase to java, but the line below this is what changes. It has to be the name of the class.
        ClassLoader loader = DeliverItApplication.class.getClassLoader();

        File file = new File(loader.getResource("serviceAccountKey.json").getFile());

        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        if(FirebaseApp.getApps().isEmpty())
            FirebaseApp.initializeApp(options);

        SpringApplication.run(DeliverItApplication.class, args);

    }

}
