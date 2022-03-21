package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.XActionAction;
import VUPShionMod.powers.AcceleratorPower;
import VUPShionMod.powers.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GensBombardae extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("GensBombardae");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc53.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -1;

    public GensBombardae() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.cardsToPreview = new BombardaMagica();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<Integer> actionConsumer = effect -> {
            AbstractCard c = new BombardaMagica();
            if (upgraded) c.upgrade();
            addToBot(new MakeTempCardInHandAction(c, effect + 1));
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }
}
