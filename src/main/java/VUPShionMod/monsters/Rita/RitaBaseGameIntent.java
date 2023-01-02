package VUPShionMod.monsters.Rita;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.RemoveAllShieldAction;
import VUPShionMod.actions.Unique.VersusEffectAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.Monster.BossShion.PotentialOutbreakPower;
import VUPShionMod.powers.Monster.PlagaAMundo.StrengthenPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

public class RitaBaseGameIntent extends AbstractMonsterIntent {
    private final PlayerDataListener playerDataListener = new PlayerDataListener();


    public RitaBaseGameIntent(AbstractMonster m) {
        super(m);
    }

    @Override
    public void initDamage() {
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.m.damage.add(new DamageInfo(this.m, 10));
            this.m.damage.add(new DamageInfo(this.m, 18));
            this.m.damage.add(new DamageInfo(this.m, 50));
            this.m.damage.add(new DamageInfo(this.m, 10));
            this.m.damage.add(new DamageInfo(this.m, 9));

        } else {
            this.m.damage.add(new DamageInfo(this.m, 10));
            this.m.damage.add(new DamageInfo(this.m, 14));
            this.m.damage.add(new DamageInfo(this.m, 50));
            this.m.damage.add(new DamageInfo(this.m, 10));
            this.m.damage.add(new DamageInfo(this.m, 9));

        }
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.playTempBGM(VUPShionMod.makeID("RitaFight1"));

//        addToBot(new VersusEffectAction("RitaShop"));
        addToBot(new ApplyPowerAction(m, m, new PotentialOutbreakPower(m, (int) (m.maxHealth * 0.3f), "Silent")));
    }


    @Override
    public void takeTurn() {
        playerDataListener.recordPlayerData();
        int amount = 0;

        switch (m.nextMove) {
            case 1:
                addToBot(new VFXAction(this.m, new VerticalAuraEffect(Color.BLACK, this.m.hb.cX, this.m.hb.cY), 0.33F));
                addToBot(new SFXAction("ATTACK_FIRE"));
                addToBot(new VFXAction(this.m, new VerticalAuraEffect(Color.PURPLE, this.m.hb.cX, this.m.hb.cY), 0.33F));
                addToBot(new VFXAction(this.m, new VerticalAuraEffect(Color.CYAN, this.m.hb.cX, this.m.hb.cY), 0.0F));
                addToBot(new VFXAction(this.m, new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));
                addToBot(new ApplyPowerAction(m, m, new StrengthenPower(m, AbstractDungeon.ascensionLevel >= 19 ? 3 : 2)));
                break;
            case 2:
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Shockwave")));
                addToBot(new VFXAction(m, new ShockWaveEffect(m.hb.cX, m.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));

                addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, 3, true)));
                addToBot(new ApplyPowerAction(p, m, new WeakPower(p, 3, true)));
                break;
            case 3:
                if (Settings.FAST_MODE)
                    addToBot(new AnimateFastAttackAction(m));
                else
                    addToBot(new AnimateSlowAttackAction(m));

                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_MeteorStrike")));
                addToBot(new VFXAction(new ClashEffect(p.hb.cX, p.hb.cY), 0.1F));
                addToBot(new DamageAction(p, m.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                break;
            case 4:
                addToBot(new GainBlockAction(m, 40));
                break;
            case 10:
                addToBot(new ApplyPowerAction(m, m, new IntangiblePlayerPower(m, 3)));
                break;
            case 11:
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Skill0")));
                addToBot(new ApplyPowerAction(m, m, new AfterImagePower(m, AbstractDungeon.ascensionLevel >= 19 ? 3 : 2) {
                    @Override
                    public void onUseCard(AbstractCard card, UseCardAction action) {
                        flash();
                        if (Settings.FAST_MODE) {
                            this.addToBot(new GainBlockAction(m, m, amount, true));
                        } else {
                            this.addToBot(new GainBlockAction(m, m, amount));
                        }
                    }
                }));
                addToBot(new ApplyPowerAction(p, m, new StrengthPower(p, -2)));
                addToBot(new ApplyPowerAction(p, m, new WeakPower(p, 2, true)));
                break;
            case 12:
                break;
            case 13:
                if (Settings.FAST_MODE) {
                    addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));
                } else {
                    addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
                }

                if (Settings.FAST_MODE)
                    addToBot(new AnimateFastAttackAction(m));
                else
                    addToBot(new AnimateSlowAttackAction(m));

                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_FiendFire1")));
                addToBot(new DamageAction(p, m.damage.get(2), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 14:
                addToBot(new GainBlockAction(m, 11));
                addToBot(new ApplyPowerAction(p, m, new WeakPower(p, 2, true)));
                break;
            case 30:
                break;
            case 31:
                addToBot(new VFXAction(new FastingEffect(m.hb.cX, m.hb.cY, Color.CHARTREUSE)));
                addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, AbstractDungeon.ascensionLevel >= 19 ? 4 : 3)));
                addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, AbstractDungeon.ascensionLevel >= 19 ? 4 : 3)));
                break;
            case 32:
                addToBot(new DamageAction(p, m.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, 2, true)));
                break;
            case 33:
                CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_TrueMod")));
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK, true));
                AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(m.hb.cX, m.hb.cY, "Divinity"));
                ((AbstractVUPShionBoss) m).stance = new DivinityStance();
                addToBot(new ApplyPowerAction(m, m, new EndTurnDeathPower(m) {
                    private boolean justApplied = false;

                    @Override
                    public void atStartOfTurn() {
                    }

                    @Override
                    public void atEndOfRound() {
                        if (!justApplied) {
                            justApplied = true;
                            return;
                        }
                        super.atStartOfTurn();
                        ((AbstractVUPShionBoss) m).stance = new NeutralStance();
                    }
                }));
                break;
            case 34:
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Expunger")));
                if (Settings.FAST_MODE)
                    addToBot(new AnimateFastAttackAction(m));
                else
                    addToBot(new AnimateSlowAttackAction(m));
                amount = 3;
                for (int i = 0; i < amount; i++) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            addToTop(new VFXAction(new AnimatedSlashEffect(
                                    AbstractDungeon.player.hb.cX,
                                    AbstractDungeon.player.hb.cY - 30.0F * Settings.scale,
                                    -500.0F, -500.0F, 135.0F, 4.0F,
                                    Color.VIOLET, Color.MAGENTA)));
                            addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.7F, true));
                            addToTop(new SFXAction("ATTACK_IRON_3", 0.2F));
                            isDone = true;
                        }
                    });
                    addToBot(new DamageAction(p, m.damage.get(4), AbstractGameAction.AttackEffect.NONE, true));
                }
                break;

            case 29:
                addToBot(new DamageAction(p, m.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new RemoveAllBlockAction(p, m));
                addToBot(new RemoveAllShieldAction(p));
                break;
        }

        addToBot(new RollMoveAction(this.m));
    }

    @Override
    public void getMove(int num) {
        if (this.moveCount >= 4 && this.moveCount < 32 && this.playerDataListener.playerHasHugeBlock()) {
            this.playerDataListener.playerHasHugeBlockUsed();
            m.setMove(getCardStringName(Melter.ID) + " EX", (byte) 29, AbstractMonster.Intent.ATTACK_DEBUFF, m.damage.get(3).base);
        }

        switch (this.moveCount) {
            case 1:
                m.setMove(getCardStringName(DemonForm.ID, AbstractDungeon.ascensionLevel >= 19), (byte) 1, AbstractMonster.Intent.BUFF);
                this.moveCount++;
                break;
            case 2:
                m.setMove(getCardStringName(Shockwave.ID), (byte) 2, AbstractMonster.Intent.DEBUFF);
                this.moveCount++;
                break;
            case 3:
                m.setMove(getCardStringName(Clash.ID, AbstractDungeon.ascensionLevel >= 4), (byte) 3, AbstractMonster.Intent.ATTACK, m.damage.get(1).base);
                this.moveCount++;
                break;
            case 4:
                m.setMove(getCardStringName(Impervious.ID, true), (byte) 4, AbstractMonster.Intent.DEFEND);
                this.moveCount = 2;
                break;
            case 10:
                m.setMove(getCardStringName(WraithForm.ID), (byte) 10, AbstractMonster.Intent.BUFF);
                this.moveCount = 11;
                break;
            case 11:
                m.setMove(getCardStringName(AfterImage.ID) + "/" + getCardStringName(Malaise.ID), (byte) 11, AbstractMonster.Intent.STRONG_DEBUFF);
                this.moveCount += 2;
                break;
            case 12:
                m.setMove(getCardStringName(Acrobatics.ID, true), (byte) 12, AbstractMonster.Intent.UNKNOWN);
                this.moveCount++;
                break;
            case 13:
                m.setMove(getCardStringName(GrandFinale.ID), (byte) 13, AbstractMonster.Intent.ATTACK, m.damage.get(2).base);
                this.moveCount++;
                break;
            case 14:
                m.setMove(getCardStringName(LegSweep.ID), (byte) 14, AbstractMonster.Intent.DEFEND_DEBUFF);
                this.moveCount = 12;
                break;


            case 30:
                m.setMove(getCardStringName(ConjureBlade.ID), (byte) 30, AbstractMonster.Intent.UNKNOWN);
                this.moveCount = 31;
                break;
            case 31:
                m.setMove(getCardStringName(Fasting.ID, AbstractDungeon.ascensionLevel >= 19), (byte) 31, AbstractMonster.Intent.BUFF);
                this.moveCount++;
                break;
            case 32:
                m.setMove(getCardStringName(CrushJoints.ID, true), (byte) 32, AbstractMonster.Intent.ATTACK_DEBUFF, m.damage.get(0).base);
                this.moveCount++;
                break;
            case 33:
                m.setMove(getCardStringName(Blasphemy.ID), (byte) 33, AbstractMonster.Intent.BUFF);
                this.moveCount++;
                break;
            case 34:
                m.setMove(getCardStringName(Expunger.ID), (byte) 34, AbstractMonster.Intent.ATTACK, m.damage.get(4).base, 3, true);
                break;

        }
    }


    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Silent":
                this.moveCount = 10;
                m.rollMove();
                m.createIntent();
                m.applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                addToBot(new ApplyPowerAction(m, m, new PotentialOutbreakPower(m, (int) (m.maxHealth * 0.3f), "Watcher")));
                break;
            case "Watcher":
                this.moveCount = 30;
                m.rollMove();
                m.createIntent();
                m.applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                break;

        }

    }

    private String getCardStringName(String id) {
        return getCardStringName(id, false);
    }

    private String getCardStringName(String id, boolean upgraded) {
        if (upgraded) return CardCrawlGame.languagePack.getCardStrings(id).NAME + "+";
        return CardCrawlGame.languagePack.getCardStrings(id).NAME;
    }


}
