package PA.distribuidor.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    private void initFirestore() throws IOException {

        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("gestion-alimentos-firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://Gestion-alimentos.firebase.io")
                .build();

        if (FirebaseApp.getApps().isEmpty())
        FirebaseApp.initializeApp(options);
    }

    public Firestore getFirestore(){
    return FirestoreClient.getFirestore();
    }


}

