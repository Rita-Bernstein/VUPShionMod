package VUPShionMod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class SansMeterPatches {
    @SpirePatch(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {boolean.class}
    )
    public static class OnMonsterDeathPatch {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(AbstractMonster _instance, boolean triggerRelics) {

            if (!AbstractDungeon.player.isDeadOrEscaped()) {
                if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                    EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).onFatal(_instance);
                }
            }


            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CampfireSleepEffect.class,
            method = "update"
    )
    public static class SleepWithCatcherPatch {
        @SpireInsertPatch(rloc = 24, localvars = {"rewardCards"})
        public static SpireReturn<Void> Insert(CampfireSleepEffect _instance, List<AbstractCard> rewardCards) {
            if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                rewardCards.addAll(returnPrayerCard());
                EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(40);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CampfireSleepEffect.class,
            method = "update"
    )
    public static class SleepPatch {
        private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSleepEffect");
        public static final String[] TEXT = uiStrings.TEXT;

        @SpireInsertPatch(rloc = 28)
        public static SpireReturn<Void> Insert(CampfireSleepEffect _instance) {
            if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel) && !AbstractDungeon.player.hasRelic(DreamCatcher.ID)) {
                EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(40);

                AbstractDungeon.cardRewardScreen.open(returnPrayerCard(), null, TEXT[0]);
            }
            return SpireReturn.Continue();
        }
    }

    public static ArrayList<AbstractCard> returnPrayerCard() {
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        ArrayList<AbstractCard> returnList = new ArrayList<AbstractCard>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(CardTagsEnum.Prayer_CARD)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(CardTagsEnum.Prayer_CARD)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(CardTagsEnum.Prayer_CARD)) {
                list.add(c);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!list.isEmpty()) {
                int index = AbstractDungeon.miscRng.random(list.size() - 1);
                returnList.add(list.get(index).makeCopy());
                list.remove(index);
            } else {
                returnList.add(new Madness());
                break;
            }
        }

        return returnList;
    }
}
