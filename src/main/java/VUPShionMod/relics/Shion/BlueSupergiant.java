package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlueSupergiant extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(BlueSupergiant.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/BlueSupergiant.png";
    private static final String OUTLINE_PATH = "img/relics/outline/BlueSupergiant.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public BlueSupergiant() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new MakeTempCardInHandAction(new FunnelMatrix()));
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof BlueGiant).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BlueGiant.ID);
    }
}
