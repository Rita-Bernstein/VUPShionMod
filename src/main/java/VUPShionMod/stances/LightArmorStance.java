package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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
