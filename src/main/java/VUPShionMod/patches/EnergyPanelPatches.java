package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
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
                }

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
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
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
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion|| AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {

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
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion|| AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
                AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnergyPanel.fontScale * 1.3f);
//                energyMsg[0] = EnergyPanel.totalCount + "" + AbstractDungeon.player.energy.energy;
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
                        m.replace("if(" + AbstractDungeon.class.getName() + ".player.chosenClass =="
                                + AbstractPlayerEnum.class.getName() + ".VUP_Shion || "+ AbstractDungeon.class.getName() + ".player.chosenClass =="
                                + AbstractPlayerEnum.class.getName() + ".WangChuan"+"){" +
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
}
