package VUPShionMod.powers.Monster.TimePortal;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DespairPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(DespairPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public DespairPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ContortTimePower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ContortTimePower32.png")), 0, 0, 32, 32);

    }


    @Override
    public void updateDescription() {
        this.description = this.amount > 1 ? String.format(DESCRIPTIONS[1], this.amount) : DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        this.amount--;
        if (this.amount <= 0) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (monster != null && !monster.isDeadOrEscaped()) {
                        addToBot(new InstantKillAction(monster));
                    }
                }
            }

            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
            return;
        }

        updateDescription();
    }
}
