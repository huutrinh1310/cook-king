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

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.storage.SharePreferenceManager;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.utils.LoggerUtil;
import fptu.prm.cookcook.utils.ToastUtil;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ImageView imgRecipe;
    private TextView txtRecipeName;
    private TextView txtRecipeDescription;
    private TextView ready_time;
    private ImageView imgAuthor;
    private TextView txtAuthorName;
    private TextView txtAuthorId;

    public RecipeViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        bindingView(itemView);
        bindingAction(itemView);
    }

    private void bindingAction(View itemView) {
        if (txtAuthorName != null)
            txtAuthorName.setOnClickListener(this::goToProfile);
    }

    private void goToProfile(View view) {
        //Callback to MainActivity
        ((MainActivity) mContext).goToProfile(txtAuthorId.getText().toString());
    }

    private void bindingView(View itemView) {
        imgRecipe = itemView.findViewById(R.id.imgRecipe);
        txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
        txtRecipeDescription = itemView.findViewById(R.id.txtRecipeDescription);
        ready_time = itemView.findViewById(R.id.ready_time);
        imgAuthor = itemView.findViewById(R.id.imgAuthor);
        txtAuthorName = itemView.findViewById(R.id.txtAuthorName);
        txtAuthorId = itemView.findViewById(R.id.txtAuthorId);
    }

    public void setRecipeItem(Recipe recipe) {
        //set data to item
        setImgToView(imgRecipe, recipe.getImage());
        setTextToView(txtRecipeName, recipe.getTitle());
        setTextToView(txtRecipeDescription, String.valueOf(recipe.getReadyInMinutes()));
        setTextToView(ready_time, recipe.getMinutes());
        setAuthorToView(recipe.getAccount());
    }

    private void setAuthorToView(LiveData<Account> account) {
        if (imgAuthor != null && txtAuthorName != null) {
            account.observe((LifecycleOwner) mContext, account1 -> {
                if (account1 != null) {
                    setImgToView(imgAuthor, account1.getAvatar());
                    setTextToView(txtAuthorName, account1.getName());
                    setTextToView(txtAuthorId, account1.getId());
                    LoggerUtil.d("Account: " + account1.getId());
                }
            });
        }
    }

    private void setImgToView(ImageView imgRecipe, String image) {
        if (image != null && !image.isEmpty()) {
            Glide.with(mContext)
                    .load(image)
                    .into(imgRecipe);
        }
    }

    //    check view not null
    public void setTextToView(View view, String value) {
        if (view != null) {
            ((TextView) view).setText(value);
        }
    }
}
