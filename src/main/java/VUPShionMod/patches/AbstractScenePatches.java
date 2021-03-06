package VUPShionMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.vfx.campfire.CampfireBurningEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireEndingBurningEffect;

@SuppressWarnings("unused")
public class AbstractScenePatches {
    public static Texture campfire = ImageMaster.loadImage("VUPShionMod/characters/Shion/Campfire.png");
    public static final float scale = 2.0f;
    public static final float offSet_x = 0.0f;
    public static final float offSet_y = -100.0f;


    @SpirePatch(
            clz = TheBottomScene.class,
            method = "renderCampfireRoom"
    )
    public static class TheBottomScenePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheBottomScene scene, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = TheCityScene.class,
            method = "renderCampfireRoom"
    )
    public static class TheCityScenePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheCityScene scene, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = TheBeyondScene.class,
            method = "renderCampfireRoom"
    )
    public static class TheBeyondScenePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheBeyondScene scene, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = TheEndingScene.class,
            method = "renderCampfireRoom"
    )
    public static class TheEndingScenePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheEndingScene scene, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = TheEndingScene.class,
            method = "renderCampfireRoom"
    )
    public static class RenderCampfireRoomPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheEndingScene scene, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CampfireBurningEffect.class,
            method = "update"
    )
    public static class CampfireBurningEffectPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireBurningEffect effect) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                effect.isDone = true;
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CampfireEndingBurningEffect.class,
            method = "update"
    )
    public static class CampfireEndingBurningEffectPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireEndingBurningEffect effect) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                effect.isDone = true;
            }

            return SpireReturn.Continue();
        }
    }

}
