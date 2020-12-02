package rubikstudio.library.model;

import android.graphics.Bitmap;

/**
 * Created by kiennguyen on 11/5/16.
 */

public class LuckyItem {
    public String topText;
    public String secondaryText;
    public int secondaryTextOrientation;
    public Bitmap icon;
    public int color;

    public LuckyItem(String topText,Bitmap icon,int color) {
        this.topText=topText;
        this.icon=icon;
        this.color=color;
    }

    public int getColor() {
        return color;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getSecondaryTextOrientation() {
        return secondaryTextOrientation;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public String getTopText() {
        return topText;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public void setSecondaryTextOrientation(int secondaryTextOrientation) {
        this.secondaryTextOrientation = secondaryTextOrientation;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }
}

