package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.skins.sk.Liyezhu.AbstractSkinLiyezhu;
import VUPShionMod.skins.sk.Shion.AbstractSkinShion;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.skins.sk.WangChuan.AbstractSkinWangChuan;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import javassist.CtBehavior;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CharacterSelectScreenPatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SkinPannel"));
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String[] EXTRA_TEXT = uiStrings.EXTRA_TEXT;

    public static Hitbox reskinRight;
    public static Hitbox reskinLeft;
    public static Hitbox reskinLock;
    public static Texture CF_RIGHT_ARROW = ImageMaster.loadImage("VUPShionMod/img/ui/CF_RIGHT_ARROW.png");
    public static Texture CF_LEFT_ARROW = ImageMaster.loadImage("VUPShionMod/img/ui/CF_LEFT_ARROW.png");
    public static Texture Lock_Icon = ImageMaster.loadImage("VUPShionMod/img/ui/SkinLock.png");


    private static float reskin_Text_W = 50.0f * Settings.scale;  //FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float reskin_W = reskin_Text_W + 200.0f * Settings.scale;
    private static float reskinX_center = 630.0F * Settings.scale;
    public static float allTextInfoX = 0.0f;

    private static boolean bgIMGUpdate = false;

    private static float buttonScale = 0.5f;
    private static float buttonY = 400.0f;


    public static ArrayList<AbstractGameEffect> char_effectsQueue = new ArrayList();
    public static ArrayList<AbstractGameEffect> char_effectsQueue_toRemove = new ArrayList();

    public static AbstractSkinCharacter[] characters = new AbstractSkinCharacter[]{
            new AbstractSkinShion(),
            new AbstractSkinWangChuan(),
            new AbstractSkinLiyezhu()
    };

    public static Color BLACK_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);

    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            VUPShionMod.loadSettings();
            char_effectsQueue.clear();

            reskinRight = new Hitbox(138.0f * Settings.scale * buttonScale, 102.0f * Settings.scale * buttonScale);
            reskinLeft = new Hitbox(138.0f * Settings.scale * buttonScale, 102.0f * Settings.scale * buttonScale);
            reskinLock = new Hitbox(138.0f * Settings.scale * buttonScale, 102.0f * Settings.scale * buttonScale);

            reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);
            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);
            reskinLock.move(Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);

            CF_RIGHT_ARROW = ImageMaster.loadImage("VUPShionMod/img/ui/CF_RIGHT_ARROW.png");
            CF_LEFT_ARROW = ImageMaster.loadImage("VUPShionMod/img/ui/CF_LEFT_ARROW.png");


        }

        @SpirePatch(
                clz = CharacterOption.class,
                method = "renderInfo"
        )
        public static class CharacterOptionRenderInfoPatch {
            @SpireInsertPatch(locator = renderRelicsLocator.class, localvars = {"infoX", "charInfo", "flavorText"})
            public static SpireReturn<Void> Insert(CharacterOption _instance, SpriteBatch sb, float infoX, CharSelectInfo charInfo, @ByRef String[] flavorText) {
                allTextInfoX = infoX - 200.0f * Settings.scale;
                for (AbstractSkinCharacter character : characters) {
                    if (charInfo.name.equals(character.id)) {
                        flavorText[0] = character.skins[character.reskinCount].DESCRIPTION;
                    }
                }
                return SpireReturn.Continue();
            }
        }

        private static class renderRelicsLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(CharacterOption.class, "renderRelics");
                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
                return lines;
            }
        }


        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_Render {
            @SpirePostfixPatch
            public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : characters) {
                        c.InitializeReskinCount();


                        if (o.name.equals(c.id) && o.selected) {
                            reskinRight.render(sb);
                            reskinLeft.render(sb);
                            reskinLock.render(sb);

                            c.skins[c.reskinCount].extraHitboxRender(sb);
//   皮肤选择箭头渲染
                            if (c.reskinUnlock) {
                                if (reskinRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(CF_RIGHT_ARROW,
                                        Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX - 69.0f,
                                        buttonY * Settings.scale - 51.0f,
                                        69.0f, 51.0f,
                                        138.0f, 102.0f,
                                        Settings.scale * buttonScale, Settings.scale * buttonScale,
                                        0.0F, 0, 0, 138, 102, false, false);


                                if (reskinLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(CF_LEFT_ARROW,
                                        Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX - 69.0f,
                                        buttonY * Settings.scale - 51.0f,
                                        69.0f, 51.0f,
                                        138.0f, 102.0f,
                                        Settings.scale * buttonScale, Settings.scale * buttonScale,
                                        0.0F, 0, 0, 138, 102, false, false);
                            } else {
                                if (reskinLock.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }

                                sb.draw(Lock_Icon,
                                        Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX - 64.0f,
                                        buttonY * Settings.scale - 64.0f,
                                        64.0f, 64.0f,
                                        128.0f, 128.0f,
                                        2.0f * Settings.scale * buttonScale, 2.0f * Settings.scale * buttonScale,
                                        0.0F, 0, 0, 128, 128, false, false);
                            }
//   皮肤选择箭头渲染

                            FontHelper.cardTitleFont.getData().setScale(1.0F);
                            FontHelper.losePowerFont.getData().setScale(0.8F);

                            if (c.reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, c.skins[c.reskinCount].NAME, Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale, Settings.GOLD_COLOR.cpy());
                            }


                        }
                    }
                }
            }

        }

        //                动态立绘
        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_portraitSkeleton {
            @SpireInsertPatch(rloc = 62)
            public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb) {

                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : characters) {
                        c.InitializeReskinCount();
                        if (o.name.equals(c.id) && o.selected && c.skins[c.reskinCount].hasAnimation()) {
                            c.skins[c.reskinCount].render(sb);

                            if (char_effectsQueue.size() > 0) {
                                for (int k = 0; k < char_effectsQueue.size(); k++) {
                                    if (!char_effectsQueue.get(k).isDone) {
                                        char_effectsQueue.get(k).update();
                                        char_effectsQueue.get(k).render(sb);
                                    } else {
                                        if (char_effectsQueue_toRemove == null)
                                            char_effectsQueue_toRemove = new ArrayList<>();
                                        if (!char_effectsQueue_toRemove.contains(char_effectsQueue.get(k)))
                                            char_effectsQueue_toRemove.add(char_effectsQueue.get(k));
                                    }
                                }
                                if (char_effectsQueue_toRemove != null)
                                    char_effectsQueue.removeAll(char_effectsQueue_toRemove);
                            }
                        }
                    }

                }

            }
        }
    }

