package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.cards.WangChuan.BombardaMagica;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Consumer;

public class SoilNB extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SoilNB.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/SoilNB.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -1;

    public SoilNB() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 6;
        this.baseDamage = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<Integer> actionConsumer = effect -> {
            if (upgraded)
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (monster != null && !monster.isDeadOrEscaped() && monster.getIntentDmg() > 0) {
                        calculateCardDamage(monster);
                        addToTop(new DamageAction(monster, new DamageInfo(p, this.damage * effect, this.damageTypeForTurn),
                                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    }
                }

            addToTop(new GainShieldAction(p, this.magicNumber * effect));

        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
            this.name = EXTENDED_DESCRIPTION[0];
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
