package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Recipe;

public class RecipeGridViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ImageView imgRecipeGrid;
    private TextView txtRecipeGrid;

    private void bindingView(View itemView) {
        imgRecipeGrid = itemView.findViewById(R.id.imgRecipeGrid);
        txtRecipeGrid = itemView.findViewById(R.id.txtRecipeGrid);
    }



    public RecipeGridViewHolder(@NonNull View itemView,Context mContext) {
        super(itemView);
        this.mContext = mContext;
        bindingView(itemView);

    }

    public void setRecipeItem(Recipe recipe) {
        Glide.with(mContext)
                .load(recipe.getImage())
                .into(imgRecipeGrid);
        txtRecipeGrid.setText(recipe.getTitle());
    }
}
