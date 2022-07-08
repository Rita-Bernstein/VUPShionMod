package VUPShionMod.actions.Wangchuan;

import VUPShionMod.cards.WangChuan.InTheBlink;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SwardChargeMaxAction extends AbstractGameAction {

    public SwardChargeMaxAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        AbstractCard card = new InTheBlink();
        card.tags.add(CardTagsEnum.NoSwardCharge);

        for (int i = 0; i < this.amount + 3; i++)
            card.upgrade();


        card.exhaustOnUseOnce = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;

        card.applyPowers();
        addToTop(new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.miscRng), false, true));
        addToTop(new UnlimboAction(card));
        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }

        isDone = true;
    }
}
