package fptu.prm.cookcook.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class AccountMenu {
    private int id;
    @DrawableRes
    private int iconRes;
    @StringRes
    private int titleRes;
    @StringRes
    private int descriptionRes;
    private boolean isOption = false;

    public AccountMenu(int id, int iconRes, int titleRes, int descriptionRes, boolean isOption) {
        this.id = id;
        this.iconRes = iconRes;
        this.titleRes = titleRes;
        this.descriptionRes = descriptionRes;
        this.isOption = isOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public int getDescriptionRes() {
        return descriptionRes;
    }

    public void setDescriptionRes(int descriptionRes) {
        this.descriptionRes = descriptionRes;
    }

    public boolean isOption() {
        return isOption;
    }

    public void setOption(boolean option) {
        isOption = option;
    }
}
