package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RosaSpinaque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("RosaSpinaque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc32.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = -1;

    public RosaSpinaque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.isEthereal = true;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<Integer> actionConsumer = effect -> {
            Supplier<AbstractPower> powerToApply = () -> new ConstrictedPower(null, AbstractDungeon.player, this.upgraded ? 9 * effect : 6 * effect);
            addToTop(new ApplyPowerToAllEnemyAction(powerToApply));
            addToTop(new GainBlockAction(p, p, upgraded ? 5 * effect : 4 * effect));
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
        Supplier<AbstractPower> powerToApply = () -> new WeakPower(null, this.magicNumber, false);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
