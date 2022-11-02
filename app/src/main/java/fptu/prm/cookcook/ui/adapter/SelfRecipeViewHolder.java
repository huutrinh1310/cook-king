package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Recipe;

public class SelfRecipeViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ImageView imgUserOfSavedRecipe;
    private TextView txtUserNameOfSavedRecipe, txtNameOfSavedRecipe, txtTimeOfSavedRecipe;
    private ImageView imgSavedRecipe;


    public SelfRecipeViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        bindingView(itemView);
        bindingAction(itemView);
    }
    private void bindingAction(View itemView) {

    }

    private void bindingView(View itemView) {
        imgUserOfSavedRecipe = itemView.findViewById(R.id.imgUserOfSavedRecipe);
        txtUserNameOfSavedRecipe = itemView.findViewById(R.id.txtUserNameOfSavedRecipe);
        txtNameOfSavedRecipe = itemView.findViewById(R.id.txtNameOfSavedRecipe);
        txtTimeOfSavedRecipe = itemView.findViewById(R.id.txtTimeOfSavedRecipe);
        imgSavedRecipe = itemView.findViewById(R.id.imgSavedRecipe);
    }

    public void setRecipeItem(Recipe recipe) {
        LiveData<Account> account = recipe.getAccount();
        //set data to item
        account.observe((LifecycleOwner) mContext, item->{
            txtUserNameOfSavedRecipe.setText(item.getName());
            Glide.with(mContext)
                    .load(item.getAvatar())
                    .apply(new RequestOptions().bitmapTransform(new RoundedCorners(500)))
                    .into(imgUserOfSavedRecipe);
        });
        txtNameOfSavedRecipe.setText(recipe.getTitle());
        txtTimeOfSavedRecipe.setText(recipe.getReadyInMinutes() + "");
        Glide.with(mContext)
                .load(recipe.getImage())
                .into(imgSavedRecipe);
    }
}
