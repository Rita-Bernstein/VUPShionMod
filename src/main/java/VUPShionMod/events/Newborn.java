package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.relics.AnastasiaNecklace;
import VUPShionMod.relics.Croissant;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Newborn extends AbstractImageEvent {
    public static final String ID = VUPShionMod.makeID("Newborn");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurrentScreen curScreen = CurrentScreen.INTRO;

    private enum CurrentScreen {
        INTRO, COMPLETE
    }


    public Newborn() {
        super(NAME, DESCRIPTIONS[0], VUPShionMod.assetPath("img/events/BossEvent1.png"));
        this.imageEventText.setDialogOption(OPTIONS[0]);
    }


    public void onEnterRoom() {
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                this.imageEventText.updateBodyText(eventStrings.DESCRIPTIONS[1]);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3],true);
                this.imageEventText.setDialogOption(OPTIONS[2]);
                this.imageEventText.loadImage(VUPShionMod.assetPath("img/events/BossEvent2.png"));
                this.curScreen = CurrentScreen.COMPLETE;
                break;
            case COMPLETE:
                if (buttonPressed == 0){
                    fightBoss();
                } else{
                    leave();
                }
                break;
        }
    }

    private void fightBoss() {
        VUPShionMod.fightSpecialBoss = false;
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, new AnastasiaNecklace());

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.bossKey = PlagaAMundo.ID;
        MapRoomNode node = new MapRoomNode(-1, 15);
        node.room = new MonsterRoomBoss();
        AbstractDungeon.nextRoom = node;
        CardCrawlGame.music.fadeOutTempBGM();
        AbstractDungeon.pathX.add(1);
        AbstractDungeon.pathY.add(15);
        AbstractDungeon.nextRoomTransitionStart();
    }

    private void leave() {
        CardCrawlGame.music.fadeOutBGM();

        MapRoomNode node = new MapRoomNode(3, 4);
        node.room = new TrueVictoryRoom();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
    }
}