//    立绘动画重置

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")

    public static class CharacterOptionPatch_reloadAnimation {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance) {
            char_effectsQueue.clear();
            bgIMGUpdate = false;

            for (AbstractSkinCharacter c : characters) {
                c.InitializeReskinCount();
                if (__instance.name.equals(c.id)) {
                    c.skins[c.reskinCount].clearWhenClick();
                    if (c.skins[c.reskinCount].hasAnimation())
                        c.skins[c.reskinCount].loadPortraitAnimation();
                }
            }
        }
    }


    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {

            for (CharacterOption o : __instance.options) {
                for (AbstractSkinCharacter c : characters) {
                    c.InitializeReskinCount();

                    if (o.name.equals(c.id) && o.selected) {
                        if (c.reskinUnlock) {
                            if (!bgIMGUpdate) {
                                __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                                bgIMGUpdate = true;
                            }

                            if (InputHelper.justClickedLeft && reskinLeft.hovered) {
                                reskinLeft.clickStarted = true;
                                CardCrawlGame.sound.play("UI_CLICK_1");
                            }

                            if (InputHelper.justClickedLeft && reskinRight.hovered) {
                                reskinRight.clickStarted = true;
                                CardCrawlGame.sound.play("UI_CLICK_1");
                            }

                            if (reskinLeft.justHovered || reskinRight.justHovered)
                                CardCrawlGame.sound.playV("UI_HOVER", 0.75f);


                            reskinRight.move(Settings.WIDTH / 2.0F + reskin_W / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);
                            reskinLeft.move(Settings.WIDTH / 2.0F - reskin_W / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);


                            reskinLeft.update();
                            reskinRight.update();


                            if (reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                                reskinRight.clicked = false;
                                c.skins[c.reskinCount].clearWhenClick();
                                char_effectsQueue.clear();

                                if (c.reskinCount < c.skins.length - 1) {
                                    c.reskinCount += 1;
                                } else {
                                    c.reskinCount = 0;
                                }
                                c.skins[c.reskinCount].loadPortraitAnimation();
                                __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "charInfo",
                                        c.skins[c.reskinCount].updateCharInfo(
                                                ReflectionHacks.getPrivate(o, CharacterOption.class, "charInfo")));
                            }


                            if (reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                                reskinLeft.clicked = false;
                                c.skins[c.reskinCount].clearWhenClick();
                                char_effectsQueue.clear();

                                if (c.reskinCount > 0) {
                                    c.reskinCount -= 1;
                                } else {
                                    c.reskinCount = c.skins.length - 1;
                                }
                                c.skins[c.reskinCount].loadPortraitAnimation();
                                __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "charInfo",
                                        c.skins[c.reskinCount].updateCharInfo(
                                                ReflectionHacks.getPrivate(o, CharacterOption.class, "charInfo")));
                            }

                            c.skins[c.reskinCount].update();

                            if (c.skins[c.reskinCount].extraHitboxClickCheck()) {
                                __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                            }


                            c.InitializeReskinCount();
                        } else {
                            if (reskinLock.hovered)
                                TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.xScale, InputHelper.mY - 10.0F * Settings.scale,
                                        TEXT[0], c.lockString);
                            reskinLock.move(Settings.WIDTH / 2.0F - reskinX_center + allTextInfoX, buttonY * Settings.scale);
                            reskinLock.update();
                        }
                    }
                }
            }

        }


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
                return SpireReturn.Continue();
            }
        }


    }
}