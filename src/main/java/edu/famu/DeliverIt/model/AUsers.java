package edu.famu.DeliverIt.model;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AUsers {
    @DocumentId
    private @Nullable String userId;
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private String role;
    private @Nullable GeoPoint location;
}
