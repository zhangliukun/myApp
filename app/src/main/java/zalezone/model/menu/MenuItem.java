package zalezone.model.menu;

/**
 * Created by zale on 16/8/29.
 */
public class MenuItem {
    public static final int TYPE_NORMAL=0;
    public static final int TYPE_NO_ICON=1;

    public MenuItem(String menuText) {
        this.menuText = menuText;
    }

    public String menuText;
}
