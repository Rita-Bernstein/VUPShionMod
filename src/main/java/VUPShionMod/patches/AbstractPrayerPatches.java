package VUPShionMod.patches;

import VUPShionMod.prayers.AbstractPrayer;
import VUPShionMod.prayers.AbstractTurnIcon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AbstractPrayerPatches {
    public static ArrayList<AbstractPrayer> prayers = new ArrayList<>();
    public static ArrayList<AbstractTurnIcon> turns = new ArrayList<>();


    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "create"
    )
    public static class initializePatch {
        @SpireInsertPatch(locator = TabNameLocator.class)
        public static SpireReturn<Void> Insert() {
            AbstractPrayer.initialize();
            turns.add(new AbstractTurnIcon(1));
            turns.add(new AbstractTurnIcon(2));
            turns.add(new AbstractTurnIcon(3));
            turns.add(new AbstractTurnIcon(4));
            turns.add(new AbstractTurnIcon(5));

            return SpireReturn.Continue();
        }


        private static class TabNameLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "initialize");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class AtStartOfTurnPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(AbstractPlayer _instance) {
            for (AbstractPrayer prayer : prayers) {
                prayer.atStartOfTurn();
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "resetPlayer"
    )
    public static class ResetPlayerPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {
            prayers.clear();

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class PrayerRenderPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(AbstractPlayer _instance, SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);

            float offsetX = AbstractDungeon.player.drawX - 120.0f * Settings.scale;
            float[] x = new float[5];
            float y = 200.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            if (!prayers.isEmpty()) {
                int line = -1;

                int preTurns = -1;
                for (AbstractPrayer prayer : prayers) {
                    if (prayer.turns > 0) {
                        if (preTurns - prayer.turns < 0) {
                            line++;
                            preTurns = prayer.turns;
                        }
                        prayer.setPosition(offsetX + x[prayer.turns - 1], y + line * 48.0f * Settings.scale);
                        prayer.render(sb);

                        x[prayer.turns - 1] += 48.0f * Settings.scale;
                    }
                }

                line = 0;

                for (AbstractTurnIcon icon : turns) {
                    if (icon.hasThisTurn) {
                        icon.setPosition(offsetX - 48.0f * Settings.scale - 32.0F, y + line * 48.0f * Settings.scale - 16.0F);
                        icon.render(sb);
                        line++;
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "combatUpdate"
    )
    public static class CombatUpdatePatch {
        @SpireInsertPatch(rloc = 14)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance) {
            if (!prayers.isEmpty()) {
                for (AbstractPrayer prayer : prayers) {
                    if (prayer.turns > 0)
                        turns.get(prayer.turns - 1).hasThisTurn = true;

                    prayer.update();
                }

                for (AbstractTurnIcon icon : turns) {
                    if (icon.hasThisTurn)
                        icon.update();
                }

            }

            return SpireReturn.Continue();
        }
    }


}
