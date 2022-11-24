package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import VUPShionMod.potions.MagiaMagazine;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class PowerCore2 extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(PowerCore2.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/PowerCore2.png";
    private static final String OUTLINE_PATH = "img/relics/outline/PowerCore2.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public PowerCore2() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
        getKeyword();
    }

    private void getKeyword(){
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1],DESCRIPTIONS[2]));
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,-5)));
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        addToBot(new ObtainPotionAction(new MagiaMagazine()));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster+=2;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster-=2;
    }


    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof PowerCore).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(PowerCore.ID);
    }
}
