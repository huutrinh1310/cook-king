package fptu.prm.cookcook.dao.callback;

public interface RecipeCallback {

    void onSuccess(Object... object);

    void onFail(String message);
}
