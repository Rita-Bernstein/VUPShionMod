package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.actions.EisluRen.LightArmorStanceAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.actions.EisluRen.RuinGuardianStanceAction;
import VUPShionMod.patches.AbstractPlayerEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class LightArmorStance extends AbstractStance {
    public static final String STANCE_ID = VUPShionMod.makeID(LightArmorStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public static CardGroup cardsToPlay = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public LightArmorStance() {
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
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL)
            return damage * 0.7f;
        return super.atDamageReceive(damage, damageType);
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        cardsToPlay.clear();

        AbstractDungeon.actionManager.addToBottom(new LightArmorStanceAction());
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LoseWingShieldAction(2));
        if (!cardsToPlay.group.isEmpty()) {
            for (AbstractCard card : cardsToPlay.group) {
                AbstractDungeon.actionManager.addToBottom(new PlayTmpCardAction(card));
            }
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

        cardsToPlay.clear();
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
}
