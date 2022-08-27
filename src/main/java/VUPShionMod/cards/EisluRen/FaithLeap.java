package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class FaithLeap extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(FaithLeap.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/FaithLeap.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public FaithLeap() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new PressEndTurnButtonAction());

        if (this.upgraded) {
            if (AbstractDungeon.cardRng.random(99) < 25)
                addToBot(new DamageAction(p, new DamageInfo(p, 15, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            else if (AbstractDungeon.cardRng.random(99) < 1)
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractDungeon.player.currentHealth = 1;
                        AbstractDungeon.player.healthBarUpdatedEvent();
                        isDone = true;
                    }
                });
        }else {
            if (AbstractDungeon.cardRng.random(99) < 35)
                addToBot(new DamageAction(p, new DamageInfo(p, 15, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            else if (AbstractDungeon.cardRng.random(99) < 2)
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractDungeon.player.currentHealth = 1;
                        AbstractDungeon.player.healthBarUpdatedEvent();
                        isDone = true;
                    }
                });
        }
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
