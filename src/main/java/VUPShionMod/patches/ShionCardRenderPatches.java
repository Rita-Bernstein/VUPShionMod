package VUPShionMod.patches;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.helpers.CardTypeHelper;
import VUPShionMod.util.SaveHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;


public class ShionCardRenderPatches {
    public static float drawYFix = 18.0f;

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderPortrait"
    )
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderJokePortrait"
    )
    public static class RenderPortraitPatch {
        @SpireInsertPatch(rloc = 3, localvars = {"drawY"})
        public static SpireReturn<Void> Insert(AbstractCard card, SpriteBatch sb, @ByRef float[] drawY) {
            if (card instanceof AbstractVUPShionCard)
                drawY[0] = card.current_y - 95.0F + drawYFix * card.drawScale * Settings.scale;

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderPortrait"
    )
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderJokePortrait"
    )
    public static class RenderPortraitPatch2 {
        @SpireInsertPatch(rloc = 12, localvars = {"drawY"})
        public static SpireReturn<Void> Insert(AbstractCard card, SpriteBatch sb, @ByRef float[] drawY) {
            if (card instanceof AbstractVUPShionCard)
                drawY[0] = card.current_y - card.portrait.packedHeight / 2.0F + drawYFix * card.drawScale * Settings.scale;

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderTitle"
    )
    public static class RenderTitle {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderRotatedText")) {
                        m.replace("if(this instanceof " + AbstractVUPShionCard.class.getName() + "){ " +
                                "if(" + Settings.class.getName() + ".language == " + Settings.class.getName() + ".GameLanguage.ZHS){" +
                                "$proceed($1,$2,$3,$4,$5, -10.0f * this.drawScale * " + Settings.class.getName()
                                + ".scale,188.0f * this.drawScale *" + Settings.class.getName() + ".scale,$8,$9,$10);} else{" +
                                CardTypeHelper.class.getName() + ".renderRotatedText($1,$2,$3,$4,$5, -90.0f * this.drawScale * " + Settings.class.getName()
                                + ".scale,188.0f * this.drawScale *" + Settings.class.getName() + ".scale,$8,$9,$10);}"
                                + "} else {"
                                + "$proceed($$);"
                                + "}"

                        );
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderType"
    )
    public static class RenderType {


        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderRotatedText")) {
                        m.replace("if(this instanceof " + AbstractVUPShionCard.class.getName() + "){"
                                + CardTypeHelper.class.getName() + ".renderRotatedText($1,$2,$3,$4,$5,$6, 28.0f * this.drawScale *" + Settings.class.getName() + ".scale,$8,$9,$10,this.rarity);"
                                + "} else {"
                                + "$proceed($$);"
                                + "}"
                        );
                    }
                }
            };
        }
    }


    //=====================大卡图
    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderPortrait"
    )
    public static class MissingPortraitFix {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(SpriteBatch.class.getName()) && m.getMethodName().equals("draw")) {
                        m.replace("if(this.card instanceof " + AbstractVUPShionCard.class.getName() + "){" +
                                " $3 = " + ShionCardRenderPatches.class.getName() + ".getCardY(); $_ = $proceed($$);"
                                + "} else {"
                                + "$proceed($$);"
                                + "}"
                        );
                    }
                }
            };
        }
    }

    public static float getCardY() {
        return Settings.HEIGHT / 2.0F - 190.0F + 190.0F * Settings.scale;
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderTitle"
    )
    public static class SingleCardViewRenderTitle {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFontCentered")) {
                        m.replace("if(this.card instanceof " + AbstractVUPShionCard.class.getName() + "){" +
                                "if(" + Settings.class.getName() + ".language == " + Settings.class.getName() + ".GameLanguage.ZHS){" +
                                " $proceed($1,$2,$3,"
                                + Settings.class.getName() + ".WIDTH / 2.0F - 20.0F * " + Settings.class.getName() + ".scale,"
                                + Settings.class.getName() + ".HEIGHT / 2.0F + 376.0F *" + Settings.class.getName() + ".scale,$6);"
                                + "}else{" + FontHelper.class.getName() + ".renderFont(" +
                                "$1,$2,$3," + Settings.class.getName() + ".WIDTH / 2.0F - 180.0F * " + Settings.class.getName() + ".scale,"
                                + Settings.class.getName() + ".HEIGHT / 2.0F + 394.0F *" + Settings.class.getName() + ".scale,$6" +
                                ");}} else {"
                                + "$proceed($$);"
                                + "}"

                        );
                    }
                }
            };
        }
    }

    public boolean leftTitle() {
        return false;
    }


    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardTypeText"
    )
    public static class RenderCardTypeText {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFontCentered")) {
                        m.replace("if(this.card instanceof " + AbstractVUPShionCard.class.getName() + "){ $proceed($1,$2,$3,$4,"
                                + Settings.class.getName() + ".HEIGHT / 2.0F + 10.0F *" + Settings.class.getName() + ".scale," +
                                Color.class.getName() + ".BLACK);"
                                + "} else {"
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
            method = "renderCost"
    )
    public static class renderCostPatch {
        private static final Texture orb_b = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_b.png");
        private static final Texture orb_g = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_g.png");
        private static final Texture orb_w = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_w.png");

        private static final Texture orb_ab = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_ab.png");
        private static final Texture orb_ag = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_ag.png");
        private static final Texture orb_aw = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/1024/card_lime_orb_aw.png");

        @SpireInsertPatch(rloc = 0, localvars = {"card"})
        public static SpireReturn<Void> Insert(SingleCardViewPopup _instance, SpriteBatch sb,
                                               @ByRef(type = "com.megacrit.cardcrawl.cards.AbstractCard") Object[] card) {
            if ((card[0] instanceof AbstractVUPShionCard)) {
                AbstractVUPShionCard c = ((AbstractVUPShionCard) card[0]);
                if (c.isLocked || !c.isSeen) {
                    return SpireReturn.Return(null);
                }

                if (c.cost > -2) {
                    if (!SaveHelper.useSimpleOrb && c.color == CardColorEnum.VUP_Shion_LIME) {
                        if (c instanceof FinFunnelUpgrade)
                            renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_ag);
                        else
                            switch (c.displayRarity) {
                                case RARE:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_ag);
                                    break;
                                case UNCOMMON:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_ab);
                                    break;
                                default:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_aw);
                                    break;
                            }


                    } else {

                        if (c instanceof FinFunnelUpgrade)
                            renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_g);
                        else
                            switch (c.displayRarity) {
                                case RARE:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_g);
                                    break;
                                case UNCOMMON:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_b);
                                    break;
                                default:
                                    renderHelper(sb, Settings.WIDTH / 2.0F - 270.0F * Settings.scale, Settings.HEIGHT / 2.0F + 380.0F * Settings.scale, orb_w);
                                    break;
                            }
                    }


                }


                Color color = null;
                if (c.isCostModified) {
                    color = Settings.GREEN_TEXT_COLOR;
                } else {
                    color = Settings.CREAM_COLOR;
                }
                switch (c.cost) {
                    case -2:
                        return SpireReturn.Return(null);
                    case -1:
                        FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, "X", 666.0F * Settings.scale, Settings.HEIGHT / 2.0F + 404.0F * Settings.scale, color);
                        break;
                    case 1:
                        FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(c.cost), 674.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F + 404.0F * Settings.scale, color);
                        break;
                    default:
                        FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(c.cost), 668.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F + 404.0F * Settings.scale, color);
                }

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        public static void renderHelper(SpriteBatch sb, float x, float y, Texture img) {
            if (img == null) {
                return;
            }

            sb.draw(img, x - img.getWidth() / 2.0F, y - img.getHeight() / 2.0F,
                    img.getWidth() / 2.0f, img.getHeight() / 2.0f,
                    img.getWidth(), img.getHeight(),
                    1.2f * Settings.scale,
                    1.2f * Settings.scale,
                    0.0F,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
        }


    }
}
