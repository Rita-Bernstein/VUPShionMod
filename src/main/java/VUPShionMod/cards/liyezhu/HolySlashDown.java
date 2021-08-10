package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.HolySlashDownPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HolySlashDown extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolySlashDown");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz12.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HolySlashDown(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public HolySlashDown() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int temp;
        for (temp = 0; temp < this.magicNumber; temp++) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public AbstractCard makeCopy() {
        return new HolySlashDown(this.timesUpgraded);
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(1);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }
}
