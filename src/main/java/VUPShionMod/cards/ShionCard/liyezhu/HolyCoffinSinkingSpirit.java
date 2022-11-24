package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.character.Shion;
import VUPShionMod.powers.Shion.HolyCoffinSinkingSpiritPower;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class HolyCoffinSinkingSpirit extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(HolyCoffinSinkingSpirit.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz11.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HolyCoffinSinkingSpirit() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HolyCoffinSinkingSpiritPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }


}
