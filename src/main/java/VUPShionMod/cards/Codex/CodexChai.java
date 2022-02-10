package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CodexChai extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("CodexChai");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/hundun.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public CodexChai(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.timesUpgraded = upgrades;
    }

    public CodexChai() {
        this(0);
    }


    @Override
    protected void useEffect(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new ExhaustAction(1, false));
        addToBot(new DiscardAction(p, p, 1, false));
    }


    @Override
    protected void useEffect1(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
        addToBot(new ExhaustAction(BaseMod.MAX_HAND_SIZE, false, true, true));
    }

    @Override
    protected void useEffect2(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new ExhaustAction(1, false));
        addToBot(new DiscardAction(p, p, 1, false));
    }

    @Override
    protected void useEffect3(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new ExhaustAction(3, false, true, true));
    }

    @Override
    protected void upgradeEffect1() {
        if (this.timesUpgraded < 1) {
            this.exhaust = true;
        } else
            this.exhaust = false;
    }

    @Override
    protected void upgradeEffect2() {
        if (this.timesUpgraded < 1) {
            upgradeMagicNumber(1);
        } else
            this.magicNumber = this.baseMagicNumber = 3;
    }
}
