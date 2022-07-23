package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarQuakes extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(StarQuakes.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/StarQuakes.png";
    private static final String OUTLINE_PATH = "img/relics/outline/StarQuakes.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public StarQuakes() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CorGladiiPower(AbstractDungeon.player, 2)));
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            flash();
            int amount = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount / 3;
            addToBot(new LoseCorGladiiAction(amount));
        }
    }
}