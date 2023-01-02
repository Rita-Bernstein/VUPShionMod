package VUPShionMod.vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.function.Predicate;

public class UpPotionEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float DUR = 1.5F;
    private boolean openedScreen;
    private final Color screenColor;
    private int times = 1;
    private Predicate<AbstractCard> predicate;
    private boolean justStart = true;


    public UpPotionEffect(int times, Predicate<AbstractCard> predicate) {
        this.openedScreen = false;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.times = times;
        this.predicate = predicate;
    }

    public UpPotionEffect(int times) {
        this.openedScreen = false;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.times = times;
    }

    public UpPotionEffect() {
        this(1);
    }


    public void update() {
        if (this.justStart) {
            this.justStart = false;

            if (AbstractDungeon.player.masterDeck.isEmpty() || this.times <= 0) {
                this.isDone = true;
                return;
            }
        }


        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }


        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.times--;
        }


        if (this.duration < 1.0F && !this.openedScreen) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.predicate != null) {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card.canUpgrade() && this.predicate.test(card))
                        temp.addToTop(card);
                }
            } else {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card.canUpgrade())
                        temp.addToTop(card);
                }
            }

            if (temp.isEmpty()) {
                this.isDone = true;
                return;
            }

            this.openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], true, false, true, false);

        }


        if (this.duration < 0.0F) {
            if (this.times <= 0)
                this.isDone = true;
            else {
                this.duration = DUR;
                this.openedScreen = false;
                this.screenColor.a = 0.0F;
            }
        }
    }


    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID)
            AbstractDungeon.gridSelectScreen.render(sb);
    }

    public void dispose() {
    }
}