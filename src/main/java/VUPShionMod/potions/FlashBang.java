package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FlashBang extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(FlashBang.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;

    public FlashBang() {
        super(POTION_ID, FlashBang.class.getSimpleName() + ".png", RARITY);
        this.isThrown = true;
        this.targetRequired = false;
//        this.labOutlineColor = VUPShionMod.Shion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = this.potency > 1 ? String.format(DESCRIPTIONS[1], this.potency) : DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        addToBot(new StunMonsterAction(monster, AbstractDungeon.player, this.potency));
                    }
                }
            }
        }
    }


    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FlashBang();
    }
}