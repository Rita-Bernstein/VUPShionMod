package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class InfiniteSushi extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(InfiniteSushi.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/InfiniteSushi.png";
    private static final String OUTLINE_PATH = "img/relics/outline/InfiniteSushi.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public InfiniteSushi() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
        addToBot(new GainEnergyAction(1));
        addToBot(new SFXAction("MINAMI_"+ MathUtils.random(2)));
    }



    @Override
    public void onTrigger() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
    }
}
