package VUPShionMod.powers;


import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class GravitoniumOverPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("GravitoniumOverPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GravitoniumOverPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
