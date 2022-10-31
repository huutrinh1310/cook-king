package fptu.prm.cookcook.dao.Impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import fptu.prm.cookcook.dao.AccountDao;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.service.FirebaseDatabaseService;
import fptu.prm.cookcook.utils.LoggerUtil;

public class AccountDaoImpl implements AccountDao {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabaseService.getInstance();
    static final String ACCOUNT_COLLECTION = "Account";
    private static AccountDaoImpl instance;

    public static AccountDaoImpl getInstance() {
        if (instance == null) {
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    private AccountDaoImpl() {
    }

    @Override
    public LiveData<Account> getAccountById(String id) {
        MutableLiveData<Account> account = new MutableLiveData<>();
        FirebaseDatabaseService.getReference(ACCOUNT_COLLECTION).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account1 = snapshot.getValue(Account.class);
                account.setValue(account1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                LoggerUtil.e(error.getMessage());
                account.setValue(null);
            }
        });
        return account;
    }
}
