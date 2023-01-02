package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class CruciformPenanceAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int duelSinTimes;

    public CruciformPenanceAction(AbstractCreature target, DamageInfo info, int healAmount, int duelSinTimes) {
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_LONG;
        this.duration = this.startDuration;
        this.amount = healAmount;
        this.target = target;
        this.info = info;
        this.duelSinTimes = duelSinTimes;
    }

    private boolean cardToExhaust(AbstractCard c) {
        return c instanceof Miracle;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            int statusCount = 0;

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (cardToExhaust(c))
                    statusCount++;
            }

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (cardToExhaust(c))
                    statusCount++;
            }

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (cardToExhaust(c))
                    statusCount++;
            }


            if (statusCount > 0) {
                for (int i = 0; i < statusCount * duelSinTimes; i++) {
                    addToTop(new DuelSinAction());
                }

                for (int i = 0; i < statusCount; i++) {
                    addToBot(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.NONE));
                }

                if (this.target != null)
                    addToBot(new VFXAction(new ClashEffect(this.target.hb.cX, this.target.hb.cY), 0.1F));

                addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, statusCount * amount));
            }

            for (int i = 0; i < AbstractDungeon.player.drawPile.size(); ) {
                AbstractCard c = AbstractDungeon.player.drawPile.group.get(i);
                if (cardToExhaust(c)) {
                    AbstractDungeon.player.drawPile.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToTop(new WaitAction(0.1F));
                    addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                } else {
                    i++;
                }
            }

            for (int i = 0; i < AbstractDungeon.player.discardPile.size(); ) {
                AbstractCard c = AbstractDungeon.player.discardPile.group.get(i);
                if (cardToExhaust(c)) {
                    AbstractDungeon.player.discardPile.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToTop(new WaitAction(0.1F));
                    addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                } else {
                    i++;
                }
            }

            for (int i = 0; i < AbstractDungeon.player.hand.size(); ) {
                AbstractCard c = AbstractDungeon.player.hand.group.get(i);
                if (cardToExhaust(c)) {
                    AbstractDungeon.player.hand.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToTop(new WaitAction(0.1F));
                    addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                } else {
                    i++;
                }
            }
        }

        tickDuration();
    }
}