package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.powers.Codex.TwoAttackPower;
import VUPShionMod.powers.Shion.ConcordPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ConcordCharge extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ConcordCharge.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ConcordCharge.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ConcordCharge.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ConcordCharge() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
        this.counter = 10;
        setDescriptionAfterLoading();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (50 - this.counter > 0)
            this.tips.add(new PowerTip(DESCRIPTIONS[1], String.format(DESCRIPTIONS[2], 50 - this.counter, 50 - this.counter)));
        else
            this.tips.add(new PowerTip(DESCRIPTIONS[1], String.format(DESCRIPTIONS[3], this.counter - 50, this.counter - 50)));

        this.tips.add(new PowerTip(DESCRIPTIONS[4],DESCRIPTIONS[5]));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new GainEnergyAction(2));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ConcordPower(AbstractDungeon.player,this.counter)));
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        int amount =0;
        if(!FinFunnelManager.getFinFunnelList().isEmpty()){
            for(AbstractFinFunnel funnel : FinFunnelManager.getFinFunnelList()){
                if(funnel.getLevel() >=5)
                    amount++;
            }
        }

        if(amount>0)
            addToBot(new GainHyperdimensionalLinksAction(amount));
    }


    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);
        this.counter++;
        if (this.counter > 100)
            this.counter = 100;
        setDescriptionAfterLoading();
    }
}
