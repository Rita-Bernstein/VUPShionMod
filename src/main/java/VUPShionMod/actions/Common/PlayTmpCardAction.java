package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayTmpCardAction extends AbstractGameAction {
    AbstractCard card;

    public PlayTmpCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        AbstractCard card = this.card.makeStatEquivalentCopy();
        card.tags = this.card.tags;
        card.purgeOnUse = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        addToTop(new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true));
        addToTop(new UnlimboAction(card));
        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        isDone = true;
    }
}
