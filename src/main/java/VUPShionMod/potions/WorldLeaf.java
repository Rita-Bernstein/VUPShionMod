package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;

public class WorldLeaf extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(WorldLeaf.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;

    public WorldLeaf() {
        super(POTION_ID, WorldLeaf.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;

    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        if (AbstractDungeon.player.currentHealth < (int) (AbstractDungeon.player.maxHealth * this.potency / 100.0F)) {
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) (AbstractDungeon.player.maxHealth * this.potency / 100.0F - AbstractDungeon.player.currentHealth)));
            } else {
                AbstractDungeon.player.heal((int) (AbstractDungeon.player.maxHealth * this.potency / 100.0F - AbstractDungeon.player.currentHealth));
            }
        }

    }


    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain) {
            return false;
        }

        return true;
    }


    public int getPotency(int ascensionLevel) {
        return 50;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new WorldLeaf();
    }
}