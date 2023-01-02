package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.events.ShionSpireHeart;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CardPreviewUpgradePatches {
    public static int upgradeNum = 1;
    public static Hitbox upgradeHbL = new Hitbox(100.0f * Settings.scale, 100.0f * Settings.scale);
    public static Hitbox upgradeHbR = new Hitbox(100.0f * Settings.scale, 100.0f * Settings.scale);
    public static boolean copyCanUpgrade = false;
    public static boolean copyCanUpgradeMuti = false;

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class UpgradeNumResetPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            if (!SingleCardViewPopup.isViewingUpgrade) {
                upgradeNum = 1;
            }


            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateUpgradePreview"
    )
    public static class UpdateHitBox {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance) {
            if (SingleCardViewPopup.isViewingUpgrade && copyCanUpgradeMuti) {
                if (upgradeNum > 1)
                    if (upgradeHbL != null) {
                        upgradeHbL.update();

                        if (InputHelper.justClickedLeft && upgradeHbL.hovered) {
                            upgradeHbL.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (upgradeHbL.justHovered)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);


                        if (upgradeHbL.clicked || CInputActionSet.proceed.isJustPressed()) {
                            upgradeHbL.clicked = false;
                            upgradeNum--;
                        }
                    }


                if (upgradeNum < 20 && copyCanUpgrade)
                    if (upgradeHbR != null) {
                        upgradeHbR.update();

                        if (InputHelper.justClickedLeft && upgradeHbR.hovered) {
                            upgradeHbR.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }

                        if (upgradeHbR.justHovered)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);


                        if (upgradeHbR.clicked || CInputActionSet.proceed.isJustPressed()) {
                            upgradeHbR.clicked = false;
                            upgradeNum++;
                        }
                    }

                if (upgradeNum > 20) {
                    upgradeNum = 20;
                }

                if (upgradeNum < 1) {
                    upgradeNum = 1;
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderUpgradeViewToggle"
    )
    public static class RenderHitbox {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            if (SingleCardViewPopup.isViewingUpgrade && copyCanUpgradeMuti) {

                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont,
                        CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(CardPreviewUpgradePatches.class.getSimpleName())).TEXT[0],
                        Settings.WIDTH / 2.0F - 410.0F * Settings.scale, 125.0F * Settings.scale, Settings.GOLD_COLOR);


                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, Integer.toString(upgradeNum),
                        Settings.WIDTH / 2.0F - 410.0F * Settings.scale, 70.0F * Settings.scale, Settings.GOLD_COLOR.cpy());

                if (upgradeHbL != null) {
                    if (upgradeHbL.hovered || Settings.isControllerMode) {
                        sb.setColor(Color.WHITE.cpy());
                    } else {
                        sb.setColor(Color.LIGHT_GRAY.cpy());
                    }

                    if (upgradeNum > 1)
                        sb.draw(ImageMaster.CF_LEFT_ARROW, upgradeHbL.cX - 24.0f,
                                upgradeHbL.cY - 24.0f, 0.0f, 0.0f, 48.0f, 48.0f,
                                Settings.scale * 1.5f, Settings.scale * 1.5f,
                                0.0F, 0, 0, 48, 48, false, false);

                    sb.setColor(Color.WHITE.cpy());
                    upgradeHbL.render(sb);
                }

                if (upgradeHbR != null) {
                    if (upgradeHbR.hovered || Settings.isControllerMode) {
                        sb.setColor(Color.WHITE.cpy());
                    } else {
                        sb.setColor(Color.LIGHT_GRAY.cpy());
                    }


                    if (upgradeNum < 20 && copyCanUpgrade)
                        sb.draw(ImageMaster.CF_RIGHT_ARROW, upgradeHbR.cX - 24.0f,
                                upgradeHbR.cY - 24.0f, 0.0f, 0.0f, 48.0f, 48.0f,
                                Settings.scale * 1.5f, Settings.scale * 1.5f,
                                0.0F, 0, 0, 48, 48, false, false);

                    sb.setColor(Color.WHITE.cpy());
                    upgradeHbR.render(sb);
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {AbstractCard.class, CardGroup.class}
    )
    public static class OpenPatch1 {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, AbstractCard card, CardGroup group) {
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {AbstractCard.class}
    )
    public static class OpenPatch2 {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, AbstractCard card) {
            upgradeHbL.move(Settings.WIDTH / 2.0F - 460.0F * Settings.scale, 70.0F * Settings.scale);
            upgradeHbR.move(Settings.WIDTH / 2.0F - 360.0F * Settings.scale, 70.0F * Settings.scale);

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class UpgradeCardPatch {
        @SpireInsertPatch(rloc = 4)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(_instance, SingleCardViewPopup.class, "card");
            copyCanUpgradeMuti = card.canUpgrade();

            for (int i = 0; i < upgradeNum - 1; i++) {
                card.upgrade();
            }

            copyCanUpgrade = card.canUpgrade();

            ReflectionHacks.setPrivate(_instance, SingleCardViewPopup.class, "card", card);
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class ClosePatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("close") && m.getLineNumber() == 291) {
                        m.replace("if(" + CardPreviewUpgradePatches.class.getName() + ".checkClickToClose()){ "
                                + "$proceed($$);"
                                + "}"

                        );
                    }
                }
            };
        }
    }


    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class DontChangeClickedCaseyByRita {
        @SpireInsertPatch(rloc = 17)
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance) {
            if (!checkClickToClose()) {
                InputHelper.justClickedLeft = true;
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class ClearSCPFontTextures {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("ClearSCPFontTextures") && m.getLineNumber() == 293) {
                        m.replace("if(" + CardPreviewUpgradePatches.class.getName() + ".checkClickToClose()){ "
                                + "$proceed($$);"
                                + "}"
                        );
                    }
                }
            };
        }
    }

    public static boolean checkClickToClose() {
        upgradeHbL.update();
        upgradeHbR.update();
        if (SingleCardViewPopup.isViewingUpgrade && copyCanUpgradeMuti) {
            return !upgradeHbL.hovered && !upgradeHbR.hovered;
        }
        return true;
    }
}
