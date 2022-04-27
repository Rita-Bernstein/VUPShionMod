package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplyPsychicAction;
import VUPShionMod.powers.LoseHPPower;
import VUPShionMod.stances.JudgeStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JudgementOfSins extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(JudgementOfSins.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public JudgementOfSins() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 1;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(JudgeStance.STANCE_ID));
        addToBot(new ApplyPsychicAction(p, this.magicNumber));

        if (this.upgraded) {
            addToBot(new LoseHPAction(p, p, 25));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.increaseMaxHp(5, false);
                    isDone = true;
                }
            });

        } else {
            addToBot(new ApplyPowerAction(p, p, new LoseHPPower(p, 3)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.name = EXTENDED_DESCRIPTION[0];
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isInnate = false;
        }
    }
}