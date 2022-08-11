package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.actions.EisluRen.RuinGuardianStanceAction;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.ui.WingShield;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RuinGuardianStance extends AbstractStance {
    public static final String STANCE_ID = VUPShionMod.makeID(RuinGuardianStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public static CardGroup cardsToPlay = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public RuinGuardianStance() {
        this.ID = STANCE_ID;// 21
        this.name = stanceString.NAME;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.EisluRen)) {

        }
    }


    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LoseWingShieldAction(1));
        if (!cardsToPlay.group.isEmpty()) {
            for (AbstractCard card : cardsToPlay.group) {
                AbstractDungeon.actionManager.addToBottom(new PlayTmpCardAction(card));
            }
        }
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        cardsToPlay.clear();

        AbstractDungeon.actionManager.addToBottom(new RuinGuardianStanceAction());
    }


    @Override
    public void onExitStance() {
        stopIdleSfx();
        cardsToPlay.clear();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            sfxId = -1L;
        }
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
}
