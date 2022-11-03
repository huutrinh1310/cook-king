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
import fptu.prm.cookcook.ui.activity.MainActivity;

public class SelfRecipeAdapter extends RecyclerView.Adapter<SelfRecipeViewHolder> {
    private Context mContext;
    private List<Recipe> recipeList;
    private final LayoutInflater layoutInflater;
    private MainActivity mainActivity;
    private RecipeAdapter.IClickItemListener mIClickItemListener;

    public SelfRecipeAdapter(Context mContext, List<Recipe> recipeList, RecipeAdapter.IClickItemListener mIClickItemListener) {
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mIClickItemListener = mIClickItemListener;
    }

    public interface IClickItemListener {
        void onItemClick(Recipe recipe);
    }

    @NonNull
    @Override
    public SelfRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_self_recipe, parent, false);
        return new SelfRecipeViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull SelfRecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.setRecipeItem(recipe);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onItemClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setListRecipe(List<Recipe> listRecipe) {
        this.recipeList = listRecipe;
    }
}
