package VUPShionMod.monsters.HardModeBoss.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Unique.BossFinFunnelMinionAction;
import VUPShionMod.actions.Unique.RemovePlayerBuffAction;
import VUPShionMod.actions.Unique.TurnTriggerAllBossFinFunnelAction;
import VUPShionMod.actions.Unique.VersusEffectAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.*;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Monster.BossShion.*;
import VUPShionMod.powers.Shion.GravitoniumPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.skins.sk.Shion.MinamiShion;
import VUPShionMod.skins.sk.Shion.OriShion;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import VUPShionMod.vfx.EisluRen.FinalFlashBlastEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class MinamiShionBoss extends AbstractShionBoss {
    public static final String ID = VUPShionMod.makeID(MinamiShionBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private int moveCount = 1;

    public MinamiShionBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 520.0F, 460.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(600);
        } else {
            setHp(500);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 46));
            this.damage.add(new DamageInfo(this, 60));
        } else {
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 46));
            this.damage.add(new DamageInfo(this, 60));
        }


        this.type = EnemyType.ELITE;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(0, 3).atlasURL,
                SkinManager.getSkin(0, 3).jsonURL,
                SkinManager.getSkin(0, 3).renderScale);


        this.state.setAnimation(0, "idle", true);

        this.flipHorizontal = true;

    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        addToBot(new VersusEffectAction(MinamiShion.ID));
        addToBot(new GainShieldAction(this, 50));
        addToBot(new ApplyPowerAction(this, this, new GravitoniumPower(this)));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Full")));
    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new SFXAction("MINAMI_4"));
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                for (int i = 0; i < 4; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

                break;
            case 2:
                addToBot(new SFXAction("MINAMI_5"));
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (int i = 0; i < 4; i++) {
                            AbstractBossFinFunnel finFunnel = new BossGravityFinFunnel(22, MinamiShionBoss.this, i, 3);
                            finFunnel.updateMinamiPos();
                            bossFinFunnels.add(finFunnel);
                        }

                        isDone = true;
                    }
                });


                break;
            case 3:
                addToBot(new SFXAction("MINAMI_6"));
                addToBot(new TalkAction(this, DIALOG[2], 1.5F, 1.5F));

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (int i = 0; i < 2; i++) {
                            AbstractBossFinFunnel finFunnel = new BossPursuitFinFunnel(10, MinamiShionBoss.this, i + 4, 3);
                            finFunnel.updateMinamiPos();
                            bossFinFunnels.add(finFunnel);
                        }

                        isDone = true;
                    }
                });

                break;
            case 4:
                addToBot(new SFXAction("MINAMI_7"));
                addToBot(new TalkAction(this, DIALOG[3], 1.5F, 1.5F));

                addToBot(new BossFinFunnelMinionAction(this));
                break;
            case 5:
                addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 3, true, true));
                break;
            case 6:
                addToBot(new SFXAction("MINAMI_8"));
                addToBot(new TalkAction(this, DIALOG[4], 1.5F, 1.5F));

                addToBot(new GainShieldAction(this, 300));
                break;
            case 7:
                addToBot(new SFXAction("MINAMI_9"));
                addToBot(new TalkAction(this, DIALOG[5], 1.5F, 1.5F));

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        bossFinFunnels.clear();

                        for (int i = 0; i < 6; i++) {
                            AbstractBossFinFunnel finFunnel = new BossPursuitFinFunnel(10, MinamiShionBoss.this, i, 3);
                            finFunnel.updateMinamiPos();
                            bossFinFunnels.add(finFunnel);
                        }

                        isDone = true;
                    }
                });


                break;
            case 8:
                addToBot(new SFXAction("MINAMI_10"));
                addToBot(new TalkAction(this, DIALOG[6], 1.5F, 1.5F));

                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (bossFinFunnels.isEmpty()) {
                            isDone = true;
                            return;
                        }


                        for (AbstractBossFinFunnel finFunnel : bossFinFunnels) {
                            AbstractDungeon.effectsQueue.add(new FinalFlashBlastEffect(finFunnel.muzzle_X, finFunnel.muzzle_Y, MinamiShionBoss.this.flipHorizontal));
                        }
                        isDone = true;
                    }
                });

                for (int i = 0; i < 6; i++) {
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));
                }


                break;
            case 9:
                addToBot(new SFXAction("MINAMI_11"));
                addToBot(new TalkAction(this, DIALOG[7], 1.5F, 1.5F));

                (AbstractDungeon.getCurrRoom()).smoked = true;
                addToBot(new EscapeAction(this));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.ATTACK, damage.get(0).base, 4, true);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.BUFF);
                this.moveCount++;
                break;
            case 3:
                setMove((byte) 3, Intent.BUFF);
                this.moveCount++;
                break;
            case 4:
                setMove((byte) 4, Intent.ATTACK_DEBUFF, damage.get(1).base);
                this.moveCount++;
                break;
            case 5:
                setMove((byte) 5, Intent.STRONG_DEBUFF);
                break;
            case 6:
                setMove((byte) 6, Intent.DEFEND);
                this.moveCount++;
                break;
            case 7:
                setMove((byte) 7, Intent.BUFF);
                this.moveCount++;
                break;
            case 8:
                setMove((byte) 8, Intent.ATTACK_DEFEND, damage.get(2).base, 6, true);
                this.moveCount++;
                break;
            case 9:
                setMove((byte) 9, Intent.ESCAPE);
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Full":
                this.moveCount = 6;
                rollMove();
                createIntent();
                applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                break;
        }
    }

    @SpireOverride
    protected void calculateDamage(int dmg) {
        if (this.moveCount != 4) {
            SpireSuper.call(dmg);
            return;
        }


        AbstractPlayer target = AbstractDungeon.player;
        float tmp = dmg;


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            if (minion != null)
                for (AbstractPower p : minion.powers) {
                    tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);
                }
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            if (minion != null)
                for (AbstractPower p : minion.powers) {
                    tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
                }
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }


    @SpireOverride
    protected void updateEscapeAnimation() {
        if (this.escapeTimer != 0.0F) {
            this.flipHorizontal = false;
            this.escapeTimer -= Gdx.graphics.getDeltaTime();
            this.drawX += Gdx.graphics.getDeltaTime() * 400.0F * Settings.scale;
        }
        if (this.escapeTimer < 0.0F) {
            this.escaped = true;
            if (AbstractDungeon.getMonsters().areMonstersDead() && !(AbstractDungeon.getCurrRoom()).isBattleOver &&
                    !(AbstractDungeon.getCurrRoom()).cannotLose) {
                AbstractDungeon.getCurrRoom().endBattle();
            }
        }
    }

}
