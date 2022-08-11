package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.function.Supplier;

public class ThousandsOfBladeStance extends AbstractStance {
    public static final String STANCE_ID = VUPShionMod.makeID(ThousandsOfBladeStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public ThousandsOfBladeStance() {
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
    public void onPlayCard(AbstractCard card) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && WingShield.getWingShield().getCount() > 0) {
            AbstractDungeon.actionManager.addToBottom(new LoseWingShieldAction(1));
            for (int i = 0; i < 2; i++) {
                AbstractCard tmp = card.makeStatEquivalentCopy();
                tmp.tags.add(CardTagsEnum.NoWingShieldCharge);
                AbstractDungeon.actionManager.addToBottom(new PlayTmpCardAction(tmp));
            }

        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LoseWingShieldAction(1));
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }
    }


    @Override
    public void onExitStance() {
        stopIdleSfx();
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
