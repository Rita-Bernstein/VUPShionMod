package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.DiscardRarityCardAction;
import VUPShionMod.actions.ExhaustRarityCardAction;
import VUPShionMod.actions.LoseMaxHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ShockPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("ShockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShockPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("painfulStabs");
        updateDescription();
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS) {
            for (int i = 0; i < this.amount; i++)
                addToBot(new DiscardRarityCardAction());
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
