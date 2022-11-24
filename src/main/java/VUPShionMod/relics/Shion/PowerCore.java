package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import VUPShionMod.potions.MagiaMagazine;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PowerCore extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(PowerCore.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/PowerCore.png";
    private static final String OUTLINE_PATH = "img/relics/outline/PowerCore.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public PowerCore() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
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
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,-5)));
        addToBot(new ObtainPotionAction(new MagiaMagazine()));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster+=2;
            AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
                @Override
                public void update() {
                    AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots));
                    AbstractDungeon.player.potionSlots ++;
                    isDone = true;
                }

                @Override
                public void render(SpriteBatch spriteBatch) {
                }

                @Override
                public void dispose() {
                }
            });
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster-=2;
    }
}
