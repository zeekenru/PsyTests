package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.LuscherResult
import com.kovapps.kovalev.psytests.enities.LusherColor
import kotlinx.android.synthetic.main.activity_luscher_result.*

class LuscherResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luscher_result)
        close_btn.setOnClickListener { finish() }
        val result: LuscherResult = intent.getParcelableExtra(RESULT_DATA_PARAM)
        val secondAnswers = result.answers
        val colorPositionValueText = StringBuilder()
        if (secondAnswers[0] == LusherColor.BlUE || secondAnswers[1] == LusherColor.BlUE) colorPositionValueText.append("Стремление к покою. Интенсивная потребность в приятном общении и удовлетворении, стремление к гармонии, чувстви-тельность.")
        if (secondAnswers[0] == LusherColor.GREEN || secondAnswers[1] == LusherColor.GREEN) colorPositionValueText.append("Напряжение воли. Самоутверждение, тщеславие, спонтанное желание играть определенную роль.")
        if (secondAnswers[0] == LusherColor.RED || secondAnswers[1] == LusherColor.RED) colorPositionValueText.append("Стремление к эмоциям. Активное участие и высокая активность.")
        if (secondAnswers[0] == LusherColor.YELLOW || secondAnswers[1] == LusherColor.YELLOW) colorPositionValueText.append("Восприятие возбуждения для разрядки напряжения. Ожидание встреч, раскрытие, суетливость, бегство от проблем, иллюзорное ожидание будущего.")
        if (secondAnswers[2] == LusherColor.BlUE || secondAnswers[3] == LusherColor.BlUE) colorPositionValueText.append("Готовность к покою без напряжения, приятным отношениям и удовлетво-рению.")
        if (secondAnswers[2] == LusherColor.GREEN || secondAnswers[3] == LusherColor.GREEN) colorPositionValueText.append("Само-определение, самообладание.")
        if (secondAnswers[2] == LusherColor.RED || secondAnswers[3] == LusherColor.RED) colorPositionValueText.append("Застой, досада, раздражаемость.")
        if (secondAnswers[2] == LusherColor.YELLOW || secondAnswers[3] == LusherColor.YELLOW) colorPositionValueText.append("Готовность к контактам.")
        if (secondAnswers[4] == LusherColor.BlUE || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append("Поверхностные связи и отношения.")
        if (secondAnswers[4] == LusherColor.GREEN || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append("Низкий уровень притязаний, пассивное отношение к социальному порядку.")
        if (secondAnswers[4] == LusherColor.RED || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append("Нервозная раздражимость, нуждается в бережном обращении, отсутствие желаний.")
        if (secondAnswers[4] == LusherColor.YELLOW || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append("Критическое отношение к выбору контактов и увлечений.")
        if (secondAnswers[6] == LusherColor.BlUE || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append("Беспокойство, суетливость, отсутствие глубоких «сердечных» связей, неудовлетво-ренность отношениями с партнером и своей деятельностью.")
        if (secondAnswers[6] == LusherColor.GREEN || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append("Ограничение само-выражения, защитное напряжение, отказано в признании. Окружающие воспринимаются как оказывающие жестокое, бессердечное давление, принуждающее делать не желаемое.")
        if (secondAnswers[6] == LusherColor.RED || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append("Сверх-раздражимость, ощущение своей слабости, чувство беспомощности. Обижен, с трудом справляется с делами. Утомлен и плохо ориентируется в окружающей обстановке.")
        if (secondAnswers[6] == LusherColor.YELLOW || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append("Беспокойное ожидание. Тематическая фиксация, ограничение самораскрытия. Скованность, пере-возбуждение.")

        if (secondAnswers[0] == LusherColor.GRAY || secondAnswers[1] == LusherColor.GRAY) colorPositionValueText.append("Отгора-живание, осторожная сдержанность, замкнутость, скрытность, социальная изоляция.")
        if (secondAnswers[0] == LusherColor.PURPLE || secondAnswers[1] == LusherColor.PURPLE) colorPositionValueText.append("Стремление очаровывать, чувственность, внушаемость.")
        if (secondAnswers[0] == LusherColor.BROWN || secondAnswers[1] == LusherColor.BROWN) colorPositionValueText.append("Регресс к физическим потребностям, бегство от проблем.")
        if (secondAnswers[0] == LusherColor.BLACK || secondAnswers[1] == LusherColor.BLACK) colorPositionValueText.append("Выражение протеста, негативизм, импульсивно-агрессивное поведение.")

        if (secondAnswers[2] == LusherColor.GRAY || secondAnswers[3] == LusherColor.GRAY) colorPositionValueText.append("Ограниченная эмоциональная готовность к контактам. Отключение.")
        if (secondAnswers[2] == LusherColor.PURPLE || secondAnswers[3] == LusherColor.PURPLE) colorPositionValueText.append("Чувственность.")
        if (secondAnswers[2] == LusherColor.BROWN || secondAnswers[3] == LusherColor.BROWN) colorPositionValueText.append("Потребность в комфорте и физическом удовлетворении.")
        if (secondAnswers[2] == LusherColor.BLACK || secondAnswers[3] == LusherColor.BLACK) colorPositionValueText.append("Протест и уход от партнера или ситуации")

        if (secondAnswers[4] == LusherColor.GRAY || secondAnswers[5] == LusherColor.GRAY) colorPositionValueText.append("Эмоциональная готовность к общению. Заинтере-сованность в социальных отношениях.")
        if (secondAnswers[4] == LusherColor.PURPLE || secondAnswers[5] == LusherColor.PURPLE) colorPositionValueText.append("Сдерживает свои чувства. Рефлексия чувств. Щепетильность. Чувстви-тельность и обидчивость.")
        if (secondAnswers[4] == LusherColor.BROWN || secondAnswers[5] == LusherColor.BROWN) colorPositionValueText.append("Разрядка физических потребностей.")
        if (secondAnswers[4] == LusherColor.BLACK || secondAnswers[5] == LusherColor.BLACK) colorPositionValueText.append("Способность терпеть ограничения, идти на компромиссы. Соглашается с условиями.")

        if (secondAnswers[6] == LusherColor.GRAY || secondAnswers[7] == LusherColor.GRAY) colorPositionValueText.append("Эмоциональная возбудимость, стремление к социальному успеху.")
        if (secondAnswers[6] == LusherColor.PURPLE || secondAnswers[7] == LusherColor.PURPLE) colorPositionValueText.append("Подавление чувстви-тельности, контроль чувств. Эстетическое, этическое или логическое стремление к порядку.")
        if (secondAnswers[6] == LusherColor.BROWN || secondAnswers[7] == LusherColor.BROWN) colorPositionValueText.append("Подавление, вытеснение или торможение физических потребностей.")
        if (secondAnswers[6] == LusherColor.BLACK || secondAnswers[7] == LusherColor.BLACK) colorPositionValueText.append("Отклонение помех и ограничений, игнорирование угрозы, предприим-чивость.")

        val firstGroup: String = when (secondAnswers[0]) {
            LusherColor.BlUE -> {
                when (secondAnswers[1]) {
                    LusherColor.GREEN -> "Чувство удовлетворенности, спокойствия, стремление к спокойной обстановке, нежелание участвовать в конфликтах, стрессе."
                    LusherColor.RED -> "Чувство целостности, активное и не всегда осознанное стремление к тесным отношениям. Потребность во внимании со стороны других."
                    LusherColor.PURPLE -> "Небольшое беспокойство, потребность в тонком окружении, стремление к эстетическому."
                    LusherColor.BROWN -> "Чувство беспокойства, страх одиночества, стремление уйти отконфликта, избежать стресса."
                    LusherColor.BLACK -> "Негативное состояние, стремление к покою, отдыху, неудовлетворенность отношением к себе, негативное отношение к ситуации."
                    LusherColor.GRAY -> "Негативное состояние, потребность освободиться от стресса, стремление к покою, отдыху"
                    else -> TODO()
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Позитивное состояние, стремление к признанию, к деятельности, обеспечивающей успех."
                    LusherColor.RED -> "Активное стремление к успеху, к самостоятельным решениям, преодолению преград в деятельности"
                    LusherColor.YELLOW -> "Небольшое беспокойство, стремление к признанию, популярности, желание произвести впечатление"
                    LusherColor.PURPLE -> "Небольшое беспокойство, стремление к признанию, популяр  ности, желание супервпечатлений, повышенное внимание к реакциям окружающих на свои поступки."
                    LusherColor.BROWN -> "Чувство неудовлетворенности, усталости, переоценка значимости отношения к себе со стороны окружающих."
                    LusherColor.BLACK -> "Чувство обиды, злости, стремление к жалости, авторитарности в отношениях."
                    LusherColor.GRAY -> "Чувство неудовлетворенности, стремление к признанию, желание произвести впечатление."
                    else -> TODO()
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Деловое возбуждение, активное стремление к деятельности, впечатлениям, удовольствиям."
                    LusherColor.GREEN -> "Деловое возбуждение, активное стремление к цели, преодоление всех трудностей, стремление к высокой оценке своей деятельности."
                    LusherColor.YELLOW -> "Деловое, слегка повышенное возбуждение, увлеченность, оптимизм, стремление к контактам, расширение сферы деятельности."
                    LusherColor.PURPLE -> "Повышенное возбуждение, не всегда адекватная увлеченность, стремление произвести впечатление."
                    LusherColor.BROWN -> "Негативное настроение, огорчение из за неудачи, нежелание лишиться благоприятной ситуации."
                    LusherColor.BLACK -> "Негативное настроение, злость, стремление уйти из неблагоприятной ситуации"
                    LusherColor.GRAY -> "Чувство неудовлетворенности, направленность на рискованное действие."
                    else -> TODO()
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Настроение в общем положительное, стремление к позитивному эмоциональному состоянию, взаимовыдержке."
                    LusherColor.GREEN -> "Настроение в общем положительное, желание поиска первых путей решения стоящих задач, стремление к самоутверждению."
                    LusherColor.RED -> "Несколько повышенное деловое возбуждение, стремление к широкой активности."
                    LusherColor.PURPLE -> "Небольшая эйфория, стремление к ярким событиям, желание произвести впечатление."
                    LusherColor.BROWN -> "Негативное настроение, огорчение и потребность в эмоциональной разрядке и отдыхе."
                    LusherColor.BLACK -> "Весьма негативное настроение, стремление уйти от любых проблем, склонность к необходимым, малоадекватным решениям."
                    LusherColor.GRAY -> "Негативное угнетенное стояние, стремление выйти из неприятной ситуации, четкое представление о том, как это сделать."
                    else -> TODO()
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Неопределенное настроение, стремление к согласию и гармонии."
                    LusherColor.GREEN -> "Настороженность, желание произвести впечатление"
                    LusherColor.RED -> "Некоторое возбуждение, увлеченность, активное стремление произвести впечатление."
                    LusherColor.YELLOW -> "Возбуждение, фантазирование, стремление к ярким событиям."
                    LusherColor.BROWN -> "Возбуждение, направленность на сильные эмоциональные переживания."
                    LusherColor.BLACK -> "Негативное состояние."
                    LusherColor.GRAY -> "Напряжение, стремление оградить себя от конфликтов, стресса."
                    else -> TODO()
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Напряжение, страх одиночества, желание уйти из неблагоприятной ситуации."
                    LusherColor.GREEN -> "Чувство беспокойства, стремление к строгому контролю над собой, чтобы избежать ошибки."
                    LusherColor.RED -> "Активное стремление к эмоциональной разрядке."
                    LusherColor.YELLOW -> "Утрата веры в положительные перспективы, вероятность необдуманных решений («мне все равно»)."
                    LusherColor.PURPLE -> "Чувство неудовлетворенности, стремление к комфорту."
                    LusherColor.BLACK -> "Негативное состояние, разочарованность, стремление к покою, желание уйти от активности."
                    LusherColor.GRAY -> "Весьма негативное состояние, стремление уйти от сложных проблем, а не бороться с ними."
                    else -> TODO()
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Весьма негативное состояние, стремление уйти от проблем («оставили бы в покое».)"
                    LusherColor.GREEN -> "Возбуждение, гневное отношение к окружающим, не всегда адекватное упрямство."
                    LusherColor.RED -> "Сильное возбуждение, возможны аффективные поступки."
                    LusherColor.YELLOW -> "Весьма негативное состояние, отчаяние, суицидные мысли."
                    LusherColor.PURPLE -> "Напряженность, мечты о гармонии."
                    LusherColor.BROWN -> "Возбуждение, постановка нереальных задач, стремление уйти от беспокойных мыслей, неблагоприятных ситуаций."
                    LusherColor.GRAY -> "Чувство безнадежности, обреченности, стремление сопротивляться всему, неадекватность."
                    else -> TODO()
                }
            }
            LusherColor.GRAY -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> "Негативное состояние, желание спокойной ситуации."
                    LusherColor.GREEN -> "Негативное состояние, ощущение враждебности окружающих и желание оградиться от среды."
                    LusherColor.RED -> "Негативное состояние, возвышенные требования к окружающим, не всегда адекватная активность."
                    LusherColor.YELLOW -> "Негативное состояние, стремление уйти от проблем, а не решать их."
                    LusherColor.PURPLE -> "Чувство беспокойства и настороженности, стремление скрыть это чувство."
                    LusherColor.BROWN -> "Весьма негативное стояние, стремление уйти от всего сложного, трудного, от волнения."
                    LusherColor.BLACK -> "Весьма негативное состояние, обида, чувство угнетенности, вероятность неадекватных решений."
                    else -> TODO()
                }
            }
        }

        val thirdGroup: String = when (secondAnswers[2]) {
            LusherColor.GRAY -> {
                when (secondAnswers[3]) {
                    LusherColor.BlUE -> "Относительно пассивен, находится в статичном состоянии. Не удовлетворена потребность во взаимной привязанности."
                    LusherColor.GREEN -> "Ситуация сложная, но он упорно пытается достичь своей цели несмотря на сопротивление."
                    LusherColor.RED -> "При осуществлении своих побуждений наталкивается на препятствия и трудности. Из-за напряжения и конфликтов обидчив, быстро раздражается, выходит из себя."
                    LusherColor.YELLOW -> "Неоправдавшиеся надежды и неумение определить, какие меры предпринять, вызвали значительный стресс."
                    LusherColor.PURPLE -> "Конфликтное напряжение мешает установлению полной взаимной близости."
                    LusherColor.BROWN -> "Из-за конфликтных напряжений чувствует себя утомленным и нуждается в отдыхе."
                    LusherColor.BLACK -> "Находится в состоянии стресса, т.е. не может выполнить требования, предъявляемые к нему сложившимися обстоятельствами. Считает себя вынужденным, по крайней мере, на время отказаться от притязаний, чтобы избежать дальнейших напряжении."
                    else -> TODO()
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY-> "Стремится решительно отстаивать свои притязания, несмотря на противодействие"
                    LusherColor.BlUE-> "Стремится к спокойной и прочной уверенности. Нуждается в уважении, признании и понимании со стороны близких людей"
                    LusherColor.RED -> "Чувствует, что ему мешают в проявлении инициативы, однако твердо придерживается притязаний, ищет путей и средств для осуществления своих намерений"
                    LusherColor.YELLOW -> "Внимательно контролирует события, в которых принимает участие. Расходится во взглядах и вырабатывает свое собственное мнение."
                    LusherColor.PURPLE -> "Хотел бы, чтобы другие люди понимали его желания и потребности и проявляли уступчивость. Умеет ловко оправдываться."
                    LusherColor.BROWN -> "Пытается создать крепкое основание для спокойного, безопасного, свободного от проблем будущего, в котором он получит уважение и признание."
                    LusherColor.BLACK -> "С бескомпромиссной настойчивостью отстаивает свои притязания и требования."
                    else -> TODO()
                }
            }
            LusherColor.BlUE -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> "Готовность к установлению мирных отношений с целью снятия напряжения. Нуждается в покое, который он может обрести в обществе близкого человека"
                    LusherColor.GREEN -> "Стремится к установлению спокойных и прочных отношений, чтобы чувствовать себя признанным, ценным и любимым"
                    LusherColor.RED -> "Ощущает помехи, препятствия в осуществлении своих желаний. Хочет освободиться от накипевшего гнева, снять напряжение и обрести успокоение в сердечном единении"
                    LusherColor.YELLOW -> "Придает большое значение своим сердечным переживаниям, поэтому собственное хорошее самочувствие зависит от гармонии отношений"
                    LusherColor.PURPLE -> "Чувствителен, нуждается в таком же чувствительном и понимающем партнере. Готов к полной взаимопонимания и чуткости близости"
                    LusherColor.BROWN -> "Нуждается в защите и опеке. Избегает прилагать какие-либо чрезмерные усилия. Испытывает потребность в безопасности, спокойном и дружеском общении"
                    LusherColor.BLACK -> "Нуждается в покое и теплых товарищеских отношениях Однако требует от партнера предупредительного и уважительного отношения к себе. Склонен прекращать общение, если не может рассчитывать на желаемое отношение к себе"
                    else -> TODO()
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> "Быстро раздражается из-за напряжения, однако пытается избегать конфликтов"
                    LusherColor.BlUE -> "Чувствует, что ему мешают осуществить желания Стремится освободиться от накопленной досады, снять напряжение и найти успокоение в сердечной связи"
                    LusherColor.GREEN -> "Проявляет инициативу в преодолении трудностей. Хочет занимать положение, в котором он был бы облечен властью и мог управлять ходом событий"
                    LusherColor.YELLOW -> "Чувствует, что ему мешают активно проявлять себя, т.к. его желания остаются неосуществленными."
                    LusherColor.PURPLE -> "Сильно реагирует на возбуждающие чувственные раздражители."
                    LusherColor.BROWN -> "Не желает затрачивать чрезмерные усилия Умеренный комфорт и безопасность предпочитает тем благам, которые сулит большое честолюбие."
                    LusherColor.BLACK -> "Раздражен торможением побуждающих импульсов. С отвращением замыкается на себе, если его потребности остаются неудовлетворенными."
                    else -> TODO()
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> "Неудовлетворен. Испытывает потребность в устранении существующих конфликтов и трудностей. Ищет выход из сложившейся ситуации."
                    LusherColor.GREEN -> "С бескомпромиссной настойчивостью требует и добивается выполнения своих притязаний. Если его притязания не удовлетворяются, с отвращением порывает отношения."
                    LusherColor.RED -> "Считает, что ему чинят преграды и мешают получить то, что он считает для себя важным. Неудовлетворен и с отвращением порывает отношения."
                    LusherColor.YELLOW -> "Резко порывает отношения, если его большие надежды должным образом не оправдываются."
                    LusherColor.PURPLE -> "Стремится к отношениям, основанным на чуткости и взаимопонимании. Отказывается от отношений, которые не приносят полной утонченной близости."
                    LusherColor.BROWN -> "Физический недуг, перенапряжение приняли серьезную форму. Сильно разочарован в связи и отвергает ее. Нуждается в заботе, бережном отношении."
                    LusherColor.BlUE -> "Нуждается для собственного спокойствия в верной связи. Однако предъявляет высокие 1ребования в отношении партнера. Если отношения не соответствуют требуемым, резко их порывает."
                    else -> TODO()
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> "Чувствует себя утомленным от конфликтов. Нуждается в отдыхе, ощущении безопасности и физическом чувственном удовлетворении."
                    LusherColor.GREEN -> "Стремится утвердиться вопреки существующим препятствиям. С трудом отвечает предъявляемым к нему требованиям. Трудности воспринимает как изнуряющие и надеется избежать дальнейших неприятностей, хочет, чтобы ситуация стала легче. Нуждается в ощущении защищенности."
                    LusherColor.RED -> "Хотел бы заторможенные импульсы компенсировать с помощью чувственного удовлетворения. Так как дальнейшие успехи связаны для него с трудностями, стремится к более спокойной обстановке, в которой он избежал бы волнений."
                    LusherColor.YELLOW -> "Стремится к бесконфликтному удовлетворению своих желаний и чувственным, наслаждениям."
                    LusherColor.PURPLE -> "х6 х5 Чувствителен. Стремится к острым ощущениям и чувственному наслаждению. Отвергает все безвкусное, пошлое, грубое."
                    LusherColor.BlUE -> "Нуждается в ощущении безопасности и спокойном дружеском общении. Может находиться в плохом физическом состоянии и нуждаться в мягком обращении и внимательной помощи."
                    LusherColor.BLACK -> " Физический недуг, перенапряжение приняли серьезную форму. Самоуважение понизилось. Нуждается в заботе, бережном отношении, удовлетворении своих желаний."
                    else -> TODO()
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[3]) {
                    LusherColor.BlUE -> "Придает большое значение сердечным чувствам. По этой причине собственное благополучие зависит от гармонии отношений."
                    LusherColor.GREEN -> "Внимательно контролирует события, в которых принимает участие Не соглашается с мнением других и вырабатывает свое собственное мнение."
                    LusherColor.RED -> "Так как ожидания остаются неосуществленными, считает, что ему мешают активно проявлять себя. Из-за этого раздражителен и недоволен."
                    LusherColor.GRAY -> "Надеется найти способ освободиться от угнетающего напряжения."
                    LusherColor.PURPLE -> "Мечтает о необременительном, чувственно-возбуждающем контакте."
                    LusherColor.BROWN -> "Стремится к бесконфликтному удовлетворению своих потребностей и чувственному наслаждению."
                    LusherColor.BLACK -> "Требует и ждет с нетерпением исполнения своих желаний и надежд."
                    else -> TODO()
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> "Из-за конфликтного напряжения ощущает повышенную потребность в потной взаимопонимания и чуткости близости."
                    LusherColor.GREEN -> "Ведет себя тактично, искусно, настаивает на выполнении своих притязаний, но сам не готов идти навстречу пожеланиям других."
                    LusherColor.RED -> "Сильно реагирует на чувственно-возбуждающие стимулы."
                    LusherColor.YELLOW -> "Ищет необременительный, возбуждающий чувства контакт."
                    LusherColor.BlUE -> "Испытывает потребность в сердечной связи и полной взаимопонимания и чуткости близости."
                    LusherColor.BROWN -> "Стремится к острым ощущениям, чувственному удовлетворению."
                    LusherColor.BLACK -> "Нуждается в отношениях, полных близости и понимания. Отказывается от связи без полной, утонченной близости."
                    else -> TODO()
                }
            }
        }

        val secondGroup: String = when (secondAnswers[6]) {
            LusherColor.GRAY -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Чувство неудовлетворенности, эмоциональной напряженности"
                    LusherColor.GREEN -> "Эмоциональная напряженность, желание выйти из неблагоприятной ситуации."
                    LusherColor.RED -> "Раздраженность, чувство беспомощности."
                    LusherColor.YELLOW -> "Тревожность, неуверенность в своих силах"
                    LusherColor.PURPLE -> "Небольшое контролируемое возбуждение"
                    LusherColor.BROWN -> "Тревожность, неуверенность в своих силах, но при этом завышенная требовательность, желание достичь признания своей личности"
                    LusherColor.BLACK -> "Отрицание каких-либо ограничений своей личности, активное стремление к деятельности."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.BlUE -> {
                when (secondAnswers[7]) {
                    LusherColor.GRAY -> "Несколько угнетенное состояние, тревожность, ощущение бесперспективности."
                    LusherColor.GREEN -> "Сильное напряжение, стремление избавиться от негативного стрессового состояния."
                    LusherColor.RED -> "Сильное напряжение, чувство беспомощности, желание выйти из эмоциональной ситуации."
                    LusherColor.YELLOW -> "Состояние, близкое к стрессу, эмоциональные негативные переживания, чувство беспомощности."
                    LusherColor.PURPLE -> "Состояние, близкое к стрессу, сложность взаимоотношений, чувство ограниченности в возможностях, нетерпеливость."
                    LusherColor.BROWN -> "Эмоциональная неудовлетворенность, самоограничение, поиск поддержки."
                    LusherColor.BLACK -> "Состояние, близкое к стрессу, эмоциональная неудовлетворенность, стремление уйти из психогенной ситуации."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Угнетенное состояние неверие в свои силы, стремление уйти из неприятной ситуации."
                    LusherColor.GRAY -> "Состояние фрустрации, раздраженность из-за ряда неудач, снижение волевых качеств."
                    LusherColor.RED -> "Сильное возбуждение, тягостные переживания, отношения со средой считает для себя враждебными, возможны аффективные поступки."
                    LusherColor.YELLOW -> "Состояние, близкое к фрустрации, чувство разочарования, нерешительность."
                    LusherColor.PURPLE -> "Состояние, близкое к стрессовому, чувство оскорбленного достоинства, неверие в свои силы."
                    LusherColor.BROWN -> "Состояние, близкое к стрессовому, неадекватно повышенный самоконтроль необоснованное стремление к признанию."
                    LusherColor.BLACK -> "Состояние фрустрации за ограничения амбициозных требований, недостаточная целеустремленность."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Подавляемое возбуждение, раздражительность, нетерпеливость, поиск выхода из негативных отношений, сложившихся с близкими людьми."
                    LusherColor.GREEN -> "Состояние стресса из-за неадекватной самооценки."
                    LusherColor.GRAY -> "Сдерживаемое возбуждение, чувство утрачиваемой перспективы, вероятность нервного истощения."
                    LusherColor.YELLOW -> "Мнительность, тревожность, неадекватная оценка среды, стремление к самооправданию."
                    LusherColor.PURPLE -> "Состояние стресса из-за за неудачных попыток достичь взаимопонимания, чувство неуверенности, беспомощности, желание сочувствия."
                    LusherColor.BROWN -> "Сильное напряжение, вызванное иногда сексуальным самоограничением, отсутствие дружеских контактов, неуверенность в своих силах."
                    LusherColor.BLACK -> "Состояние стресса из-за глубокого разочарования, фрустрация, чувство тревожности, бессилия решить конфликтную проблему, желание выйти из фрустрирующей ситуации любым путем, сомнение в том, что это удастся."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Чувство разочарования, состояние, близкое к стрессу, стремление подавить негативные эмоции."
                    LusherColor.GREEN -> "Состояние нерешительности, тревожности, разочарования."
                    LusherColor.RED -> "Состояние стресса, сопровождаемое возбуждением, сомнения в успехе, претензии, не подкрепляемые реальными возможностями, самооправдание."
                    LusherColor.GRAY, LusherColor.PURPLE -> "Эмоциональное разочарование и чувство беспокойства, тревоги, разочарования."
                    LusherColor.BROWN, LusherColor.BLACK -> "Напряженность, чувство неуверенности, настороженности, стремление избежать контроля извне."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Чувство неудовлетворенности, стимулирующее к активности, стремление к сотрудничеству."
                    LusherColor.GREEN -> "Стрессовые состояния из-за неосуществившегося самоутверждения."
                    LusherColor.RED -> "Стрессовое состоя из-за неудач в активных, иногда необдуманных действ!"
                    LusherColor.YELLOW -> "Настороженность, подозрительность, разочарование, замкнутость."
                    LusherColor.GRAY -> "Проявление нетерпения, но в то же время стремление к самоконтролю, вызывает некоторое эмоциональное возбуждение."
                    LusherColor.BROWN -> "Стресс, вызванный нарушением желательных взаимоотношений, повышенная взыскательность к другим."
                    LusherColor.BLACK -> "Напряжение из-за ограничения в самостоятельных решениях, стремление к взаимопониманию, откровенному выражению мыслей."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Негативное состояние, чувство неудовлетворенности из-за недостаточного признания заслуг (реальных и предполагаемых), стремление к самоограничению и самоконтролю."
                    LusherColor.GREEN -> "Негативное состояние из-за чрезмерного самоконтроля, упрямое желание выделиться, сомнения в том, что это удастся."
                    LusherColor.RED -> "Стрессовое состояние из-за подавленности эротических и других биологических потребностей, стремление к сотрудничеству для выхода из стресса."
                    LusherColor.YELLOW -> "Напряженность стремления скрыть тревогу под маской уверенности и беспечности."
                    LusherColor.PURPLE -> "Негативное состояние из-за неудовлетворенного стремления к чувствственной гармонии."
                    LusherColor.GRAY -> "Стрессовое состояние из-за подавления биологических, сексуальных потребностей"
                    LusherColor.BLACK -> "Стремление уйти из подчинения, негативное отношение к различным запретам."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> "Состояние беспокойства в связи со скрываемым желанием получить помощь, поддержку."
                    LusherColor.GREEN -> "Состояние, близкое к фрустрации из-за ограничения свободы желаемых действий, стремление избавиться от помех."
                    LusherColor.RED -> "Стрессовое состояние, вызванное разочарованием в ожидаемой ситуации, эмоциональное возбуждение."
                    LusherColor.YELLOW -> "Стрессовое состояние из-за боязни дальнейших неудач, отказ от разумных компромиссов."
                    LusherColor.PURPLE -> "Поиски идеализированной ситуации."
                    LusherColor.BROWN -> "Стрессовое состояние из-за неприятных ограничений, запретов, стремление сопротивляться ограничениям, уйти от заурядности."
                    LusherColor.BLACK -> "Стремление уйти из неблагоприятной ситуации."
                    else -> throw IllegalArgumentException("Unexpected color")
                }
            }

        }
        first_scale_result.text = firstGroup
        second_scale_result.text = secondGroup
        third_scale_result.text = colorPositionValueText.toString().trim()
        scale_4_result.text = thirdGroup
        info_btn.setOnClickListener {
            val intent = Intent(this, TestDescriptionActivity::class.java)
                    .putExtra(TestDescriptionActivity.TEST_ID_PARAM, result.id)
            startActivity(intent)
        }
        share_btn.setOnClickListener {
            val shareIntentText = "Пройден цветовой тест Люшера в приложении '${getString(R.string.app_name)}': " +
                    "${first_scale_result.text.toString().toLowerCase()}. ${second_scale_result.text}. ${third_scale_result.text}. ${scale_4_result.text}"
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareIntentText)
            }
            startActivity(shareIntent)
        }
    }
}
