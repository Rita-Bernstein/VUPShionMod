package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.EisluRen;
import VUPShionMod.msic.CharacterPriority;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.util.SaveHelper;
import basemod.BaseMod;
import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

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
            SaveHelper.loadSkins();

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

            if (__instance.c.name.equals(CardCrawlGame.languagePack.getCharacterString(VUPShionMod.makeID("EisluRen")).NAMES[0])) {
                if (__instance.selected) {
                    glowColor.r = 0.0f;
                    glowColor.g = 1.0f;
                    glowColor.b = 0.0f;
                    sb.setColor(glowColor);
                } else {
                    sb.setColor(BLACK_OUTLINE_COLOR);
                }

            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class AddFields {
        public static SpireField<CharacterPriority> characterPriority = new SpireField<>(() -> new CharacterPriority());
    }

    @SpirePatch(
            clz = CustomCharacterSelectScreen.class,
            method = "initialize"
    )
    public static class CharacterSelectScreenPatch_BasemodInitialize {
        @SpireInsertPatch(rloc = 5)
        public static SpireReturn<Void> Insert(CustomCharacterSelectScreen __instance) {
            for (CharacterOption option : __instance.options) {
                if (option.c instanceof Watcher) {
                    AddFields.characterPriority.get(option.c).setCharacterPriority(-1);
                }

                if (option.c instanceof Defect) {
                    AddFields.characterPriority.get(option.c).setCharacterPriority(-2);
                }

                if (option.c instanceof TheSilent) {
                    AddFields.characterPriority.get(option.c).setCharacterPriority(-3);
                }

                if (option.c instanceof Ironclad) {
                    AddFields.characterPriority.get(option.c).setCharacterPriority(-4);
                }
            }


            __instance.options.sort(Comparator.comparing(o -> AddFields.characterPriority.get(o.c).getCharacterPriority()));

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = BaseMod.class,
            method = "generateCustomCharacterOptions"
    )
    public static class CharacterSelectScreenPatch_BasemodGenerateCustomCharacterOptions{
        @SpireInsertPatch(rloc = 4,localvars = {"options"})
        public static void Insert(ArrayList<CustomModeCharacterButton> options) {
            options.removeIf(b -> b.c instanceof EisluRen);

        }
    }

}