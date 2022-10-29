package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fptu.prm.cookcook.IItemClickListener;
import fptu.prm.cookcook.databinding.ItemAccountMenuBinding;
import fptu.prm.cookcook.model.AccountMenu;
import fptu.prm.cookcook.ui.adapter.AccountMenuAdapter.AccountMenuViewHolder;

public class AccountMenuAdapter extends RecyclerView.Adapter<AccountMenuViewHolder> {
    private Context mContext;
    private ArrayList<AccountMenu> mAccountMenus;
    private IItemClickListener itemClickListener;

    public AccountMenuAdapter(Context mContext,
                              ArrayList<AccountMenu> mAccountMenus,
                              IItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mAccountMenus = mAccountMenus;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AccountMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAccountMenuBinding itemBinding = ItemAccountMenuBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new AccountMenuViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountMenuViewHolder holder, int position) {
        AccountMenu accountMenu = mAccountMenus.get(position);
        holder.bind(accountMenu);
    }

    @Override
    public int getItemCount() {
        return mAccountMenus.size();
    }

    public class AccountMenuViewHolder extends RecyclerView.ViewHolder {
        ItemAccountMenuBinding itemBinding;

        public AccountMenuViewHolder(@NonNull ItemAccountMenuBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bind(AccountMenu accountMenu) {
            Glide.with(mContext).load(accountMenu.getIconRes()).into(itemBinding.ivMenuIcon);
            itemBinding.tvMenuName.setText(accountMenu.getTitleRes());
            itemBinding.getRoot().setOnClickListener(
                    view -> itemClickListener.onClickAccountMenu(accountMenu)
            );
        }
    }
}
