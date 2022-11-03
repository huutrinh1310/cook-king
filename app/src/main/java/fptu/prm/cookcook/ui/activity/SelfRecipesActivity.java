package fptu.prm.cookcook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.adapter.SelfRecipeAdapter;
import fptu.prm.cookcook.ui.fragment.DetailRecipeFragment;
import fptu.prm.cookcook.ui.fragment.SelfFragment;
import fptu.prm.cookcook.utils.ToastUtil;

public class SelfRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_recipes);
        replaceFragment(new SelfFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.self_recipes, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void goToDetailRecipe(Recipe recipe) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailRecipeFragment detailFragment = new DetailRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);

        detailFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.self_recipes, detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}