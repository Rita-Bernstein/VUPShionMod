package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.BetterDamageRandomEnemyAction;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MagiaSwordRed extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(MagiaSwordRed.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/MagiaSwordRed.png";
    private static final String OUTLINE_PATH = "img/relics/outline/MagiaSwordRed.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public MagiaSwordRed() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MagiamObruorPower(AbstractDungeon.player, 1)));
        }
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL)
            addToBot(new BetterDamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 4, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new LoseCorGladiiAction(0.5f));
    }
}
