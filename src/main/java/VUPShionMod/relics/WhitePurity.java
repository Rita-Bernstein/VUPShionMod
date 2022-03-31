package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.OnrushingTip;
import VUPShionMod.powers.CorGladiiPower;
import basemod.abstracts.CustomRelic;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhitePurity extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("WhitePurity");
    public static final String IMG_PATH = "img/relics/WhitePurity.png";
    private static final String OUTLINE_PATH = "img/relics/outline/WhitePurity.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public WhitePurity() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {

        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new CorGladiiPower(AbstractDungeon.player, 5)));
        
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            flash();
            int amount = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount / 3;
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID, amount));
        }
    }


    @Override
    public void atTurnStartPostDraw() {
        AbstractCard c = new OnrushingTip();
        c.upgrade();
        CardModifierManager.addModifier(c, new EtherealMod());
        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof PureHeart).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(PureHeart.ID);
    }
}
