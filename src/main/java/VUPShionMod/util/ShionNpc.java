package VUPShionMod.util;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.skins.SkinManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShionNpc extends AnimatedNpc {
    public ShionNpc() {
        super(1534.0F * Settings.xScale, AbstractDungeon.floorY - 60.0F * Settings.yScale,
                "images/npcs/neow/skeleton.atlas", "images/npcs/neow/skeleton.json", "idle");

        try {
            Method method = AnimatedNpc.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
            method.setAccessible(true);
            method.invoke(this, SkinManager.getSkin(0, 0).atlasURL,
                    SkinManager.getSkin(0, 0).jsonURL,
                    SkinManager.getSkin(0, 0).renderScale);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        this.skeleton.setPosition(1534.0F * Settings.xScale, AbstractDungeon.floorY - 60.0F * Settings.yScale);


        try {
            Field field = AnimatedNpc.class.getDeclaredField("state");
            field.setAccessible(true);
            AnimationState state = (AnimationState) field.get(this);

            state.setAnimation(0, "idle", true);
            state.setAnimation(4, "wing_idle", true);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.skeleton.setFlip(true, false);
    }
}
