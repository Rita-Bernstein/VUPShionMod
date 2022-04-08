package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;

public class PlacidAqua extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("PlacidAqua");
    public static final String IMG_PATH = "img/relics/PlacidAqua.png";
    private static final String OUTLINE_PATH = "img/relics/outline/PlacidAqua.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public PlacidAqua() {
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
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3)));
    }

    @Override
    public void atTurnStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false)));
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof TheRipple).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TheRipple.ID);
    }
}
