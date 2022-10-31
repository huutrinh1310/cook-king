package fptu.prm.cookcook.dao;

import androidx.lifecycle.LiveData;

import fptu.prm.cookcook.entities.Account;

public interface AccountDao {

    LiveData<Account> getAccountById(String id);
}
