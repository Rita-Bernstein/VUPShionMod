package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Codex.TwoAttackPower;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.powers.Shion.ConcordPower;
import VUPShionMod.powers.Shion.FireCalibrationPower;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.PrayerStance;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ConcordSnipe extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ConcordSnipe.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ConcordSnipe.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ConcordSnipe.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ConcordSnipe() {
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
    public void atTurnStart() {
        flash();
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new FireCalibrationPower(m, 1),0));
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
    public void atBattleStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ConcordPower(AbstractDungeon.player,this.counter)));
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
