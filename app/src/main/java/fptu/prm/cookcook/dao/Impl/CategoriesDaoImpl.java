package fptu.prm.cookcook.dao.Impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.dao.CategoriesDao;
import fptu.prm.cookcook.entities.Categories;
import fptu.prm.cookcook.service.FirebaseDatabaseService;
import fptu.prm.cookcook.utils.LoggerUtil;

public class CategoriesDaoImpl implements CategoriesDao {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabaseService.getInstance();
    static final String CATEGORIES_COLLECTION = "Categories";
    private static CategoriesDaoImpl instance;

    public static CategoriesDaoImpl getInstance() {
        if (instance == null) {
            instance = new CategoriesDaoImpl();
        }
        return instance;
    }

    @Override
    public LiveData<List<Categories>> getAllCategories() {
        MutableLiveData<List<Categories>> categoriesMutableLiveData = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(CATEGORIES_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Categories> categoriesList = new ArrayList<Categories>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Categories categories = dataSnapshot.getValue(Categories.class);
                    categoriesList.add(categories);
                }
                categoriesMutableLiveData.setValue(categoriesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                LoggerUtil.e(error.getMessage());
                categoriesMutableLiveData.setValue(null);
            }
        });
        return categoriesMutableLiveData;
    }
}
