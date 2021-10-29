package fun.archware.impl.modules.misc;

import fun.archware.impl.modules.combat.KillAura;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.network.play.server.SPacketChat;

public class KillSult extends Module {
    private StringValue mode = new StringValue("Mode", "KillSultMode", this, "WellMore", new String[]{"WellMore"});
    private BooleanValue custom = new BooleanValue("Custom sults", "KillSultCustom", this, false);

    private String[] sults = {
            "!Well, $player got f+u+с+k+e+d by ArchWare",
            "!$player сброшена атомная бомба на город под названием \"клитop\" твоей матери",
            "!$player ты понимаешь, что я свой xyй запускал как ракету на пиздy твоей матери, её пиздa это поле боя??",
            "!$player СЛЫШЬ,Я ТВОЮ МАТЬ СВОИМ ХYEМ НА ПРОГИБ ВОЗЬМУ И УЕБУ В ПОЧВУ",
            "!$player МОЯ ЗAЛYПA ДЛЯ ТЕБЯ КАК АРБУЗ,МОЖЕТ НА КОРМИТЬ И НАССАТЬ В ТЕБЯ ТЕМ САМЫМ УТОЛЯЯ ТВОЮ ЖАЖДУ",
            "!$player Я ЩАС ТЕБЕ СВОЕЙ ЗАЛYПOЙ РЕБРА СЛОМАЮ И ТЫ СМОЖЕШЬ В ЛЮБОМ ПОЛОЖЕНИИ СОСАТЬ МОЙ ХYЙ ДЯТЕЛ ЕБАНЫЙ",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ПИЗДAК ТВОЕЙ МАТЕРИ НА СВОЙ XУЙ КАК МАКАРОНИНУ НАМОТАЛ БЛЯДb И НАЧАЛ РАСКРУЧИВАТЬ ЕЁ, ПОСЛЕ ЧЕГО ВЫКИНУЛ В КОСМОС, ЧТОБ ЕЁ ТАМ ИНОПЛАНЕТЯНЕ ХYЯМИ РВАЛИ?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ТВОЮ МАТЬ ОТПРАВИЛ СО СВОЕГО XYЯ В НЕБО, ЧТОБ ОНА СВОИМ ПИЗДAКОМ ПРИНИМАЛА МИТЕОРИТНУЮ АТАКУ?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ВО ВРЕМЯ ХОККЕЙНОГО МАТЧА, ТВОЮ МАТЬ ВЫКИНУЛ НАХYЙ НА ПЛОЩАДКУ, ЧТОБ ОНА ПИЗДAКОМ СВОИМ ВОРОТА РУССКИХ ЗАЩИЩАЛА?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ТВОЯ МАТЬ СИДИТ У МЕНЯ НА ЦЕПИ И КАК БУЛЬДОГ ЁБAHНЫЙ НА МОЙ ХYЙ СЛЮНИ БЛЯДb ПУСКАЕТ?-)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО МОЙ ХYЙ ПАРАБАТИЛ ПИЗДAК ТВОЕЙ МАТЕРИ И НАЧАЛ ТАМ ПРОВОДИТЬ ОПЫТЫ НАД ЕЁ КЛИТOPНЫМИ СТЕНАМИ, ОБЛИВАЯ ИХ КИСЛОТОЙ?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я НА ПИЗДAК ТВОЕЙ МАМАШИ ПОСПОРИЛ, ЧТО СМОГУ ТУДА ОБЪЁМНОГО ДЕСЯТИРАЗМЕРНОЕ ЯДРО ПРОТАЛКНУТЬ?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ТВОЮ МАМУ В РОТ ДОЛБИЛ СО СКОРОСТЬЮ ПЕРЕДВИЖЕНИЯ ГЕПАРДА НАХYЙ, ПОКА ЧТО У НЕЁ ЗУБЫ ОБ МОЙ ХYЙ НЕ СТЁРЛИСЬ))",
            "!$player Я ТВОЮ МАМАШУ ВЫСТАВИЛ НА РАССТРЕЛ ХYЁВ)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я В ПИ3ДAKЕ ТВОЕЙ МТАРЕИ СВИНАРНИК НАXYЙ УСТРОИЛ, И СЛЫШНО КАК ОТТУДА СВИНьИ ОРУТ?)",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я В ПИ3ДAKЕ ТВОЕЙ МАТЕРИ СВОИМ XYЁМ СДЕЛАЛ НАТЯЖНЫЕ ПОТОЛКИ, ТЕПЕРЬ ТАМ 5 ЗВЁЗД НААXYЙ)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я В ПИ3ДAKЕ ТВОЕЙ МТАЕРИ СВОИМ XYЁМ ВИНТЕЛЯЦИЮ ПРОБИЛ, А  ТО ОТ ТУДА ТУХЛЫМ ГОВНОМ ВОНЯЕТ ЧОТ))",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ТВОЯ МАТЬ СКОНЧАЛАСЬ ПРЯМ НА ТЕПЛОТРАССЕ ОТ ТОГО ЧТО ЕЁ XYИ АТАКАВАЛИ))",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ИЗ ПИ3ДAKА ТВОЕЙ МАТЕРИ СЕЮ УДОБРЕНИЕ ПО ПОЛЯМ И ОТ ТУЛДА ВАЛИТСЯ БЛЯДЬ НАВОЗ0)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО В ПИ3ДAKЕ ТОВЕЙ МАТЕРИ ОТЛИЧНАЯ ВИДИМОСТЬ, ПОТОМУ ЧТО ЕЁ ВАГИНАЛЬНЫЕ СТЕНКИ ПОКРЫТЫ УВЕЛИЧИВАЮЩИМ СТЕКЛОМ?)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я XYЁМ НА ПИ3ДAKЕ ТВОЕЙ МАТЕРИ ПОСТАВЛИ КРАСНУЮ МЕТКУ, ЧТОБ ПОТОМ ЕЁ ПИ3ДAK СЛУЧАНОЙ В ТОЛПЕ НЕ ПОТЕРЯТЬ)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ТВОЯ МАТЬ СЕЙЧАС БУДЕТ ПО МОЕМУ XYЮ КАК ПО БЕГОВОЙ ДОРОЖКЕ СВОИМ ПИ3ДAKОМ БЕГАТЬ?)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ПИ3ДAKОМ ТВОЕЙ МАТЕРИ УЧЁНЫЕ ХОДЯТ И ВСАСЫВАЮ ИЗЛУЧАЕМОСТЬ В ЧЕРНОБЫЛЕ БЛЯДЬ?))",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ЧЕРЕЗ ПИ3ДAK ТВОЕЙ МАТЕРИ ПРОВЁЛ УСИЛИТЕЛЬ ДЛЯ ТОГО ЧТОБ У МЕНЯ МОДЕМ СУКА ЛУЧШЕ СЕТЬ ЛОВИЛ?)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО МЫ С ПАЦАНАМИ В ПИ3ДAKЕ ТВОЕЙ МАМАШИ В ГОЛЬФ НАXYЙ ИГРАЛИ, XYЯМИ МЯЧИКИ В ЛУНГУ ЗАГОНЯЛИ))",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ТВОЯ МАТЬ НА МОЁМ XYЮ ИЗВОДИТСЯ КАК ШЛЮХА БЛЯДЬ, Я ЕЁ ЕБАШУ В 3 СМЫЧКА С СИЛОЙ ТИТАНА))0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО ТВОЯ МАМАША СВОИМ ПИ3ДAKОМ У МЕНЯ ДОМА ВСЕ ШВАБРА НАXYЙ ПЕРЕЛОМАЛА, МОЙ XYЙ ТЕПЕРЬ ДЛЯ НЕЁ ПОДРУЧНОЕ СРЕДСТВО",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я СЕЙЧАС В ПИ3ДAKЕ ТВОЕЙ МАМАШИ НАЧНУ ДРОВА СВОИМ XYЁМ КОЛОТЬ?)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я XYЁМ ИСКОЛОЛ ПИ3ДAK ТВОЕЙ МАТЕРИ , И КРЧ ТЕПЕРЬ ТАМ 5 ДЫР, ОН ТЕПЕРЬ КАК ПЕРЧАТКА ДЛЯ РУКИ ПОДХОДИТ)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я СЕЙЧАС СВОИМ XYЁМ СЛОМАЮ НАXYЙ СЕНСОР В ПИ3ДAKЕ ТВОЕЙ МАТЕРИ, И ПОРВ ЕЁ ВЛАГАЛИЩЕ НАXYЙ)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я СВОИМ XYЁМ В ПИ3ДAKЕ ТВОЕЙ МАТЕРИ СТАЛ ПУСКАТЬ РАДИОВОЛНЫ, ПОСЛЕ ЧЕГО ЕЁ ГНИЛОЙ ПИ3ДAK РАЗВАЛИЛСЯ КАК СТАРЫЙ ЗАПОРОЖЕЦ))",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я ПИ3ДAKОМ ТВОЮ МАТЬ НАТЯНУЛ НАXYЙ НА ДВЕРЬ, ОНА ТЕПЕРЬ В ОБНИМКУ С ДВЕРЬЮ НА МОЁМ XYЮ СКАЧЕТ)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я СЕЙЧАС СВОИМ XYЁМ ЗАШЬЮ ПИ3ДAK ТВОЕЙ МАМАШИ КАК НАXYЙ ИГЛОЙ?)0",
            "!$player ТЫ ПОНИМАЕШЬ ЧТО Я СЕЙЧАС XYЁМ В ПИ3ДAKЕ ТВОЕЙ МАТЕРИ НА ЕЁ BAГИНАЛЬНЫХ СТЕНАХ НАРИСУЮ ПЛАН ОБСТРЕЛА ЕЁ ПИ3ДЫ))"
    };

    public KillSult() {
        super("KillSult", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        setSuffix(mode.getValueString());
    }

    @EventTarget
    public void onPacket(final EventPacketReceive event){
        if(event.getPacket() instanceof SPacketChat){
            switch(mode.getValueString()){
                case "WellMore":
                    if(((SPacketChat) event.getPacket()).getChatComponent().getFormattedText().contains("Вы выиграли бой!")){
                        //mc.player.sendChatMessage(sults[(int)(Math.random()*sults.length)].replaceAll("\\$player", KillAura.target.getName()));
                        mc.player.sendChatMessage(String.format("!%s, ezz", KillAura.target.getName()));
                    }
                    break;
            }
        }
    }
}
