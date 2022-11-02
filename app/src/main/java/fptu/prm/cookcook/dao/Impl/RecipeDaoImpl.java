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
import fptu.prm.cookcook.entities.Ingredients;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.entities.Step;
import fptu.prm.cookcook.service.FirebaseDatabaseService;
import fptu.prm.cookcook.utils.LoggerUtil;

public class RecipeDaoImpl implements RecipeDao {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabaseService.getInstance();
    static final String FOOD_COLLECTION = "recipe";
    static final String FOOD_USER_ID = "accountId";
    static final String FOOD_ID = "id";
    static final String FOOD_INGREDIENTS = "ingredients";
    static final String FOOD_DESCRIPTION = "description";
    static final String FOOD_READY_IN_MINUTES = "readyInMinutes";
    static final String FOOD_SERVINGS = "servings";
    static final String FOOD_STEPS = "steps";
    static final String FOOD_IMAGE = "image";
    static final String FOOD_NAME = "title";

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
    public LiveData<List<Recipe>> getAllRecipe() {
        MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> foodList = new ArrayList<Recipe>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe food = new Recipe();
//                    food.setId((Integer.parseInt(dataSnapshot.child(FOOD_ID).getValue().toString())));
//                    food.setTitle(dataSnapshot.child(FOOD_NAME).getValue(String.class));
                    LoggerUtil.d(dataSnapshot.getValue().toString() + " recipe : " + dataSnapshot.child(FOOD_ID).getValue().toString() + " " + (dataSnapshot.child(FOOD_INGREDIENTS).getValue() instanceof Map));
////                    if(dataSnapshot.child(FOOD_INGREDIENTS).getValue() instanceof Map){
//                    food.setIngredients((Map<String, Ingredients>) dataSnapshot.child(FOOD_INGREDIENTS).getValue());
////                    } else{
////                        food.setIngredients(null);
////                    }
//                    food.setAccountId(dataSnapshot.child(FOOD_USER_ID).getValue(String.class));
//                    food.setDescription(dataSnapshot.child(FOOD_DESCRIPTION).getValue(String.class));
//                    food.setImage(dataSnapshot.child(FOOD_IMAGE).getValue(String.class));
//                    food.setReadyInMinutes(dataSnapshot.child(FOOD_READY_IN_MINUTES).getValue(Integer.class));
//                    food.setServings(dataSnapshot.child(FOOD_SERVINGS).getValue(String.class));
////                    if(dataSnapshot.child(FOOD_STEPS).getValue() instanceof Map){
//                    food.setSteps((Map<String, Step>) dataSnapshot.child(FOOD_STEPS).getValue());
////                    }else{
////                    food.setSteps(null);
////                    }
//                    foodList.add(food);
                    foodList.add(dataSnapshot.getValue(Recipe.class));
                }
                recipeList.setValue(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                recipeList.setValue(null);
            }
        });
        return recipeList;
    }

    @Override
    public LiveData<List<Recipe>> getRecipesById(String foodId) {
        MutableLiveData<List<Recipe>> recipe = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> foodList = new ArrayList<Recipe>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe food = dataSnapshot.getValue(Recipe.class);
                    foodList.add(food);
                }
                recipe.setValue(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                recipe.setValue(null);
            }
        });
        return recipe;
    }

    @Override
    public LiveData<List<Recipe>> getRecipeByUserId(String userId) {
        MutableLiveData<List<Recipe>> recipe = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).orderByChild(FOOD_USER_ID).equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> foodList = new ArrayList<Recipe>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe food = dataSnapshot.getValue(Recipe.class);
                    foodList.add(food);
                }
                recipe.setValue(foodList);
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
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).child(food.getAccountId() + "_" + food.getId()).push().setValue(food);
    }

    @Override
    public void updateRecipe(Map<String, Object> food, RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).updateChildren(food);
    }

    @Override
    public void deleteRecipe(int foodId, RecipeCallback callback) {
        FirebaseDatabaseService.getReference(FOOD_COLLECTION).child(String.valueOf(foodId)).removeValue();
    }
}
