package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
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
    public static Texture campfire_Wc = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/" + (VUPShionMod.safeCampfire ? "Campfire2.png" : "Campfire.png"));
    public static Texture campfire_Li = ImageMaster.loadImage("VUPShionMod/characters/Liyezhu/Campfire.png");
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

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Wc, Settings.WIDTH / 2.0f - campfire_Wc.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Wc.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Wc.getWidth() / 2.0f, campfire_Wc.getHeight() / 2.0f, campfire_Wc.getWidth(), campfire_Wc.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Wc.getWidth(), campfire_Wc.getHeight(), false, false);
                return SpireReturn.Return();
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Li, Settings.WIDTH / 2.0f - campfire_Li.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Li.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Li.getWidth() / 2.0f, campfire_Li.getHeight() / 2.0f, campfire_Li.getWidth(), campfire_Li.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Li.getWidth(), campfire_Li.getHeight(), false, false);
                return SpireReturn.Return();
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

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Wc, Settings.WIDTH / 2.0f - campfire_Wc.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Wc.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Wc.getWidth() / 2.0f, campfire_Wc.getHeight() / 2.0f, campfire_Wc.getWidth(), campfire_Wc.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Wc.getWidth(), campfire_Wc.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Li, Settings.WIDTH / 2.0f - campfire_Li.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Li.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Li.getWidth() / 2.0f, campfire_Li.getHeight() / 2.0f, campfire_Li.getWidth(), campfire_Li.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Li.getWidth(), campfire_Li.getHeight(), false, false);
                return SpireReturn.Return();
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

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Wc, Settings.WIDTH / 2.0f - campfire_Wc.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Wc.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Wc.getWidth() / 2.0f, campfire_Wc.getHeight() / 2.0f, campfire_Wc.getWidth(), campfire_Wc.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Wc.getWidth(), campfire_Wc.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Li, Settings.WIDTH / 2.0f - campfire_Li.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f- campfire_Li.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Li.getWidth() / 2.0f, campfire_Li.getHeight() / 2.0f, campfire_Li.getWidth(), campfire_Li.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Li.getWidth(), campfire_Li.getHeight(), false, false);
                return SpireReturn.Return();
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

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Wc, Settings.WIDTH / 2.0f - campfire_Wc.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Wc.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Wc.getWidth() / 2.0f, campfire_Wc.getHeight() / 2.0f, campfire_Wc.getWidth(), campfire_Wc.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Wc.getWidth(), campfire_Wc.getHeight(), false, false);
                return SpireReturn.Return(null);
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                sb.draw(campfire_Li, Settings.WIDTH / 2.0f - campfire_Li.getWidth() / 2.0f + offSet_x * Settings.scale,
                        Settings.HEIGHT / 2.0f - campfire_Li.getHeight() / 2.0f + offSet_y * Settings.scale,
                        campfire_Li.getWidth() / 2.0f, campfire_Li.getHeight() / 2.0f, campfire_Li.getWidth(), campfire_Li.getHeight(),
                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Li.getWidth(), campfire_Li.getHeight(), false, false);
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

//    @SpirePatch(
//            clz = TheEndingScene.class,
//            method = "renderCampfireRoom"
//    )
//    public static class RenderCampfireRoomPatch {
//        @SpirePrefixPatch
//        public static SpireReturn<Void> Prefix(TheEndingScene scene, SpriteBatch sb) {
//            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
//                sb.setColor(Color.WHITE);
//                sb.setBlendFunction(770, 771);
//                sb.draw(campfire, Settings.WIDTH / 2.0f - campfire.getWidth() / 2.0f + offSet_x * Settings.scale,
//                        Settings.HEIGHT / 2.0f - campfire.getHeight() / 2.0f + offSet_y * Settings.scale,
//                        campfire.getWidth() / 2.0f, campfire.getHeight() / 2.0f, campfire.getWidth(), campfire.getHeight(),
//                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire.getWidth(), campfire.getHeight(), false, false);
//                return SpireReturn.Return(null);
//            }
//
//            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
//                sb.setColor(Color.WHITE);
//                sb.setBlendFunction(770, 771);
//                sb.draw(campfire_Wc, Settings.WIDTH / 2.0f - campfire_Wc.getWidth() / 2.0f + offSet_x * Settings.scale,
//                        Settings.HEIGHT / 2.0f - campfire_Wc.getHeight() / 2.0f + offSet_y * Settings.scale,
//                        campfire_Wc.getWidth() / 2.0f, campfire_Wc.getHeight() / 2.0f, campfire_Wc.getWidth(), campfire_Wc.getHeight(),
//                        scale * Settings.scale, scale * Settings.scale, 0.0F, 0, 0, campfire_Wc.getWidth(), campfire_Wc.getHeight(), false, false);
//                return SpireReturn.Return(null);
//            }
//
//            return SpireReturn.Continue();
//        }
//    }

    @SpirePatch(
            clz = CampfireBurningEffect.class,
            method = "update"
    )
    public static class CampfireBurningEffectPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireBurningEffect effect) {
            if (EnergyPanelPatches.isShionModChar()) {
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
            if (EnergyPanelPatches.isShionModChar()) {
                effect.isDone = true;
            }

            return SpireReturn.Continue();
        }
    }

}
