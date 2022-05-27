package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.List;

public class DissectingFinFunnelUpgradePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("DissectingFinFunnelUpgradePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean used = false;

    public DissectingFinFunnelUpgradePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/PursuitFinFunnelUpgrade128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/PursuitFinFunnelUpgrade32.png")), 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(this.amount > 0)
        if (card.hasTag(CardTagsEnum.FIN_FUNNEL) || card.hasTag(CardTagsEnum.TRIGGER_FIN_FUNNEL)){
            this.amount--;
            updateDescription();
            if (this.amount <= 0 && !used) {
                this.used = true;
                this.amount = -1;
                List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).finFunnelList;
                for (AbstractFinFunnel funnel : funnelList) {
                    if (funnel instanceof DissectingFinFunnel) {
                        this.flash();
                        funnel.upgradeLevel(1);
                        this.description = DESCRIPTIONS[2];
                        break;
                    }
                }
                EnergyPanelPatches.levelUP = true;
            }
        }
    }
}