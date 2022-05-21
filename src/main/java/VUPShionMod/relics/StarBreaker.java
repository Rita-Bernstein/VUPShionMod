package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StarBreaker extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(StarBreaker.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/StarBreaker.png";
    private static final String OUTLINE_PATH = "img/relics/outline/StarBreaker.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public StarBreaker() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new CorGladiiPower(AbstractDungeon.player, 4)));
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            flash();
            int amount = (int) Math.floor(AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * 0.15f);
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID, amount));
        }
    }
    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof StarQuakes).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(StarQuakes.ID);
    }

}
