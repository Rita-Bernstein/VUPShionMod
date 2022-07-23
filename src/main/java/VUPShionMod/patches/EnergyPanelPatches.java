package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.ui.FinFunnelCharge;
import VUPShionMod.ui.SansMeter;
import VUPShionMod.ui.SwardCharge;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SuppressWarnings("unused")
public class EnergyPanelPatches {
    public static int energyUsedThisTurn = 0;
    public static final Color ENERGY_TEXT_COLOR = new Color(0.86F, 0.86F, 0.86F, 1.0F);
    public static Texture energyUsedBack = ImageMaster.loadImage("VUPShionMod/img/ui/topPanel/Shion/energyUsedBack.png");
    public static Texture energyUsed1 = ImageMaster.loadImage("VUPShionMod/img/ui/topPanel/Shion/energyUsed1.png");
    public static Texture energyUsed2 = ImageMaster.loadImage("VUPShionMod/img/ui/topPanel/Shion/energyUsed2.png");

    public static boolean rolling = false;
    public static float rollingTimmer = 0.0f;
    public static float energyUsed1Angle = -180.0f;

    public static boolean levelUP = false;
    public static float levelTimer = 3.0f;
    public static Color levelColor = VUPShionMod.transparent.cpy();

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("ShionFinFunnelSwitch"));
    public static Hitbox hb = new Hitbox(120.0f * Settings.scale, 120.0f * Settings.scale);



    @SpirePatch(
            clz = EnergyPanel.class,
            method = SpirePatch.CLASS
    )
    public static class PatchEnergyPanelField {
        public static SpireField<Boolean> canUseSans = new SpireField<>(() -> false);
        public static SpireField<SansMeter> sans = new SpireField<>(() -> new SansMeter());

        public static SpireField<Boolean> canUseFunnelCharger = new SpireField<>(() -> false);
        public static SpireField<FinFunnelCharge> finFunnelCharger = new SpireField<>(() -> new FinFunnelCharge());

        public static SpireField<Boolean> canUseSwardCharge = new SpireField<>(() -> false);
        public static SpireField<SwardCharge> swardCharge = new SpireField<>(() -> new SwardCharge());

    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchEnergyPanelCon {
        public static void Prefix(EnergyPanel _instance) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu) {
                PatchEnergyPanelField.canUseSans.set(_instance, true);
            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                PatchEnergyPanelField.canUseFunnelCharger.set(_instance, true);

            }

            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                PatchEnergyPanelField.canUseSwardCharge.set(_instance, true);
            }

        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "useEnergy"
    )
    public static class PatchUseEnergy {
        public static void Prefix(int e) {
            energyUsedThisTurn += Math.min(e, EnergyPanel.totalCount);
            rolling = true;
            rollingTimmer = 0.0f;
            energyUsed1Angle = -180.0f;
        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "render"
    )
    public static class PatchRender {
        public static void Postfix(EnergyPanel panel, SpriteBatch sb) {
            if (isShionModChar()) {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
                float x = -45.0f;
                float y = 35.0f;

                sb.draw(energyUsedBack,
                        panel.current_x - 72.0F + x * Settings.scale, panel.current_y - 64.0F + y * Settings.scale,
                        72.0F, 64.0F,
                        144.0F, 128.0F,
                        1.15F * Settings.scale, 1.15F * Settings.scale,
                        0.0f,
                        0, 0,
                        144, 128,
                        false, false);

                sb.draw(energyUsed1,
                        panel.current_x - 72.0F + x * Settings.scale, panel.current_y - 64.0F + y * Settings.scale,
                        72.0F, 64.0F,
                        144.0F, 128.0F,
                        1.15F * Settings.scale, 1.15F * Settings.scale,
                        energyUsed1Angle,
                        0, 0,
                        144, 128,
                        false, false);


                sb.draw(energyUsed2,
                        panel.current_x - 72.0F + x * Settings.scale, panel.current_y - 64.0F + y * Settings.scale,
                        72.0F, 64.0F,
                        144.0F, 128.0F,
                        1.15F * Settings.scale, 1.15F * Settings.scale,
                        0.0f,
                        0, 0,
                        144, 128,
                        false, false);


                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                    AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnergyPanel.fontScale * 0.7F);
                    FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(),
                            Integer.toString(energyUsedThisTurn),
                            panel.current_x + 10.0f * Settings.scale + x * Settings.scale, panel.current_y + 25.0f * Settings.scale - 32.0F * Settings.scale + y * Settings.scale,
                            ENERGY_TEXT_COLOR);


                    hb.render(sb);

                    if (hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                        TipHelper.renderGenericTip(hb.cX + hb.width * 0.5f,
                                0.5f * Settings.HEIGHT, uiStrings.TEXT[0], uiStrings.TEXT[1]);
                    }


                }

            }

            if (PatchEnergyPanelField.canUseSans.get(panel)) {
                SansMeter sansMeter = PatchEnergyPanelField.sans.get(panel);
                sansMeter.render(sb);
            }

            if (PatchEnergyPanelField.canUseFunnelCharger.get(panel)) {
                FinFunnelCharge charge = PatchEnergyPanelField.finFunnelCharger.get(panel);
                charge.render(sb);
            }

            if (PatchEnergyPanelField.canUseSwardCharge.get(panel)) {
                SwardCharge charge = PatchEnergyPanelField.swardCharge.get(panel);
                charge.render(sb);
            }

        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "update"
    )
    public static class PatchUpdate {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(EnergyPanel panel) {
            if (isShionModChar()) {
                if (rolling) {
                    rollingTimmer += Gdx.graphics.getDeltaTime();
                    energyUsed1Angle = Interpolation.fade.apply(-180.0f, 180.0F, rollingTimmer);
                    if (rollingTimmer > 1.0f) {
                        energyUsed1Angle = -180.0f;
                        rolling = false;
                        rollingTimmer = 0.0f;
                    }
                }

                if (levelUP) {
                    levelTimer -= Gdx.graphics.getDeltaTime();
                    levelColor.a = levelTimer / 3.0f;
                    if (levelTimer <= 0.0f) {
                        levelUP = false;
                        levelTimer = 3.0f;
                        levelColor.a = 0.0f;
                    }
                }
            }

            if (PatchEnergyPanelField.canUseSans.get(panel)) {
                SansMeter sansMeter = PatchEnergyPanelField.sans.get(panel);
                sansMeter.updatePos(panel);
                sansMeter.update();
            }

            if (PatchEnergyPanelField.canUseFunnelCharger.get(panel)) {
                FinFunnelCharge charge = PatchEnergyPanelField.finFunnelCharger.get(panel);
                charge.updatePos(panel);
                charge.update();
            }

            if (PatchEnergyPanelField.canUseSwardCharge.get(panel)) {
                SwardCharge charge = PatchEnergyPanelField.swardCharge.get(panel);
                charge.updatePos(panel);
                charge.update();
            }

            hb.move(panel.current_x + 10.0f * Settings.scale + -45.0f * Settings.scale,
                    panel.current_y + 25.0f * Settings.scale - 32.0F * Settings.scale + 35.0f * Settings.scale);

            hb.update();


            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "renderVfx"
    )
    public static class PatchRenderVfx {
        @SpireInsertPatch(rloc = 3)
        public static SpireReturn<Void> Insert(EnergyPanel panel, SpriteBatch sb, Texture ___gainEnergyImg, float ___energyVfxScale) {
            if (isShionModChar()) {

                sb.draw(___gainEnergyImg,
                        panel.current_x - 221.0F, panel.current_y - 221.0F,
                        221.0F, 221.0F, 442.0F, 442.0F,
                        ___energyVfxScale, ___energyVfxScale, 0.0f,
                        0, 0, 442, 442, false, false);

                sb.setBlendFunction(770, 771);
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = EnergyPanel.class,
            method = "render"
    )
    public static class PatchRenderSetScale {
        @SpireInsertPatch(rloc = 11, localvars = "energyMsg")
        public static SpireReturn<Void> Insert(EnergyPanel panel, SpriteBatch sb, @ByRef String[] energyMsg) {
            if (isShionModChar()) {
                AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnergyPanel.fontScale * 1.3f);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "render"
    )
    public static class PatchRenderFontCentered {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFontCentered")) {
                        m.replace("if(" + EnergyPanelPatches.class.getName() + ".isShionModChar()){" +
                                "$proceed($1,$2,$3,"
                                + "this.current_x + 12.0f * " + Settings.class.getName() + ".scale,"
                                + "this.current_y - 12.0f * " + Settings.class.getName() + ".scale,$6);"
                                + "} else {"
                                + "$proceed($$);"
                                + "}"

                        );
                    }
                }
            };
        }
    }

    public static boolean isShionModChar() {
        return AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.EisluRen;
    }

}
