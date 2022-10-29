package fptu.prm.cookcook.dao.callback;

import fptu.prm.cookcook.entities.Account;

public interface AccountCallBack {
    void onSuccess(Object object);
    void onFail(String message);
}
