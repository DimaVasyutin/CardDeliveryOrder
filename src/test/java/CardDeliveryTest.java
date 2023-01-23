import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidData() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима Ва-сютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div[@class='notification__title'][contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldValidDataWithDateMoreThreeDays() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div[@class='notification__title'][contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldDateTwentyNineLeapYear() {
        LocalDate today = LocalDate.now();
        for (int i = 1; i < 4; i++) {
            if (today.isLeapYear() == true) {
                break;

            }
            today = today.plusYears(i);
        }
        String date = "29" + "02" + today.getYear();

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div[@class='notification__title'][contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldValidDataYoNameCity() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Орёл");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div[@class='notification__title'][contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldCityNotAdminCenter() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Вязьма");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Доставка в выбранный город недоступна')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameLatin() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Moscow");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Доставка в выбранный город недоступна')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameSymbols() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("!!!!!");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Доставка в выбранный город недоступна')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameNumbers() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("12458");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Доставка в выбранный город недоступна')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameEmpty() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameSpaces() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("    ");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldCityNameTab() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +7);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("       ");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldDateLessThanThreeDays() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +1);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Заказ на выбранную дату невозможен')]").shouldBe(visible);

    }

    @Test
    void shouldDateInThePast() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, -2);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Заказ на выбранную дату невозможен')]").shouldBe(visible);

    }

    @Test
    void shouldDateNoExist() {
        Calendar calendar = new GregorianCalendar();
        String date = "35" + "12" + calendar.get(Calendar.YEAR);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateTwentyNineNoLeapYear() {
        LocalDate today = LocalDate.now();
        LocalDate noLeapDate = today;
        if (today.isLeapYear()) {
            noLeapDate = today.plusYears(1);
        }
        String date = "29" + "02" + noLeapDate.getYear();

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateSymbol() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue("@@@@@@@@");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateLetters() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue("fffфффф");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateEmpty() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue("");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateSpaces() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue("     ");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldDateTab() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue("       ");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Неверно введена дата')]").shouldBe(visible);

    }

    @Test
    void shouldNameLatin() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Dima");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").shouldBe(visible);

    }

    @Test
    void shouldNameSymbol() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("%^^^^");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").shouldBe(visible);

    }

    @Test
    void shouldNameNumbers() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("855244");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").shouldBe(visible);

    }

    @Test
    void shouldNameSpaces() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("     ");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldNameTab() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("         ");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldNameEmpty() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneLetters() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("аааdssdsd");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneSymbol() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("!!!!!!!!&&&");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneMoreElevenNumber() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+799999999999999999999999999");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneLessElevenNumber() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+79999");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneSpaces() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("    ");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneTab() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("            ");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldPhoneEmpty() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@class='input__sub'][contains(text(),'Поле обязательно для заполнения')]").shouldBe(visible);

    }

    @Test
    void shouldEmptyCheckBox() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(DAY_OF_MONTH, +3);
        Date datePlus = calendar.getTime();
        String date = format.format(datePlus);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+79999995555");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("label.input_invalid").shouldBe(visible);

    }


}
