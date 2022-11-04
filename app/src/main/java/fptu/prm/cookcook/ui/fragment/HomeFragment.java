package fptu.prm.cookcook.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.CategoriesDao;
import fptu.prm.cookcook.dao.Impl.CategoriesDaoImpl;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Categories;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.storage.SharePreferenceManager;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.ui.adapter.ListCategoryAdapter;
import fptu.prm.cookcook.ui.adapter.RecipeAdapter;
import fptu.prm.cookcook.utils.LoggerUtil;

public class HomeFragment extends Fragment {

    private RecipeAdapter adapterRecipe;
    private RecipeAdapter adapterRecipeMore;
    private ListCategoryAdapter adapterCategory;
    private RecyclerView recyclerViewCategoryList,  recyclerViewNewestList, recyclerViewPopularList;
    private ImageView avatar;
    private TextView txtUsername;
    private List<Categories> categoriesList;
    private List<Recipe> recipesList;

    private void bindingView(View view) {
        recyclerViewCategoryList = view.findViewById(R.id.recyclerViewCate);
        recyclerViewNewestList = view.findViewById(R.id.recyclerViewNewest);
        avatar = view.findViewById(R.id.avatar);
        txtUsername = view.findViewById(R.id.txtUsername);
        recyclerViewPopularList = view.findViewById(R.id.recyclerViewPopu);
        recipesList = new ArrayList<>();
        categoriesList = new ArrayList<>();
    }

    private void bindingAction(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction(view);
//        recyclerViewCategory();
        loadUser();
        loadRecipe();
        loadRandomRecipe();
        recyclerViewCategory();
        adapterRecipe = new RecipeAdapter(getContext(), recipesList, R.layout.item_card_add_screen, this::onItemClick);
        recyclerViewNewestList.setAdapter(adapterRecipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewestList.setLayoutManager(linearLayoutManager);

        adapterRecipeMore = new RecipeAdapter(getContext(), recipesList, R.layout.item_popular, this::onItemClick);
        recyclerViewPopularList.setAdapter(adapterRecipeMore);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager1);
    }

    private void loadUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txtUsername.setText(user.getDisplayName());
        Glide.with(getContext())
                .load(user.getPhotoUrl())
                .transform(new RoundedCorners(500))
                .into(avatar);
    }

    private void recyclerViewCategory() {
        loadCate();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        adapterCategory = new ListCategoryAdapter(getContext(), categoriesList);
        recyclerViewCategoryList.setAdapter(adapterCategory);
    }

    private void onItemClick(Recipe recipe) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.goToDetailRecipe(recipe);
    }

    private void loadCate() {
        LiveData<List<Categories>> listCate = CategoriesDaoImpl.getInstance().getAllCategories();
        listCate.observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoriesList = categories;
                adapterCategory.setCategories(categoriesList);
                adapterCategory.notifyDataSetChanged();
            }
        });
    }

    private void loadRecipe() {
        LiveData<List<Recipe>> listRecipe = RecipeDaoImpl.getInstance().getAllRecipe();
        listRecipe.observe(getViewLifecycleOwner(), recipeList -> {
            this.recipesList = recipeList;
            ((RecipeAdapter) adapterRecipeMore).setListRecipe(recipesList);
            adapterRecipe.notifyDataSetChanged();
        });
    }
    private void loadRandomRecipe(){
        LiveData<List<Recipe>> listRecipe = RecipeDaoImpl.getInstance().getAllRecipe();
        listRecipe.observe(getViewLifecycleOwner(), recipeList -> {
            Collections.shuffle(recipeList, new Random());
            this.recipesList = recipeList;
            if(recipeList.size() >= 3){
                recipesList = recipeList.subList(0, 3);
            }
            ((RecipeAdapter) adapterRecipe).setListRecipe(recipesList);
            adapterRecipe.notifyDataSetChanged();
        });
    }
}