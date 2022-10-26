package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Recipe;

public class AddRecipeAdapter extends RecyclerView.Adapter<AddRecipeViewHolder> {
    private Context mContext;
    private List<Recipe> recipeList;
    private final LayoutInflater layoutInflater;

    public AddRecipeAdapter(Context context, List<Recipe> recipeList) {
        this.mContext = context;
        this.recipeList = recipeList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setListRecipe(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public AddRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        return new AddRecipeViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AddRecipeViewHolder holder, int position) {
        holder.setRecipeItem(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }
        return 0;
    }
}
