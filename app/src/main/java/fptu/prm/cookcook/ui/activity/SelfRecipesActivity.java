package fptu.prm.cookcook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.adapter.SelfRecipeAdapter;

public class SelfRecipesActivity extends AppCompatActivity {
    private ImageView imgBack;
    private RecyclerView rcvSavedRecipes;
    private List<Recipe> listRecipe;
    private SelfRecipeAdapter savedRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_recipes);
        bindingView();
        loadData();
        bindingAction();

        savedRecipesAdapter = new SelfRecipeAdapter(this, listRecipe, this::onItemClick);
        rcvSavedRecipes.setAdapter(savedRecipesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        rcvSavedRecipes.setLayoutManager(layoutManager);
    }

    private void bindingAction() {
        imgBack.setOnClickListener(v -> finish());
    }

    private void bindingView() {
        imgBack = findViewById(R.id.imgBack);
        rcvSavedRecipes = findViewById(R.id.rcvSavedRecipes);
        listRecipe = new ArrayList<>();
    }

    private void onItemClick(Recipe recipe) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("recipeSaved", (Parcelable) recipe);
        intent.putExtra("fragment", "DetailFragment");
        startActivity(intent);
    }

    private void loadData() {
        LiveData<List<Recipe>> listLiveData = RecipeDaoImpl.getInstance().getRecipeByUserId(FirebaseAuth.getInstance().getUid());
        listLiveData.observe(this, recipes -> {
            listRecipe = recipes;
            savedRecipesAdapter.setListRecipe(listRecipe);
            savedRecipesAdapter.notifyDataSetChanged();
        });
    }
}