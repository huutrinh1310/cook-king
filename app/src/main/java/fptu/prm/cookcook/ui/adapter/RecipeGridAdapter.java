package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Recipe;

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridViewHolder> {
    // list of recipes
    private LayoutInflater layoutInflater;
    private List<Recipe> recipeList;
    private Context context;

    public RecipeGridAdapter(List<Recipe> recipeList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.recipeList = recipeList;
        this.context = context;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeGridViewHolder(layoutInflater.inflate(R.layout.item_card_recipe_grid, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeGridViewHolder holder, int position) {
        holder.setRecipeItem(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
