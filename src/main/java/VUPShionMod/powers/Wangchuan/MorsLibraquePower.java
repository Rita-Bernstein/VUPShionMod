package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.skins.sk.WangChuan.ChinaWangChuan;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.vfx.combat.GiantTextEffect;

public class MorsLibraquePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("MorsLibraquePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean justApplied = true;

    public MorsLibraquePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MorsLibraquePower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MorsLibraquePower36.png")), 0, 0, 36, 36);
    }

    @Override
    public void stackPower(int stackAmount) {

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void atStartOfTurn() {
        if (justApplied) {
            justApplied = false;
            return;
        }
        addToBot(new ReducePowerAction(this.owner, this.owner, MorsLibraquePower.POWER_ID, 1));
        if (this.amount == 1) {
            flash();
            addToBot(new VFXAction(new GiantTextEffect(this.owner.hb.cX, this.owner.hb.cY)));
            addToBot(new InstantKillAction(this.owner));

            if(this.owner.id.equals(CorruptHeart.ID))
                CharacterSelectScreenPatches.skinManager.unlockSkin(ChinaWangChuan.ID);

        }
    }
}