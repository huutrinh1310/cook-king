package fptu.prm.cookcook.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fptu.prm.cookcook.utils.ToastUtil;

public class FirebaseService {
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    public static FirebaseDatabase getInstance(){
        if(database == null){
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    public static void setKeyValue(String key, Object value){
        myRef = database.getReference(key);
        myRef.setValue(value);
    }

    public static void updateKeyValue(String key, Object value){
        myRef = database.getReference(key);
        myRef.updateChildren((java.util.Map<String, Object>) value);
    }

    public static void deleteKeyValue(String key){
        myRef = database.getReference(key);
        myRef.removeValue();
    }

    public static DatabaseReference getReference(String key){
        myRef = database.getReference(key);
        return myRef;
    }

    // read data from firebase
    public static <T> void readDatabase(Context context, String references, Class<T> tClass, FirebaseDatabase db){
        myRef = db.getReference(references);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                T value = (T) dataSnapshot.getValue(tClass);
                if(value == null){
                    ToastUtil.error(context, "No data");
                }else{
                    ToastUtil.success(context, "Success");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                ToastUtil.success(context, "Value is: failed");
            }
        });
    }
}
