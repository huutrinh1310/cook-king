package fptu.prm.cookcook.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.activity.SelfRecipesActivity;
import fptu.prm.cookcook.ui.adapter.SelfRecipeAdapter;

public class SelfFragment extends Fragment {
    private ImageView imgBack;
    private RecyclerView rcvSavedRecipes;
    private List<Recipe> listRecipe;
    private SelfRecipeAdapter savedRecipesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingView(view);
        loadData();
        bindingAction(view);

        savedRecipesAdapter = new SelfRecipeAdapter(getContext(), listRecipe, this::onItemSelected);
        rcvSavedRecipes.setAdapter(savedRecipesAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        rcvSavedRecipes.setLayoutManager(layoutManager);

    }

    private void onItemSelected(Recipe recipe) {
        ((SelfRecipesActivity) getActivity()).goToDetailRecipe(recipe);
    }

    private void bindingAction(View view) {
        imgBack.setOnClickListener(v -> getActivity().finish());
    }

    private void bindingView(View view) {
        imgBack = view.findViewById(R.id.imgBack);
        rcvSavedRecipes = view.findViewById(R.id.rcvSavedRecipes);
        listRecipe = new ArrayList<>();
    }

    private void loadData() {
        LiveData<List<Recipe>> listLiveData = RecipeDaoImpl.getInstance().getRecipeByUserId(FirebaseAuth.getInstance().getUid());
        listLiveData.observe(getViewLifecycleOwner(), recipes -> {
            listRecipe = recipes;
            savedRecipesAdapter.setListRecipe(listRecipe);
            savedRecipesAdapter.notifyDataSetChanged();
        });
    }
}