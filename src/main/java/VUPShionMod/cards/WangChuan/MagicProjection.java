package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MagicProjection extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(MagicProjection.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc54.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public MagicProjection() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new GladiiInfiniti();
        switch (this.timesUpgraded) {
            default:
                while (c instanceof GladiiInfiniti) {
                    c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                }
                for (int i = 0; i < this.magicNumber; i++) {
                    c.setCostForTurn(0);
                    CardModifierManager.addModifier(c, new ExhaustMod());
                    addToBot(new MakeTempCardInHandAction(c, true));
                }
                break;
            case 1:
                while (c instanceof GladiiInfiniti) {
                    c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                }
                for (int i = 0; i < this.magicNumber; i++) {
                    c.upgrade();
                    c.setCostForTurn(0);
                    CardModifierManager.addModifier(c, new ExhaustMod());
                    addToBot(new MakeTempCardInHandAction(c, true));
                }
                break;
            case 2:
                c.upgrade();
                c.setCostForTurn(0);
                CardModifierManager.addModifier(c, new ExhaustMod());
                addToBot(new MakeTempCardInHandAction(c, true));
                break;
        }

        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }


    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }
    }
}
