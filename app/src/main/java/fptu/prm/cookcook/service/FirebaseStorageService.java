package fptu.prm.cookcook.service;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;

public class FirebaseStorageService {
    private static FirebaseStorageService instance;
    private static StorageReference storageReference;

    public static FirebaseStorageService getInstance(){
        if(instance == null){
            instance = new FirebaseStorageService();
        }
        return instance;
    }

    public static StorageReference getStorageReference(){
        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }

    public static void uploadImage(String path, Uri uri, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putFile(uri).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, byte[] data, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putBytes(data).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, InputStream stream, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putStream(stream).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, File file, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putFile(Uri.fromFile(file)).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, Uri uri, OnProgressListener<UploadTask.TaskSnapshot> onProgressListener, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putFile(uri).addOnProgressListener(onProgressListener).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, byte[] data, OnProgressListener<UploadTask.TaskSnapshot> onProgressListener, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putBytes(data).addOnProgressListener(onProgressListener).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void uploadImage(String path, InputStream stream, OnProgressListener<UploadTask.TaskSnapshot> onProgressListener, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        StorageReference ref = storageReference.child(path);
        ref.putStream(stream).addOnProgressListener(onProgressListener).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }
}
