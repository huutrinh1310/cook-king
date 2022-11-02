package fptu.prm.cookcook.ui.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import fptu.prm.cookcook.databinding.ItemUserInfoBinding;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> {
    private ArrayList<Pair> mUserInfo;

    public UserInfoAdapter(ArrayList<Pair> mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    @NonNull
    @Override
    public UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserInfoBinding viewBinding = ItemUserInfoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new UserInfoViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {
        Pair pair = mUserInfo.get(position);
        holder.bind(pair);
    }

    @Override
    public int getItemCount() {
        return mUserInfo.size();
    }

    class UserInfoViewHolder extends RecyclerView.ViewHolder {
        private ItemUserInfoBinding itemBinding;

        public UserInfoViewHolder(@NonNull ItemUserInfoBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bind(@NonNull Pair pair) {
            itemBinding.tvTitle.setText(Objects.requireNonNull(pair.first).toString());
            itemBinding.tvContent.setText(Objects.requireNonNull(pair.second).toString());
        }
    }
}
