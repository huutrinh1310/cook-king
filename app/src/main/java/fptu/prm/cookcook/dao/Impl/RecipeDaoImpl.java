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
import java.util.Map;

import fptu.prm.cookcook.dao.RecipeDao;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.service.FirebaseDatabaseService;

public class RecipeDaoImpl implements RecipeDao {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabaseService.getInstance();
    static final String FOOD_COLLECTION = "recipe";
    static final String FOOD_ID = "id";
    static final String FOOD_NAME = "title";
    static final String FOOD_INGREDIENTS = "ingredients";
    static final String FOOD_USER_ID = "accountId";
    static final String FOOD_DESCRIPTION = "description";
    static final String FOOD_IMAGE = "image";

    private static RecipeDaoImpl instance;

    public RecipeDaoImpl() {
    }

    public static RecipeDaoImpl getInstance() {
        if (instance == null) {
            instance = new RecipeDaoImpl();
        }
        return instance;
    }


    @Override
    public void getAllRecipe(RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> foodList = new ArrayList<Recipe>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe food = dataSnapshot.getValue(Recipe.class);
                    foodList.add(food);
                }
                callback.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFail(error.getMessage());
            }
        });
    }

    @Override
    public void getRecipesById(String foodId, RecipeCallback callback) {
        MutableLiveData<Recipe> recipe = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> foodList = new ArrayList<Recipe>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe food = dataSnapshot.getValue(Recipe.class);
                    foodList.add(food);
                }
                callback.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFail(error.getMessage());
            }
        });
    }

    @Override
    public LiveData<Recipe> getRecipeByUserId(String userId) {
        MutableLiveData<Recipe> recipe = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).orderByChild(FOOD_USER_ID).equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe food = snapshot.getValue(Recipe.class);
                recipe.setValue(food);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                recipe.setValue(null);
            }
        });
        return recipe;
    }

    @Override
    public void addRecipe(Recipe food, RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).push().setValue(food);
    }

    @Override
    public void updateRecipe(Map<String, Object> food, RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).updateChildren(food);
    }

    @Override
    public void deleteRecipe(String foodId, RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).child(foodId).removeValue();
    }
}
