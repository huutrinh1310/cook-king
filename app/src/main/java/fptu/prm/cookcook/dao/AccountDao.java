package fptu.prm.cookcook.dao;

import fptu.prm.cookcook.dao.callback.AccountCallBack;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Account;

public interface AccountDao {
    void getAccountById(int id, AccountCallBack callback);

    void getAccountById(String id, AccountCallBack callback);
}
