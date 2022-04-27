package VUPShionMod.powers.Common;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NoSkillsPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("NoSkillsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public NoSkillsPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        loadRegion("entangle");
        updateDescription();
    }


    @Override
    public void playApplyPowerSfx() { CardCrawlGame.sound.play("POWER_ENTANGLED", 0.05F); }

    @Override
    public void updateDescription() {
        this.description =DESCRIPTIONS[0];
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return (card.type != AbstractCard.CardType.SKILL);
    }
}