package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.cards.WangChuan.OnrushingTip;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrototypeCup extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(PrototypeCup.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/PrototypeCup.png";
    private static final String OUTLINE_PATH = "img/relics/outline/PrototypeCup.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public PrototypeCup() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onPlayerEndTurn() {
        addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, MagiamObruorPower.POWER_ID, 5));
    }


    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster++;
    }
}
