package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MedicalCollar extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(MedicalCollar.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/MedicalCollar.png";
    private static final String OUTLINE_PATH = "img/relics/outline/MedicalCollar.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public MedicalCollar() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
            flash();
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
