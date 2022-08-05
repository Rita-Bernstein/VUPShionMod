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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CombustPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.function.Supplier;

public class SpiralBladeStance extends AbstractStance {
    public static final String STANCE_ID = VUPShionMod.makeID(SpiralBladeStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public SpiralBladeStance() {
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

            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(6, true),
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY, true));


            Supplier<AbstractPower> powerToApply = () -> new BleedingPower(null, AbstractDungeon.player, 4);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerToAllEnemyAction(powerToApply));
        }
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
