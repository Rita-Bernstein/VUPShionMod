package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.function.Supplier;

public class LotusOfWarStance extends AbstractStance {
    public static final String STANCE_ID = VUPShionMod.makeID(LotusOfWarStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public LotusOfWarStance() {
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
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player,1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new DexterityPower(AbstractDungeon.player,1)));
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
