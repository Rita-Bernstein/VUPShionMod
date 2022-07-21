package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;

public class TheRipple extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(TheRipple.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/TheRipple.png";
    private static final String OUTLINE_PATH = "img/relics/outline/TheRipple.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public TheRipple() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 2, true, true));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2)));
    }

    @Override
    public void atTurnStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false)));
    }
}
