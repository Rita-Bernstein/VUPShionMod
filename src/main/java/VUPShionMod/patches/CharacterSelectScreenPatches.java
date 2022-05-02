package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.skins.SkinManager;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import javassist.CtBehavior;

@SuppressWarnings("unused")
public class CharacterSelectScreenPatches {
    public static Color BLACK_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);


    public static SkinManager skinManager = new SkinManager();

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class CharacterSelectScreenPatch_Initialize {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            VUPShionMod.loadSkins();
            skinManager.initialize();
        }
    }


    //刚点击角色按钮
    @SpirePatch(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class CharacterSelectScreenPatch_JustSelected {
        @SpireInsertPatch(rloc = 45)
        public static void Insert(CharacterOption __instance) {
            CharacterSelectScreenPatches.skinManager.justSelected(__instance);
        }

    }


    //      渲染-立绘（确认按钮下面）
    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class CharacterSelectScreenPatch_portraitSkeleton {
        @SpireInsertPatch(rloc = 62)
        public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb) {
            CharacterSelectScreenPatches.skinManager.characterRender(__instance, sb);

        }
    }

    //      渲染-立绘（确认按钮上面）
    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class CharacterSelectScreenPatch_Render {
        @SpireInsertPatch(rloc = 80)
        public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
            CharacterSelectScreenPatches.skinManager.panelRender(__instance, sb);
        }
    }


    //    角色选择初始化
    @SpirePatch(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class CharacterOptionPatch_reloadAnimation {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance) {
        }
    }


    //    更新
    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class CharacterSelectScreenPatch_Update {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            CharacterSelectScreenPatches.skinManager.update(__instance);
        }
    }


//    紫音按钮改色

    @SpirePatch(
            clz = CharacterOption.class,
            method = "renderOptionButton"
    )
    public static class CharacterOptionGlowPatch {
        @SpireInsertPatch(rloc = 6)
        public static SpireReturn<Void> Insert(CharacterOption __instance, SpriteBatch sb) {
            Color glowColor = ReflectionHacks.getPrivate(__instance, CharacterOption.class, "glowColor");
            if (__instance.c.name.equals(CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("Shion")).NAMES[0])) {
                if (__instance.selected) {
                    glowColor.r = 0.0f;
                    glowColor.g = 1.0f;
                    glowColor.b = 1.0f;
                    sb.setColor(glowColor);
                } else {
                    sb.setColor(BLACK_OUTLINE_COLOR);
                }

            }
            if (__instance.c.name.equals(CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("Liyezhu")).NAMES[0])) {
                if (__instance.selected) {
                    glowColor.r = 1.0f;
                    glowColor.g = 1.0f;
                    glowColor.b = 1.0f;
                    sb.setColor(glowColor);
                } else {
                    sb.setColor(BLACK_OUTLINE_COLOR);
                }

            }
            return SpireReturn.Continue();
        }
    }
}