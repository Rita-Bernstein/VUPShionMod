package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BladeFragment extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(BladeFragment.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/BladeFragment.png";
    private static final String OUTLINE_PATH = "img/relics/outline/BladeFragment.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public BladeFragment() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new LoseEnergyAction(3));
    }

    @Override
    public void atBattleStartPreDraw() {
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (card.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                cardsToRemove.add(card);
            }
        }

        for (AbstractCard card : cardsToRemove) {
            AbstractDungeon.player.drawPile.removeCard(card);
            addToBot(new PlayTmpCardAction(card));
        }


    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
