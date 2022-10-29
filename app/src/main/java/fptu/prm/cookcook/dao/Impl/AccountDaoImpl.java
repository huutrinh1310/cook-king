package fptu.prm.cookcook.dao.Impl;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import fptu.prm.cookcook.dao.AccountDao;
import fptu.prm.cookcook.dao.callback.AccountCallBack;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.service.FirebaseDatabaseService;

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
    public void getAccountById(int id, AccountCallBack callback) {
        FirebaseDatabaseService.getReference(ACCOUNT_COLLECTION).child(String.valueOf(id)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                callback.onSuccess(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFail(error.getMessage());
            }
        });
    }

    @Override
    public void getAccountById(String id, AccountCallBack callback) {
        FirebaseDatabaseService.getReference(ACCOUNT_COLLECTION).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                callback.onSuccess(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFail(error.getMessage());
            }
        });
    }
}
