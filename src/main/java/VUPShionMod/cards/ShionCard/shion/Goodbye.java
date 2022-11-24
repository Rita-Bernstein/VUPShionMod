package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.PlayTmpCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.cards.ShionCard.anastasia.TeamWork;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Goodbye extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Goodbye.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy18.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Goodbye() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 3;
        this.selfRetain = true;
        GraveField.grave.set(this,true);
        this.exhaust =true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new SFXAction("SHION_12"));
        addToBot(new ExhaustAction(BaseMod.MAX_HAND_SIZE, true, false, false, Settings.ACTION_DUR_XFAST));

        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new PlayTmpCardAction(AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK)));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
