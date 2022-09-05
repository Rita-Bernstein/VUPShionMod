package VUPShionMod.msic;

import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;

public class AchievementShionItem extends AchievementItem {
    public static TextureAtlas achievemenAtlas;

    public AchievementShionItem(String title, String desc, String imgUrl, String key, boolean hidden) {
        super(title, desc, imgUrl, key, hidden);

        this.isUnlocked = SaveHelper.getAchievement(key);
        this.key = key;

        if (this.isUnlocked) {
            if (achievemenAtlas != null) {
                ReflectionHacks.setPrivate(this, AchievementItem.class, "img", achievemenAtlas.findRegion("unlocked/" + imgUrl));
            }
        } else if (hidden) {
            if (achievemenAtlas != null) {
                ReflectionHacks.setPrivate(this, AchievementItem.class, "img", achievemenAtlas.findRegion("locked/" + imgUrl));
            }
        } else {
            if (achievemenAtlas != null) {
                ReflectionHacks.setPrivate(this, AchievementItem.class, "img", achievemenAtlas.findRegion("locked/" + imgUrl));
            }
        }
    }

    @Override
    public void reloadImg() {
        ReflectionHacks.setPrivate(this, AchievementItem.class, "img",
                achievemenAtlas.findRegion( ((TextureAtlas.AtlasRegion) ReflectionHacks.getPrivate(this, AchievementItem.class, "img")).name));
    }

}
