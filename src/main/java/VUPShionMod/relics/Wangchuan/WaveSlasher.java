package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

public class WaveSlasher extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(WaveSlasher.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/WaveSlasher.png";
    private static final String OUTLINE_PATH = "img/relics/outline/WaveSlasher.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean triggered = false;

    public WaveSlasher() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.SKILL) {
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, StiffnessPower.POWER_ID, 1));
        }
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FreeAttackPower(AbstractDungeon.player, 1)));
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            flash();
            int amount = (int) Math.floor(AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * 0.5f);
            addToBot(new LoseCorGladiiAction(amount));
        }
    }
}
