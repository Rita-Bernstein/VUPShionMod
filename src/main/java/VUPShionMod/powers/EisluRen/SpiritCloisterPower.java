package VUPShionMod.powers.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SpiritCloisterPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(SpiritCloisterPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public SpiritCloisterPower(AbstractCreature owner) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        updateDescription();
        this.isTurnBased = true;
        loadShionRegion("SpiritCloisterPower");
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        ElfMinion elf = MinionGroup.getElfMinion();
        if (elf != null)
            elf.onSpiritCloisterPower(this);

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}
